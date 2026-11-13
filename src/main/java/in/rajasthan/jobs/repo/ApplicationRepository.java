package in.rajasthan.jobs.repo;
import in.rajasthan.jobs.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ApplicationRepository extends JpaRepository<JobApplication,Long> {
    List<JobApplication> findByStudentIdOrderByAppliedAtDesc(Long studentId);
    List<JobApplication> findByJobId(Long jobId);
    boolean existsByJobIdAndStudentId(Long jobId, Long studentId);
}
