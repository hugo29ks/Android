package com.example.usrgam.taller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import control.LeerEscribirArchivo;
import modelo.Usuario;

public class RegistrarActivity extends AppCompatActivity {

    EditText nombreUsuario;
    EditText contraseniaUsuario;

    private LeerEscribirArchivo lea = new LeerEscribirArchivo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nombreUsuario = (EditText) findViewById(R.id.textUsuarioRegistrar);
        contraseniaUsuario = (EditText) findViewById(R.id.textContraseniaRegistrar);
    }

    public void crearArchivo(View v) {
        lea.escribirArchivo(new Usuario(nombreUsuario.getText().toString(), contraseniaUsuario.getText().toString()), "usuario u");
        Toast.makeText(getApplicationContext(), "Registro Correcto", Toast.LENGTH_LONG).show();
    }
}
