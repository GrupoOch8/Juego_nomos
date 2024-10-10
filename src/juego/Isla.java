package juego;

import java.awt.Color;

public class Isla {
	private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color color;

    public Isla(int x, int y, int ancho, int alto, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
    }

    // Getters para acceder a las propiedades de la isla
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public Color getColor() {
        return color;
    }
}
