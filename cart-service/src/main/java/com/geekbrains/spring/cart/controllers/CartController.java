package com.geekbrains.spring.cart.controllers;

import com.geekbrains.spring.cart.dto.Cart;
import com.geekbrains.spring.cart.services.CartService;
import com.geekbrains.spring.web.api.dto.OrderDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public Cart getCurrentCart(@RequestBody String cartName) {
        return cartService.getCurrentCart(cartName);
    }

    @PostMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id, @RequestBody String cartName) {
        cartService.addProductByIdToCart(id, cartName);
    }

    @PostMapping("/delete/{id}")
    public void deleteFromCart(@PathVariable Long id, @RequestBody String cartName) {
        cartService.deleteFromCart(id, cartName);
    }

    @PostMapping("/decrease/{id}")
    public void decreaseFromCart(@PathVariable Long id, @RequestBody String cartName) {
        cartService.decreaseFromCart(id, cartName);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName) {
        cartService.clear(cartName);
    }

    @PostMapping("/createOrder/{cartName}")
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto, @PathVariable String cartName) {
        cartService.createOrder(username, orderDetailsDto, cartName);
    }

}