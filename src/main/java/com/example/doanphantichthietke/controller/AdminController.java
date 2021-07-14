package com.example.doanphantichthietke.controller;


import com.example.doanphantichthietke.model.*;
import com.example.doanphantichthietke.service.admin.AdminService;
import com.example.doanphantichthietke.service.cart.CartService;
import com.example.doanphantichthietke.service.dish.DishService;
import com.example.doanphantichthietke.service.mainDish.MainDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Controller
@EnableSpringDataWebSupport
public class AdminController {
    @Autowired
    AdminService admin;
    @Autowired
    DishService dishService;
    @Autowired
    CartService cartService;
    @Autowired
    MainDishService mainDishService;


    /*//-----ADMIN LOGIN
    @GetMapping("/admin/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("admin/login");
        modelAndView.addObject("login", new Login());
        return modelAndView;
    }
    @PostMapping("/admin/sign-in")
    public ModelAndView signIn(@ModelAttribute("login") Login login, RedirectAttributes redirectAttributes) throws NullPointerException {
        Iterable<Admin> admins = admin.findAll();
        Admin admin = new Admin();
        for (Admin a : admins) {
            if (a.getAccount().equals(login.getAccount())
                    && a.getPassword().equals(login.getPassword())) {
                admin = a;
                break;
            } else {
                admin = null;
            }
        }
        ModelAndView modelAndView;
        if (admin.getAccount() == null && admin.getPassword() == null || admin == null) {
            redirectAttributes.addFlashAttribute("message", "Bạn đã nhập tài khoản hoặc mật khẩu sai, xin mời " +
                    "nhập lại");
            modelAndView = new ModelAndView("redirect:/admin/login");
        } else {
            modelAndView = new ModelAndView("redirect:/admin");
        }
        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullClient(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Bạn đã nhập tài khoản hoặc mật khẩu sai, xin mời nhập lại");

        return "redirect:/admin/login";
    }*/



    //-----HOME PAGE
    @GetMapping("/admin")
    public String index(Model model, @PageableDefault(size = 10) Pageable pageable) {
        model.addAttribute("Carts", cartService.findAllOrderByDate(pageable));
        return "Admin/Cart/list";
    }

    //DANH SACH MON
    @GetMapping("/admin/dish")
    public String ShowDish(Model model, @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("Dishes", mainDishService.findAll(pageable));
        return "Admin/dish/list";
    }

    //THEM MON MOI
    @GetMapping("/admin/dish/createDish")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("Admin/dish/create");
        MainDish mainDish = new MainDish();
        modelAndView.addObject("mainDish", mainDish);
        return modelAndView;
    }

    //LUU MON MOI
    @PostMapping("/admin/dish/save")
    public String save(MainDish mainDish, RedirectAttributes redirectAttributes) {
        mainDishService.save(mainDish);
        redirectAttributes.addFlashAttribute("message", "Created Dish successfully!");
        return "redirect:/admin/dish";
    }

    //SUA THONG TIN MON
    @GetMapping("/admin/dish/{id}/edit")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<MainDish> mainDish = mainDishService.findById(id);
        ModelAndView modelAndView = new ModelAndView("Admin/dish/edit");
        modelAndView.addObject("mainDish", mainDish.get());
        modelAndView.addObject("imageDish", mainDish.get().getImage());
        return modelAndView;
    }

    @PostMapping("/admin/dish/update")
    public String update(MainDish mainDish, RedirectAttributes redirect, @RequestParam("fileLabel") String fileLabel,
                         @RequestParam("image") String image) {
        if (image == "") {
            mainDish.setImage(fileLabel);
        }
        mainDishService.save(mainDish);
        redirect.addFlashAttribute("message", "Edit dish successfully!");
        return "redirect:/admin/dish";
    }

    //XOA MON
    @GetMapping("/admin/dish/{id}/delete")
    public ModelAndView delete(@PathVariable("id") Long id) {
        Optional<MainDish> mainDish = mainDishService.findById(id);
        ModelAndView modelAndView = new ModelAndView("Admin/dish/delete");
        modelAndView.addObject("mainDish", mainDish.get());
        return modelAndView;
    }

    @PostMapping("/admin/dish/delete")
    public String delete(MainDish mainDish, RedirectAttributes redirect) {
        mainDishService.remove(mainDish.getId());
        redirect.addFlashAttribute("message", "Delete dish successfully!");
        return "redirect:/admin/dish";
    }

    //CHI TIET MON
    @GetMapping("/admin/dish/{id}/view")
    public String view(@PathVariable("id") Long id, Model model) {
        Optional<MainDish> mainDish = mainDishService.findById(id);
        model.addAttribute("mainDish", mainDish.get());
        return "Admin/dish/view";
    }

    //CHI TIET GIO HANG
    @GetMapping("/admin/{id}/view")
    public ModelAndView viewCart(@PathVariable("id") Long id) {
        Optional<Cart> cartOptional = cartService.findById(id);
        Iterable<Dish> dishes = dishService.findAllByCart(cartOptional.get());
        ModelAndView modelAndView = new ModelAndView("/Admin/Cart/view");
        modelAndView.addObject("id", cartOptional.get().getId());
        modelAndView.addObject("cart", cartOptional.get());
        modelAndView.addObject("dishes", dishes);
        return modelAndView;
    }

    //XOA GIO HANG
    @GetMapping("/admin/{id}/delete")
    public ModelAndView deleteCart(@PathVariable("id") Long id) {
        Optional<Cart> cart = cartService.findById(id);
        ModelAndView modelAndView = new ModelAndView("Admin/Cart/delete");
        modelAndView.addObject("cart", cart.get());
        return modelAndView;
    }

    @PostMapping("/admin/delete")
    public String deleteCart(Cart cart, RedirectAttributes redirect) {
        cartService.remove(cart.getId());
        redirect.addFlashAttribute("message", "Delete dish successfully!");
        return "redirect:/admin";
    }

    //-----CHỈNH SỬA TÌNH TRẠNG GIỎ HÀNG
    @GetMapping("/admin/{id}/edit")
    public ModelAndView editCart(@PathVariable("id") Long id) {
        Double sum = 0.;
        Double discount = 0.;
        Optional<Cart> cart = cartService.findById(id);
        Iterable<Dish> dishes = dishService.findAllByCart(cart.get());
        for (Dish dish : dishes) {
            Double totalDish;
            totalDish = dish.getPrice() * dish.getAmount();
            sum = sum + totalDish;
        }
        if (sum > 0 && sum < 100000) {
            discount = 0.1;
        } else if (sum > 99999 && sum < 500000) {
            discount = 0.2;
        } else if (sum > 499999 && sum < 999999 ) {
            discount = 0.3;
        }
        sum = sum - sum * discount;
        ModelAndView modelAndView = new ModelAndView("Admin/Cart/edit");
        modelAndView.addObject("sum", sum);
        modelAndView.addObject("discount", discount*100);
        modelAndView.addObject("cart", cart.get());
        modelAndView.addObject("dishes", dishes);
        return modelAndView;
    }

    @PostMapping("/admin/edit")
    public String editCart(Cart cart, RedirectAttributes redirect) {
        cartService.save(cart);
        redirect.addFlashAttribute("message", "Chỉnh sửa tình trạng giỏ hàng thành công");
        return "redirect:/admin";
    }
}


