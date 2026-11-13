package in.rajasthan.jobs.controller;

import in.rajasthan.jobs.model.User;
import in.rajasthan.jobs.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserRepository users;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public AuthController(UserRepository users){this.users=users;}

    @GetMapping("/") public String home(Model model, HttpSession session) {
        model.addAttribute("jobs", java.util.List.of());
        return "redirect:/jobs";
    }
    @GetMapping("/register") public String registerPage(){return "register";}
    @PostMapping("/register") public String register(@RequestParam String name, @RequestParam String email,
        @RequestParam String password, @RequestParam String role, @RequestParam(required=false) String college,
        @RequestParam(required=false) String department, @RequestParam(required=false) String skills,
        @RequestParam(required=false) Double cgpa, @RequestParam(required=false) String companyName, Model model) {
        if (users.findByEmail(email).isPresent()) {model.addAttribute("error","Email already registered."); return "register";}
        if (!role.equals("STUDENT") && !role.equals("COMPANY")) {model.addAttribute("error","Invalid account type."); return "register";}
        User u=new User(name,email,encoder.encode(password),role);
        u.setCollege(college); u.setDepartment(department); u.setSkills(skills); u.setCgpa(cgpa); u.setCompanyName(companyName);
        users.save(u);
        model.addAttribute("message","Registration successful. Please log in.");
        return "login";
    }
    @GetMapping("/login") public String loginPage(){return "login";}
    @PostMapping("/login") public String login(@RequestParam String email,@RequestParam String password,
        HttpSession session, Model model) {
        User u=users.findByEmail(email).orElse(null);
        if(u==null || !encoder.matches(password,u.getPassword())) {
            model.addAttribute("error","Invalid email or password."); return "login";
        }
        session.setAttribute("userId",u.getId()); session.setAttribute("userName",u.getName()); session.setAttribute("role",u.getRole());
        return "redirect:/dashboard";
    }
    @GetMapping("/logout") public String logout(HttpSession session){session.invalidate();return "redirect:/login";}
}
