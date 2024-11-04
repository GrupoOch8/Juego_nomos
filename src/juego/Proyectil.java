package juego;

import java.awt.Image;
import entorno.Entorno;

public class Proyectil {
	// Constantes
	private static final int ANCHO = 20;
	private static final int ALTO = 20;
	private static final int VELOCIDADX = 5;

	// Variables de instancia
	private int coordenadaX;
	private int coordenadaY;
	private int direccion;
	private boolean enElAire;

	/**
	 * Constructor para inicializar el proyectil.
	 * 
	 * @param coordenadaX Coordenada X inicial del proyectil.
	 * @param coordenadaY Coordenada Y inicial del proyectil.
	 * @param direccion   Dirección del movimiento: 1 para derecha, -1 para
	 *                    izquierda.
	 */
	public Proyectil(int coordenadaX, int coordenadaY, int direccion) {
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		this.direccion = direccion;
	}

	/**
	 * Devuelve la coordenada X actual del proyectil.
	 * 
	 * @return Coordenada X del proyectil.
	 */
	public int getX() {
		return this.coordenadaX;
	}

	public int getY() {
		return this.coordenadaY;
	}

	public int getAncho() {
		return ANCHO;
	}

	public int getAlto() {
		return ALTO;
	}

	public boolean getEnElAire() {
		return enElAire;
	}

	/**
	 * Dibuja el proyectil en el entorno.
	 * 
	 * @param e Entorno donde se dibuja el proyectil.
	 */
	public void dibujar(Entorno e, Image proyectil) {
		if (proyectil != null) {
			e.dibujarImagen(proyectil, this.coordenadaX, this.coordenadaY, 0, 0.08);
		}
	}

	/**
	 * Avanza el proyectil en la dirección indicada a una velocidad constante. La
	 * coordenada X cambia dependiendo de la dirección.
	 */
	public void avanzar() {
		this.coordenadaX += VELOCIDADX * direccion;
	}

	/**
	 * Determina si el proyectil debe desaparecer cuando sale de la pantalla o si
	 * colisiona con alguna tortuga.
	 * 
	 * @param tortugas Arreglo de tortugas para comprobar colisión.
	 * @return true si el proyectil debe desaparecer, false si debe seguir activo.
	 */
	public boolean desaparecer() {
		return (direccion == 1 && this.coordenadaX >= 800) || (direccion == -1 && this.coordenadaX <= 0);
	}

	public boolean colisionConBolaDeFuego(Proyectil bolaDeFuego) {
		int bombaIzquierda = this.coordenadaX - ANCHO / 2;
		int bombaDerecha = this.coordenadaX + ANCHO / 2;

		int bolaDeFuegoIzquierda = bolaDeFuego.getX() - bolaDeFuego.getAncho() / 2;
		int bolaDeFuegoDerecha = bolaDeFuego.getX() + bolaDeFuego.getAncho() / 2;
		boolean colisionX = false;

		if (getY() == bolaDeFuego.getY()) {
			colisionX = bombaDerecha > bolaDeFuegoIzquierda && bombaIzquierda < bolaDeFuegoDerecha;
		}
		return colisionX;
	}
}