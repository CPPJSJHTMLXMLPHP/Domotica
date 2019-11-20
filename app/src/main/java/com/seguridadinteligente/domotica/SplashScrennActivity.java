package com.seguridadinteligente.domotica;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScrennActivity extends AppCompatActivity  {
    private Button next2;

    public Timer timer;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screnn);

        Toast.makeText(SplashScrennActivity.this,"Cargando Registros",Toast.LENGTH_SHORT).show();
        timer = new Timer();
        timer.schedule(new TimerTask() { //el timer como objeto permite crear esta funcion override run a la que se le agrega un intent por abrir una nueva actividad
            @Override
            public void run() {


                Intent intent = new Intent(SplashScrennActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        } ,5000); // el tiempo antes de que se abra la otra actividad

    }

    }
