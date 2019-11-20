package com.seguridadinteligente.domotica;


import android.content.Intent;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class ShowSenActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener, View.OnClickListener {

    public int sensorvalueh, sensorvalue, sensorvaluei,sensorvalue1,sensorvalue2,sensorvalue3,sensorvalue4,sensorvalueh1,sensorvalueh2,sensorvalueh3,sensorvalueh4,sensorvaluei4,sensorvaluei1,sensorvaluei2,sensorvaluei3;
    private LineChart mChart;
    public Button Buttonng,Refresh;
    private int contadorrefrsh = 0;
    private int valor2;
    public ArrayList yVals1, yVals2, yVals3;
    private String SensorValue;
    public int sv, sv2;
    private String SensorValue2; // modificados a int para el child listener
    public DatabaseReference Users;
    public ValueEventListener valueEventListener;
    public long ts;

    public int intval, intval2;

    public int refint = 0,refint2 = 0,refint3 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_showsen);

        Buttonng = (Button) findViewById(R.id.buttonng);
        Buttonng.setOnClickListener(this);

        Refresh = (Button) findViewById(R.id.refresh);
        Refresh.setOnClickListener(this);



        mChart = (LineChart) findViewById(R.id.linechart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        setData();

        Users = FirebaseDatabase.getInstance().getReference("users");

        Legend l = mChart.getLegend();

        l.setForm(Legend.LegendForm.LINE);

        mChart.setDescription("Temperatura Humedad e iluminacion");
        mChart.setNoDataTextDescription("Los Datos No Estan Siendo Verdaderos.");

        mChart.setTouchEnabled(true);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        LimitLine upper_limit = new LimitLine(45f, "Limite Superior De Temperatura");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);


        LimitLine upper_limit2 = new LimitLine(100f, "Limite Superior De Humedad");
        upper_limit2.setLineWidth(4f);
        upper_limit2.enableDashedLine(10f, 10f, 10f);
        upper_limit2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit2.setTextSize(10f);


        LimitLine lower_limit = new LimitLine(-30f, "Limite Inferior De Temperatura");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);

        LimitLine lower_limit2 = new LimitLine(0f, "Limite Inferior De Humedad");
        lower_limit2.setLineWidth(4f);
        lower_limit2.enableDashedLine(10f, 10f, 10f);
        lower_limit2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(upper_limit2);
        leftAxis.addLimitLine(lower_limit);
        leftAxis.addLimitLine(lower_limit2);
        leftAxis.setAxisMaxValue(130f);
        leftAxis.setAxisMinValue(-50f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);


        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        mChart.notifyDataSetChanged();

        mChart.invalidate();

        Users.child("utilizacion").child("SpinnSensores").child("s1").child("temperatura").addValueEventListener(new ValueEventListener() {

//Temperatura
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             //   refint = refint++;

            //    Toast.makeText(ShowSenActivity.this, refint, Toast.LENGTH_SHORT).show();

                mChart = (LineChart) findViewById(R.id.linechart);

                final ArrayList<Entry> yData1 = new ArrayList<>();

                sensorvalue = (dataSnapshot.getValue(Integer.class));

                setYAxisValues1();
                //setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//humedad
        Users.child("utilizacion").child("SpinnSensores").child("s2").child("humedad").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            //    refint2 = refint2++ ;
            //    Toast.makeText(ShowSenActivity.this,refint2,Toast.LENGTH_SHORT).show();

                mChart = (LineChart) findViewById(R.id.linechart);

                final ArrayList<Entry> yData2 = new ArrayList<>();

                sensorvalueh = (dataSnapshot.getValue(Integer.class));



                setData();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Users.child("utilizacion").child("SpinnSensores").child("s7").child("iluminacion").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

           //     refint3 = refint3++;
             //   Toast.makeText(ShowSenActivity.this, refint3, Toast.LENGTH_SHORT).show();

                mChart = (LineChart) findViewById(R.id.linechart);

                final ArrayList<Entry> yData3 = new ArrayList<>();

                sensorvaluei = (dataSnapshot.getValue(Integer.class));


                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private ArrayList<String> setXAxisValues() {
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10M");
        xVals.add("20M");
        xVals.add("30M");
        xVals.add("40M");
        xVals.add("50M");

        return xVals;
    }

    //Temperatura

        public ArrayList<Entry> setYAxisValues1() {

            ArrayList<Entry> yVals1 = new ArrayList<Entry>();

            if (contadorrefrsh == 0) {

                yVals1.add(new Entry(5, 0));
                yVals1.add(new Entry(10, 1));
                yVals1.add(new Entry(10, 2));
                yVals1.add(new Entry(17, 3));
                yVals1.add(new Entry(sensorvalue, 4));

            }
            if (contadorrefrsh == 1){

                mChart = (LineChart) findViewById(R.id.linechart);
                final ArrayList<Entry> yData1 = new ArrayList<>();

                yVals1.add(new Entry(sensorvalue, 0));
                yVals1.add(new Entry(10, 1));
                yVals1.add(new Entry(10, 2));
                yVals1.add(new Entry(17, 3));
                yVals1.add(new Entry(14, 4));
            }
            if (contadorrefrsh == 2){


                mChart = (LineChart) findViewById(R.id.linechart);
                final ArrayList<Entry> yData1 = new ArrayList<>();

                yVals1.add(new Entry(5, 0));
                yVals1.add(new Entry(sensorvalue, 1));
                yVals1.add(new Entry(10, 2));
                yVals1.add(new Entry(17, 3));
                yVals1.add(new Entry(14, 4));
            }
            if (contadorrefrsh == 3){


                mChart = (LineChart) findViewById(R.id.linechart);
                final ArrayList<Entry> yData1 = new ArrayList<>();

                yVals1.add(new Entry(5, 0));
                yVals1.add(new Entry(10, 1));
                yVals1.add(new Entry(sensorvalue, 2));
                yVals1.add(new Entry(17, 3));
                yVals1.add(new Entry(14, 4));

            }
            if (contadorrefrsh == 4){


                mChart = (LineChart) findViewById(R.id.linechart);
                final ArrayList<Entry> yData1 = new ArrayList<>();

                yVals1.add(new Entry(5, 0));
                yVals1.add(new Entry(10, 1));
                yVals1.add(new Entry(10, 2));
                yVals1.add(new Entry(sensorvalue, 3));
                yVals1.add(new Entry(14, 4));

            }

            if (contadorrefrsh == 5){


                mChart = (LineChart) findViewById(R.id.linechart);
                final ArrayList<Entry> yData1 = new ArrayList<>();

                yVals1.add(new Entry(sensorvalue, 0));
                yVals1.add(new Entry(10, 1));
                yVals1.add(new Entry(10, 2));
                yVals1.add(new Entry(17, 3));
                yVals1.add(new Entry(sensorvalue, 4));

            }
            return yVals1;
        }


    //Humedad
    public ArrayList<Entry> setYAxisValues2() {
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        yVals2.add(new Entry(88, 0));
        yVals2.add(new Entry(100, 1));
        yVals2.add(new Entry(79, 2));
        yVals2.add(new Entry(90, 3));
        yVals2.add(new Entry(sensorvalueh, 4));


        return yVals2;
    }

//iluminacion
    public ArrayList<Entry> setYAxisValues3() {

ArrayList<Entry> yVals3 = new ArrayList<Entry>();

        yVals3.add(new Entry(5, 0));
        yVals3.add(new Entry(98, 1));
        yVals3.add(new Entry(89, 2));
        yVals3.add(new Entry(100, 3));
        yVals3.add(new Entry(sensorvaluei, 4));

        
        return yVals3;
    }


    public void setData() {

        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals1 = setYAxisValues1();

        ArrayList<Entry> yVals2 = setYAxisValues2();

        ArrayList<Entry> yVals3 = setYAxisValues3();

        LineDataSet set1, set2,set3;



        set1 = new LineDataSet(yVals1, "Temperatura");
        set2 = new LineDataSet(yVals2, "Humedad");
        set3 = new LineDataSet(yVals3,"Iluminacion");

        set1.setFillAlpha(110);
        set2.setFillAlpha(110);
        set3.setFillAlpha(110);
        // set1.setFillColor(Color.RED);n


        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        set2.setColor(Color.BLACK);
        set2.setCircleColor(Color.BLACK);
        set2.setLineWidth(1f);
        set2.setCircleRadius(3f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(9f);
        set2.setDrawFilled(true);

        set3.setColor(Color.GRAY);
        set3.setCircleColor(Color.GRAY);
        set3.setLineWidth(1f);
        set3.setCircleRadius(3f);
        set3.setDrawCircleHole(false);
        set3.setValueTextSize(9f);
        set3.setDrawFilled(true);


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);


        LineData data = new LineData(xVals, dataSets);


        mChart.setData(data);

        setYAxisValues1();

    }

    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture
                                            lastPerformedGesture) {

        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {

        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);


        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)

            mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex()
                + ", high: " + mChart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin()
                + ", xmax: " + mChart.getXChartMax()
                + ", ymin: " + mChart.getYChartMin()
                + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    protected void onStart() {
        super.onStart();

        setData();
    }

    @Override
    public void onResume() {

    setData();
        super.onResume();
    }
    private void Refresh(View view){

        contadorrefrsh ++;

        Intent intent = new Intent(view.getContext(), ShowSenActivity.class);
        startActivityForResult(intent, 0);

      setData();
    }
    private void Interact(View view) {

        Intent intent = new Intent(view.getContext(), InteractActivity.class);
        startActivityForResult(intent, 0);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                Refresh(view);
                break;
            case R.id.buttonng:
                Interact(view);
                break;
        }
    }
}


