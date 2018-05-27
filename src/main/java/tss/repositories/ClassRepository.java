package tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tss.entities.ClassEntity;
import tss.entities.CourseEntity;
import tss.entities.SemesterEnum;

import java.util.List;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity> findByCourse_NameAndYearAndSemester(String name, Integer year, SemesterEnum semester);

}
