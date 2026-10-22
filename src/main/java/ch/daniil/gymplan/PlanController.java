package ch.daniil.gymplan;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }
    @GetMapping("/plan")
    public String showPlan(Model model) {
        model.addAttribute("planRequest", new PlanRequest());
        return "plan-form";
    }
    @PostMapping("/plan")
    public String submitPlan(@ModelAttribute @Valid PlanRequest planRequest, BindingResult bindingResult, Model model){
        String split = planRequest.getSplitPreference();
        Integer days = planRequest.getDaysPerWeek();
        if (split != null && days != null){
            int minDays = 0;
            if (split.equals("push_pull_legs")) {
                minDays = 3;
            } else if (split.equals("upper_lower")) {
                minDays = 2;
            }
            if (days < minDays){
                bindingResult.rejectValue("daysPerWeek", "invalid", "Please enter a valid days per week");
            }
        }
        if (bindingResult.hasErrors()){
            return "plan-form";
        }
        Plan plan;
        try {
            plan = planService.generatePlan(planRequest);
        } catch (GeminiException e) {
            bindingResult.reject("gemini.failed", e.getMessage());
            return "plan-form";
        }
        model.addAttribute("plan", plan);
        return "plan-result";
    }




}
