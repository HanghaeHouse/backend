package hanghaehouse.hanghaehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

//@CrossOrigin(origins = "*")
@Controller
public class IndexController {
    @GetMapping({"", "/index"})
    public String index() {
        return "redirect:/all";
    }
}
