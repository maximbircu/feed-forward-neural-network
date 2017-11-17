package com.company.utils;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Random;
import java.util.function.Function;

public class Tensor {
    private RealMatrix matrix;

    private Tensor(RealMatrix matrix) {
        this.matrix = matrix;
    }

    public Tensor(int rows, int columns) throws NotStrictlyPositiveException {
        matrix = MatrixUtils.createRealMatrix(rows, columns);
    }

    public Tensor(double[][] rawData) throws DimensionMismatchException, NotStrictlyPositiveException {
        matrix = MatrixUtils.createRealMatrix(rawData);
    }

    public static Tensor nextTensor(int rows, int columns) {
        return Tensor.nextTensor(-Math.sqrt(6)/60, Math.sqrt(6)/60, rows, columns);
    }

    public static Tensor nextTensor(double rangeMin, double rangeMax, int rows, int columns) {
        Tensor tensorTest = new Tensor(rows, columns);
        tensorTest.randInit(rangeMin, rangeMax);
        return tensorTest;
    }

    private void randInit(double rangeMin, double rangeMax) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                matrix.setEntry(i, j, getRand(rangeMin, rangeMax));
            }
        }
    }

    private double getRand(double rangeMin, double rangeMax) {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (double[] rows : matrix.getData()) {
            for (double valueInRow : rows) {
                stringBuilder.append(String.format(valueInRow > 0 ? " %f " : "%f ", valueInRow));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // Operations.

    public Tensor add(Tensor tensor) {
        return new Tensor(this.matrix.add(tensor.matrix));
    }

    public Tensor subtract(Tensor tensor) {
        return new Tensor(this.matrix.subtract(tensor.matrix));
    }

    public Tensor multiply(Tensor tensor) {
        return new Tensor(this.matrix.multiply(tensor.matrix));
    }

    public Tensor multiply(double scalar) {
        return new Tensor(this.matrix.scalarMultiply(scalar));
    }

    public Tensor preMultiply(Tensor tensor) {
        return new Tensor(this.matrix.preMultiply(tensor.matrix));
    }

    public Tensor transpose() {
        return new Tensor(this.matrix.transpose());
    }

    public Tensor copy() {
        return new Tensor(matrix.copy());
    }

    public int getRowsCount() {
        return matrix.getRowDimension();
    }

    public int getColumnsCount() {
        return matrix.getColumnDimension();
    }

    public double sum() {
        double totalSum = 0;
        for (double[] rows : matrix.getData()) {
            for (double valueInRow : rows) {
                totalSum += valueInRow;
            }
        }
        return totalSum;
    }

    public Tensor square() {
        RealMatrix resultMatrix = matrix.copy();
        for (int i = 0; i < resultMatrix.getRowDimension(); i++) {
            for (int j = 0; j < resultMatrix.getColumnDimension(); j++) {
                resultMatrix.setEntry(i, j, Math.pow(resultMatrix.getEntry(i, j), 2));
            }
        }
        return new Tensor(resultMatrix);
    }

    public Tensor applyToEachElement(Function<Double, Double> functionToApply) {
        RealMatrix resultMatrix = matrix.copy();
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < resultMatrix.getColumnDimension(); j++) {
                resultMatrix.setEntry(i, j, functionToApply.apply(resultMatrix.getEntry(i, j)));
            }
        }
        return new Tensor(resultMatrix);
    }

    public Tensor multiplyElementWise(Tensor tensor) {
        RealMatrix resultMatrix = tensor.matrix.copy();
        for (int i = 0; i < resultMatrix.getRowDimension(); i++) {
            for (int j = 0; j < resultMatrix.getColumnDimension(); j++) {
                resultMatrix.setEntry(i, j, this.matrix.getEntry(i, j) * resultMatrix.getEntry(i, j));
            }
        }
        return new Tensor(resultMatrix);
    }

    public double[][] getData() {
        return matrix.getData();
    }
}
