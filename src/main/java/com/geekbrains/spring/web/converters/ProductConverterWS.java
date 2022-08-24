package com.geekbrains.spring.web.converters;

import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.soap.products.ProductWS;
import org.springframework.stereotype.Component;

@Component
public class ProductConverterWS {
    public Product dtoToEntity(ProductWS productWS) {
        return new Product(productWS.getId(), productWS.getTitle(), productWS.getPrice());
    }

    public ProductWS entityToDto(Product product) {
        ProductWS productWS = new ProductWS();
        productWS.setId(product.getId());
        productWS.setTitle(product.getTitle());
        productWS.setPrice(product.getPrice());
        return productWS;
    }
}
