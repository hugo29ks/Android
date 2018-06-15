package com.example.usrgam.taller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import control.LeerEscribirArchivo;
import modelo.Usuario;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    EditText contraseña;

    private LeerEscribirArchivo lea = new LeerEscribirArchivo();
    private GoogleApiClient googleApiClient;
    private final int CODERC = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.textUsuarioIngresar);
        contraseña = (EditText) findViewById(R.id.textContraseniaIngresar);

        SignInButton botongoogle = (SignInButton) findViewById(R.id.logeogmail);
        botongoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logeoGmail();
            }
        });
    }

    public void abrirPantallaLista(View v) {
        Intent intento = new Intent(getApplicationContext(), ListaActivity.class);
        ArrayList<Usuario> u = lea.leerArchivo("usuario u");
        boolean bandera = false;

        for (int i = 0; i < u.size(); i++) {
            if(usuario.getText().toString().equals(u.get(i).getNombre()) && contraseña.getText().toString().equals(u.get(i).getContrasenia())) {
                bandera = true;
                break;
            }
            else {
                bandera = false;
            }
            Log.e("usuario : ", u.get(i).toString());
        }
        if(bandera) {
            Toast.makeText(getApplicationContext(), "Ingreso Correcto", Toast.LENGTH_LONG).show();
            startActivity(intento);
        }
        else {
            Toast.makeText(getApplicationContext(), "Usuario o Contraseña Incorrecto", Toast.LENGTH_LONG).show();
        }
    }


    public void abrirPantallaRegistrar(View v) {
        Intent intento = new Intent(getApplicationContext(), RegistrarActivity.class);
        startActivity(intento);
    }

    public void logeoGmail(){
        if(googleApiClient!=null){
            //desconectando
            googleApiClient.disconnect();
        }
        //solicitar correo para iniciar sesion
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //igual el cliente con el logeo
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();
        //abrir ventana de google
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signIntent,CODERC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intento = new Intent(getApplicationContext(), ListaActivity.class);

        if(requestCode==CODERC){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                GoogleSignInAccount acc = result.getSignInAccount();
                String token = acc.getIdToken();
                Log.e("Correo: ",acc.getEmail());
                Log.e("Nombre: ",acc.getDisplayName());
                Log.e("id: ",acc.getId());
                if(token!=null){
                    Toast.makeText(this,token,Toast.LENGTH_LONG).show();
                }
                Toast.makeText(this,"Ingreso Correcto",Toast.LENGTH_LONG).show();
                intento.putExtra("idUsuario", acc.getDisplayName().toString());
                startActivity(intento);
            }
        }
    }
}
