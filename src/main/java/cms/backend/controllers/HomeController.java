package cms.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public @ResponseBody String home() {
        return "Hello World";
    }

    @GetMapping(value = "/admin")
    public @ResponseBody String dashboard() {
        return "Hello World";
    }
}
