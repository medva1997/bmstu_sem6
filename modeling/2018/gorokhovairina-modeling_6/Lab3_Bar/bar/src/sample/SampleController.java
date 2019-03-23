package sample;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.NumberStringConverter;

public class SampleController {

    public LineChart linechart;
    public TextField barL;
    public TextField k0;
    public TextField barR;
    public TextField p;
    public TextField F0;
    public TextField a0;
    public TextField Tos;
    public TextField aN;
    public TextField step;
    public TextField teta;

    public void getGraph(ActionEvent actionEvent) {
        int n;

        Constants.barL = Float.valueOf(barL.getText());
        Constants.barR = Float.valueOf(barR.getText());
        Constants.F0 = Float.valueOf(F0.getText());
        Constants.tempEnv = Float.valueOf(Tos.getText());
        Constants.k0 = Float.valueOf(k0.getText());
        Constants.p = Float.valueOf(p.getText());
        Constants.alpha0 = Float.valueOf(a0.getText());
        Constants.alphaN = Float.valueOf(aN.getText());
        Constants.teta = Float.valueOf(teta.getText());
        n = Integer.valueOf(step.getText());

        SweepMethod answer = new SweepMethod(n);
        float[] y = answer.getYn();
        float h = Constants.barL/n;

        //linechart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();

        for (int i = 0; i <= n; i++) {
            series.getData().add(new XYChart.Data<Number, Number>(i * h, y[i]));
            System.out.printf("%10.3f %10.3f\n", i*h, y[i]);
        }
        series.setName(String.valueOf(n));
        linechart.getData().add(series);
        System.out.println("-------------------------------------------");


    }

    public void clearGraph(ActionEvent actionEvent) {
        linechart.getData().clear();
    }
}
