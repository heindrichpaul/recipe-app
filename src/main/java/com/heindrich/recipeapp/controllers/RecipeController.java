package com.heindrich.recipeapp.controllers;

import com.heindrich.recipeapp.commands.RecipeCommand;
import com.heindrich.recipeapp.exceptions.NotFoundException;
import com.heindrich.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class RecipeController {
    public static final String RECIPE = "recipe";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(value = "/recipe/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model) {
        model.addAttribute(RECIPE, recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping(value = {"recipe/new"})
    public String newRecipe(Model model) {
        model.addAttribute(RECIPE, new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping(value = "recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping(value = {"recipe/{id}/update"})
    public String getRecipeUpdate(@PathVariable String id, Model model) {
        model.addAttribute(RECIPE, recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping(value = {"recipe/{id}/delete"})
    public String deleteById(@PathVariable String id) {
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception.");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException(Exception exception) {
        log.error("Handling number format exception.");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
