package sample;

import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.util.List;

public class SampleController {
    public TextField barL;
    public TextField k0;
    public TextField barR;
    public TextField p;
    public TextField F0;
    public TextField a0;
    public TextField Tos;
    public TextField step;
    public TextField aN;
    public TextField teta;
    public LineChart linechart;

    public void getGraph(ActionEvent actionEvent) {

        Constants.barL = Float.valueOf(barL.getText());
        Constants.barR = Float.valueOf(barR.getText());
        Constants.F0 = Float.valueOf(F0.getText());
        Constants.tempEnv = Float.valueOf(Tos.getText());
        Constants.alpha0 = Float.valueOf(a0.getText());
        Constants.alphaN = Float.valueOf(aN.getText());
        //int n = Integer.valueOf(step.getText());
        int n = 100;

        Algorithm answer = new Algorithm(n);
        List<Float[]> y = answer.getyN();
        float h = Constants.barL/n;

        linechart.getData().clear();

        for (int j = 0; j < y.size(); j+=2) {
            XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();


            for (int i = 0; i <= n; i++) {
                series.getData().add(new XYChart.Data<Number, Number>(i * h, y.get(j)[i]));
                System.out.printf("%10.3f %10.3f\n", i * h, y.get(j)[i]);
            }
            System.out.println(j+"-------------------------------------------");
            series.setName(String.valueOf(j));
            linechart.getData().add(series);
        }
    }

    public void clearGraph(ActionEvent actionEvent) {
        linechart.getData().clear();
    }
}

