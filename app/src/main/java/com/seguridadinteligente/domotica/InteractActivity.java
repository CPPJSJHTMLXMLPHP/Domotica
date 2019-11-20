package com.seguridadinteligente.domotica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class InteractActivity extends AppCompatActivity implements View.OnClickListener {  // Esta clase es la principal en la que se controlan los actuadores por medio de los switches o se puede dejar activado el modo automatico


    public static String users2 = "names";

    public int value;
    public int value1;
    public int value2;
    public int value3;
    public int value4;

    public int calidad;

    public int value6;

    public String TName;

    private FirebaseAuth firebaseAuth;


    public DatabaseReference users;
    public DatabaseReference sino;

    public int fr;
    public Switch simpleSwitch1, simpleSwitch2, simpleSwitch3, simpleSwitch4, simpleSwitch5, simpleswitchautomatico, simpleSwitch7, simpleSwitch12;
    public Button next1, DataBase;
    public SharedPreferences sharedPref;
    public ImageButton Image3;


    int valorpalabra;

    //public DatabaseReference utilizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact);

        users = FirebaseDatabase.getInstance().getReference("users"); //referencia de firebase
        sino = users.child("seguridad").child("estado");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Lstat", "value");
        editor.apply();

        simpleSwitch1 = (Switch) findViewById(R.id.switch1);
        simpleSwitch2 = (Switch) findViewById(R.id.switch2);
        simpleSwitch3 = (Switch) findViewById(R.id.switch3);
        simpleSwitch4 = (Switch) findViewById(R.id.switch4);
        simpleSwitch5 = (Switch) findViewById(R.id.switch5);
        simpleSwitch7 = (Switch) findViewById(R.id.switch7);

        simpleswitchautomatico = (Switch) findViewById(R.id.switchauto);


        DataBase = (Button) findViewById(R.id.DataBase);
        DataBase.setOnClickListener(this); // se referencia el this como hablando de si misma la actividad
                                           //se puede usar el onClick fuera del onCreate

        next1 = (Button) findViewById(R.id.nextActivityButton);
        next1.setOnClickListener(this);

        Image3 = (ImageButton) findViewById(R.id.image3);
        Image3.setImageResource(R.drawable.idea);
        Image3.setOnClickListener(this);


        users.child("utilizacion").child("SpinnSensores").child("s0").child("calidad del aire").addValueEventListener(new ValueEventListener() { //se utiliza esta estructura para recibir el dato cada vez que sea modificado

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                calidad = (dataSnapshot.getValue(Integer.class));

            //    Toast.makeText(InteractActivity.this,"ca" +calidad,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


    public void Siguiente(View view) {
        Intent intent = new Intent(view.getContext(), WebView.class);
        startActivityForResult(intent, 0);
    }

    public void DataBase(View view) {
        Intent intent = new Intent(view.getContext(), TestSpinner.class);
        startActivityForResult(intent, 0);
    }

    public void home(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivityForResult(intent, 0);

    }

    public void AbrirLuz(View view) {
        Intent intent = new Intent(view.getContext(), LightActivity.class);
        startActivityForResult(intent, 0);

        FirebaseAuth.getInstance().getCurrentUser().getEmail();


        Bundle LoadInfo = new Bundle();
        LoadInfo.putSerializable("image3", (Serializable) Image3);
        intent.putExtras(LoadInfo);

        Bundle LoadSwitch = new Bundle();
        LoadSwitch.putSerializable("Switch2", (Serializable) simpleSwitch2);
        intent.putExtras(LoadSwitch);
    }
//todos los intent son para poder utilizar el metodo onClick para pasar a una nueva actividad y dado que los datos ya estan en la base de datos el shared preferences
    public void SenderData(View view) {

        int pos = FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf("@");

        // declara la variable pos y la iguala a conseguir la instancia del usuario ingresado con el constructor de firebase a partir del arroba

        String userr = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0, pos);
        //igualo la variable a la posicion entre 0 y pos por lo que se consigue un string igual a todo lo que este antes del arroba

        TName = userr; //la variable TName es la que se va a pasar a firebase para poder separar la informacion de todos los usuarios


        if (simpleSwitch1.isChecked()) { // si el switch esta en uno

            value = 1; //valor es 1
        } else {
            value = 0;
        }

        users.child("Estados").child(TName).child("SpinnActuadores").child("a0").child("reguladores(toma)").setValue(value); // y le pasamos el valor teniendo la referencia especifica en este caso del toma


        if (simpleSwitch2.isChecked()) {

            value1 = 1;

        } else {

            value1 = 0;
        }
        final DatabaseReference utilizacion = FirebaseDatabase.getInstance().getReference("utilizacion");

        users.child("Estados").child(TName).child("SpinnActuadores").child("a1").child("rele de estado solido(luces)").setValue(value1);


        if (simpleSwitch3.isChecked()) { // este ejemplo es diferente

            value2 = 1; // primero pone en uno el rele y luego entra al for

            Toast.makeText(InteractActivity.this, "Habilitado", Toast.LENGTH_SHORT).show();

            for (fr = 0; fr < 100; fr++)  //deja un tiempo con un for para que el usuario tenga tiempo de brir la puerta poniendo nuevamente en 0 la cerraduraen la base de datos al llegar fr a ser igual a 1500 siendo dependiendo del wifi cerca de tres segundos

            {
                users.child("Estados").child(TName).child("SpinnActuadores").child("a2").child("rele de estado solido(puerta)").setValue(value2);

            }

            value2 = 0;


        } else {

            value2 = 0;
        }

        users.child("ultimo usuario ingresado").setValue(TName);

        users.child("Estados").child(TName).child("SpinnActuadores").child("a2").child("rele de estado solido(puerta)").setValue(value2);


        if (simpleSwitch7.isChecked()) {

            value3 = 1;

            // Toast.makeText(InteractActivity.this, "El Sensor De Movimiento Se Encuentra Activado", Toast.LENGTH_SHORT).show();

        } else {

            value3 = 0;

            //  Toast.makeText(InteractActivity.this, "El Sensor De Movimiento Se Encuentra Desactivado", Toast.LENGTH_SHORT).show();
        }
        FirebaseDatabase.getInstance().getReference("utilizacion");

        users.child("Modo").child("Sensor de Movimiento").child("a3").setValue(value3);


        if (simpleSwitch5.isChecked()) {

            value4 = 1;

            if (calidad == 1) {

                Toast.makeText(InteractActivity.this, "La Estufa no sera utilizada porque no es buena la calidad del aire", Toast.LENGTH_SHORT).show();
                Toast.makeText(InteractActivity.this,"Se Recomienda Abrir La Puerta",Toast.LENGTH_SHORT).show();
            }


        } else {

            value4 = 0;
        }

        FirebaseDatabase.getInstance().getReference("utilizacion");

        users.child("Estados").child(TName).child("SpinnActuadores").child("a4").child("rele de estado solido(estufa)").setValue(value4);

        if (simpleSwitch4.isChecked()) {

            value6 = 1;

        } else {

            value6 = 0;
        }
        FirebaseDatabase.getInstance().getReference("utilizacion");

        users.child("Estados").child(TName).child("SpinnActuadores").child("a6").child("rele de estado solido ventilador").setValue(value6);

        if (simpleswitchautomatico.isChecked()) {

            valorpalabra = 1;

            Toast.makeText(InteractActivity.this, "Seleccione Valores De Iluminacion y Temperatura ", Toast.LENGTH_SHORT).show();

        } else {

            valorpalabra = 0;

            //     Toast.makeText(InteractActivity.this, "El Modo Automatico Se Encuentra Desactivado", Toast.LENGTH_SHORT).show();

        }

        users.child("Modo").child("Automatico").setValue(valorpalabra);
    }

// para el metodo onClick se agrega un switch case para poder usar el id de los objetos creados para poder determinar si fue precionado que boton y dependiendo de eso carga un metodo diferentre
// de los que tienen los intent para abrir nuevas actividades

        @Override
        public void onClick (View view){

            switch (view.getId()) {
                case R.id.nextActivityButton:
                    Siguiente(view);
                    break;
                case R.id.image3:
                    AbrirLuz(view);
                    break;
                case R.id.DataBase:
                    DataBase(view);
                    break;
                case R.id.upload:
                    SenderData(view);
                    break;
                case R.id.home:
                    home(view);
                    break;

            }
        }
    }









