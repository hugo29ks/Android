package control;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modelo.Usuario;

public class LeerEscribirArchivo {

    private File file = Environment.getExternalStorageDirectory();
    private String ruta = file.getAbsolutePath() + File.separator;
    ArrayList<Usuario> list = new ArrayList<Usuario>();

    public void escribirArchivo(Usuario u, String nombre) {
        try {
            if(file.exists()) {
            FileInputStream file = new FileInputStream(ruta+nombre);
            ObjectInputStream in = new ObjectInputStream(file);

            list = (ArrayList<Usuario>) in.readObject();
            in.close();

            FileOutputStream fos = new FileOutputStream(ruta+nombre);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            list.add(u);
            out.writeObject(list);
            out.close();
            }
            else {
            FileOutputStream fos = new FileOutputStream(ruta+nombre);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            list.add(u);
            out.writeObject(list);
            out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Usuario> leerArchivo(String nombre) {
        try {
            FileInputStream file = new FileInputStream(ruta + nombre);
            ObjectInputStream in = new ObjectInputStream(file);

            list = (ArrayList<Usuario>) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            Log.e("ERROR EN ESCRIBIR : ", e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("ERROR IO : ", e.toString());
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
