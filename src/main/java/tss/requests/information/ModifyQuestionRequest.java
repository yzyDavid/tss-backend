package tss.requests.information;

public class ModifyQuestionRequest {
    public String qid;
    public String question;
    public String qanswer;
    public String qtype;
    public String qunit;

    public String getQid() {return qid;}

    public void setQid(String qid){this.qid = qid;}

    public String getQuestion(){return question;}

    public void setQuestion(String question){this.question = question; }

    public String getQanswer(){return qanswer;}

    public void setQanswer(String qanswer){this.qanswer = qanswer;}

    public String getQtype(){return qtype;}

    public void setQtype(String qtype){this.qtype = qtype;}

    public String getQunit() {return qunit;}

    public void setQunit(String qunit) {this.qunit = qunit;}
}
