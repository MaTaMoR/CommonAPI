package me.matamor.commonapi.inventory.amount;

public class SimpleAmount implements Amount {

    private final int amount;

    public SimpleAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
