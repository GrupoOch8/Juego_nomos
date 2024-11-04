package juego;

import java.awt.Image;
import entorno.Entorno;

public class Isla {
	private int x;
	private int y;
	private int ancho;
	private int alto;

	public Isla(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
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



	public void dibujar(Entorno entorno, Image imgIsla) {
		if (imgIsla != null) {
			entorno.dibujarImagen(imgIsla, x, y, 0, 0.06);
		}
	}

}
