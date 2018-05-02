package tss.interceptors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tss.TssApplication;
import tss.entities.AuthorityEntity;
import tss.entities.RoleEntity;
import tss.entities.UserEntity;
import tss.repositories.AuthorityRepository;
import tss.repositories.RoleRepository;
import tss.repositories.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
        String uid = bundle.getString("spring.datasource.username");
        String pwd = bundle.getString("spring.datasource.password");
        String ddl_auto = bundle.getString("spring.jpa.hibernate.ddl-auto");
        if(!userRepository.existsById(uid)) {
            UserEntity user = new UserEntity();
            user.setUid(uid);
            user.setName("Root");
            String salt = getSalt();
            String hashedPassword = getHashedPasswordByPasswordAndSalt(pwd, salt);
            user.setSalt(salt);
            user.setHashedPassword(hashedPassword);
            user.setType(UserEntity.TYPE_MANAGER);
            userRepository.save(user);
        }
        if(ddl_auto.equals("create")) {
            initRole();
        }
    }

    private void initRole() {
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
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName(roleName);
            roleRepository.save(roleEntity);
            JSONObject role = (JSONObject)roleList.get(roleName);
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
    }

}
