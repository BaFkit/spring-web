package com.geekbrains.spring.cart.tests;

import com.geekbrains.spring.cart.api.CoreApi;
import com.geekbrains.spring.cart.dto.Cart;
import com.geekbrains.spring.cart.services.CartService;
import com.geekbrains.spring.web.api.dto.OrderDto;
import com.geekbrains.spring.web.api.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;
    @MockBean
    private CacheManager cacheManager;
    @MockBean
    private KafkaTemplate<Long, OrderDto> kafkaTemplate;
    @MockBean
    private CoreApi coreApi;

    @BeforeEach
    public void initCart() {
        cartService = new CartService(kafkaTemplate, coreApi, cacheManager);
        Mockito.when(cacheManager.getCache("Cart")
                .get(Mockito.anyString(), Cart.class)).thenReturn(new Cart());
        cartService.clear("test_cart2");
    }

    @Test
    public void addToCartTest() {
        ProductDto productDtoMilk = new ProductDto();
        productDtoMilk.setId(10L);
        productDtoMilk.setTitle("Milk");
        productDtoMilk.setPrice(100);

        Mockito.doReturn(productDtoMilk).when(coreApi).getProductById(10L);

        cartService.addProductByIdToCart(10L, "test_cart2");
        cartService.addProductByIdToCart(10L, "test_cart2");
        cartService.addProductByIdToCart(10L, "test_cart2");

        Mockito.verify(coreApi, Mockito.times(1)).getProductById(10L);
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart2").getItems().size());
    }


    @Test
    public void deleteFromCartTest() {
        ProductDto productDtoMilk = new ProductDto();
        productDtoMilk.setId(10L);
        productDtoMilk.setTitle("Milk");
        productDtoMilk.setPrice(100);

        ProductDto productDtoCheese = new ProductDto();
        productDtoCheese.setId(11L);
        productDtoCheese.setTitle("Cheese");
        productDtoCheese.setPrice(170);

        ProductDto productDtoBread = new ProductDto();
        productDtoBread.setId(12L);
        productDtoBread.setTitle("Bread");
        productDtoBread.setPrice(20);

        Mockito.doReturn(productDtoMilk).when(coreApi).getProductById(10L);
        Mockito.doReturn(productDtoCheese).when(coreApi).getProductById(10L);
        Mockito.doReturn(productDtoBread).when(coreApi).getProductById(10L);

        cartService.addProductByIdToCart(10L, "test_cart2");
        cartService.addProductByIdToCart(11L, "test_cart2");
        cartService.addProductByIdToCart(12L, "test_cart2");

        Assertions.assertEquals(3, cartService.getCurrentCart("test_cart2").getItems().size());

        cartService.deleteFromCart(10L, "test_cart2");
        cartService.deleteFromCart(12L, "test_cart2");

        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart2").getItems().size());
    }
}
