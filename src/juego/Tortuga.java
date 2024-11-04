package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;

public class Tortuga {

	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Color color;
	private double velocidadY = 0;
	private boolean enElAire = true;
	private boolean direccion;
	private final double GRAVEDAD = 0.5;
	private Random random = new Random();

	public Tortuga(int x, int y, int ancho, int alto, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
		this.direccion = random.nextBoolean();

	}

	public void mover(Entorno entorno) {
		if (!enElAire) {
			if(direccion) {
				this.x += 1;
			} else {
				this.x += -1;
			}
		}
	}
	public void dibujar(Entorno entorno, Image imagenTortuga) {
	    if (imagenTortuga != null) {
	        entorno.dibujarImagen(imagenTortuga, x, y, 0, 0.05);
	    }
	}
	
	public void aplicarGravedad() {
		if (enElAire) {
			velocidadY += GRAVEDAD;
			y += velocidadY;
		}
	}
	public void verificarColisiones(Isla[] islas) {
		boolean colisionAbajo = false;

		int limIzq = this.x - this.ancho / 2;
        int limDer = this.x + this.ancho / 2;
        int limTop = this.y - this.alto / 2;
        int limBot = this.y + this.alto / 2;

        for (Isla isla : islas) {
            if (isla != null) {
                if (verificarColisionAbajo(isla, limIzq, limDer, limBot, limTop)) {
                    this.y = isla.getY() - isla.getAlto() / 2 - this.alto / 2;
                    this.enElAire = false;
                    colisionAbajo = true;
                    if(limIzq < isla.getX() - isla.getAncho() / 2) {
                    	direccion = true;
                    }
                    if(limDer > isla.getX() + isla.getAncho() / 2) {
                    	direccion = false;
                    }
                    break;
                }
            }
        }
		if (!colisionAbajo) {
			enElAire = true;
		}
	}
	private boolean verificarColisionAbajo(Isla isla, int limIzq, int limDer, int limBot, int limTop) {
        return limDer > isla.getX() - isla.getAncho() / 2 && limIzq < isla.getX() + isla.getAncho() / 2
                && limBot >= isla.getY() - isla.getAlto() / 2 && limTop < isla.getY();
    }

	public int getX() {return x;}
	public int getY() {return y;}
	public int getAlto() {return alto;}
	public int getAncho() {return ancho;}
	public boolean getEnElAire() { return enElAire;}
	public int getDireccion() { if(direccion) { return 1; } else { return -1; } }
	
	public boolean colisionConBolaDeFuego(Proyectil bolaDeFuego) {
        int tortugaIzquierda = this.x - this.ancho / 2;
        int tortugaDerecha = this.x + this.ancho / 2;

        int bolaDeFuegoIzquierda = bolaDeFuego.getX() - bolaDeFuego.getAncho() / 2;
        int bolaDeFuegoDerecha = bolaDeFuego.getX() + bolaDeFuego.getAncho() / 2;
        boolean colisionX = false;

        if(getY()==bolaDeFuego.getY()) {
        	colisionX = tortugaDerecha > bolaDeFuegoIzquierda && tortugaIzquierda < bolaDeFuegoDerecha;
        }
        return colisionX;
    }
	
	public void respawnear() {
		int xTortuga = random.nextInt(800);
		while(!(xTortuga > 85 && xTortuga < 315 || xTortuga > 485 && xTortuga < 800)) {
			xTortuga = random.nextInt(800);
		}
		x = xTortuga;
		y = 0;
	}
	
}