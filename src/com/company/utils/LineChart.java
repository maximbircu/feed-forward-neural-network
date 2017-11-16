package com.company.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

public class LineChart extends ApplicationFrame {
    private JFreeChart chart;
    private DefaultCategoryDataset dataSet;
    private int width = 1024;
    private int height = 768;


    public LineChart(String title, String xlabel, String ylabel) {
        super(title);
        dataSet = new DefaultCategoryDataset();
        chart.getXYPlot().setRenderer(new XYAreaRenderer());
        chart = ChartFactory.createLineChart(title, ylabel, ylabel, dataSet, PlotOrientation.VERTICAL, false, true, false);
        setupChartWindow();
    }

    public LineChart() {
        super("");
        dataSet = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart("",
                "",
                "",
                dataSet, PlotOrientation.VERTICAL, false, true, false);
        setupChartWindow();
    }

    public LineChart(String title) {
        super(title);
        dataSet = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(title,
                "",
                "",
                dataSet, PlotOrientation.VERTICAL, false, true, false);
        setupChartWindow();
    }

    private void setupChartWindow() {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(width, height));
        setContentPane(chartPanel);

        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void addNewValue(Number number, Comparable columnKey) {
        addNewValue(number, "", columnKey);
    }

    public void addNewValue(Number number, Comparable rowKey, Comparable columnKey) {
        if(!this.isVisible()){
            this.setVisible(true);
        }
        dataSet.addValue(number, rowKey, columnKey);
        chart.getPlot().datasetChanged(new DatasetChangeEvent(this, dataSet));
    }
}
