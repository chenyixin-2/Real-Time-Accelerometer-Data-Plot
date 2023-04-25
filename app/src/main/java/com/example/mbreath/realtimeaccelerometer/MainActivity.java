package com.example.mbreath.realtimeaccelerometer;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class ClearDataTask extends TimerTask {
    private LineChart chart = null;
    public ClearDataTask(LineChart chart) {
        this.chart = chart;
    }
    public void run() {
        //calculate the new position of myBall
        if(chart!= null && chart.getLineData() != null) {
            chart.getLineData().clearValues();
        }
    }
}

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Timer timer = new Timer();
    private static final String TAG = "MainActivity";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private  Sensor sensors;

    private LineChart mChart;
    private Thread thread;
    private boolean plotData = true;

    private int samplePeriodUs = 50;
    private long sampleCount = 0;
    private long sampleTimeSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(int i=0; i<sensors.size(); i++){
            Log.d(TAG, "onCreate: Sensor "+ i + ": " + sensors.get(i).toString());
        }

        if (mAccelerometer != null) {
            boolean breg = mSensorManager.registerListener(this, mAccelerometer, samplePeriodUs);
            Log.d(TAG, "onCreate: Sensor registered: " + breg);
        }

        mChart = (LineChart) findViewById(R.id.chart1);

        // enable description text
        mChart.getDescription().setEnabled(true);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(50f);
        leftAxis.setAxisMinimum(-50f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.setDrawBorders(false);

        //TimerTask cleartask = new ClearDataTask(mChart);
        //timer.scheduleAtFixedRate(cleartask, 0, 1000);

        TimerTask sampletask = new TimerTask() {
            @Override
            public void run() {
                sampleTimeSec++;
                Log.d("SampleRateTask", "every minutes smaples : " + (float)sampleCount/(float)sampleTimeSec);
            }
        };
        timer.scheduleAtFixedRate(sampletask, 0, 1000);
        feedMultiple();
    }

    private void addEntry(SensorEvent event) {

        LineData data = mChart.getData();
        int timeCount = 10;
        sampleCount+=timeCount;
        if (data != null) {

            {
                int idx = 0;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.BLACK);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 1;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.DKGRAY);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 2;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.GRAY);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 3;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.LTGRAY);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 4;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.WHITE);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 5;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.RED);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 6;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.GREEN);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 7;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.BLUE);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 8;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.YELLOW);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 9;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.CYAN);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }
            {
                int idx = 10;
                ILineDataSet set = data.getDataSetByIndex(idx);
                if (set == null) {
                    set = createSet(Color.MAGENTA);
                    data.addDataSet(set);
                }
                for (int i = 0; i < timeCount; ++i) {
                    data.addEntry(new Entry(set.getEntryCount(), event.values[0] + -5 + idx), idx);
                }
            }

            data.notifyDataChanged();
            data.calcMinMaxY(data.getXMin(), data.getXMax());
            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(150);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());
            mChart.setVisibleYRange(-100, 50, YAxis.AxisDependency.LEFT);
        }
    }

    private LineDataSet createSet(int color) {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3f);
        set.setColor(color);
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        return set;
    }

    private void feedMultiple() {

        if (thread != null){
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true){
                    plotData = true;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (thread != null) {
            thread.interrupt();
        }
        mSensorManager.unregisterListener(this);

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if(plotData){
            addEntry(event);
            plotData = false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean breg = mSensorManager.registerListener(this, mAccelerometer, samplePeriodUs);
        Log.d(TAG, "onCreate: Sensor registered: " + breg);
    }

    @Override
    protected void onDestroy() {
        mSensorManager.unregisterListener(MainActivity.this);
        thread.interrupt();
        super.onDestroy();
    }
}
