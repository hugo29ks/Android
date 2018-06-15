package com.example.usrgam.taller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.StringTokenizer;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import modelo.Items;

public class CodigoActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUESTCAMERA=1;
    private ZXingScannerView scannerView;
    private static int casID= Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);

        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);//pasar de unactivity a otro activity y atodo y con vista un pedazo
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(verficarPermisos()){
                Toast.makeText(getApplicationContext(),"Permiso Otorgado",Toast.LENGTH_LONG).show();
            }else{
                solicitarPermisos();
            }
        }
    }

    public boolean verficarPermisos(){
        return ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED; //ALGO EN ESPECIFICO;
    }

    public void solicitarPermisos(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUESTCAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int apiVersion=Build.VERSION.SDK_INT;
        if(apiVersion>= Build.VERSION_CODES.M) {
            if (verficarPermisos()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }else{
                solicitarPermisos();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUESTCAMERA:
                if(grantResults.length>0){
                    boolean aceptaPermiso = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(aceptaPermiso){

                    }else{
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUESTCAMERA);
                            }
                        }
                    }
                }
        }
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(result.getBarcodeFormat().toString());
        alertDialog.setMessage(result.getText());

        String nom = "";
        String mar = "";
        String col = "";
        String val = "";
        String tal = "";
        String resultado = result.getText();
        if(resultado != "") {

            StringTokenizer st = new StringTokenizer(resultado, "-");
            while (st.hasMoreTokens()) {
                nom = st.nextToken();
                mar = st.nextToken();
                col = st.nextToken();
                val = st.nextToken();
                tal = st.nextToken();
            }

            onResume();

            Items ite = new Items(nom, mar, col, val, tal);
            Intent intento = new Intent(getApplicationContext(), VerActivity.class);
            intento.putExtra("idZapato", ite);
            startActivity(intento);
        }

        Log.e("resultado;",result.getText());
        Log.e("resultadoBar:",result.getBarcodeFormat().toString());
        onResume();
    }
}
