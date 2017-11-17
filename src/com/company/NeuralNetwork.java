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
    private Tensor a1;

    private Tensor djdw0;
    private Tensor djdw1;

    private double learningRate;

    public NeuralNetwork(int featuresCount, int batchSize, int hiddenUnits, int classesCount) {
        w0 = Tensor.nextTensor(featuresCount, hiddenUnits);
        w1 = Tensor.nextTensor(hiddenUnits, classesCount);
    }

    public void feedForward(Tensor x){
        z0 = x.multiply(w0);
        a0 = z0.applyToEachElement(Activation::sigmoid);


    }
}
