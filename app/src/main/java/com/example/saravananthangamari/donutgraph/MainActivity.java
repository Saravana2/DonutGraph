package com.example.saravananthangamari.donutgraph;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.saravananthangamari.donutgraph.Views.DonutView;
import com.example.saravananthangamari.donutgraph.model.DonutGraphData;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int canvasWidth=0,canvasHeight=0,canvasMarginLeft=0,canvasMarginTop=0,canvasTransparency=0,donutWidth=0;

        List<Float> percentage=new ArrayList<>();
        List<String> fieldname=new ArrayList<>();
        List<Integer> colors=new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            InputStream is=getResources().openRawResource(R.raw.donut_graph);

            dBuilder=documentBuilderFactory.newDocumentBuilder();
            Document doc=dBuilder.parse(is);
            //Log.i("node name", doc.getDocumentElement().getNodeName());

            Node donut_graph = doc.getDocumentElement();
            if (donut_graph.getNodeType() == Node.ELEMENT_NODE) {
                Node canvas=doc.getElementsByTagName("canvas").item(0);
                if(canvas.getNodeType()==Node.ELEMENT_NODE){
                    Element ele=(Element)canvas;
                    canvasMarginLeft= Integer.parseInt(getValue("canvas_x",ele));
                    canvasMarginTop=Integer.parseInt(getValue("canvas_y",ele));
                    canvasWidth=Integer.parseInt(getValue("canvas_width",ele));
                    canvasHeight=Integer.parseInt(getValue("canvas_height",ele));
                    canvasTransparency=(Integer.parseInt(getValue("canvas_transparency",ele))*255)/100;
                   Log.i("canvas transparency", String.valueOf(canvasTransparency));
                }

                donutWidth=Integer.parseInt(doc.getElementsByTagName("donut_width").item(0).getTextContent());

                NodeList fields=doc.getElementsByTagName("field");
                for(int i=0;i<fields.getLength();i++){
                    Node field=fields.item(i);
                    if(field.getNodeType()==Node.ELEMENT_NODE){
                        Element fieldEle=(Element)field;
                        fieldname.add(getValue("field_name",fieldEle));
                        percentage.add(Float.parseFloat(getValue("field_value",fieldEle)));
                        colors.add(Color.parseColor(getValue("field_color",fieldEle)));
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        DonutGraphData donutGraphData=new DonutGraphData(canvasWidth,canvasHeight,donutWidth,percentage,fieldname,colors);

        RelativeLayout r=(RelativeLayout)findViewById(R.id.rl);
        DonutView view=new DonutView(this);
        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(canvasWidth,canvasHeight);
        rl.setMargins(canvasMarginLeft,canvasMarginTop,0,0);
        view.setLayoutParams(rl);
        r.addView(view);
        view.setBackgroundColor(Color.BLUE);
        Drawable d=view.getBackground();
        d.setAlpha(255-canvasTransparency);
        view.setDonutGraphValues(donutGraphData);
        view.start(percentage.size());


    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public float convertPxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}
