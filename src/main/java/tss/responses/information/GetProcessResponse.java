package tss.responses.information;

import java.util.ArrayList;
import java.util.List;

public class GetProcessResponse {
    private String status;
    private List<Long> ids;
    private List<String> reasons;
    private List<Integer> scores;
    private List<String> uids;
    private List<Long> cids;

    public GetProcessResponse(String status) {
        this.status = status;
        this.cids = null;
        this.ids = null;
        this.scores = null;
        this.reasons = null;
        this.uids = null;
    }

    public GetProcessResponse(String status, List<Long> ids, List<String> reasons, List<Integer> scores, List<String> uids, List<Long> cids) {
        this.status = status;
        this.ids = ids;
        this.reasons = reasons;
        this.scores = scores;
        this.uids = uids;
        this.cids = cids;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public List<String> getUids() {
        return uids;
    }

    public List<Long> getCids() {
        return cids;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public void setUids(List<String> uids) {
        this.uids = uids;
    }

    public void setCids(List<Long> cids) {
        this.cids = cids;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
