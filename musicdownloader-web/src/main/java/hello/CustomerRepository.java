package hello;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<AudioTrackProgress, Long> {

	List<AudioTrackProgress> findByLastNameStartsWithIgnoreCase(String lastName);
}
