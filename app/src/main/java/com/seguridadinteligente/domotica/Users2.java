package com.seguridadinteligente.domotica;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class Users2 extends AppCompatActivity{


    String usersid2,at,et,st;


    public Users2(String usersid2){

        this.at = at;
        this.et = et;
        this.st= st;
        this.usersid2= usersid2;


    }

    public String getat() {
        return at;
    }

    public String getet() {
        return et;
    }

    public String getst() {

        return st;
    }

    public String getUsersid2(){

        return usersid2;
    }

}