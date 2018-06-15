package com.example.usrgam.taller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import modelo.Items;

public class ListaActivity extends AppCompatActivity {

    ListView listaI;
    ArrayAdapter<Items> adaptador;
    ArrayList<Items> list;
    EditText agregar;
    Button modificar;
    int posicion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        agregar = (EditText) findViewById(R.id.textAgregar);
        modificar = (Button) findViewById(R.id.botonModificar);
        listaI = (ListView) findViewById(R.id.listaItems);
        list = new ArrayList<Items>();
        cargarLista();
        adaptador = new ArrayAdapter<Items>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        listaI.setAdapter(adaptador);

        listaI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                abrirMenu(view, i);
            }
        });
    }

    public void agregar(View v) {
        String nom = "";
        String mar = "";
        String col = "";
        String val = "";
        String tal = "";

        if(agregar.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campos Vacios", Toast.LENGTH_LONG).show();
        }
        else {
            StringTokenizer  st = new StringTokenizer(agregar.getText().toString(), "-");
            while (st.hasMoreTokens()) {
                nom = st.nextToken();
                mar = st.nextToken();
                col = st.nextToken();
                val = st.nextToken();
                tal = st.nextToken();
            }
            list.add(new Items(nom, mar, col, val, tal));
            Toast.makeText(getApplicationContext(), "Item Ingresado", Toast.LENGTH_LONG).show();
            agregar.setText("");
            adaptador.notifyDataSetChanged();
        }
    }

    public void modificar(View v) {
        dialogoModificar();
    }

    public void datoModificar() {
        int i = posicion;
        String nom = "";
        String mar = "";
        String col = "";
        String val = "";
        String tal = "";

        if(agregar.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campos Vacios", Toast.LENGTH_LONG).show();
        }
        else {
            StringTokenizer st = new StringTokenizer(agregar.getText().toString(), "-");
            while (st.hasMoreTokens()) {
                nom = st.nextToken();
                mar = st.nextToken();
                col = st.nextToken();
                val = st.nextToken();
                tal = st.nextToken();
            }
            list.get(i).setNombre(nom);
            list.get(i).setMarca(mar);
            list.get(i).setColor(col);
            list.get(i).setValor(val);
            list.get(i).setTalla(tal);
            Toast.makeText(getApplicationContext(), "Item Modificado", Toast.LENGTH_LONG).show();
            agregar.setText("");
            adaptador.notifyDataSetChanged();
        }
    }



    public void abrirPantallaCodigo(View v) {
        Intent intento = new Intent(getApplicationContext(), CodigoActivity.class);
        startActivity(intento);
    }

    public void abrirMenu(View v, final int j) {
        PopupMenu menuPopupMenu = new PopupMenu(this, v);
        menuPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemVer:
                        Intent intento = new Intent(getApplicationContext(), VerActivity.class);
                        intento.putExtra("idZapato", list.get(j));
                        startActivity(intento);
                        return true;
                    case R.id.itemModificar:
                        agregar.setText(list.get(j).getNombre() + "-" +list.get(j).getMarca() + "-" + list.get(j).getColor() + "-" + list.get(j).getValor() + "-" + list.get(j).getTalla());
                        modificar.setEnabled(true);
                        posicion = j;
                        return true;
                    case R.id.itemEliminar:
                        dialogoEliminar(j);
                        return true;
                }
                return false;
            }
        });
        menuPopupMenu.inflate(R.menu.pop_menu_principal);
        menuPopupMenu.show();
    }

    public void cargarLista() {
        list.add(new Items("Zapato 1", "Marca 1", "Blanco", "45", "40"));
        list.add(new Items("Zapato 2", "Marca 2", "Negro", "18", "38"));
        list.add(new Items("Zapato 3", "Marca 3", "Café", "90", "36"));
        list.add(new Items("Zapato 4", "Marca 4", "Rojo", "40", "34"));
        list.add(new Items("Zapato 5", "Marca 5", "Azul", "25", "32"));
    }

    public void dialogoEliminar(final int k) {
        AlertDialog.Builder alertaDialogo = new AlertDialog.Builder(this);
        alertaDialogo.setTitle("Mensaje : Eliminar");
        alertaDialogo.setMessage("¿Desea Eliminar este Item?");

        alertaDialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_LONG).show();
                list.remove(k);
                adaptador.notifyDataSetChanged();
            }
        });
        alertaDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_LONG).show();
            }
        });
        alertaDialogo.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "Cancelar", Toast.LENGTH_LONG).show();
            }
        });

        alertaDialogo.setCancelable(true);
        alertaDialogo.create();
        alertaDialogo.show();
    }

    public void dialogoModificar() {
        AlertDialog.Builder alertaDialogo = new AlertDialog.Builder(this);
        alertaDialogo.setTitle("Mensaje : Modificar");
        alertaDialogo.setMessage("¿Desea Modificar este Item?");

        alertaDialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_LONG).show();
                datoModificar();
                modificar.setEnabled(false);
            }
        });
        alertaDialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_LONG).show();
            }
        });
        alertaDialogo.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "Cancelar", Toast.LENGTH_LONG).show();
            }
        });

        alertaDialogo.setCancelable(true);
        alertaDialogo.create();
        alertaDialogo.show();
    }
}
