package com.seguridadinteligente.domotica;
import android.app.ProgressDialog;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Se envio el email";

    private SharedPreferences sharedPref;
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;
    private ProgressDialog progressDialog;
    public String usuario;
    public DatabaseReference users;
    public int sino;


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializo el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference users;

        users = FirebaseDatabase.getInstance().getReference("users");

        usuario = firebaseAuth.getCurrentUser().getEmail();

        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);

        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnLogin = (Button) findViewById(R.id.botonLogin);

        progressDialog = new ProgressDialog(this);

        //asociamos un oyente al evento clic del botÛn
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("firebasekey", "value");
        editor.apply();
    }

    private void registrarUsuario() { // este bloque permite agregar a la seccion de autenticacion un usuario por medio de email y clave para
        //poder tener una vista de los usuarios ingresados y que estos se puedan loguear
        // tambien se le envia un mensaje viamail de confirmacion al usuario del mail ingresado

        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) { // para que no quede vacio el edit text destinado al ingreso de email
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();

            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseÒa", Toast.LENGTH_LONG).show(); // lo mismo que el aterior pero en el caso de la clave

            return;

        } else {
            progressDialog.setMessage("Realizando registro en linea...");
            progressDialog.show();
/*
            if (email.contains(".")) {
                Toast.makeText(this, "Caracter Inadecuado", Toast.LENGTH_LONG).show();  // no se acepta el punto ates del @ por lo que le da la indicacion al usuario
 */


            //registramos un nuevo usuario
            firebaseAuth.createUserWithEmailAndPassword(email, password) // usamos las variables creadas para poder tener los datos de los edit text y creamos el usuario
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                final FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseUser user = auth.getCurrentUser(); // conseguimos el suario ingresado
                                assert user != null; // si no es nula la variable a la que se le asigna el usuario
                                user.sendEmailVerification() // se le envia un mail de verificacion que fue editado en la pagina de firebase
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "Email sent."); // se notifica con un onComplete al igual que en el caso anterior que seria e caso de que se haya podido realizar la tarea
                                                }
                                            }
                                        });

                                if (user.isEmailVerified()){

                                    sino = 1;
                                } else {

                                    sino = 0;

                                if (!user.isEmailVerified()) {
                                    sino = 0;

                                    Toast.makeText(MainActivity.this, "Se Debe Verfificar el email", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                    users.child("seguridad").child("estado").setValue(sino);
                                    Toast.makeText(MainActivity.this, "Se ha registrado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(MainActivity.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });

        }

    }


    private void loguearUsuario() {  // es similar a la anterior pero utiliza el objeto FirebaseAuth para poder permitirle al usuario ingresar sus datos y en caso de que exista el usuario wn la parte de autenticacion le permite ingresar

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
                                                     // consigue el usuario
        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

            progressDialog.setMessage("Realizando consulta en linea...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
// la misma funcion que en el caso anterior pero para poder ingresar
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) { //el oncomplete es para tener la comprobacion sea del campo del login o el singup

                            if (task.isSuccessful()) {
                                                         // si el usuario se encontraba creado entonces la tarea es exitosa

                                int pos = email.indexOf("@");
                                String user = email.substring(0, pos); //consigue el nombre del usuario
                                Toast.makeText(MainActivity.this, "Bienvenido: " + TextEmail.getText(), Toast.LENGTH_LONG).show(); // consigue el email que habia sido asignado a la variable TextEmail
                                Intent intencion = new Intent(getApplication(), InteractActivity.class); // nueva actividad
                                intencion.putExtra(InteractActivity.users2, user); // le pasa como dato al usuario ingresado a la proxima actividad
                                startActivity(intencion);

                            } else {

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisiÛn

                                    Toast.makeText(MainActivity.this, "No se pudo loguear", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "No se pudo loguear ", Toast.LENGTH_LONG).show();
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {

        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                String userId = firebaseUser.getUid();
                String userEmail = firebaseUser.getEmail();
                sharedPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("firebasekey", userId);
                editor.apply(); // un intento de pasar el usuario nuevo si cmbia el mismo que va a ser usado en futuras versiones de la app
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.botonRegistrar:
                registrarUsuario();
                break;
            case R.id.botonLogin:
                loguearUsuario();
                break;
        }
    }

}

