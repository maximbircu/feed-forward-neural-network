package com.company;

import com.company.utils.Activation;
import com.company.utils.Tensor;

import javax.swing.*;

public class NeuralNetwork {
    private Tensor w0;
    private Tensor z0;
    private Tensor a0;

    private Tensor w1;
    private Tensor z1;
    private Tensor yHat;

    private Tensor djdw0;
    private Tensor djdw1;


    private Tensor bias0;
    private Tensor bias1;

    private double normCoefficient = 0.0001;
    private double learningRate;

    public NeuralNetwork(int featuresCount, int batchSize, int hiddenUnits, int classesCount, double learningRate) {
        w0 = Tensor.nextTensor(featuresCount, hiddenUnits);
        w1 = Tensor.nextTensor(hiddenUnits, classesCount);
        this.learningRate = learningRate;

        bias0 = Tensor.nextTensor(batchSize, hiddenUnits);
        bias1 = Tensor.nextTensor(batchSize, classesCount);
    }

    public void epoch(Tensor x, Tensor y){
        feedForwardWithBias(x);
        backPropagate(x,y);
        adjustWeights();
    }

    private void adjustWeights() {
        w0 = w0.subtract(djdw0.multiply(learningRate));
        w1 = w1.subtract(djdw1.multiply(learningRate));
    }

    public double getError(Tensor y){
        return 0.5 * y.subtract(yHat).square().sum() + normCoefficient/2 * (w0.square().sum() + w1.square().sum());
    }

    public void feedForwardWithBias(Tensor x){
        z0 = x.multiply(w0);
        z0.add(bias0);
        a0 = z0.applyToEachElement(Activation::sigmoid);

        z1 = a0.multiply(w1);
        z1.add(bias1);
        yHat = z1.applyToEachElement(Activation::sigmoid);
    }

    public void feedForward(Tensor x) {
        z0 = x.multiply(w0);
        a0 = z0.applyToEachElement(Activation::sigmoid);

        z1 = a0.multiply(w1);
        yHat = z1.applyToEachElement(Activation::sigmoid);
    }

    public void backPropagate(Tensor x, Tensor y) {
        Tensor delta1 = yHat.subtract(y).multiplyElementWise(z1.applyToEachElement(Activation::sigmoidPrime));
        djdw1 = delta1.preMultiply(a0.transpose()).add(w1.multiply(normCoefficient));

        Tensor delta2 = delta1.multiply(w1.transpose()).multiplyElementWise(z0.applyToEachElement(Activation::sigmoidPrime));
        djdw0 = delta2.preMultiply(x.transpose()).add(w0.multiply(normCoefficient));
    }

    public Tensor getyHat() {
        return yHat;
    }
}
