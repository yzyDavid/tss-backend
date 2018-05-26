

package tss.requests.information;

public class GetGradeRequest {

    public enum QueryType {SID,PID, QTYPE, QUNIT};
    private QueryType Type;
    private String Sid;
    private String Pid;
    private String QType;
    private String QUnit;



    public QueryType getType() {
        return Type;
    }
    public void setType(QueryType type) {
        Type = type;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getPid() {
        return Pid;
    }
    public void setPid(String pid) {
        Pid = pid;
    }
    public String getQType() {
        return QType;
    }
    public void setQType(String qType) {
        QType = qType;
    }
    public String getQUnit() {
        return QUnit;
    }
    public void setQUnit(String qUnit) {
        QUnit = qUnit;
    }
}
