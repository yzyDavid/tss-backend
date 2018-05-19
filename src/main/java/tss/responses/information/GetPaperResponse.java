package tss.responses.information;

import org.jetbrains.annotations.Nls;
import tss.entities.PapersEntity;
import tss.information.PaperResponseStruct;

import java.util.List;

public class GetPaperResponse {
    @Nls
    public final String status;
    public final List<PaperResponseStruct>paperlist;        //和QuestionEntity不一样

    public GetPaperResponse(String status, List<PaperResponseStruct> paperlist){
        this.status = status;
        this.paperlist = paperlist;
    }

    public String getStatus() {return status;}

    public List<PaperResponseStruct> getPaperlist() {return paperlist; }
}
