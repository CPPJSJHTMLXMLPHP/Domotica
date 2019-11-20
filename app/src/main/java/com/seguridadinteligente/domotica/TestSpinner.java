package com.seguridadinteligente.domotica;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class  TestSpinner extends AppCompatActivity implements View.OnClickListener { // en esta actividad se utilizan spinners siendo un objeto que permite elegir la configuracion del usuario para los modulos en mercado que ofrecemos y nos hace saber que modulos posee

    public TextView TXTSensores, TxtMedios, TXTActuadores,TXUSUARIO;

    public EditText med,sens,act;

    public String TXTName,et,st,at,TXTNAMEUS;

    public int contador = 0,TWName = 1, contadornm = 0;

    public ArrayAdapter arrayAdapter2;

    Spinner SpinnSensores, SpinnMedios, SpinnActuadores;

    String userid, Sensores , Actuadores , Medios;

    private DatabaseReference users;

    public Button ButtonCargar, ButtonUpdate,ButtonNuevoModulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_testspinner);

        ArrayAdapter<String> SpinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item);
        SpinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        users = FirebaseDatabase.getInstance().getReference("users");

        med = (EditText) findViewById(R.id.editText7);
        med.setOnClickListener(this);

        sens = (EditText) findViewById(R.id.editText6);
        sens.setOnClickListener(this);

        act = (EditText) findViewById(R.id.editText8);
        act.setOnClickListener(this);


        et = med.getText().toString();
        st = sens.getText().toString();
        at = act.getText().toString();

        SpinnSensores = (Spinner) findViewById(R.id.Spinnersensores);

        SpinnMedios = (Spinner) findViewById(R.id.SpinnerMedios);

        SpinnActuadores = (Spinner) findViewById(R.id.SpinnerActuadores);


        TXTActuadores = (TextView) findViewById(R.id.editText3);
        TXTSensores = (TextView) findViewById(R.id.editText);
        TxtMedios = (TextView) findViewById(R.id.editText2);
        TXUSUARIO = (TextView) findViewById(R.id.editText9);

        ButtonCargar = (Button) findViewById(R.id.btnVerGraficas);
        ButtonCargar.setOnClickListener(this);


        ButtonUpdate = (Button) findViewById(R.id.btnUpdate);
        ButtonUpdate.setOnClickListener(this);

        ButtonNuevoModulo = (Button) findViewById(R.id.buttonnm);
        ButtonNuevoModulo.setOnClickListener(this);

        Toast.makeText(TestSpinner.this,"Ingrese modulos y su configuracion",Toast.LENGTH_SHORT).show();

        int pos = FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf("@");

        // se consigue el nombre del usuario ingresado tomando del mail ingresado en ese momento los caracteres desde la posicion 0 hasta el @

        TXTNAMEUS = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0, pos);

        TXUSUARIO.setText(TXTNAMEUS);

    }

    public void NuevoDato(View view) { // al presionar el boton con cuya id es update entra en este metodo

        try {

            if (ButtonUpdate.isPressed()) { // si el boton es presionado


                contador = contador + 1;
                                           //se hace uso de dos contadores para poder separar la informacion suida a la base de datos
                TWName = TWName + 1;

                Sensores = SpinnSensores.getSelectedItem().toString();

                Actuadores = SpinnActuadores.getSelectedItem().toString();

                Medios = SpinnMedios.getSelectedItem().toString(); // consigue almacenar en una variable de tipo string el objeto seleccionado con el spinnenr

                String userid = users.push().getKey(); // consigue una id o direccion autogenerada por los servicios de firebase

                Users usuarios = new Users(userid);

                int pos = FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf("@");

               // se consigue el nombre del usuario ingresado tomando del mail ingresado en ese momento los caracteres desde la posicion 0 hasta el @

                TXTName = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0, pos); // asigna ese valor a una variable que va a representar el ultimo usuario ingresado y a la vez se va a usar como un hijo en la cadena que representa el valor para separar que usuario ingreso que valor


                if (TWName == 2) { // asi y en los demas se puede ver que al ingresar un nuevo dato este va a estar separado gracias a que volvio a entrar a la funcion al ser presionado el boton antes menvionado y por endeel valor del contador diferencia los valores ademas de que los numeros en los hijos respectivos son diferentes para evitar confusion
                    
                    if (contador == 1) {

                        users.child("ultimo usuario ingresado").setValue(TXTName);

                        users.child(userid).child(TXTName).child("estado agregado").setValue(1);

                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);
                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                    }

                }

                if (TWName == 3) {

                    if (contador == 2) {

                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);

                        users.child(userid).child(TXTName).child("estado agregado").setValue(2);

                    }
                }

                if (TWName == 4) {

                    if (contador == 3) {

                        users.child("ultimo usuario ingresado").setValue(TXTName);

                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);

                        users.child(userid).child(TXTName).child("estado agregado").setValue(3);


                    }
                }
                if (TWName == 5) {

                    if (contador == 4) {

                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);


                        users.child(userid).child(TXTName).child("estado agregado").setValue(4);


                        users.child("ultimo usuario ingresado").setValue(TXTName);

                    }
                }

                if (TWName == 6) {

                    if (contador == 5) {

                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);

                        users.child(userid).child(TXTName).child("estado agregado").setValue(5);

                        users.child("ultimo usuario ingresado").setValue(TXTName);

                    }
                }

                if (TWName == 7) {

                    if (contador == 6) {

                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);

                        users.child(userid).child(TXTName).child("estado agregado").setValue(6);

                        users.child("ultimo usuario ingresado").setValue(TXTName);

                    }
                }

                if (TWName == 8) {

                    if (contador == 7) {
                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);

                        users.child(userid).child(TXTName).child("estado agregado").setValue(7);

                        users.child("ultimo usuario ingresado").setValue(TXTName);

                    }
                }

                if (TWName == 9) {

                    if (contador == 8) {

                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);

                        users.child(userid).child(TXTName).child("estado agregado").setValue(8);

                        users.child("ultimo usuario ingresado").setValue(TXTName);

                    }
                }

                if (TWName == 10) {

                    if (contador == 9) {
                        users.child(userid).child(TXTName).child("SpinnSensores").setValue(Sensores);
                        users.child(userid).child(TXTName).child("SpinnActuadores").setValue(Actuadores);
                        users.child(userid).child(TXTName).child("SpinnMedios").setValue(Medios);

                        users.child(TXTName).child("estado agregado").setValue(9);

                        users.child(userid).child("ultimo usuario ingresado").setValue(TXTName);
                    }
                }


            }
        } catch (Exception e){

            Toast.makeText(this,"Error" + e.getMessage(),Toast.LENGTH_LONG).show(); // en caso de que haya un error en la funcion el try/catch provee informacion del error
        }

    }

    public void  NuevoModulo(View view) {

        contadornm ++ ;

        try {

            et = med.getText().toString();
            st = sens.getText().toString();
            at = act.getText().toString();

            Toast.makeText(TestSpinner.this, "probando nuevo modulo", Toast.LENGTH_SHORT).show();

            String userid2 = users.push().getKey(); // consigue una id o direccion autogenerada por los servicios de firebase

            Users2 Users2 = new Users2(userid2);

            Toast.makeText(TestSpinner.this, "Sensores" + st, Toast.LENGTH_SHORT).show();
            Toast.makeText(TestSpinner.this, "Medios" + et, Toast.LENGTH_SHORT).show();
            Toast.makeText(TestSpinner.this, "Actuadores" + at, Toast.LENGTH_SHORT).show();


            if (TextUtils.isEmpty(et)) {

                Toast.makeText(TestSpinner.this, "Es Necesario Especificar El Medio", Toast.LENGTH_SHORT).show();

            } else {
                if (et == null) {
                    Toast.makeText(TestSpinner.this, "MEDIO NULO ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestSpinner.this, "Entre a NuevoModulo", Toast.LENGTH_SHORT).show();

                    users.child(userid2).child(TXTName).child("SpinnMediosAgregado").setValue(et);
                    users.child(userid2).child(TXTName).child("EstadoAgregadoExtra").setValue(contadornm);
                }
            }

            if (TextUtils.isEmpty(st)) {
                Toast.makeText(TestSpinner.this, "Es Necesario Especificar El Sensor", Toast.LENGTH_SHORT).show();

            } else {

                if (st == null) {
                    Toast.makeText(TestSpinner.this, "SENSOR NULO", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(TestSpinner.this, "Entre a NuevoModulo", Toast.LENGTH_SHORT).show();

                    users.child(userid2).child(TXTName).child("SpinnSensoresAgregado").setValue(st);
                    users.child(userid2).child(TXTName).child("EstadoAgregado").setValue(contadornm);
                }
            }

            if (TextUtils.isEmpty(at)) {
                Toast.makeText(TestSpinner.this, "Es Necesario Especificar El Actuador", Toast.LENGTH_SHORT).show();

            } else {

                if (at == null) {
                    Toast.makeText(TestSpinner.this, "ACTUADOR NULO", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(TestSpinner.this, "Entre a NuevoModulo", Toast.LENGTH_SHORT).show();

                    users.child(userid2).child(TXTName).child("Spinnactuadores").setValue(at);
                    users.child(userid2).child(TXTName).child("EstadoAgregado").setValue(contadornm);
                }
            }

        } catch (Exception e){
            Toast.makeText(TestSpinner.this,"Error Nuevo Modulo" + e,Toast.LENGTH_LONG).show();
        }
    }

    public void VerGraficas(View view) {

        Intent intent = new Intent(view.getContext(), ShowSenActivity.class);
        startActivityForResult(intent, 0);

    } // permite de esta actividad abrir la clase correspondiente a los graficos

    public  void cleart6(View view){

        sens.getText().clear();
    }

    public  void cleart7(View view){

        med.getText().clear();
    }

    public  void clear8(View view){

        act.getText().clear();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnVerGraficas:

                VerGraficas(view); // para poder separar por id las funciones deben tener el parametro View por lo que de otra forma no se podria identificar a los respectivos botones y el compilador no permitiria hacer un switch case de esas caracteristicas
                break;

            case R.id.btnUpdate:

                NuevoDato(view);
                break;

            case R.id.buttonnm:
                NuevoModulo(view);

                break;

            case R.id.editText6:
                cleart6(view);

                break;

            case R.id.editText7:
                cleart7(view);

                break;

            case R.id.editText8:
                clear8(view);

                break;
        }


    }


    }



