package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        Map<Product, Integer> productMap = new LinkedHashMap<>();
        basket.getProducts().forEach(product -> productMap.merge(product, 1, Integer::sum));

        List<ReceiptEntry> receiptEntries = productMap.entrySet()
                .stream()
                .map(entry -> new ReceiptEntry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new Receipt(receiptEntries);
    }
}
