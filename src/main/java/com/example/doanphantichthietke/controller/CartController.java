package com.example.doanphantichthietke.controller;

import com.example.doanphantichthietke.exception.DuplicateAccountClient;
import com.example.doanphantichthietke.model.*;
import com.example.doanphantichthietke.service.cart.CartService;
import com.example.doanphantichthietke.service.client.ClientService;
import com.example.doanphantichthietke.service.dish.DishService;
import com.example.doanphantichthietke.service.mainDish.MainDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Controller

public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    DishService dishService;

    @Autowired
    MainDishService mainDishService;

    @Autowired
    ClientService clientService;

    //-----CLIENT SIGN-UP
    @GetMapping("/home/client/sign-up")
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView("/cart/client/sign-up");
        modelAndView.addObject("client", new Client());
        return modelAndView;
    }

    @PostMapping("/home/client/sign-up")
    public String signUp(Client client, RedirectAttributes redirectAttributes) throws DuplicateAccountClient {
        clientService.save(client);
        redirectAttributes.addFlashAttribute("message", "Đăng ký thành công, xin mời đăng nhập");
        return "redirect:/home/client/login";
    }

    @ExceptionHandler(DuplicateAccountClient.class)
    public String showInputNotAcceptable(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Tài khoản đã có người sử dụng, xin mời nhập lại");

        return "redirect:/home/client/sign-up";
    }

    //-----Client login
    @GetMapping("/home/client/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("cart/client/login");
        modelAndView.addObject("login", new Login());
        return modelAndView;
    }

    //-----client sign in
    @PostMapping("/home/client/sign-in")
    public ModelAndView signIn(@ModelAttribute("login") Login login, RedirectAttributes redirectAttributes) throws NullPointerException {
        Iterable<Client> clients = clientService.findAll();
        Client client = new Client();
        for (Client c : clients) {
            if (c.getAccount().equals(login.getAccount())
                    && c.getPassword().equals(login.getPassword())) {
                client = c;
                break;
            } else {
                client = null;
            }
        }
        ModelAndView modelAndView;
        if (client.getAccount() == null && client.getPassword() == null || client == null) {
            redirectAttributes.addFlashAttribute("message", "Bạn đã nhập tài khoản hoặc mật khẩu sai, xin mời " +
                    "nhập lại");
            modelAndView = new ModelAndView("redirect:/home/client/login");
        } else {
            modelAndView = new ModelAndView("/cart/create");
            Cart cart = new Cart();
            LocalDateTime date = LocalDateTime.now();
            cart.setDateCreated(date);
            cart.setNameClientCreated(client.getName());
            cart.setAddressClientCreated(client.getAddress());
            cart.setNumberContactClientCreated(client.getNumberContact());
            modelAndView.addObject("cart", cart);
        }
        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullClient(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Bạn đã nhập tài khoản hoặc mật khẩu sai, xin mời nhập lại");

        return "redirect:/home/client/login";
    }


    @PostMapping("/home/cart/save")
    public String save(Cart cart, Model model) {
        cartService.save(cart);
        model.addAttribute("id", cart.getId());
        model.addAttribute("cart", cart);
        return "cart/home";
    }

    //-----XEM GIỎ HÀNG CHI TIẾT
    @GetMapping("/home/cart/{id}/view")
    public ModelAndView viewCart(@PathVariable("id") Long id) {
        Double sum = 0.;
        Double discount = 0.;
        Optional<Cart> cartOptional = cartService.findById(id);
        if (!cartOptional.isPresent()) {
            return new ModelAndView("/error.404");
        }
        Iterable<Dish> dishes = dishService.findAllByCart(cartOptional.get());
        ModelAndView modelAndView = new ModelAndView("/cart/view");
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
        modelAndView.addObject("sum", sum);
        modelAndView.addObject("discount", discount*100);
        modelAndView.addObject("id", cartOptional.get().getId());
        modelAndView.addObject("cart", cartOptional.get());
        modelAndView.addObject("status", cartOptional.get().getStatus());
        modelAndView.addObject("dishes", dishes);
        return modelAndView;
    }

    //-----CREATE NEW dish IN cart
    @GetMapping("/home/cart/{id}/create/dish")
    public ModelAndView createDish(@PathVariable Long id, @PageableDefault(size = 5) Pageable pageable) {

        ModelAndView modelAndView = new ModelAndView("cart/create-dish");
        Optional<Cart> cartOptional = cartService.findById(id);
        modelAndView.addObject("cart", cartOptional.get());
        modelAndView.addObject("dishes", mainDishService.findAll(pageable));

        return modelAndView;
    }

    @PostMapping("/home/cart/{id}/save/dish")
    public String save(@PathVariable Long id, Dish dish, RedirectAttributes redirectAttributes) {
        dishService.save(dish);
        redirectAttributes.addFlashAttribute("message", "Đặt món thành công! Xin mời chọn thêm sản phẩm");
        return "redirect:/home/cart/{id}/create/dish";
    }


    //-----EDIT dish IN cart
    @GetMapping("/home/cart/{id}/view/edit/{id2}")
    public ModelAndView editDish(@PathVariable("id") Long id, @PathVariable("id2") Long id2) {
        Optional<Dish> dish = dishService.findById(id2);
        ModelAndView modelAndView = new ModelAndView("cart/edit-dish");
        modelAndView.addObject("id", id);
        modelAndView.addObject("dish", dish.get());
        return modelAndView;
    }

    @PostMapping("/home/cart/{id}/view/update/{id2}")
    public String update(@PathVariable("id") Long id, @PathVariable("id2") Long id2, Dish dish, RedirectAttributes
            redirectAttributes) {
        dishService.save(dish);
        redirectAttributes.addFlashAttribute("message", "Chỉnh sửa món thành công");
        return "redirect:/home/cart/{id}/view";
    }

    //-----DELETE dish IN cart
    @GetMapping("/home/cart/{id}/view/delete/{id2}")
    public ModelAndView deleteDish(@PathVariable("id") Long id, @PathVariable("id2") Long id2) {
        Optional<Dish> dish = dishService.findById(id2);
        ModelAndView modelAndView = new ModelAndView("cart/delete-dish");
        modelAndView.addObject("id", id);
        modelAndView.addObject("dish", dish.get());
        return modelAndView;
    }

    @PostMapping("/home/cart/{id}/view/delete/{id2}")
    public String deleteDish(@PathVariable("id") Long id, @PathVariable("id2") Long id2, Dish dish, RedirectAttributes
            redirectAttributes) {
        dishService.remove(id2);
        redirectAttributes.addFlashAttribute("message", "Xóa món ăn thành công");
        return "redirect:/home/cart/{id}/view";
    }

    //-----CHỌN MÓN ĂN
    @GetMapping("/home/cart/{id}/dish/{id2}")
    public String buyDish(@PathVariable("id") Long id, @PathVariable("id2") Long id2, Model model, RedirectAttributes redirectAttributes) {
        Optional<MainDish> mainDishOptional = mainDishService.findById(id2);
        Optional<Cart> cartOptional = cartService.findById(id);
        Dish dish = new Dish();
        dish.setCart(cartOptional.get());
        dish.setName(mainDishOptional.get().getName());
        dish.setAmount(1L);
        dish.setPrice(mainDishOptional.get().getPrice());
        model.addAttribute("dish", dish);
        return "cart/create-dish";
    }

    @GetMapping("/home/cart/{id}/order")
    public ModelAndView order(@PathVariable Long id) {
        Double sum = 0.;
        Double discount = 0.;
        ModelAndView modelAndView = new ModelAndView("cart/order");
        Optional<Cart> cartOptional = cartService.findById(id);
        cartOptional.get().setStatus("Đặt hàng");
        cartService.save(cartOptional);
        Iterable<Dish> dishes = dishService.findAllByCart(cartOptional.get());
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
        modelAndView.addObject("discount", discount*100);
        modelAndView.addObject("sum", sum);
        modelAndView.addObject("cart", cartOptional.get());
        modelAndView.addObject("dishes", dishes);
        return modelAndView;
    }

    //-----xem giỏ hàng
    @GetMapping("/home/cart/{id}/view/main")
    public ModelAndView viewMainCart(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cart/view-main");
        Optional<Cart> cartOptional = cartService.findById(id);
        modelAndView.addObject("cart", cartOptional.get());
        modelAndView.addObject("message", "Đặt hàng thành công! Cửa hàng sẽ liên hệ cho bạn để" +
                " hoàn tất thủ tục");
        return modelAndView;
    }
}
