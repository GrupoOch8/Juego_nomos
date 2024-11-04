package juego;

import java.awt.Color;
import entorno.Entorno;

public class Isla {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color color;
    private boolean direccion;

    public Isla(int x, int y, int ancho, int alto, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
        this.direccion = false;
    }

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

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, this.color);
    }

	public void mover() {
		if(direccion) {
			x += 1;
		} else {
			x -= 1;
		}
	}

	public boolean esIslaSuperior() {
		if(y <= 300) {
			return true;
		}
		return false;
	}

	public void cambiarDireccion() {
		if(x <= 60 || x >= 740) {
			direccion = !direccion;
		}
	}

}
