package io.terminus.dice.trial.demo.demomysql.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Effet
 */
@Controller
public class AnalyticsController {

    @Value("${dice.ta.terminusKey}")
    private String terminusKey;

    @Value("${dice.ta.terminusCollectorUrl}")
    private String terminusCollectorUrl;

    @GetMapping("/analytics")
    public String greeting(Model model) {
        model.addAttribute("terminusKey", terminusKey);
        model.addAttribute("terminusCollectorUrl", terminusCollectorUrl);
        return "analytics";
    }
}
