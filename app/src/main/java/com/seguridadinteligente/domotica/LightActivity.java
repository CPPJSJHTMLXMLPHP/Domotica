package com.seguridadinteligente.domotica;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;



public class LightActivity extends InteractActivity implements OnSeekBarChangeListener { // esta va a tener los indicadores para definir la intensidad deseada para la luz t la temperatura deseada para el funcionamiento del sistema automatico
    // por lo que este valor es enviado a la base de datos y se pedira por el mismo en el programa de los ESP

    private int sb, sb2;
    public String vddv;
    public static String user = "names";
    public int progress,progress2;
    private TextView textView, textView2;
    private SeekBar seekBar, seekbar2;
    SharedPreferences sharedpreferences;
    public SharedPreferences sharedPref;
    TextView txtUser;

    String valpru = "Por Definir";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        users = FirebaseDatabase.getInstance().getReference("users"); // referencia de la base de datos


        initializeVariables(); // funcion para definir las variables a los objetos correspondientes (es lo mismo que dentro del oncreate pero en otros casos es conveniente mantenerlo de esta forma)

        textView.setText("Intensidad: " + seekBar.getProgress() + "/" + seekBar.getMax());

        textView2.setText("Temperatura:" + seekbar2.getProgress() + "/" + seekbar2.getMax());

    }

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textView = (TextView) findViewById(R.id.textView);
        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        textView2 = (TextView) findViewById(R.id.textView2);

        seekbar2.setOnSeekBarChangeListener(this);
        seekBar.setOnSeekBarChangeListener(this);

    }

    public void EstadoProgreso(View view) {

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String sharedPrefString = sharedPref.getString("Lstat", "1");

        SharedPreferences sharedPreferences1 = getPreferences(MODE_PRIVATE);
        String sharedPrefString1 = sharedPref.getString("Lstat", "0");

    } // esta iba a ser usado para pasar datos entre actividades pero al tener la base de datos recibiendo todos los datos necesarios no hace falta pasar datos entre actividades sino que estas sirvan para modificar la base de datos y
    // para recibir datos de la misma

    public void Save(View view) {

        Toast.makeText(getBaseContext(), "Listo", Toast.LENGTH_SHORT).show();
        vddv = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        int pos = vddv.indexOf("@");
        String user = vddv.substring(0, pos);
        Toast.makeText(LightActivity.this, "Bienvenido: " + vddv, Toast.LENGTH_SHORT).show();
        Intent intencion = new Intent(getApplication(), InteractActivity.class);
        intencion.putExtra(InteractActivity.users2, user);
        startActivity(intencion);

        Intent intent = new Intent(view.getContext(), InteractActivity.class);
        startActivityForResult(intent, 0);

    } // lo que ocurre al ser presionado el boton que se encuentra en esta actividad  tambien se vuelve a conseguir el nombre antes del @ del usuario ingresado y se presenta un texto en pantalla



    @Override
    public void onProgressChanged(SeekBar seekBar, int progresValue , boolean fromUser) { //esta al igual que las proximas funciones son las que hay que utilizar al querer conseguir el valor de una seekbar o barra de progreso

        progress = progresValue; //conseguimos el valor


switch (seekBar.getId())
        { // se utiliza para separar si se quiere el progreso del seekbar uno o dos
            case  R.id.seekbar:

                sb = seekBar.getProgress();
                users.child("utilizacion").child("SpinnSensores").child("s4").child("iluminacion deseada").setValue(sb);

                break;

            case  R.id.seekBar2:

                sb2 = seekBar.getProgress();
                users.child("utilizacion").child("SpinnSensores").child("s6").child("temperatura deseada").setValue(sb2);

                break;
// y se pasa el valor a la base de datos
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        switch (seekBar.getId()) { //para que se este cambiando mientras que el usuario va cambiando el valor
            case R.id.seekbar:

                users.child("utilizacion").child("SpinnSensores").child("s4").child("iluminacion deseada").setValue(sb);

                // users.child("utilizacion").child("SpinnSensores").child("s6").child("temperatura deseada").setValue(valpru);


            case R.id.seekBar2:

                if (seekBar.getProgress() == 100) {

                    users.child("utilizacion").child("SpinnSensores").child("s6").child("temperatura deseada").setValue(valpru);
                } else {

                    users.child("utilizacion").child("SpinnSensores").child("s6").child("temperatura deseada").setValue(sb2);
                }
        }

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        switch (seekBar.getId()) {

            case R.id.seekbar:

                textView.setText("Covered: " + progress + "/" + seekBar.getMax());

                Toast.makeText(getApplicationContext(), "Cambiando la Intensidad", Toast.LENGTH_SHORT).show();

                users.child("utilizacion").child("SpinnSensores").child("s4").child("iluminacion deseada").setValue(sb);

                if (sb != 100) { // para que coloque la indicacion de que falta definir el otro valor dado que se quiere que se definan ambos parametros para el funcionamiento automatico

                    users.child("utilizacion").child("SpinnSensores").child("s6").child("temperatura deseada").setValue(valpru); // se define que todavia no se selecciono nada

                } else {

                    users.child("utilizacion").child("SpinnSensores").child("s6").child("temperatura deseada").setValue(sb2);

                }


                break;

            case R.id.seekBar2:

                textView2.setText("Covered:" + progress + "/" + seekbar2.getMax());

                Toast.makeText(getApplicationContext(),"cambiando la Temperatura",Toast.LENGTH_SHORT).show();

                users.child("utilizacion").child("SpinnSensores").child("s6").child("temperatura deseada").setValue(sb2);


        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //case R.id.save:
            //  Save(view);
            //break;
            case R.id.image3:
                EstadoProgreso(view);
                break;
            case R.id.save:
                //  IntensidadLista(view);
                Save(view);
                break;
        }
    }
}







