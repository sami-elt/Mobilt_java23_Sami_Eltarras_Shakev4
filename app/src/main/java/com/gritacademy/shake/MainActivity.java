package com.gritacademy.shake;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private int color = 0;
    private boolean showProgBar;
    private ProgressBar x,y,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton t = findViewById(R.id.toggleButton);
        Button b = findViewById(R.id.button);
        x = findViewById(R.id.progBarX);
        y = findViewById(R.id.progBarY);
        z = findViewById(R.id.progBarZ);

        //Hide progressbar so its starts hidden
        x.setVisibility(View.GONE);
        y.setVisibility(View.GONE);
        z.setVisibility(View.GONE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //option to toggle progressbars to se sensordata
        t.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean
                    b) {
                showProgBar = b;
                if (b){
                    x.setVisibility(View.VISIBLE);
                    y.setVisibility(View.VISIBLE);
                    z.setVisibility(View.VISIBLE);
                } else {
                    x.setVisibility(View.GONE);
                    y.setVisibility(View.GONE);
                    z.setVisibility(View.GONE);
                }
            }
        });


        //knapp som byter text oc skapar en toast
        b.setOnClickListener( (e)-> {

            Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_LONG).show();
            Log.i("clicked", "button");

            if (color == 0){
                b.setText("Button");
                b.setBackgroundColor(getResources().getColor(R.color.red));
                color = 1;
            } else if (color == 1 ){
                b.setText("Knapp");
                b.setBackgroundColor(getResources().getColor(R.color.blue));
                color = 0;
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.catlist,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        ImageView imagev = findViewById(R.id.imageView2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        imagev.setImageResource(R.drawable.nedladdning);
                        break;
                    case 1:
                        imagev.setImageResource(R.drawable.nedladdning__1_);
                        break;
                    case 2:
                        imagev.setImageResource(R.drawable.nedladdning__2_);
                        break;
                    case 3:
                        imagev.setImageResource(R.drawable.nedladdning__3_);
                        break;

                    default: imagev.setImageResource(R.drawable.ic_launcher_background);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Sami", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xAcc = sensorEvent.values[0];
        float yAcc = sensorEvent.values[1];
        float zAcc = sensorEvent.values[2];

        Log.d("sami", "onSensorChanged:x "+ sensorEvent.values[0] + "y:"+ sensorEvent.values[1] + "z:" + sensorEvent.values[2]);

        //visar inte exakt det jag önskar. lägger på +50 för att barsen ska starta från mitten.
        // beroende på om man har roterat mer på x eller y så startar den lite över +50.
        int xProgress = (int) (xAcc + 50);
        int yProgress = (int) (yAcc + 50);
        int zProgress = (int) (zAcc + 50);

        // set progress to progress bars
        x.setProgress(xProgress);
        y.setProgress(yProgress);
        z.setProgress(zProgress);

    }

}