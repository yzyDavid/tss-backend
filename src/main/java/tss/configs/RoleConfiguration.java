package tss.configs;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tss.entities.AuthorityEntity;
import tss.entities.RoleEntity;
import tss.entities.TypeGroupEntity;
import tss.entities.UserEntity;
import tss.repositories.*;
import tss.services.QueryService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tss.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static tss.utils.SecurityUtils.getSalt;

/**
 * @author Mingqi Yi
 */
@Component
public class RoleConfiguration implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final TypeGroupRepository typeGroupRepository;
    private final QueryService queryService;
    private final DepartmentRepository departmentRepository;
    private final MajorRepository majorRepository;
    private final MajorClassRepository majorClassRepository;
    private static Pattern pattern = Pattern.compile("\\{.*}");

    @Autowired
    public RoleConfiguration(UserRepository userRepository, RoleRepository roleRepository, AuthorityRepository authorityRepository, TypeGroupRepository typeGroupRepository, QueryService queryService, DepartmentRepository departmentRepository, MajorRepository majorRepository, MajorClassRepository majorClassRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.typeGroupRepository = typeGroupRepository;
        this.queryService = queryService;
        this.departmentRepository = departmentRepository;
        this.majorRepository = majorRepository;
        this.majorClassRepository = majorClassRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String ddlAuto = bundle.getString("spring.jpa.hibernate.ddl-auto");
        if ("create".equals(ddlAuto)) {
            initRole();
        }

        String uid = "0000000000";
        String pwd = Config.INIT_PWD;
        if (!userRepository.existsById(uid)) {
            UserEntity user = new UserEntity();
            user.setUid(uid);
            user.setName("Root");
            String salt = getSalt();
            String hashedPassword = getHashedPasswordByPasswordAndSalt(pwd, salt);
            user.setSalt(salt);
            user.setHashedPassword(hashedPassword);
            Optional<TypeGroupEntity> typeGroup = typeGroupRepository.findByName(Config.TYPES[0]);
            typeGroup.ifPresent(user::setType);
            userRepository.save(user);
        }
    }

    private void initRole() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("role.json");
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String json = new String(buffer);
            String uriFormat = "/%s/%s";
            JSONObject roleList = new JSONObject(json);

            String[] types = Config.TYPES;
            TypeGroupEntity[] typeGroups = new TypeGroupEntity[types.length];
            for (int i = 0; i < types.length; i++) {
                typeGroups[i] = new TypeGroupEntity();
                typeGroups[i].setName(types[i]);
                typeGroupRepository.save(typeGroups[i]);
            }

            for (String roleName : roleList.keySet()) {
                JSONObject role = (JSONObject) roleList.get(roleName);
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setName(roleName);
                roleRepository.save(roleEntity);
                String prefix = role.getString("prefix");
                JSONArray paths = role.getJSONArray("paths");
                for (int i = 0; i < paths.length(); i++) {
                    String path = paths.getString(i);
                    Matcher matcher = pattern.matcher(path);
                    if (matcher.find()) {
                        path = path.substring(0, matcher.start());
                    }
                    String uri = String.format(uriFormat, prefix, path);
                    AuthorityEntity authority = new AuthorityEntity();
                    authority.setUri(uri);
                    roleEntity.addAuthority(authority);
                    authorityRepository.save(authority);
                }
                JSONArray belongs = role.getJSONArray("belong2");
                for (int i = 0; i < belongs.length(); i++) {
                    roleEntity.addTypeGroup(typeGroups[belongs.getInt(i)]);
                }
                roleRepository.save(roleEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception ignored) {

            }
        }
    }

}
