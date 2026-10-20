package ch.daniil.gymplan;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlanController {
    @GetMapping("/plan")
    public String showPlan(Model model) {
        model.addAttribute("planRequest", new PlanRequest());
        return "plan-form";
    }
    @PostMapping("/plan")
    public String submitPlan(@ModelAttribute PlanRequest planRequest){
        return "plan-result";
    }


}
