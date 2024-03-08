package com.omega.proiects10;

import com.omega.proiects10.user.User;
import com.omega.proiects10.user.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

// login section
    @Autowired
    private UserServices service;

    @GetMapping("")
    public String getAccount(Model model){
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/login")
    private String redirectAccount(User user, Model model) throws Exception{
        byte answer = service.checkAccount(user);
            switch (answer) {
                case 1:
                    model.addAttribute("selector", new String());
                    return "user";
                case 2:
                    model.addAttribute("selector", new String());
                    return "admin";
                default:
                    return "fail";
            }
    }
}
