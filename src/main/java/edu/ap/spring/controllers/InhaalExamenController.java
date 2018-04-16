package edu.ap.spring.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ap.spring.model.InhaalExamen;
import edu.ap.spring.redis.RedisService;

@Controller
public class InhaalExamenController {

    @Autowired
    RedisService repository;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String list(@RequestParam(name = "student") String student) {
       /*String content = repository.getKey(student);
       if(content != null) {
        String[] splittedContent = content.split("-");
        if(splittedContent.length == 3) {
        InhaalExamen inhaalExamen = new InhaalExamen(splittedContent[0], splittedContent[1], splittedContent[2]);
        return inhaalExamen.toString();
        }
       }*/
    	StringBuilder stringBuilder = new StringBuilder();
    	repository.hgetAll(student).forEach((o, o2) -> stringBuilder.append(o.toString()));
    	if(stringBuilder.length() != 0) {
    	    return stringBuilder.toString();
        }
       return "Does not exist!";
    }

    @PostMapping("/new")
    @ResponseBody
    public String newStudent(@RequestParam("student") String student, @RequestParam("exam") String exam, @RequestParam("reason") String reason) {
        InhaalExamen inhaalExamen = new InhaalExamen(student, exam, reason);
        if(repository.getKey(inhaalExamen.getStudent()) != null) {
            return "Already exists";
        }
        //repository.setKey(inhaalExamen.getStudent(), inhaalExamen.toString());
        Map<String, String> map = new HashMap<>();
        map.put(inhaalExamen.getStudent(), null);
        repository.hset(student, map);
        return inhaalExamen.toString();
    }
}
