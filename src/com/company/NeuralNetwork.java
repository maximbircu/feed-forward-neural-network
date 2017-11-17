package com.company;

import com.company.utils.Activation;
import com.company.utils.Tensor;

import javax.swing.*;

class NeuralNetwork {
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

    NeuralNetwork(int featuresCount, int batchSize, int hiddenUnits, int classesCount, double learningRate) {
        w0 = Tensor.nextTensor(featuresCount, hiddenUnits);
        w1 = Tensor.nextTensor(hiddenUnits, classesCount);
        this.learningRate = learningRate;

        bias0 = Tensor.nextTensor(batchSize, hiddenUnits);
        bias1 = Tensor.nextTensor(batchSize, classesCount);
    }

    void epoch(Tensor x, Tensor y){
        feedForwardWithBias(x);
        backPropagate(x,y);
        adjustWeights();
    }

    private void adjustWeights() {
        w0 = w0.subtract(djdw0.multiply(learningRate));
        w1 = w1.subtract(djdw1.multiply(learningRate));
    }

    double getError(Tensor y){
        return 0.5 * y.subtract(yHat).square().sum() + normCoefficient/2 * (w0.square().sum() + w1.square().sum());
    }

    private void feedForwardWithBias(Tensor x){
        z0 = x.multiply(w0);
        z0.add(bias0);
        a0 = z0.applyToEachElement(Activation::sigmoid);

        z1 = a0.multiply(w1);
        z1.add(bias1);
        yHat = z1.applyToEachElement(Activation::sigmoid);
    }

    void feedForward(Tensor x) {
        // TODO implement here feed forward algorithm for this neural net.
    }

    private void backPropagate(Tensor x, Tensor y) {
        // TODO implement here back-propagation training algorithm for this neural net.
    }

    Tensor getYHat() {
        return yHat;
    }
}
