package tss.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.configs.Config;
import tss.entities.*;
import tss.repositories.*;
import tss.services.QueryService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tss.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static tss.utils.SecurityUtils.getSalt;

/**
 * @author Mingqi Yi
 */
@Controller
@RequestMapping(path = "/init")
public class InitController {
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
    public InitController(UserRepository userRepository, RoleRepository roleRepository, AuthorityRepository authorityRepository, TypeGroupRepository typeGroupRepository, QueryService queryService, DepartmentRepository departmentRepository, MajorRepository majorRepository, MajorClassRepository majorClassRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.typeGroupRepository = typeGroupRepository;
        this.queryService = queryService;
        this.departmentRepository = departmentRepository;
        this.majorRepository = majorRepository;
        this.majorClassRepository = majorClassRepository;
    }

    @GetMapping
    public ResponseEntity run() throws Exception {
        initRole();

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
        return new ResponseEntity(HttpStatus.OK);
    }

    private void generateMajorClass() {
        Random rand = new Random(1000);
        int year = 2015;
        short id = 1;
        Iterable<MajorEntity> majors = majorRepository.findAll();
        for (MajorEntity major : majors) {
            for (int i = 1; i <= rand.nextInt(2) + 1; i++) {
                MajorClassEntity majorClass = new MajorClassEntity();
                majorClass.setId(id);
                majorClass.setName(major.getName().substring(0, major.getName().length() - 1) + "150" + Integer.toString(i));
                majorClass.setMajor(major);
                majorClass.setYear(year);
                majorClassRepository.save(majorClass);
                id++;
            }
        }

    }

    private void generateUser() {
        TypeGroupEntity student = typeGroupRepository.findByName(Config.TYPES[3]).get();
        TypeGroupEntity teacher = typeGroupRepository.findByName(Config.TYPES[2]).get();
        TypeGroupEntity teachAdmin = typeGroupRepository.findByName(Config.TYPES[1]).get();
        String[] gender = {"男", "女"};
        String[] name1 = {"赵", "钱", "孙", "李", "张", "章", "陈", "程", "孟", "王", "朱", "黄", "孙", "董", "潘", "吴", "杨", "陆", "沈", "徐"};
        String[] name3 = {"蓉", "天", "翔", "健", "慧", "栋", "梅", "安", "明", "强", "昊", "凯", "贤", "光", "鸿", "鹏", "甜", "豪", "芳", "佳"};
        String[] name2 = {"丽", "天", "一", "展", "启", "博", "国", "欣", "广", "立", "高", "宏", "子", "宁", "力", "园", "雅", "宇", "思", "嘉"};
        Random rand1 = new Random(1000);
        Random rand2 = new Random(2000);
        Random rand3 = new Random(3000);
        Random rand = new Random(2);
        Random randm = new Random(5000);
        Random length = new Random(4000);
        Iterable<MajorClassEntity> majorClassEntities = majorClassRepository.findAll();
        Map<Short, MajorClassEntity> majorClassEntityMap = new HashMap<>();
        for (MajorClassEntity majorClass : majorClassEntities) {
            majorClassEntityMap.put(majorClass.getId(), majorClass);
        }
        for (int i = 1000; i < 2000; i++) {
            UserEntity user = new UserEntity();
            user.setUid("315010" + Integer.toString(i));
            if (length.nextInt(30) >= 25) {
                user.setName(name1[rand1.nextInt(20)] + "  " + name3[rand3.nextInt(20)]);
            } else {
                user.setName(name1[rand1.nextInt(20)] + name2[rand2.nextInt(20)] + name3[rand3.nextInt(20)]);
            }
            String salt = getSalt();
            String hashedPassword = getHashedPasswordByPasswordAndSalt(Config.INIT_PWD, salt);
            user.setSalt(salt);
            user.setHashedPassword(hashedPassword);
            user.setType(student);
            user.setGender(gender[rand.nextInt(2)]);
            MajorClassEntity majorClass = majorClassEntityMap.get((short) (randm.nextInt(majorClassEntityMap.size()) + 1));
            DepartmentEntity department = majorClass.getMajor().getDepartment();
            departmentRepository.save(department);
            user.setMajorClass(majorClass);
            user.setDepartment(department);
            user.setYear(2015);
            userRepository.save(user);
        }

        for (int i = 1000; i < 1050; i++) {
            UserEntity user = new UserEntity();
            user.setUid("200000" + Integer.toString(i));
            if (length.nextInt(30) >= 25) {
                user.setName(name1[rand1.nextInt(20)] + "  " + name3[rand3.nextInt(20)]);
            } else {
                user.setName(name1[rand1.nextInt(20)] + name2[rand2.nextInt(20)] + name3[rand3.nextInt(20)]);
            }
            String salt = getSalt();
            String hashedPassword = getHashedPasswordByPasswordAndSalt(Config.INIT_PWD, salt);
            user.setSalt(salt);
            user.setHashedPassword(hashedPassword);
            user.setType(teacher);
            user.setGender(gender[rand.nextInt(2)]);
            userRepository.save(user);
        }

        for (int i = 1; i < 10; i++) {
            UserEntity user = new UserEntity();
            user.setUid("100000000" + Integer.toString(i));
            if (length.nextInt(30) >= 25) {
                user.setName(name1[rand1.nextInt(20)] + "  " + name3[rand3.nextInt(20)]);
            } else {
                user.setName(name1[rand1.nextInt(20)] + name2[rand2.nextInt(20)] + name3[rand3.nextInt(20)]);
            }
            String salt = getSalt();
            String hashedPassword = getHashedPasswordByPasswordAndSalt(Config.INIT_PWD, salt);
            user.setSalt(salt);
            user.setHashedPassword(hashedPassword);
            user.setType(teachAdmin);
            user.setGender(gender[rand.nextInt(2)]);
            userRepository.save(user);
        }


    }


    private void initRole() {
        if (typeGroupRepository.findByName("Teacher").isPresent()) {
            return;
        }
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
