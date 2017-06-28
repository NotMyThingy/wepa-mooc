package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
