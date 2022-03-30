package com.virtuslab.internship.cotrollers;


import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.DiscountManager;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private final ReceiptGenerator receiptGenerator = new ReceiptGenerator();


    @PostMapping("/showReceipt")
    public Receipt getReceipt(@RequestBody Basket basket) {
        Receipt receipt = receiptGenerator.generate(basket);
        receipt = DiscountManager.applyDiscounts(receipt);
        return receipt;
    }
}
