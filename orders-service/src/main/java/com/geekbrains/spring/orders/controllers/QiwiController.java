package com.geekbrains.spring.orders.controllers;

import com.geekbrains.spring.orders.dto.QiwiResponse;
import com.geekbrains.spring.orders.services.QiwiService;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/qiwi")
@RequiredArgsConstructor
@Slf4j
public class QiwiController {
    private final QiwiService qiwiService;
    @Value("${qiwi.client-secret}")
    private String secretKey;
    private BillPaymentClient client = BillPaymentClientFactory.createDefault(secretKey);

    @GetMapping("/create/{orderId}")
    public QiwiResponse createOrder(@PathVariable Long orderId) throws URISyntaxException {
        BillResponse response = client.createBill(qiwiService.createOrderRequest(orderId));
        log.info("resp = {}", response);
        return new QiwiResponse(response.getPayUrl(), response.getBillId());
    }

    @PostMapping("/capture/{billId}")
    public ResponseEntity<?> captureOrder(@PathVariable String billId) throws IOException {
        BillResponse response = client.getBillInfo(billId);
        if("COMPLETED".equals(response.getStatus())) {

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
