package com.example.saravananthangamari.donutgraph.model;

import android.graphics.Color;

import java.io.Serializable;
import java.util.List;

public class DonutGraphData implements Serializable {

    int canvasWidth;
    int canvasHeight;
    int donutWidth;
    List<Float> percentage;
    List<String> fieldName;
    String imageURL;
    List<Integer> colors;

    public DonutGraphData(int canvasWidth, int canvasHeight, int donutWidth, List<Float> percentage, List<String> fieldName,List<Integer> colors) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.donutWidth = donutWidth;
        this.percentage = percentage;
        this.fieldName = fieldName;
        this.colors=colors;
    }
    public DonutGraphData(){

    }

    public float getCanvasWidth() {
        return canvasWidth;
    }

    public void setCanvasWidth(int canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public float getCanvasHeight() {
        return canvasHeight;
    }

    public void setCanvasHeight(int canvasHeight) {
        this.canvasHeight = canvasHeight;
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
