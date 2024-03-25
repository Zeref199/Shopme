package com.shopme.checkout.paypal;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PayPalApiTests {
    private static final String BASE_URL = "https://api.sandbox.paypal.com";
    private static final String GET_ORDER_API = "/v2/checkout/orders/";
    private static final String CLIENT_ID = "AU_szd6jMWCTYCENJSLjHFxGrlKqLo5GdihUrzKtStdJXKEmbooLDqMbnW5i6eTli8oAkP98-JwK01RI";
    private static final String CLIENT_SECRET= "EC7-O9K2VAEcZSWTS-BmpNSkNY09MDgMlQmNUyacLS7hrVMv5L7b4kh3FASrCc-S8aTg0Eq2zo7FmSrK";

    @Test
    public void testGetOrderDetails() {
        String orderId = "4A027975W0474063L";
        String requestURL = BASE_URL + GET_ORDER_API + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Accept-Language", "en_US");
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PayPalOrderResponse> response = restTemplate.exchange(
                requestURL, HttpMethod.GET, request, PayPalOrderResponse.class);
        PayPalOrderResponse orderResponse = response.getBody();

        System.out.println("Order ID: " + orderResponse.getId());
        System.out.println("Validated: " + orderResponse.validate(orderId));

    }
}
