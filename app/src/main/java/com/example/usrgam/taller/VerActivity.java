package com.example.usrgam.taller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import modelo.Items;

public class VerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);


        Items ite = (Items) getIntent().getExtras().getSerializable("idZapato");

        ((TextView)findViewById(R.id.viewNombre)).setText(ite.getNombre());
        ((TextView)findViewById(R.id.viewMarca)).setText(ite.getMarca());
        ((TextView)findViewById(R.id.viewColor)).setText(ite.getColor());
        ((TextView)findViewById(R.id.viewTalla)).setText(ite.getTalla());
        ((TextView)findViewById(R.id.viewValor)).setText(ite.getValor());
    }
}
