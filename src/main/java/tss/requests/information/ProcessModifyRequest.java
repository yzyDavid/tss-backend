package tss.requests.information;

import java.util.List;

public class ProcessModifyRequest {
    private String uids;
    private Long cids;
    private Integer score;
    private Boolean process;

    public String getUids() {
        return uids;
    }

    public Long getCids() {
        return cids;
    }

    public Integer getScore() {
        return score;
    }

    public Boolean getProcess() {
        return process;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public void setCids(Long cids) {
        this.cids = cids;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setProcess(Boolean process) {
        this.process = process;
    }
}
