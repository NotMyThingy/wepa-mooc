package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
