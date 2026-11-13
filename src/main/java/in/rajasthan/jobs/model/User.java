package in.rajasthan.jobs.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private String role; // STUDENT, COMPANY, ADMIN
    private String college;
    private String department;
    private String skills;
    private Double cgpa;
    private String companyName;

    public User() {}
    public User(String name, String email, String password, String role) {
        this.name=name; this.email=email; this.password=password; this.role=role;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getPassword(){return password;} public void setPassword(String password){this.password=password;}
    public String getRole(){return role;} public void setRole(String role){this.role=role;}
    public String getCollege(){return college;} public void setCollege(String college){this.college=college;}
    public String getDepartment(){return department;} public void setDepartment(String department){this.department=department;}
    public String getSkills(){return skills;} public void setSkills(String skills){this.skills=skills;}
    public Double getCgpa(){return cgpa;} public void setCgpa(Double cgpa){this.cgpa=cgpa;}
    public String getCompanyName(){return companyName;} public void setCompanyName(String companyName){this.companyName=companyName;}
}
