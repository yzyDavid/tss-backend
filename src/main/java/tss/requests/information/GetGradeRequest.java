

package tss.requests.information;

public class GetGradeRequest {

    public enum QueryType {SID,PID, QTYPE, QUNIT};
    private QueryType type;
    private String sid;
    private String pid;
    private String qtype;
    private String qunit;



    public QueryType getType() {
        return type;
    }
    public void setType(QueryType type) {
        this.type = type;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQtype() {
        return qtype;
    }
    public void setQtype(String qType) {
        this.qtype = qType;
    }

    public String getQunit() {
        return qunit;
    }
    public void setQunit(String qUnit) {
        this.qunit = qUnit;
    }
}