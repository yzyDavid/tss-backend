package tss.interceptors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tss.entities.AuthorityEntity;
import tss.entities.RoleEntity;
import tss.entities.UserEntity;
import tss.repositories.AuthorityRepository;
import tss.repositories.RoleRepository;
import tss.repositories.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.ResourceBundle;

import static tss.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static tss.utils.SecurityUtils.getSalt;

@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String ddl_auto = bundle.getString("spring.jpa.hibernate.ddl-auto");
        initRole(ddl_auto.equals("create"));

        String uid = bundle.getString("spring.datasource.username");
        String pwd = bundle.getString("spring.datasource.password");
        if(!userRepository.existsById(uid)) {
            UserEntity user = new UserEntity();
            user.setUid(uid);
            user.setName("Root");
            String salt = getSalt();
            String hashedPassword = getHashedPasswordByPasswordAndSalt(pwd, salt);
            user.setSalt(salt);
            user.setHashedPassword(hashedPassword);
            user.setType(UserEntity.TYPE_SYS_ADMIN);
            for (String name : UserEntity.ROLE_ALLOC.get(user.getType())) {
                Optional<RoleEntity> role = roleRepository.findByName(name);
                if (role.isPresent()) {
                    user.addRole(role.get());
                }
            }
            userRepository.save(user);
        }
    }

    private void initRole(boolean isCreate) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("role.json");
        String json = "{}";
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            json = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String uriFormat = "/%s/%s";
        JSONObject roleList = new JSONObject(json);
        for(String roleName : roleList.keySet()) {
            JSONObject role = (JSONObject)roleList.get(roleName);
            if(isCreate) {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setName(roleName);
                roleRepository.save(roleEntity);
                String prefix = role.getString("prefix");
                JSONArray paths = role.getJSONArray("paths");
                for(int i = 0 ; i < paths.length(); i++) {
                    String uri = String.format(uriFormat, prefix, paths.getString(i));
                    AuthorityEntity authority = new AuthorityEntity();
                    authority.setUri(uri);
                    roleEntity.addAuthority(authority);
                    authorityRepository.save(authority);
                }
                roleRepository.save(roleEntity);
            }
            JSONArray belongs = role.getJSONArray("belong2");
            for(int i = 0 ; i < belongs.length(); i++) {
                int type = belongs.getInt(i);
                UserEntity.ROLE_ALLOC.get(type).add(roleName);
            }
        }
    }

}
