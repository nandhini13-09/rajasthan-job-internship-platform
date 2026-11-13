package in.rajasthan.jobs.repo;
import in.rajasthan.jobs.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByApprovedTrueOrderByIdDesc();
    List<Job> findByPostedByOrderByIdDesc(Long postedBy);
    List<Job> findByApprovedFalseOrderByIdDesc();
}
