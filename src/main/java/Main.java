import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    private static final int HEIGHT = 720;
    private static final int WIDTH = 1080;

    final static int[] Y = new int[]{5, 6, 8, 10, 12, 13, 12, 10, 8, 10, 8, 11, 7, 9, 11, 10, 9, 12, 11, 6};
    final static int[] X = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private final static ArrayList<Double> abs = new ArrayList<>();
    private final static ArrayList<Double> functionValues = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle("Graphic");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Lab 3");
        //populating the series with data

        for (int i = 0; i < abs.size(); i++) {
            series.getData().add(new XYChart.Data(abs.get(i), functionValues.get(i)));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }


    @FunctionalInterface
    public interface Operation {
        double execute(double... nums);
    }

    public static double differenceFirst(int index2, int index1) {
        return (double) (Y[index2] - Y[index1]) / (X[index2] - X[index1]);
    }

    public static double differenceSecond(int index3, int index2, int index1) {
        return (double) (differenceFirst(index3, index2) - differenceFirst(index2, index1))
                / (X[index3] - X[index1]);
    }

    public static double differenceThird(int index4, int index3, int index2, int index1) {
        return (double) (differenceSecond(index4, index3, index2) - differenceSecond(index3, index2, index1))
                / (X[index4] - X[index1]);
    }

    public static double func(double x, int index) {
        return (double) (Y[index] + (x - X[index]) *
                (differenceFirst(index + 1, index) + (x - X[index + 1]) *
                        (differenceSecond(index + 2, index + 1, index) + (x - X[index + 3]) *
                                differenceThird(index + 3, index + 2, index + 1, index))));
    }

    public static void main(String[] args) {
        double x = 1;
        int k = 0;


        while (x <= 20) {
            if (x < 4) {
                functionValues.add(func(x, 0));
            } else if (x >= 17) {
                functionValues.add(func(x, 16));
            } else {
                functionValues.add(func(x, ((int) x) - 2));
            }
            abs.add(x);
            System.out.printf("%f : f(x) = %.4f", x, functionValues.get(k));
            System.out.println();
            k += 1;
            x += 0.25;
        }
        Application.launch(args);

    }


}