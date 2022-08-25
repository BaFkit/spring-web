package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.converters.ProductConverterWS;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.soap.products.GetAllProductsRequest;
import com.geekbrains.spring.web.soap.products.GetAllProductsResponse;
import com.geekbrains.spring.web.soap.products.GetProductByIdRequest;
import com.geekbrains.spring.web.soap.products.GetProductByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/spring/products";
    private final ProductsService productsService;
    private final ProductConverterWS productConverterWS;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productsService.getAll().stream().map(productConverterWS::entityToDto).forEach(response.getProducts()::add);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productConverterWS.entityToDto(productsService.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"))));
        return response;
    }
}
