package tss.information;

import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CompositeKeys implements Serializable {
    @NonNull
    String uid;
    @NonNull
    String cid;

    CompositeKeys() {
    }

    CompositeKeys(String uid, String cid) {
        this.uid = uid;
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof CompositeKeys)) {
            return false;
        }
        CompositeKeys tmp = (CompositeKeys) other;

        return uid.equals(tmp.uid) & cid.equals(tmp.cid);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (uid == null ? 0 : this.uid.hashCode());
        result = 37 * result + (cid == null ? 0 : this.cid.hashCode());
        return result;
    }
}
