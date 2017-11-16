package com.company.utils;

public class BinaryUtils {
    public static int binaryStringToDecimal(String binaryString){
        return Integer.parseInt(binaryString, 2);
    }

    public static Tensor getTensorValueFromInt(int number, int length) {
        double[] bits = new double[length];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = (number & (1 << i)) != 0 ? 1 : 0;
        }
        reverse(bits);
        return new Tensor(new double[][]{bits});
    }

    private static void reverse(double[] data) {
        for (int left = 0, right = data.length - 1; left < right; left++, right--) {
            // swap the values at the left and right indiceswap the values at the left and right indices
            double temp = data[left];
            data[left] = data[right];
            data[right] = temp;
        }
    }

    public static String getBinaryString(double[] row) {
        String binaryString = "";
        for (double value : row) {
            binaryString = String.format("%s%s", binaryString, value > 0.5 ? "1" : "0");
        }
        return binaryString;
    }
}
