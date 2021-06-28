package com.example.doanphantichthietke.controller;

import com.example.doanphantichthietke.model.Cart;
import com.example.doanphantichthietke.service.dish.DishService;
import com.example.doanphantichthietke.service.mainDish.MainDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("cart")
public class HomeController {
    @Autowired
    MainDishService mainDishService;
    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }
    @GetMapping("/")
    public String home(Model model, @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("dishes", mainDishService.findAll(pageable));
        return "home";
    }

}
