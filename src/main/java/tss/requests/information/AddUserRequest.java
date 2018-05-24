package tss.requests.information;

import org.jetbrains.annotations.NotNull;

/**
 * @author yzy
 */
public class AddUserRequest {
    @NotNull
    private String[] uids;
    @NotNull
    private String[] names;
    @NotNull
    private String[] passwords;
    @NotNull
    private String[] genders;
    @NotNull
    private String type;

    public String[] getUids() {
        return uids;
    }

    public void setUids(String[] uids) {
        this.uids = uids;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String[] getGenders() {
        return genders;
    }

    public void setGenders(String[] genders) {
        this.genders = genders;
    }

    public String[] getPasswords() {
        return passwords;
    }

    public void setPasswords(String[] passwords) {
        this.passwords = passwords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
