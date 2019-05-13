package me.matamor.commonapi.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PaymentNotification {

    @Getter
    private final String name;

    @Getter
    private final long date;

    @Getter
    private final double amount;

}
