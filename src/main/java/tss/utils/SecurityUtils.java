package tss.utils;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author yzy
 */
public class SecurityUtils {
    public static @NotNull
    @NonNls
    String getSalt() {
        Random rand = new SecureRandom();
        byte[] salt = new byte[16];
        rand.nextBytes(salt);
        return new BASE64Encoder().encode(salt);
    }

    public static @Nullable
    @NonNls
    String getHashedPasswordByPasswordAndSalt(@NotNull String password, @NotNull @NonNls String salt) {
        String plain = salt + password;
        MessageDigest messageDigest;
        String cipher = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(plain.getBytes("UTF-8"));
            cipher = new BASE64Encoder().encode(messageDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cipher;
    }
}
