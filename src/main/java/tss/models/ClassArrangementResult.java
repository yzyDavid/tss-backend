package tss.models;

import java.util.List;

/**
 * @author reeve
 */
public class ClassArrangementResult {

    private Integer numArrangedClasses;
    private List<Long> pendingClassIds;

    public ClassArrangementResult() {
    }

    public ClassArrangementResult(Integer numArrangedClasses, List<Long> pendingClassIds) {
        this.numArrangedClasses = numArrangedClasses;
        this.pendingClassIds = pendingClassIds;
    }

    public Integer getNumArrangedClasses() {
        return numArrangedClasses;
    }

    public void setNumArrangedClasses(Integer numArrangedClasses) {
        this.numArrangedClasses = numArrangedClasses;
    }

    public List<Long> getPendingClassIds() {
        return pendingClassIds;
    }

    public void setPendingClassIds(List<Long> pendingClassIds) {
        this.pendingClassIds = pendingClassIds;
    }
}
