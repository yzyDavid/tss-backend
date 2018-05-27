package tss.models;

import tss.entities.ClassEntity;

import java.util.ArrayList;
import java.util.List;

public class ClazzList {
    private List<Clazz> clazzes;

    public ClazzList() {
        clazzes = new ArrayList<>();
    }

    public ClazzList(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }

    public List<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(List<Clazz> clazzes) {
        this.clazzes = clazzes;
    }

}
