package es.uam.eps.bmi.search.test;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import es.uam.eps.bmi.search.index.Index;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class BarChart{
    private XYSeries series;
    private XYSeriesCollection dataset;


    public BarChart(String description) {
        this.series = new XYSeries(description);
        this.dataset = new XYSeriesCollection();
    }

    public XYSeriesCollection createDataSet(BarChart barChart, List<String> terms, String type, Index index)throws IOException {
        double position = 1;

        if(type == "a"){
            for (String term : terms) {

                barChart.series.add(Math.log(position)/ Math.log(2),Math.log(index.getTotalFreq(term))/Math.log(2));
                position++;
            }
        }else{
            for (String term : terms) {
                barChart.series.add(Math.log(position)/ Math.log(2),Math.log(Integer.parseInt(index.getDocFreq(term)))/Math.log(2));
                position++;
            }
        }

        barChart.dataset.addSeries(barChart.series);
        return barChart.dataset;
    }

    public void createBarChart(String tagX, String tagY,String description){

        LogAxis xAxis = new LogAxis(tagX);
        xAxis.setSmallestValue(1);

        xAxis.setBase(2);
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


        LogAxis yAxis = new LogAxis(tagY);

        yAxis.setBase(2);
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setSmallestValue(1);


        XYPlot plot = new XYPlot(new XYSeriesCollection(series), xAxis, yAxis, new XYSplineRenderer());
        plot.setBackgroundPaint(new Color(255,228,196));



        JFreeChart chart = new JFreeChart(tagY, JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        //Changes background color
        ChartPanel chartPanel = new ChartPanel(chart);
        chart.setBackgroundPaint(Color.white);
        chart.addSubtitle(new TextTitle(description));
        chartPanel.setFillZoomRectangle(false);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(500, 270));

        ChartFrame frame = new ChartFrame("Gráfica log-log", chart);
        frame.pack();
        frame.setVisible(true);
    }
}

