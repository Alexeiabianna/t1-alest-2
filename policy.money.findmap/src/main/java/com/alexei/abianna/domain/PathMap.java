package com.alexei.abianna.domain;

import java.util.ArrayList;
import java.util.List;

public class PathMap {

    private List<Integer> bills = new ArrayList<>();;

    public int getAmountBills() {
        return this.bills.stream().mapToInt(Integer::intValue).sum();
    }

    public List<Integer> getBills() {
        return this.bills;
    }

    public void addBills(int bill) {
        this.bills.add(bill);
    }

}
