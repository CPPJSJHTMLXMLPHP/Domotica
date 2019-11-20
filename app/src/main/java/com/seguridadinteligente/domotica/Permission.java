package com.seguridadinteligente.domotica;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*

ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,

        R.array.planets_array, android.R.layout.simple_spinner_item);

 */

public class Permission extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public Button Buttonreq, ButtonSeg;

    private static final String TAG = "Se necesitan Permisos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Buttonreq = (Button) findViewById(R.id.buttonn);
        Buttonreq.setOnClickListener(this);

        ButtonSeg = (Button) findViewById(R.id.buttonnn);
        ButtonSeg.setOnClickListener(this);

        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            onResume();
        }
    }

    private boolean checkAndRequestPermissions() {
        int permissionInternet = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);
        int accountPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (accountPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
        }
        if (permissionInternet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Se necesitan permisos-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();

                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);

                    if (perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Permisos concedidos");

                    } else {
                        Log.d(TAG, "No se dieron Los Permisos ");

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("  Se necesitan permisos para el correcto funcionamiento de la app",
                                     new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:

                                                    break;
                                            }
                                        }
                                    });
                        }

                        else {
                            Toast.makeText(this, "Habilite los permisos", Toast.LENGTH_LONG)
                                    .show();

                            finish();
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    public void SigMain(View view){
            Intent intent = new Intent(view.getContext(),CargaActivity.class);
            startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonn:
                checkAndRequestPermissions();
                break;
            case R.id.buttonnn:
                SigMain(view);
        }

    }

}