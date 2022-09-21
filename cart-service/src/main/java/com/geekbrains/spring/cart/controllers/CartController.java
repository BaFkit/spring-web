package com.geekbrains.spring.cart.controllers;

import com.geekbrains.spring.cart.dto.Cart;
import com.geekbrains.spring.cart.services.CartService;
import com.geekbrains.spring.web.api.dto.OrderDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Controller for cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Get cart by name")
    public Cart getCurrentCart(@RequestBody String cartName) {
        return cartService.getCurrentCart(cartName);
    }

    @PostMapping("/add/{id}")
    @Operation(summary = "Add to cart product by id")
    public void addProductToCart(@PathVariable Long id, @RequestBody String cartName) {
        cartService.addProductByIdToCart(id, cartName);
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Remove product from cart by id")
    public void deleteFromCart(@PathVariable Long id, @RequestBody String cartName) {
        cartService.deleteFromCart(id, cartName);
    }

    @PostMapping("/decrease/{id}")
    @Operation(summary = "Decrease product quantity by id")
    public void decreaseFromCart(@PathVariable Long id, @RequestBody String cartName) {
        cartService.decreaseFromCart(id, cartName);
    }

    @PostMapping("/clear")
    @Operation(summary = "Clear current cart")
    public void clearCart(@RequestBody String cartName) {
        cartService.clear(cartName);
    }

    @PostMapping("/createOrder/{cartName}")
    @Operation(summary = "Create an order with items from the cart")
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto, @PathVariable String cartName) {
        cartService.createOrder(username, orderDetailsDto, cartName);
    }

}
