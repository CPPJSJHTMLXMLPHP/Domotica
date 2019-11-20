package com.seguridadinteligente.domotica;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class Users extends AppCompatActivity{


    String usersid,Actuadores,Medios,Sensores;


    public Users(String usersid){

       this.Actuadores = Actuadores;
       this.Medios = Medios;
       this.Sensores= Sensores;
       this.usersid= usersid;


    }

    public String getActuadores() {
        return Actuadores;
    }

    public String getMedios() {
        return Medios;
    }

    public String getSensores() {

        return Sensores;
       }

    public String getUsersid(){

        return usersid;
    }

    }



