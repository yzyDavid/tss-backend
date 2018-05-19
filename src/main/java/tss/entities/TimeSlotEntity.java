package tss.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mingqi Yi
 */
@Entity
@Table(name = "time_slot")
public class TimeSlotEntity {
    private Short id;
    private String day;
    private int start;
    private int end;
    private Set<SectionEntity> sections = new HashSet();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Column(name = "day", length = 4, nullable = false)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Column(name = "start_time", nullable = false)
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Column(name = "end_time", nullable = false)
    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "timeSlot")
    public Set<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(Set<SectionEntity> sections) {
        this.sections = sections;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        } else {
            return id.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass()) || id == null) {
            return false;
        } else {
            return (id.equals(((TimeSlotEntity) obj).id));
        }
    }
}
