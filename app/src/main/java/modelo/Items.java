package modelo;

import java.io.Serializable;

public class Items implements Serializable {
    public String nombre, marca, color, valor, talla;

    public Items(String nombre, String marca, String color, String valor, String talla) {
        this.nombre = nombre;
        this.marca = marca;
        this.color = color;
        this.valor = valor;
        this.talla = talla;
    }

    public String getMarca() {

        return marca;
    }

    public String getColor() {
        return color;
    }

    public String getTalla() {
        return talla;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    @Override
    public String toString() {
        return nombre + " $" + valor;
    }
}
