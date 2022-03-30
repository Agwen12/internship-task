package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public interface IDiscount {

    Receipt apply(Receipt receipt);
}
