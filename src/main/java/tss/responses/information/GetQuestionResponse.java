package tss.responses.information;

import org.jetbrains.annotations.Nls;
import tss.entities.QuestionEntity;

import java.util.List;

public class GetQuestionResponse {
    @Nls
    public final String status;
    public final List<QuestionEntity> questions;

    public GetQuestionResponse(String status, List<QuestionEntity> questions){
        this.status = status;
        this.questions = questions;
    }

    public String getStatus() {return status;}

    public List<QuestionEntity> getQuestions() {return questions;}
}
