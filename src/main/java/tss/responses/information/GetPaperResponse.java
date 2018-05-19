package tss.responses.information;

import org.jetbrains.annotations.Nls;
import tss.entities.PapersEntity;

import java.util.List;

public class GetPaperResponse {
    @Nls
    public final String status;
    public final List<PapersEntity>paperlist;        //和QuestionEntity不一样

    public GetPaperResponse(String status, List<PapersEntity> paperlist){
        this.status = status;
        this.paperlist = paperlist;
    }

    public String getStatus() {return status;}

    public List<PapersEntity> getPaperlist() {return paperlist; }
}
