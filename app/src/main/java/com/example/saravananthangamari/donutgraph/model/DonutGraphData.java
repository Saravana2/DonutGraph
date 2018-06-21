package com.example.saravananthangamari.donutgraph.model;

import android.graphics.Color;

import java.io.Serializable;
import java.util.List;

public class DonutGraphData implements Serializable {
    int donutWidth;
    List<Float> percentage;
    List<String> fieldName;
    List<Integer> colors;

    public DonutGraphData(int donutWidth, List<Float> percentage, List<String> fieldName,List<Integer> colors) {
        this.donutWidth = donutWidth;
        this.percentage = percentage;
        this.fieldName = fieldName;
        this.colors=colors;
    }

    public int getDonutWidth() {
        return donutWidth;
    }

    public void setDonutWidth(int donutWidth) {
        this.donutWidth = donutWidth;
    }

    public List<Float> getPercentage() {
        return percentage;
    }

    public void setPercentage(List<Float> percentage) {
        this.percentage = percentage;
    }

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;
    }

    public List<Integer>  getColors() {
        return colors;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }


}
