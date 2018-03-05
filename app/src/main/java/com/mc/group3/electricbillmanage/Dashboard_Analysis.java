package com.mc.group3.electricbillmanage;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard_Analysis extends Fragment {


    public Dashboard_Analysis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_analysis, container, false);

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(1, 3),
                new DataPoint(2, 6),
                new DataPoint(3, 2),
                new DataPoint(4, 5)
        });

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(1, 2),
                new DataPoint(2, 7),
                new DataPoint(3, 3),
                new DataPoint(4, 7)
        });

        series.setTitle("Random Curve 1");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{2.0f,1.0f}, 0));
        series.setCustomPaint(paint);


        series2.setTitle("Random Curve 2");
        series2.setDrawDataPoints(true);
        series2.setDrawAsPath(true);
        series2.setDataPointsRadius(10);
        series2.setColor(Color.RED);

        graph.addSeries(series);
        graph.addSeries(series2);

        return view;
    }

}
