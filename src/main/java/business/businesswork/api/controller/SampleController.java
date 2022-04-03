package business.businesswork.api.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SampleController {
    @RequestMapping(value = "/userLogin", method = {RequestMethod.GET})
    public String userLogin(Model model) {
        return "userLogin";
    }
}
