package com.seguridadinteligente.domotica;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7 .app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;


import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.Date;

public class WebView extends AppCompatActivity implements View.OnClickListener {

    public TextView tw5, tw6;
    public Button btnv;
    public Chronometer cs;
    public Long Time;
    public String TAG = "TAG";

    android.webkit.WebView myWebView; // permite mostrar una pagina y navegar dentro de la misma teniendo en cuenta que algunas aginas puden requerir permisos adicionales al uso de internet

    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()); // se utiliza para poder mostrar el tiempo en el momento que se entra a la actividad

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        myWebView = (android.webkit.WebView) findViewById(R.id.visweb);

        cs = (Chronometer) findViewById(R.id.cronometrostart);
        cs.start();

        tw5 = (TextView) findViewById(R.id.t5);

        tw6 = (TextView) findViewById(R.id.t6);

        btnv = (Button) findViewById(R.id.buttontuto);
        btnv.setOnClickListener(this);

        WebSettings webSettings = myWebView.getSettings(); // son necesarias para configurar el webview

        webSettings.setJavaScriptEnabled(true); // es la minima requerida dado que asi se encuentran desarrollados las aplicaciones web a las que se quiere referenciar

        tw6.setText(currentDateTimeString); // se muestra en un text view el tiempo del que hablamos anteriormente

        myWebView.loadUrl("http://192.168.43.116:8080"); // se le pasa el url que tiene que cargar siendo en este caso el de la camara que genera su propio servidor y corre el video en vivo.

/*
        cs.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                long minutes = ((Time - cs.getBase())/1000) / 60;
                long seconds = ((Time - cs.getBase())/1000) % 60;

                Time = Time + 1000;

                Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);
            }
        }); */ // esto es importante pero con uno para cada si se quiere hacer un timer manejable

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), VideoActivity.class);
        startActivityForResult(intent, 0);
    }
}


