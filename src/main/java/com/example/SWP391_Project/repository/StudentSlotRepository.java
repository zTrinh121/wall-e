        package com.example.SWP391_Project.repository;

        import com.example.SWP391_Project.model.StudentSlot;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;

        import java.time.LocalDate;
        import java.util.Date;
        import java.util.List;
        import java.util.Optional;

        public interface StudentSlotRepository extends JpaRepository<StudentSlot, Integer> {

                Optional<StudentSlot> findByStudentIdAndSlotId(int studentId, int slotId);

                @Query("SELECT ss FROM StudentSlot ss JOIN ss.slot sl WHERE sl.course.id = :courseId AND sl.slotDate = :slotDate")
                List<StudentSlot> findByCourseIdAndDate(@Param("courseId") int courseId, @Param("slotDate") Date slotDate);



        }
