
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.apache.commons.math3.analysis.interpolation.*;
import org.jfree.ui.*;

public class OutsideLibraries extends JFrame {

    public void original(XYSeries series) {

        XYSeriesCollection collection = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createScatterPlot("Original", "x", "|sin(5x)|", collection);
        chart.removeLegend();
        ChartFrame frame = new ChartFrame("Original", chart);
        frame.setSize(500, 500);
        frame.setVisible(true);
        RefineryUtilities.positionFrameOnScreen(frame, .01, .5);
    }

    public void salt(XYSeries series) {
        XYSeriesCollection collection = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createScatterPlot("Salted", "x", "Salted y values", collection);
        chart.removeLegend();
        ChartFrame frame = new ChartFrame("Salted", chart);
        frame.setSize(500, 500);
        frame.setVisible(true);
        RefineryUtilities.positionFrameOnScreen(frame, .5, .5);
    }

    public void smooth(XYSeries series) {
        XYSeriesCollection collection = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("Smoothed", "x", "Smoothed y values", collection);
        chart.removeLegend();
        ChartFrame frame = new ChartFrame("Smoothed", chart);
        frame.setSize(500, 500);
        frame.setVisible(true);
        RefineryUtilities.positionFrameOnScreen(frame, .99, .5);
    }

    public XYSeries originalSeries() {
        XYSeries series = new XYSeries("Original", false, true);
        for (double i = 0; i <= 1; i += .001) {
            series.add(i, Math.abs(Math.sin(5 * i)));
        }
        return series;
    }

    public XYSeries saltedSeries(XYSeries original) {
        XYSeries salted = new XYSeries("Salted", false, true);
        ArrayList<Double> yValues = new ArrayList<>();
        Random rng = new Random();

        for (int i = 0; i < original.getItemCount(); i++) {
            yValues.add((Double) original.getY(i));
        }

        for (int i = 0; i < original.getItemCount(); i++) {
            int randomIndex = rng.nextInt(yValues.size());
            salted.add(original.getX(i), yValues.get(randomIndex));
            yValues.remove(randomIndex);
        }
        return salted;
    }

    public XYSeries smoothedSeries(XYSeries salted) {
        XYSeries smoothed = new XYSeries("Smoothed", false, true);
        LoessInterpolator smoother = new LoessInterpolator();

        int length = salted.getItemCount();
        double[] xValues = new double[length];
        double[] yValues = new double[length];

        for (int i = 0; i < length; i++) {
            xValues[i] = (double) salted.getX(i);
            yValues[i] = (double) salted.getY(i);
        }

        double[] smoothedYValues = smoother.smooth(xValues, yValues);
        for (int i = 0; i < xValues.length; i++) {
            smoothed.add(xValues[i], smoothedYValues[i]);
        }
        return smoothed;
    }

}
