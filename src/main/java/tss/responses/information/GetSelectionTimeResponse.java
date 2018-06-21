package tss.responses.information;

import tss.entities.SelectionTimeEntity;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GetSelectionTimeResponse {

    private final List<SelectionTimeInfo> timeList;

    class SelectionTimeInfo {
        private Long id;
        private String start;
        private String end;
        private Boolean register; // registrationPermissive
        private Boolean drop; // dropPerm
        private Boolean complement; // complementPermissive

        SelectionTimeInfo(SelectionTimeEntity selectionTimeEntity) {
            id = selectionTimeEntity.getId();
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            start = sdf.format(selectionTimeEntity.getStart());
            end = sdf.format(selectionTimeEntity.getEnd());
            register = selectionTimeEntity.isRegister();
            drop = selectionTimeEntity.isDrop();
            complement = selectionTimeEntity.isComplement();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public Boolean getRegister() {
            return register;
        }

        public void setRegister(Boolean register) {
            this.register = register;
        }

        public Boolean getDrop() {
            return drop;
        }

        public void setDrop(Boolean drop) {
            this.drop = drop;
        }

        public Boolean getComplement() {
            return complement;
        }

        public void setComplement(Boolean complement) {
            this.complement = complement;
        }
    }

    public GetSelectionTimeResponse(List<SelectionTimeEntity> timeList) {
        this.timeList = new ArrayList<>();
        for (SelectionTimeEntity selectionTimeEntity : timeList) {
            SelectionTimeInfo selectionTimeInfo = new SelectionTimeInfo(selectionTimeEntity);
            this.timeList.add(selectionTimeInfo);
        }
    }

    public List<SelectionTimeInfo> getTimeList() {
        return timeList;
    }
}
