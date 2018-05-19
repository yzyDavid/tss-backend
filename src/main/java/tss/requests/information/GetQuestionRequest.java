package tss.requests.information;

/**
 * 和前端不一样，前端：direction:string, info:string
 */
public class GetQuestionRequest {
    public String direction;
    public String info;


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
