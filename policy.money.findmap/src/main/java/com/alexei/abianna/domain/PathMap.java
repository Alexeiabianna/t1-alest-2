package com.alexei.abianna.domain;

import java.util.ArrayList;
import java.util.List;

public class PathMap {

    private List<Integer> bills = new ArrayList<>();
    private List<Character> pathMapRecorded = new ArrayList<>();

    public int getAmountBills() {
        return this.bills.stream().mapToInt(Integer::intValue).sum();
    }

    public List<Integer> getBills() {
        return this.bills;
    }

    public void addBills(int bill) {
        this.bills.add(bill);
    }

    public List<Character> getPathMapRecorded() {
        return this.pathMapRecorded;
    }

    public void addBlockToMap(final char block) {
        this.pathMapRecorded.add(block);
    }
}
