package com.geekbrains.spring.cart.services;

import com.geekbrains.spring.cart.dto.Cart;
import com.geekbrains.spring.web.api.dto.OrderDetailsDto;
import com.geekbrains.spring.web.api.dto.OrderDto;
import com.geekbrains.spring.web.api.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<Long, OrderDto> kafkaTemplate;

    @Qualifier("test")
    private final CacheManager cacheManager;
    @Value("${spring.cache.user.name}")
    private String CACHE_CART;
    @Value("${spring.kafka.topic}")
    private String topic;
    private Cart cart;


    @Cacheable(value = "Cart", key = "#cartName")
    public Cart getCurrentCart(String cartName){
        cart = cacheManager.getCache(CACHE_CART).get(cartName, Cart.class);
        if(!Optional.ofNullable(cart).isPresent()){
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache(CACHE_CART).put(cartName, cart);
        }
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName){
        Cart cart = getCurrentCart(cartName);
        if(!cart.addProductCount(id)){
            ProductDto productDto = restTemplate.getForObject("http://localhost:8189/web-market-core/api/v1/products/" + id , ProductDto.class);
            if(productDto != null) cart.addProduct(productDto);
        }
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart deleteFromCart(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.removeProduct(id);
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart decreaseFromCart(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.decreaseProduct(id);
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart clear(String cartName){
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        return cart;
    }

    public void createOrder(String username, OrderDetailsDto orderDetailsDto, String cartName) {
        Cart currentCart = getCurrentCart(cartName);
        OrderDto order = new OrderDto();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        order.setItemDtoList(currentCart.getItems());
        currentCart.clear();
        kafkaTemplate.send(topic, order);
    }
}
