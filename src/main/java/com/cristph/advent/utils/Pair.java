package com.cristph.advent.utils;

public class Pair<L, R> {

    private L left;

    private R right;

    private Pair() {
    }

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }


    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
