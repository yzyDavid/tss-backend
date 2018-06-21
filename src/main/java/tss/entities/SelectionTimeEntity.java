package tss.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "selection_time", indexes = {
        @Index(name = "selection_time_index", columnList = "id")
})
public class SelectionTimeEntity {
    private Long id;
    private Timestamp start;
    private Timestamp end;
    private Boolean register; // registrationPermissive
    private Boolean drop; // dropPerm
    //private boolean lowcredit; // lowCreditRegistrationPermissive
    private Boolean complement; // complementPermissive


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    @Column(name = "drop_perm", nullable = false)
    public boolean isDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }


    @Column(name = "register_perm", nullable = false)
    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }


    @Column(name = "complement_perm", nullable = false)
    public boolean isComplement() {
        return complement;
    }

    public void setComplement(boolean complement) {
        this.complement = complement;
    }
}