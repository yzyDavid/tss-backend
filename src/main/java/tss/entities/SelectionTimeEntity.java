package tss.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "selection_time")
public class SelectionTimeEntity {
    private int id;
    private Timestamp start;
    private Timestamp end;
    private boolean register; // registrationPermissive
    private boolean drop; // dropPerm
    //private boolean lowcredit; // lowCreditRegistrationPermissive
    private boolean complement; // complementPermissive



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "start_time", nullable = false)
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }



    @Column(name = "end_time", nullable = false)
    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }


    @Column(name = "drop", nullable = false)
    public boolean isDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }


    @Column(name = "register", nullable = false)
    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }


    @Column(name = "complement", nullable = false)
    public boolean isComplement() {
        return complement;
    }

    public void setComplement(boolean complement) {
        this.complement = complement;
    }
}