package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public class DiscountManager {
    static IDiscount fifteenDiscount = new FifteenPercentDiscount();
    static IDiscount tenDiscount = new TenPercentDiscount();


    public static Receipt applyDiscounts(Receipt receipt) {
        return tenDiscount.apply(fifteenDiscount.apply(receipt));
    }

    public static Receipt applyTenPercentDiscount(Receipt receipt) {
        return tenDiscount.apply(receipt);
    }

}
