package edu.ap.spring.controllers;

import edu.ap.spring.model.InhaalExamen;
import edu.ap.spring.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InhaalExamenController {

    @Autowired
    RedisService repository;

    @RequestMapping("/list")
    public String list(@RequestParam(name = "student") String student) {
        String content = repository.getKey(student);

        return content;
    }

    @PutMapping("/new")
    public void newStudent(@RequestParam(name = "student") String student, @RequestParam(name = "exam") String exam, @RequestParam(name = "reason") String reason) {
        InhaalExamen inhaalExamen = new InhaalExamen(student, exam, reason);
        if(repository.getKey(inhaalExamen.getStudent()) != null)
        repository.setKey(inhaalExamen.getStudent(), inhaalExamen.toString());
    }
}
