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
import tss.repositories.AuthorityRepository;
import tss.repositories.RoleRepository;
import tss.repositories.TypeGroupRepository;
import tss.repositories.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.ResourceBundle;

import static tss.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static tss.utils.SecurityUtils.getSalt;

@Component
public class RoleConfiguration implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private TypeGroupRepository typeGroupRepository;

    @Override
    public void run(String... args) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String ddl_auto = bundle.getString("spring.jpa.hibernate.ddl-auto");
        if (ddl_auto.equals("create")) {
            initRole();
        }

        String uid = bundle.getString("spring.datasource.username");
        String pwd = bundle.getString("spring.datasource.password");
        if (!userRepository.existsById(uid)) {
            UserEntity user = new UserEntity();
            user.setUid(uid);
            user.setName("Root");
            String salt = getSalt();
            String hashedPassword = getHashedPasswordByPasswordAndSalt(pwd, salt);
            user.setSalt(salt);
            user.setHashedPassword(hashedPassword);
            Optional<TypeGroupEntity> typeGroup = typeGroupRepository.findByName(Config.TYPES[0]);
            if (typeGroup.isPresent()) {
                user.setTypeGroup(typeGroup.get());
            }
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
                    String uri = String.format(uriFormat, prefix, paths.getString(i));
                    AuthorityEntity authority = new AuthorityEntity();
                    authority.setUri(uri);
                    roleEntity.addAuthority(authority);
                    authorityRepository.save(authority);
                }
                JSONArray belongs = role.getJSONArray("belong2");
                for (int i = 0; i < belongs.length(); i++) {
                    roleEntity.setTypeGroup(typeGroups[belongs.getInt(i)]);
                }
                roleRepository.save(roleEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e1) {

            }
        }
    }

}
