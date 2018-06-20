package tss.configs;

/**
 * @author yzy
 */
public class Config {
    public static final String FRONTEND_URL = "";
    public static final String AUTH_HEADER = "X-Auth-Token";
    public static final String CURRENT_UID_ATTRIBUTE = "Current-UID";
    public static final String USER_RESOURCE_DIR = "./src/main/resources/static/user/";
    public static final String USER_PHOTO_DIR_PATTERN = USER_RESOURCE_DIR + "%s/photo.jpg";
    public static final String INIT_PWD = "123456";
    public static final String[] TYPES = {"System Administrator", "Teaching Administrator", "Teacher", "Student"};
    public static final long TOKEN_EXPIRE_TIME = 3600000;
}
