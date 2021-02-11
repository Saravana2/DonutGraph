# DonutGraph

Custom donut graph view with customizable parameters 

```sh
<com.example.saravananthangamari.donutgraph.Views.DonutView
    android:id="@+id/donut_graph"
    android:layout_width="400dp"
    android:layout_height="400dp"
    android:layout_marginLeft="25dp"
    android:layout_marginTop="25dp"
    android:background="@drawable/canvas_background"
    />
```

```sh
DonutGraphData donutGraphData=new DonutGraphData(donutWidth,percentage,fieldname,colors);
DonutView view=(DonutView)findViewById(R.id.donut_graph);
view.setDonutGraphValues(donutGraphData);
view.start(percentage.size());
```
