package com.virtuslab.internship.BasketController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testForNotEmptyBasket() throws Exception{
        // Given
        var productDb = new ProductDb();
        var pork = productDb.getProduct("Pork");
        var banana = productDb.getProduct("Banana");
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");

        var basket = new Basket();
        basket.addProduct(pork);
        basket.addProduct(banana);
        basket.addProduct(bread);
        basket.addProduct(bread);
        basket.addProduct(bread);
        basket.addProduct(cheese);

        var realTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(3))
                .add(cheese.price())
                .add(banana.price())
                .add(pork.price())
                .multiply(BigDecimal.valueOf(0.85));

        //when
        MvcResult res = mockMvc.perform(
                        post("/basket/showReceipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(basket))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();


        Receipt receipt = new ObjectMapper().readValue(res.getResponse().getContentAsString(), Receipt.class);

        //then
        assertEquals(realTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }
}
