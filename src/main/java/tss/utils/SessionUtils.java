package tss.utils;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author yzy
 */
public class SessionUtils {
    public static @NonNls
    @NotNull String getToken() {
        return UUID.randomUUID().toString();
    }
}
