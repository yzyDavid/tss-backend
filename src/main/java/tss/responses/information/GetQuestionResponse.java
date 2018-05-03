package tss.responses.information;

import org.jetbrains.annotations.Nls;
import tss.entities.QuestionResponseEntity;

import java.util.List;

public class GetQuestionResponse {
    @Nls
    public final String status;
    public final List<QuestionResponseEntity> questions;         //和QuestionEntity不一样

    public GetQuestionResponse(String status, List<QuestionResponseEntity> questions){
        this.status = status;
        this.questions = questions;
    }

    public String getStatus() {return status;}

    public List<QuestionResponseEntity> getQuestions() {return questions;}
}
