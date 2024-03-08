package com.omega.proiects10.user;

import com.omega.proiects10.SQL.Exceptii;
import com.omega.proiects10.SQL.ExceptiiJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired ExceptiiJdbc repo;

    @GetMapping("/adminPanel")
    public String getConsole(){

        return "adminPanel";
    }

    @GetMapping("/exceptii")
    public String getExceptii(Model model){
        List<Exceptii> dataf = repo.getTable();
        model.addAttribute("dataf", dataf);
        return "exceptii";
    }

    @GetMapping("/redirectAdmin")
    public String goToAdmin(){
        return "admin";
    }
}
