package com.shopme.admin;

import com.shopme.admin.dashboard.DashboardInfo;
import com.shopme.admin.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("")
        public String viewHomePage(Model model) {
            DashboardInfo summary = dashboardService.loadSummary();
            model.addAttribute("summary", summary);
            return "index";
    }

    @GetMapping("/login")
    public String ViewLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";

    }

}
