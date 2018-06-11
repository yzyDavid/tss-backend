package tss.models;

import java.util.List;

/**
 * @author reeve
 */
public class AutoArrangementResult {
    private Integer numFinished;
    private List<Clazz> unfinishedClasses;

    public AutoArrangementResult() {
    }

    public AutoArrangementResult(Integer numFinished, List<Clazz> unfinishedClasses) {
        this.numFinished = numFinished;
        this.unfinishedClasses = unfinishedClasses;
    }

    public Integer getNumFinished() {
        return numFinished;
    }

    public void setNumFinished(Integer numFinished) {
        this.numFinished = numFinished;
    }

    public List<Clazz> getUnfinishedClasses() {
        return unfinishedClasses;
    }

    public void setUnfinishedClasses(List<Clazz> unfinishedClasses) {
        this.unfinishedClasses = unfinishedClasses;
    }
}
