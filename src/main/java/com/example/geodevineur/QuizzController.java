package com.example.geodevineur;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizzController {

    private final TableController tableController;

    public QuizzController(TableController tableController) {
        this.tableController = tableController;
    }

    @GetMapping("nextStep")
    public void nextStep(Model model){


    }
}
