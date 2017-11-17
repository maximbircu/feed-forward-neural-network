package com.company.utils;

public class Activation {
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double sigmoidPrime(double x){
        return Math.exp(-x) / Math.pow(1 + Math.exp(-x), 2);
    }
}
