package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;

public class Tortuga {

	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Color color;
	private double velocidadY = 0;
	private boolean enElAire = false;
	private boolean direccion; // verdadero = izquierda, falso = derecha
	private final double GRAVEDAD = 0.5;
	private Random random = new Random();

	
	// quiero dejar la carrera
	public Tortuga(int x, int y, int ancho, int alto, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
		this.direccion = random.nextBoolean();

	}

	public void mover(Entorno entorno) {
		int limIZQ = this.ancho / 2;
		int limDER = entorno.ancho() - limIZQ;

		if (!enElAire) {
			if (this.direccion && this.x >= limIZQ) {
				this.x -= 2;
			} else if (!this.direccion && this.x <= limDER) {
				this.x += 2;
			} else {
				this.direccion = !this.direccion;
			}
		}
	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, color);
	}

	public void actualizar(Isla[] islas) {
		aplicarGravedad();
		verificarColisiones(islas);
		verificarLímiteSuelo();
	}

	private void aplicarGravedad() {
		if (enElAire) {
			velocidadY += GRAVEDAD;
			y += velocidadY;
		}
	}

	private void verificarColisiones(Isla[] islas) {
		boolean colisionAbajo = false;
		boolean colisionArriba = false;

		int limiteIzquierdoPersonaje = x - ancho / 2;
		int limiteDerechoPersonaje = x + ancho / 2;
		int limiteSuperiorPersonaje = y - alto / 2;
		int limiteInferiorPersonaje = y + alto / 2;

		for (Isla isla : islas) {
			if (isla != null) {
				if (verificarColisionAbajo(isla, limiteIzquierdoPersonaje, limiteDerechoPersonaje,
						limiteInferiorPersonaje, limiteSuperiorPersonaje)) {
					y = isla.getY() - isla.getAlto() / 2 - alto / 2;
					velocidadY = 0;
					enElAire = false;
					colisionAbajo = true;
					break;
				}
				if (verificarColisionArriba(isla, limiteIzquierdoPersonaje, limiteDerechoPersonaje,
						limiteInferiorPersonaje, limiteSuperiorPersonaje)) {
					y = isla.getY() + isla.getAlto() / 2 + alto / 2;
					velocidadY = 0;
					colisionArriba = true;
				}
			}
		}

		if (colisionArriba) {
			velocidadY = GRAVEDAD;
		}
		if (!colisionAbajo) {
			enElAire = true;
		}
	}

	private boolean verificarColisionAbajo(Isla isla, int limiteIzquierdo, int limiteDerecho, int limiteInferior,
			int limiteSuperior) {
		return limiteDerecho > isla.getX() - isla.getAncho() / 2 && limiteIzquierdo < isla.getX() + isla.getAncho() / 2
				&& limiteInferior >= isla.getY() - isla.getAlto() / 2 && limiteSuperior < isla.getY();
	}

	private boolean verificarColisionArriba(Isla isla, int limiteIzquierdo, int limiteDerecho, int limiteInferior,
			int limiteSuperior) {
		return limiteDerecho > isla.getX() - isla.getAncho() / 2 && limiteIzquierdo < isla.getX() + isla.getAncho() / 2
				&& limiteSuperior <= isla.getY() + isla.getAlto() / 2 && limiteInferior > isla.getY();
	}

	private void verificarLímiteSuelo() {
		if (y > 600) {
			y = 600;
			enElAire = false;
		}
	}

	public int getX() {return x;}

	public int getY() {return y;}
	
	public int getAlto() {return alto;}
	
	public int getAncho() {return ancho;}
	
}