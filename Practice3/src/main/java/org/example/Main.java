package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;
import org.apache.commons.math3.special.Erf;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //######################Task1#############################
        XYSeries f_10_2 = new XYSeries("f(m = 10, tetta = 2)");
        XYSeries f_10_1 = new XYSeries("f(m = 10, tetta = 1)");
        XYSeries f_10_1_2 = new XYSeries("f(m = 10, tetta = 0.5)");
        XYSeries f_12_1 = new XYSeries("f(m = 12, tetta = 1)");

        for (float i = 0; i <= 20; i += 0.1) {
            f_10_2.add(i, normalDistributionD(i, 10, 2));
            f_10_1.add(i, normalDistributionD(i, 10, 1));
            f_10_1_2.add(i, normalDistributionD(i, 10, 0.5));
            f_12_1.add(i, normalDistributionD(i, 12, 1));
        }
        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(f_10_1);
        xyDataset.addSeries(f_10_2);
        xyDataset.addSeries(f_10_1_2);
        xyDataset.addSeries(f_12_1);
        JFreeChart chart = ChartFactory
                .createXYLineChart("Probability density", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        showChart(chart);
        //saveAsPng("density_chart.png", chart);*/
        //######################Task2#############################
        /*XYSeries F_10_2 = new XYSeries("F(m = 10, tetta = 2)");
        for (float i = 0; i <= 20; i += 0.1) {
            F_10_2.add(i, normalDistributionF(i, 10, 2));
        }
        XYDataset normDistrF = new XYSeriesCollection(F_10_2);
        JFreeChart normDistrChart = ChartFactory.createXYLineChart("Normal distribution function",
                "x", "y", normDistrF);
        showChart(normDistrChart);
        //saveAsPng("func_chart.png", chart);*/
        //######################Task3#############################
        /*XYSeries series = getTabF(0, 20, 100);
        double[] values1000 = getValues(series, ((int) pow(10, 3)));
        double[] freqDistr1000 = getFreqDistr(values1000, 0, 20, 100);

        double[] values10000 = getValues(series, ((int) pow(10, 4)));
        double[] freqDistr10000 = getFreqDistr(values10000, 0, 20, 100);

        double[] values100000 = getValues(series, ((int) pow(10, 5)));
        double[] freqDistr100000 = getFreqDistr(values100000, 0, 20, 100);

        double[] values1000000 = getValues(series, ((int) pow(10, 6)));
        double[] freqDistr1000000 = getFreqDistr(values1000000, 0, 20, 100);
        //######################Task4#############################
        double[] values100 = getValues(series, ((int) pow(10, 2)));
        double[] freqDistr100 = getFreqDistr(values100, 0, 20, 100);
        JFreeChart bars_100 = createChart(((int) pow(10, 2)), series, freqDistr100);
        //saveAsPng("barchart100.png", bars_100);

        JFreeChart bars_1000 = createChart(((int) pow(10, 3)), series, freqDistr1000);
        //saveAsPng("barchart1000.png", bars_1000);

        JFreeChart bars_10000 = createChart(((int) pow(10, 4)), series, freqDistr10000);
        //saveAsPng("barchart10000.png", bars_10000);

        JFreeChart bars_100000 = createChart(((int) pow(10, 5)), series, freqDistr100000);
        //saveAsPng("barchart100000.png", bars_100000);*/
        //######################Task5#############################
        /*double d100 = getDeviation(freqDistr100, 100);
        double d1000 = getDeviation(freqDistr1000, 100);
        double d10000 = getDeviation(freqDistr10000, 100);
        double d100000 = getDeviation(freqDistr100000, 100);
        System.out.println("100 experiments: " + d100);
        System.out.println("1000 experiments: " + d1000);
        System.out.println("10000 experiments: " + d10000);
        System.out.println("100000 experiments: " + d100000);
        XYSeries dev = new XYSeries("Deviation series");
        dev.add(100, d100);
        dev.add(1000, d1000);
        dev.add(10000, d10000);
        dev.add(100000, d100000);
        XYDataset devDataset = new XYSeriesCollection(dev);
        JFreeChart devChart = ChartFactory.createXYLineChart("Deviation",
                "x", "y", devDataset);
        showChart(devChart);
        //saveAsPng("deviation_chart.png", devChart);*/
    }

    /**
     * Функция нормального распределения
     * @param x случайная величина
     * @param m мат. ожидание
     * @param tetta среднеквадратическое отклонение
     * @return значение ф-ии в точке x
     */
    public static double normalDistributionF(double x, double m, double tetta) {
        return 0.5 * (1 + Erf.erf((x - m) / sqrt(2 * pow(tetta, 2))));
    }

    /**
     * Плотность вероятности нормального распределения
     * @param x случайная величина
     * @param m мат. ожидание
     * @param tetta среднеквадратическое отклонение
     * @return значение ф-ии в точке x
     */
    public static double normalDistributionD(double x, double m, double tetta) {
        return (1 / (tetta * sqrt(2 * PI))) * Math.exp(-(pow(x - m, 2) / (2 * pow(tetta, 2))));
    }

    public static double getArg(double minArg, double maxArg, double value, double eps) {
        while ((maxArg - minArg) / maxArg > eps) {
            double midArg = (minArg + maxArg) / 2;
            double midVal = normalDistributionF(midArg, 10, 2);
            if (midVal > value)
                maxArg = midArg;
            else
                minArg = midArg;
        }
        return (minArg + maxArg) / 2;
    }

    public static XYSeries getTabF(double minArg, double maxArg, int pointsCount) {
        XYSeries series = new XYSeries("");
        double minVal = normalDistributionF(minArg, 10, 2);
        double maxVal = normalDistributionF(maxArg, 10, 2);
        double dVal = (maxVal - minVal) / (pointsCount - 1);
        series.add(minVal, minArg);
        for (int i = 0; i < pointsCount - 1; i++) {
            double y = minVal + dVal * i;
            series.add(getArg(minArg, maxArg, y, pow(10, -15)), y);
        }
        series.add(maxArg, maxVal);
        return series;
    }

    public static double modelN(XYSeries series, double p) {
        double y = 0;
        for (int i = 1; i < series.getItemCount(); i++) {
            double xTab0 = series.getX(i - 1).doubleValue();
            double xTab1 = series.getX(i).doubleValue();
            double yTab0 = series.getY(i - 1).doubleValue();
            double yTab1 = series.getY(i).doubleValue();
            if (p >= yTab0 && p <= yTab1){
                y = ((p - yTab1) / (yTab0 - yTab1)) * xTab0 + ((p - yTab0) / (yTab1 - yTab0)) * xTab1;
                break;
            }
        }
        return y;
    }

    public static double[] getValues(XYSeries series, int length) {
        double[] out = new double[length];
        for (int i = 0; i < length; i++)
            out[i] = modelN(series, Math.random());
        return out;
    }

    public static double[] getFreqDistr(double[] params, double A, double B, int intervals) {
        double dY = (B - A) / intervals;
        double[] freq = new double[intervals];
        for (double param : params)
            freq[((int) Math.floor(param / dY))] += 1;
        for (int i = 0; i < intervals; i++)
            freq[i] /= (params.length * dY);
        return freq;
    }
    
    public static JFreeChart createChart(int experimentsCount, XYSeries series, double[] freq) {
        //XYSeries series = getTabF(0, 20, 100);
        XYSeries elementsFreq = new XYSeries("Frequency");
        //double[] tmp = getValues(series, experimentsCount);
        //double[] tmpY = getFreqDistr(tmp, 0, 20, 100);
        for (int i = 0; i < freq.length; i++){
            elementsFreq.add((20.0 / 100)*(0.5 + i), freq[i]);
        }
        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(elementsFreq);
        XYBarDataset barDataset = new XYBarDataset(xyDataset, 0.2);
        return ChartFactory.createXYBarChart("Experiments count: " + experimentsCount,
                "interval", false, "frequency", barDataset);
    }

    public static double getDeviation(double[] frequency, int count) {
        double dev = 0;
        for (int i = 0; i < count; i++) {
            dev += pow((normalDistributionD((20.0 / 100)*(0.5 + i), 10, 2) - frequency[i]), 2) / count;
        }
        return dev;
    }

    public static void saveAsPng(String filename, JFreeChart chart) {
        File file = new File(filename);
        try {
            ChartUtils.saveChartAsPNG(file, chart, 800, 600);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void showChart(JFreeChart chart) {
        JFrame frame = new JFrame("");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(400,300);
        frame.setVisible(true);
    }
}