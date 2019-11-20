package com.seguridadinteligente.domotica;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class CargaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


public int contador=0,TWName=1;
    public DatabaseReference Datas;

    public Spinner SpinnSensores, SpinnMedios, SpinnActuadores;
    public Button ButtonCargar,ButtonUpdate;

    public String TXTName;
    public TextView TXTSensores, TxtMedios, TXTActuadores;

    public DatabaseReference users;

    public ArrayAdapter<CharSequence> adapters,adaptera,adapterm; //son de el intento que quedo comentado

    public String nombreSensores, nombreActuadores, nombreMedios;

    public String[] strMedios, strActuadores, strSensores; // same

    public ArrayAdapter<String> comboAdaptera,comboAdapters,comboadapterm;

    public List<String> listaSensores, listaActuadores, listaMedios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cargar);

        users = FirebaseDatabase.getInstance().getReference("users");


        Datas = users.child(TXTName).child("estado agregado");

        SpinnSensores = (Spinner) findViewById(R.id.Spinnersensores);


        SpinnMedios = (Spinner) findViewById(R.id.SpinnerMedios);


        SpinnActuadores = (Spinner) findViewById(R.id.SpinnerActuadores);


        TXTActuadores = (TextView) findViewById(R.id.editText3);
        TXTSensores = (TextView) findViewById(R.id.editText);
        TxtMedios = (TextView) findViewById(R.id.editText2);

        ButtonCargar = (Button) findViewById(R.id.btnVerGraficas);
        ButtonCargar.setOnClickListener(this);



        ButtonUpdate = (Button) findViewById(R.id.btnUpdate);
        ButtonUpdate.setOnClickListener(this);


        SpinnSensores.setOnItemSelectedListener(this);

        listaSensores = new ArrayList<>();
        listaSensores.add("corriente");
        listaSensores.add("humedad");
        listaSensores.add("movimiento");
        listaSensores.add("calidad del aire");
        listaSensores.add("temperatura");
        /*
        strSensores = new String[]{"corriente", "humedad","movimiento","calidad del aire","temperatura"};
        Collections.addAll(listaSensores, strSensores);
        */
        comboAdapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaSensores);

        comboAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        comboAdapters.notifyDataSetChanged();

        SpinnSensores.setAdapter(comboAdapters);


        SpinnActuadores.setOnItemSelectedListener(this);

        listaActuadores = new ArrayList<>();
        listaActuadores.add("reguladores(toma)");
        listaActuadores.add("rele de estado solido(luces)");
        listaActuadores.add("rele de estado solido(puerta)");
        listaActuadores.add("rele de estado solido(estufa)");
        listaActuadores.add("rele de estado solido(ventilador)");
        listaActuadores.add("rele de estado solido(aire)");
        listaActuadores.add("rele de estado solido(ventana)");

        comboAdaptera = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listaActuadores);

        comboAdaptera.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        comboAdaptera.notifyDataSetChanged();

        SpinnActuadores.setAdapter(comboAdaptera);
/*
        strActuadores = new String[]{"reguladores(toma)", "rele de estado solido(luces)", "rele de estado solido(puerta)", "rele de estado solido(estufa)", "rele de estado solido(ventilador)", "rele de estado solido(aire)","dimmer","rele de estado solido(ventana)"};
        Collections.addAll(listaActuadores, strActuadores);
        comboAdaptera = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, strActuadores);
        SpinnActuadores.setAdapter(comboAdaptera);
*/

        SpinnMedios.setOnItemSelectedListener(this);
        listaMedios = new ArrayList<>();
        listaMedios.add("reguladores(toma)");
        listaMedios.add("rele de estado solido(luces)");
        listaMedios.add("rele de estado solido(puerta)");
        listaMedios.add("rele de estado solido(estufa)");
        listaMedios.add("rele de estado solido(ventilador)");
        listaMedios.add("rele de estado solido(aire)");
        listaMedios.add("dimmer");
        listaMedios.add("rele de estado solido(ventana)");

        comboadapterm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMedios);

        comboadapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        comboadapterm.notifyDataSetChanged();

        SpinnMedios.setAdapter(comboadapterm);

        /*
        strMedios = new String[]{"reguladores(toma)", "rele de estado solido(luces)", "rele de estado solido(puerta)", "rele de estado solido(estufa)", "rele de estado solido(ventilador)", "rele de estado solido(aire)","dimmer","rele de estado solido(ventana)"};
        Collections.addAll(listaMedios, strMedios);
        comboadapterm= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, strMedios);
        SpinnMedios.setAdapter(comboadapterm);
        */

    }


     @Override

        public void onItemSelected (AdapterView < ? > Users, View view,int position, long id) {

         try {
             switch (Users.getId()) {

                 case R.id.Spinnersensores:

                     Log.d("DEBUGGER", String.valueOf(Users.getId()));

                     nombreSensores = SpinnSensores.getSelectedItem().toString();
                     Toast.makeText(Users.getContext(), "nombre sensor: " + nombreSensores, Toast.LENGTH_SHORT).show();

                     break;

                 case R.id.SpinnerActuadores:

                     nombreActuadores = SpinnActuadores.getSelectedItem().toString();
                     Toast.makeText(Users.getContext(), "nombre actuador: " + nombreActuadores, Toast.LENGTH_SHORT).show();
                     break;

                 case R.id.SpinnerMedios:

                     nombreMedios = SpinnSensores.getSelectedItem().toString();
                     Toast.makeText(Users.getContext(), "nombre medio: " + nombreMedios, Toast.LENGTH_SHORT).show();
                     break;
             }
         } catch (Exception e) {

             Toast.makeText(CargaActivity.this, "ERROR" + e.getMessage(), Toast.LENGTH_LONG).show();


         }
     }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(CargaActivity.this,"nothing selected",Toast.LENGTH_SHORT).show();

    }


    public void updateData(View view) {

        if (ButtonUpdate.isPressed()) {

            contador = contador + 1;

            TWName = TWName + 1;


            int pos = FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf("@");

            String userr = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0, pos);


            TXTName = userr;

            if (TWName == 2) {
                if (contador == 1) {

                    users.child("ultimo usuario ingresado").setValue(TXTName);

                    users.child(TXTName).child("estado agregado").setValue(1);

                    users.child(TXTName).child("SpinnActuadores1").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios1").setValue(nombreMedios);
                    users.child(TXTName).child("SpinnSensores1").setValue(nombreSensores);
                }

            }

            if (TWName == 3) {
                if (contador == 2) {
                    users.child(TXTName).child("SpinnSensores2").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores2").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios2").setValue(nombreMedios);

                    users.child(TXTName).child("estado agregado").setValue(2);

                }
            }

            if (TWName == 4) {
                if (contador == 3) {

                    users.child("ultimo usuario ingresado").setValue(TXTName);

                    users.child(TXTName).child("SpinnSensores3").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores3").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios3").setValue(nombreMedios);

                    users.child(TXTName).child("estado agregado").setValue(3);


                }
            }
            if (TWName == 5) {

                if (contador == 4) {

                    users.child(TXTName).child("SpinnSensores4").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores4").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios4").setValue(nombreMedios);


                    users.child(TXTName).child("estado agregado").setValue(4);


                    users.child("ultimo usuario ingresado").setValue(TXTName);

                }
            }

            if (TWName == 6) {

                if (contador == 5) {

                    users.child(TXTName).child("SpinnSensores5").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores5").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios5").setValue(nombreMedios);

                    users.child(TXTName).child("estado agregado").setValue(5);

                    users.child("ultimo usuario ingresado").setValue(TXTName);

                }
            }

            if (TWName == 7) {

                if (contador == 6) {

                    users.child(TXTName).child("SpinnSensores6").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores6").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios6").setValue(nombreMedios);

                    users.child(TXTName).child("estado agregado").setValue(6);

                    users.child("ultimo usuario ingresado").setValue(TXTName);

                }
            }

            if (TWName == 8) {

                if (contador == 7) {
                    users.child(TXTName).child("SpinnSensores7").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores7").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios7").setValue(nombreMedios);
                    users.child(TXTName).child("estado agregado").setValue(7);
                    users.child("ultimo usuario ingresado").setValue(TXTName);

                }
            }

            if (TWName == 9) {

                if (contador == 8) {
                    users.child(TXTName).child("SpinnSensores8").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores8").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios8").setValue(nombreMedios);
                    users.child(TXTName).child("estado agregado").setValue(8);
                    users.child("ultimo usuario ingresado").setValue(TXTName);

                }
            }

            if (TWName == 10) {

                if (contador == 9) {
                    users.child(TXTName).child("SpinnSensores9").setValue(nombreSensores);
                    users.child(TXTName).child("SpinnActuadores9").setValue(nombreActuadores);
                    users.child(TXTName).child("SpinnMedios9").setValue(nombreMedios);
                    users.child(TXTName).child("estado agregado").setValue(9);
                    users.child("ultimo usuario ingresado").setValue(TXTName);
                }
            }

        }
    }



    public void VerGraficas(View view) {
        Intent intent = new Intent(view.getContext(), ShowSenActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnVerGraficas:

                VerGraficas(view);
                break;

            case R.id.btnUpdate:
                updateData(view);

                break;
        }


    }

    }













