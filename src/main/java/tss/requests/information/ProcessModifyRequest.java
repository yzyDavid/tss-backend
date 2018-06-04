package tss.requests.information;

import java.util.List;

public class ProcessModifyRequest {
    private Long id;
    private String uids;
    private Long cids;
    private Integer score;
    private String agree;

    public ProcessModifyRequest() {
    }

    public ProcessModifyRequest(Long id, String uids, Long cids, Integer score, String agree) {
        this.id = id;
        this.uids = uids;
        this.cids = cids;
        this.score = score;
        this.agree = agree;
    }

    public String getUids() {
        return uids;
    }

    public Long getCids() {
        return cids;
    }

    public Integer getScore() {
        return score;
    }

    public String getAgree() {
        return agree;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCids(Long cids) {
        this.cids = cids;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }
}
