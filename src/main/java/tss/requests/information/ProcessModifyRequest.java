package tss.requests.information;

import java.util.List;

public class ProcessModifyRequest {
    private List<String> uids;
    private List<Long> cids;
    private List<Integer> score;
    private List<Boolean> process;

    public void setUids(List<String> uids) {
        this.uids = uids;
    }

    public void setCids(List<Long> cids) {
        this.cids = cids;
    }

    public void setProcess(List<Boolean> process) {
        this.process = process;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    public List<Integer> getScore() {

        return score;
    }

    public List<String> getUids() {

        return uids;
    }

    public List<Long> getCids() {
        return cids;
    }

    public List<Boolean> getProcess() {
        return process;
    }
}
