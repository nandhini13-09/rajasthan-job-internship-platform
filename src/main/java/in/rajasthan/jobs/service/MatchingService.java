package in.rajasthan.jobs.service;

import in.rajasthan.jobs.model.Job;
import in.rajasthan.jobs.model.User;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MatchingService {
    // Simple transparent matching: percentage of required skills found in student's skills.
    public int score(User student, Job job) {
        Set<String> studentSkills = split(student.getSkills());
        Set<String> required = split(job.getRequiredSkills());
        if (required.isEmpty()) return 0;
        long matched = required.stream().filter(studentSkills::contains).count();
        return (int)Math.round(matched * 100.0 / required.size());
    }
    private Set<String> split(String value) {
        if (value == null || value.isBlank()) return Collections.emptySet();
        Set<String> result = new HashSet<>();
        for (String s : value.split(",")) result.add(s.trim().toLowerCase(Locale.ROOT));
        return result;
    }
}
