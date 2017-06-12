/*
 */
package wad.jobs;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Miska Janhunen
 */
public interface JobRepository extends JpaRepository<Job, Long> {

}
