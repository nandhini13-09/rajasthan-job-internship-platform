package in.rajasthan.jobs.controller;

import in.rajasthan.jobs.model.*;
import in.rajasthan.jobs.repo.*;
import in.rajasthan.jobs.service.MatchingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class JobController {
    private final JobRepository jobs; private final UserRepository users; private final ApplicationRepository applications;
    private final MatchingService matching;
    public JobController(JobRepository jobs, UserRepository users, ApplicationRepository applications, MatchingService matching) {
        this.jobs=jobs; this.users=users; this.applications=applications; this.matching=matching;
    }
    private Long uid(HttpSession s){return (Long)s.getAttribute("userId");}
    private String role(HttpSession s){return (String)s.getAttribute("role");}

    @GetMapping("/jobs") public String list(@RequestParam(required=false) String q, @RequestParam(required=false) String type,
        Model model, HttpSession session) {
        List<Job> all=jobs.findByApprovedTrueOrderByIdDesc();
        if(q!=null && !q.isBlank()) all.removeIf(j -> !(j.getTitle()+" "+j.getCompanyName()+" "+j.getRequiredSkills()+" "+j.getLocation()).toLowerCase().contains(q.toLowerCase()));
        if(type!=null && !type.isBlank()) all.removeIf(j -> !type.equalsIgnoreCase(j.getJobType()));
        model.addAttribute("jobs",all); model.addAttribute("q",q); model.addAttribute("type",type);
        model.addAttribute("role",role(session)); return "jobs";
    }
    @GetMapping("/dashboard") public String dashboard(Model model,HttpSession session) {
        if(uid(session)==null) return "redirect:/login";
        String r=role(session);
        if("COMPANY".equals(r)) model.addAttribute("myJobs",jobs.findByPostedByOrderByIdDesc(uid(session)));
        if("STUDENT".equals(r)) {
            User u=users.findById(uid(session)).orElse(null);
            List<Job> recommended=new ArrayList<>(jobs.findByApprovedTrueOrderByIdDesc());
            recommended.sort((a,b)->Integer.compare(matching.score(u,b),matching.score(u,a)));
            model.addAttribute("recommended",recommended);
            model.addAttribute("matching",matching); model.addAttribute("student",u);
            model.addAttribute("myApplications",applications.findByStudentIdOrderByAppliedAtDesc(uid(session)));
        }
        if("ADMIN".equals(r)) {
            model.addAttribute("pendingJobs",jobs.findByApprovedFalseOrderByIdDesc());
            model.addAttribute("allJobs",jobs.findAll());
            model.addAttribute("users",users.findAll());
        }
        model.addAttribute("role",r); model.addAttribute("name",session.getAttribute("userName"));
        return "dashboard";
    }
    @GetMapping("/company/job/new") public String newJob(HttpSession s){return "COMPANY".equals(role(s))?"job-form":"redirect:/login";}
    @PostMapping("/company/job/new") public String createJob(@RequestParam String title,@RequestParam String description,
        @RequestParam String requiredSkills,@RequestParam String location,@RequestParam String jobType,
        @RequestParam(required=false) Double minimumCgpa,HttpSession s) {
        if(!"COMPANY".equals(role(s))) return "redirect:/login";
        User company=users.findById(uid(s)).orElseThrow();
        Job j=new Job(); j.setTitle(title); j.setDescription(description); j.setRequiredSkills(requiredSkills);
        j.setLocation(location); j.setJobType(jobType); j.setMinimumCgpa(minimumCgpa);
        j.setCompanyName(company.getCompanyName()==null?company.getName():company.getCompanyName());
        j.setPostedBy(uid(s)); j.setApproved(true); jobs.save(j);
        return "redirect:/dashboard";
    }
    @PostMapping("/jobs/{id}/apply") public String apply(@PathVariable Long id,HttpSession s,Model model) {
        if(!"STUDENT".equals(role(s))) return "redirect:/login";
        Job j=jobs.findById(id).orElseThrow();
        if(!j.isApproved()) return "redirect:/jobs";
        if(applications.existsByJobIdAndStudentId(id,uid(s))) return "redirect:/dashboard";
        User student=users.findById(uid(s)).orElseThrow();
        if(j.getMinimumCgpa()!=null && (student.getCgpa()==null || student.getCgpa()<j.getMinimumCgpa())) return "redirect:/jobs?error=eligibility";
        JobApplication a=new JobApplication(); a.setJobId(id); a.setStudentId(uid(s)); applications.save(a);
        return "redirect:/dashboard";
    }
    @PostMapping("/applications/{id}/status") public String updateStatus(@PathVariable Long id,@RequestParam String status,HttpSession s) {
        if(!"COMPANY".equals(role(s))) return "redirect:/login";
        JobApplication a=applications.findById(id).orElseThrow();
        Job j=jobs.findById(a.getJobId()).orElseThrow();
        if(!Objects.equals(j.getPostedBy(),uid(s))) return "redirect:/dashboard";
        if(List.of("SHORTLISTED","REJECTED","SELECTED").contains(status)) {a.setStatus(status);applications.save(a);}
        return "redirect:/dashboard";
    }
    @GetMapping("/company/job/{id}/applicants") public String applicants(@PathVariable Long id,Model model,HttpSession s) {
        if(!"COMPANY".equals(role(s))) return "redirect:/login";
        Job j=jobs.findById(id).orElseThrow();
        if(!Objects.equals(j.getPostedBy(),uid(s))) return "redirect:/dashboard";
        List<Map<String,Object>> rows=new ArrayList<>();
        for(JobApplication a:applications.findByJobId(id)) {
            User u=users.findById(a.getStudentId()).orElse(null);
            if(u!=null){Map<String,Object> row=new HashMap<>();row.put("app",a);row.put("student",u);rows.add(row);}
        }
        model.addAttribute("job",j);model.addAttribute("rows",rows);return "applicants";
    }
    @PostMapping("/admin/jobs/{id}/approve") public String approve(@PathVariable Long id,HttpSession s) {
        if(!"ADMIN".equals(role(s))) return "redirect:/login";
        Job j=jobs.findById(id).orElseThrow();j.setApproved(true);jobs.save(j);return "redirect:/dashboard";
    }
}
