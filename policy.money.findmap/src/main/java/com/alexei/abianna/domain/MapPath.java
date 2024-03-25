package com.alexei.abianna.domain;

import java.util.ArrayList;
import java.util.List;

public class MapPath {

    private List<Integer> bills;
    private String path;
    private char direction;

    public int getAmountBills() {
        return bills.stream().mapToInt(Integer::intValue).sum();
    }

    public List<Integer> getBills() {
        return bills;
    }

    public void addBills(int bill) {
        this.bills = new ArrayList<>();
        this.bills.add(bill);
    }

    public String getPath() {
        return path;
    }

    public void addPath(final String pathBlock) {
        this.path = path.concat(pathBlock);
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}
