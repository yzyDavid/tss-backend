[1mdiff --git a/build.gradle b/build.gradle[m
[1mindex 047197a..952a40a 100644[m
[1m--- a/build.gradle[m
[1m+++ b/build.gradle[m
[36m@@ -31,13 +31,10 @@[m [mdependencies {[m
     compile 'org.springframework.boot:spring-boot-starter-web'[m
     compile 'org.springframework.boot:spring-boot-starter-data-jpa'[m
     compile 'org.springframework:spring-context:5.0.4.RELEASE'[m
[31m-    compile 'org.springframework.session:spring-session-core:2.0.2.RELEASE'[m
[31m-    compile 'org.springframework.session:spring-session-jdbc:2.0.2.RELEASE'[m
     compile 'mysql:mysql-connector-java'[m
     // https://mvnrepository.com/artifact/org.jetbrains/annotations[m
     compile group: 'org.jetbrains', name: 'annotations', version: '16.0.1'[m
 [m
[31m-[m
     testCompile group: 'junit', name: 'junit', version: '4.12'[m
     testCompile 'org.springframework.boot:spring-boot-starter-test'[m
 }[m
[1mdiff --git a/src/main/java/tss/configs/CorsConfiguration.java b/src/main/java/tss/configs/CorsConfiguration.java[m
[1mindex 340f6a9..8243a59 100644[m
[1m--- a/src/main/java/tss/configs/CorsConfiguration.java[m
[1m+++ b/src/main/java/tss/configs/CorsConfiguration.java[m
[36m@@ -1,12 +1,14 @@[m
 package tss.configs;[m
 [m
 import org.springframework.context.annotation.Bean;[m
[32m+[m[32mimport org.springframework.context.annotation.Configuration;[m
 import org.springframework.web.servlet.config.annotation.CorsRegistry;[m
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;[m
 [m
 /**[m
  * @author yzy[m
  */[m
[32m+[m[32m@Configuration[m
 public class CorsConfiguration {[m
     @Bean[m
     public WebMvcConfigurer corsConfigurer() {[m
[1mdiff --git a/src/main/java/tss/information/SecurityUtils.java b/src/main/java/tss/information/SecurityUtils.java[m
[1mindex 4c7958a..4f1ac29 100644[m
[1m--- a/src/main/java/tss/information/SecurityUtils.java[m
[1m+++ b/src/main/java/tss/information/SecurityUtils.java[m
[36m@@ -22,7 +22,9 @@[m [mpublic class SecurityUtils {[m
         return new BASE64Encoder().encode(salt);[m
     }[m
 [m
[31m-    public static @Nullable String getHashedPasswordByPasswordAndSalt(@NotNull String password, @NotNull @NonNls String salt) {[m
[32m+[m[32m    public static @Nullable[m
[32m+[m[32m    @NonNls[m
[32m+[m[32m    String getHashedPasswordByPasswordAndSalt(@NotNull String password, @NotNull @NonNls String salt) {[m
         String plain = salt + password;[m
         MessageDigest messageDigest;[m
         String cipher = null;[m
[1mdiff --git a/src/main/java/tss/information/UserEntity.java b/src/main/java/tss/information/UserEntity.java[m
[1mindex 5d24311..bb4c33e 100644[m
[1m--- a/src/main/java/tss/information/UserEntity.java[m
[1m+++ b/src/main/java/tss/information/UserEntity.java[m
[36m@@ -18,6 +18,10 @@[m [mpublic class UserEntity {[m
 [m
     private String name;[m
 [m
[32m+[m[32m    private String hashedPassword;[m
[32m+[m
[32m+[m[32m    private String salt;[m
[32m+[m
     public String getName() {[m
         return name;[m
     }[m
[36m@@ -41,4 +45,20 @@[m [mpublic class UserEntity {[m
     public void setId(Long id) {[m
         this.id = id;[m
     }[m
[32m+[m
[32m+[m[32m    public String getHashedPassword() {[m
[32m+[m[32m        return hashedPassword;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setHashedPassword(String hashedPassword) {[m
[32m+[m[32m        this.hashedPassword = hashedPassword;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public String getSalt() {[m
[32m+[m[32m        return salt;[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    public void setSalt(String salt) {[m
[32m+[m[32m        this.salt = salt;[m
[32m+[m[32m    }[m
 }[m
[1mdiff --git a/src/main/java/tss/information/UserRepository.java b/src/main/java/tss/information/UserRepository.java[m
[1mindex 5987862..2575a61 100644[m
[1m--- a/src/main/java/tss/information/UserRepository.java[m
[1m+++ b/src/main/java/tss/information/UserRepository.java[m
[36m@@ -12,4 +12,6 @@[m [mpublic interface UserRepository extends CrudRepository<UserEntity, Long> {[m
      * check exists of a UID[m
      */[m
     boolean existsByUid(String uid);[m
[32m+[m
[32m+[m[32m    UserEntity findByUid(String uid);[m
 }[m
[1mdiff --git a/src/main/java/tss/session/SessionController.java b/src/main/java/tss/session/SessionController.java[m
[1mindex 56e38f1..91904e8 100644[m
[1m--- a/src/main/java/tss/session/SessionController.java[m
[1m+++ b/src/main/java/tss/session/SessionController.java[m
[36m@@ -19,12 +19,12 @@[m [mimport tss.information.UserRepository;[m
 public class SessionController {[m
     private final UserRepository userRepository;[m
 [m
[31m-    private final SessionRepository sessionRepository;[m
[32m+[m[32m    private final SqlSessionRepository sqlSessionRepository;[m
 [m
     @Autowired[m
[31m-    public SessionController(UserRepository userRepository, SessionRepository sessionRepository) {[m
[32m+[m[32m    public SessionController(UserRepository userRepository, SqlSessionRepository sqlSessionRepository) {[m
         this.userRepository = userRepository;[m
[31m-        this.sessionRepository = sessionRepository;[m
[32m+[m[32m        this.sqlSessionRepository = sqlSessionRepository;[m
     }[m
 [m
     @PostMapping(path = "/login")[m
[1mdiff --git a/src/main/java/tss/session/SqlSessionRepository.java b/src/main/java/tss/session/SqlSessionRepository.java[m
[1mindex f662c09..5791a82 100644[m
[1m--- a/src/main/java/tss/session/SqlSessionRepository.java[m
[1m+++ b/src/main/java/tss/session/SqlSessionRepository.java[m
[36m@@ -5,5 +5,5 @@[m [mimport org.springframework.data.repository.CrudRepository;[m
 /**[m
  * @author yzy[m
  */[m
[31m-public interface SessionRepository extends CrudRepository<SessionEntity, Long> {[m
[32m+[m[32mpublic interface SqlSessionRepository extends CrudRepository<SessionEntity, Long> {[m
 }[m
