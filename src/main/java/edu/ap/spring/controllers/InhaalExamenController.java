package edu.ap.spring.controllers;

import edu.ap.spring.model.InhaalExamen;
import edu.ap.spring.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InhaalExamenController {

    @Autowired
    RedisService repository;

    @RequestMapping("/list")
    public String list(@RequestParam(name = "student") String student, Model model) {
        String content = repository.getKey(student);
        String[] splittedContent = content.split("-");
        InhaalExamen inhaalExamen = new InhaalExamen(splittedContent[0], splittedContent[1], splittedContent[1]);
        model.addAttribute( "inhaalExamen", inhaalExamen.toString());
        return "list";
    }

    @PostMapping("/new")
    public String newStudent(@RequestParam("student") String student, @RequestParam("exam") String exam, @RequestParam("reason") String reason, Model model) {
        InhaalExamen inhaalExamen = new InhaalExamen(student, exam, reason);
        if(repository.getKey(inhaalExamen.getStudent()) != null) {
            model.addAttribute("inhaalExamen", "Already exists");
            return "new";
        }
        repository.setKey(inhaalExamen.getStudent(), inhaalExamen.toString());
        model.addAttribute("inhaalExamen", inhaalExamen.toString());
        return "new";
    }
}
