package in.rajasthan.jobs.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "jobs")
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String title;
    @Column(length=3000) private String description;
    private String requiredSkills;
    private String location;
    private String jobType; // JOB or INTERNSHIP
    private Double minimumCgpa;
    private String companyName;
    private Long postedBy;
    private boolean approved = true;

    public Job() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getTitle(){return title;} public void setTitle(String title){this.title=title;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public String getRequiredSkills(){return requiredSkills;} public void setRequiredSkills(String requiredSkills){this.requiredSkills=requiredSkills;}
    public String getLocation(){return location;} public void setLocation(String location){this.location=location;}
    public String getJobType(){return jobType;} public void setJobType(String jobType){this.jobType=jobType;}
    public Double getMinimumCgpa(){return minimumCgpa;} public void setMinimumCgpa(Double minimumCgpa){this.minimumCgpa=minimumCgpa;}
    public String getCompanyName(){return companyName;} public void setCompanyName(String companyName){this.companyName=companyName;}
    public Long getPostedBy(){return postedBy;} public void setPostedBy(Long postedBy){this.postedBy=postedBy;}
    public boolean isApproved(){return approved;} public void setApproved(boolean approved){this.approved=approved;}
}
