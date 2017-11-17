package com.company;

import com.company.data.TestingData;
import com.company.data.TrainingData;
import com.company.utils.BinaryUtils;
import com.company.utils.LineChart;
import com.company.utils.TextToSpeechConverter;

import java.util.Scanner;

public class Main {

    private static int hiddenUnits = 60;
    private static double learningRate = 0.4;
    private static double desiredError = 1;
    private static int batchSize = TrainingData.x.getRowsCount();
    private static int featuresCount = TrainingData.x.getColumnsCount();
    private static int classesCount = TrainingData.x.getColumnsCount();
    private static LineChart lineChart = new LineChart();
    private static NeuralNetwork neuralNetwork;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        neuralNetwork = new NeuralNetwork(featuresCount, batchSize, hiddenUnits, classesCount, learningRate);
        train();
        TextToSpeechConverter.getInstance().speak("Hi, I am, a robot that was trained to count, Give me a number from 0 to 255, and I'll give you it successor");
        while (true) {
            System.out.println("Enter your value: ");
            int number = scanner.nextInt();
            if(number >= 255){
                TextToSpeechConverter.getInstance().speak("Sorry, but I was trained to count up to 255");
                TextToSpeechConverter.getInstance().speak("I don't know what are the next numbers after 255");
                continue;
            }
            neuralNetwork.feedForward(BinaryUtils.getTensorValueFromInt(number,featuresCount));
            int nextNumber = BinaryUtils.binaryStringToDecimal(BinaryUtils.getBinaryString(neuralNetwork.getyHat().getData()[0]));
            TextToSpeechConverter.getInstance().speak(String.format("the next number after %d, is %d",number,nextNumber));
            System.out.println(nextNumber);
        }
    }

    public static void train() {
        neuralNetwork.feedForward(TrainingData.x);
        double error = neuralNetwork.getError(TrainingData.y);
        int iterations = 0;
        while (error > desiredError) {
            iterations++;
            neuralNetwork.epoch(TrainingData.x, TrainingData.y);
            error = neuralNetwork.getError(TrainingData.y);
            System.out.println(error);
            lineChart.addNewValue(error, "error", iterations);
        }
    }
}
