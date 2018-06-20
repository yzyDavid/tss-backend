package tss.information;

public class QuestionExamResponseStruct {
    private String qid;
    private String question;
    private String qtype;
    private String qunit;
    private String myanswer;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }


    public String getQunit() {
        return qunit;
    }

    public void setQunit(String qunit) {
        this.qunit = qunit;
    }


    public String getMyanswer() {
        return myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }

}
