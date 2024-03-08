package com.omega.proiects10.user;

import com.omega.proiects10.SQL.*;
import com.omega.proiects10.SQL.SQLcustom.FullJoinClasses;
import com.omega.proiects10.SQL.SQLcustom.Subject7;
import com.omega.proiects10.SQL.SQLcustom.Subject8;
import com.omega.proiects10.querys.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired FurnizoriJdbc repoFur;
    @Autowired ComponenteJdbc repoComp;
    @Autowired LivrariJdbc repoLi;
    @Autowired ProiecteJdbc repoPr;

    @PostMapping("/userSelector")
    public String getSelect(String selector, Model model){
        if(selector.isEmpty())
            return "user";
        switch (selector){
            case "1":
                model.addAttribute("query1", new Query1());
                return "subject1";
            case "2":
                model.addAttribute("query2", new Query2());
                return "subject2";
            case "3":
                return "subject3";
            case "4":
                return "subject4";
            case "5":
                model.addAttribute("query5", new Query5());
                return "subject5";
            case "6":
                model.addAttribute("query6", new Query6());
                return "subject6";
            case "7":
                return "subject7";
            case "8":
                model.addAttribute("query8", new Query8());
                return "subject8";
        }
        return "user";
    }

    @PostMapping("/subject1")
    public String insertAccount(Model model, Query1 query){
        System.out.println(query);

        List<Furnizori> dataf = repoFur.select1(query);
        model.addAttribute("dataf", dataf);
        return "subject1Answer";
    }

    @PostMapping("/subject2")
    public String insertAccount(Model model, Query2 query){
        System.out.println(query);

        List<Componente> dataf = repoComp.select2(query);
        model.addAttribute("dataf", dataf);
        return "subject2Answer";
    }

    @GetMapping("/subject3")
    public String getSubject3(Model model) {
        List<FullJoinClasses> dataf = repoLi.select3();
        model.addAttribute("dataf", dataf);
        return "subject3Answer";
    }

    @GetMapping("/subject4")
    public String getSubject4(Model model) {
        List<FullJoinClasses> dataf = repoLi.select4();
        model.addAttribute("dataf", dataf);
        return "subject4Answer";
    }

    @PostMapping("/subject5")
    public String getSubject5(Model model, Query5 query) {
            List<FullJoinClasses> dataf = repoLi.select5(query);
        model.addAttribute("dataf", dataf);
        return "subject5Answer";
    }

    @PostMapping("/subject6")
    public String getSubject5(Model model, Query6 query) {
        List<Proiecte> dataf = repoPr.select6(query);
        model.addAttribute("dataf", dataf);
        return "subject6Answer";
    }

    @GetMapping("/subject7")
    public String getSubject7(Model model) {
        List<Subject7> dataf = repoFur.select7();
        model.addAttribute("dataf", dataf);
        return "subject7Answer";
    }

    @PostMapping("/subject8")
    public String getSubject5(Model model, Query8 query) {
        List<Subject8> dataf = repoComp.select8(query);
        model.addAttribute("dataf", dataf);
        return "subject8Answer";
    }

    @GetMapping("/subjectredirect")
    public String redirectSubject(){

        return "user";
    }

    @PostMapping("/subjectredirect")
    public String redirectSubject1(){
        return "user";
    }
}
