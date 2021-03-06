package cn.mandata.react_native_mpchart;

import android.graphics.Color;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;


public class MPPieChartManager extends MPPieRadarChartManager {
    private String CLASS_NAME="MPPieChart";

    @Override
    public String getName() {
        return this.CLASS_NAME;
    }

    @Override
    protected PieChart createViewInstance(ThemedReactContext reactContext) {
        PieChart chart= new PieChart(reactContext);
        return chart;
    }

    @ReactProp(name = "holeRadius", defaultFloat = 50f)
    public void setHoleRadius(PieChart chart, float holeRadius){
        chart.setHoleRadius(holeRadius);
        chart.invalidate();
    }

    @ReactProp(name="drawSliceText", defaultBoolean = false)
    public void setDrawSliceText(PieChart chart, boolean enabled){
        chart.setDrawSliceText(enabled);
        chart.invalidate();
    }

    @ReactProp(name="usePercentValues", defaultBoolean = false)
    public void setUsePercentValues(PieChart chart, boolean enabled){
        chart.setUsePercentValues(enabled);
        chart.invalidate();
    }

    @ReactProp(name="centerText")
    public void setCenterText(PieChart chart, String v){
        chart.setCenterText(v);
        chart.invalidate();
    }

    @ReactProp(name = "centerTextRadiusPercent", defaultFloat = 1.f)
    public void setCenterTextRadiusPercent(PieChart chart, float percent){
        chart.setCenterTextRadiusPercent(percent);
        chart.invalidate();
    }

    @ReactProp(name="data")
    public void setData(PieChart chart,ReadableMap rm){

        ReadableArray xArray=rm.getArray("xValues");
        ArrayList<String> xVals=new ArrayList<String>();
        for(int m=0;m<xArray.size();m++){
            xVals.add(xArray.getString(m));
        }
        ReadableArray ra=rm.getArray("yValues");
        PieData pieData=new PieData(xVals);
        for(int i=0;i<ra.size();i++){
            ReadableMap map=ra.getMap(i);
            ReadableArray data=map.getArray("data");
            String label=map.getString("label");
            float[] vals=new float[data.size()];
            ArrayList<Entry> entries=new ArrayList<Entry>();
            for (int j=0;j<data.size();j++){
                vals[j]=(float)data.getDouble(j);
                Entry be=new Entry((float)data.getDouble(j),j);
                entries.add(be);
            }
            PieDataSet dataSet=new PieDataSet(entries,label);
            ReadableMap config= map.getMap("config");
            if(config.hasKey("colors")){
                ReadableArray colorsArray = config.getArray("colors");
                ArrayList<Integer> colors = new ArrayList<>();
                for(int c = 0; c < colorsArray.size(); c++){
                    colors.add(Color.parseColor(colorsArray.getString(c)));
                }
                dataSet.setColors(colors);
            }else
            if(config.hasKey("color")) {
                int[] colors=new int[]{Color.parseColor(config.getString("color"))};
                dataSet.setColors(colors);
            }
            pieData.addDataSet(dataSet);

        }
        chart.setBackgroundColor(Color.WHITE);
        chart.setData(pieData);
        chart.invalidate();
    }
}
