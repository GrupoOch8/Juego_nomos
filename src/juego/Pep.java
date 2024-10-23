package juego;

import java.awt.Color;
import entorno.Entorno;

public class Pep {
    // Constantes
    private static final double GRAVEDAD = 0.5;
    private static final double VELOCIDAD_SALTO = -10;
    private static final double VELOCIDAD_MOVIMIENTO = 5;
    private static final int LIMITE_INFERIOR = 600;
    private static final int LIMITE_IZQUIERDO = 0;
    private static final int LIMITE_DERECHO = 800;
    private static final int POSICION_RESET_X = 50;
    private static final int POSICION_RESET_Y = 500;

    // Variables de instancia
    private int coordenadaX;
    private int coordenadaY;
    private final int ancho;
    private final int alto;
    private final Color color;
    private double velocidadY = 0;
    private boolean enElAire = false;
    private int direccion = 1;

    /**
     * Constructor para inicializar a Pep.
     * @param x Coordenada inicial en el eje X.
     * @param y Coordenada inicial en el eje Y.
     * @param ancho Ancho del personaje.
     * @param alto Alto del personaje.
     * @param color Color del personaje.
     */
    public Pep(int x, int y, int ancho, int alto, Color color) {
        this.coordenadaX = x;
        this.coordenadaY = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
    }

    /**
     * Dibuja a Pep en la pantalla.
     * @param e Entorno donde se dibuja el personaje.
     */
    public void dibujar(Entorno e) {
        e.dibujarRectangulo(this.coordenadaX, this.coordenadaY, this.ancho, this.alto, 0, this.color);
    }

    // Getters
    public int getX() { return this.coordenadaX; }
    public int getY() { return this.coordenadaY; }
    public int getDireccion() { return this.direccion; }
    public boolean getEnElAire() { return this.enElAire; }
    public int getAncho() { return this.ancho; }
    public int getAlto() {return this.alto;}

    /**
     * Mueve a Pep hacia la izquierda.
     * Disminuye la coordenada X y ajusta la dirección.
     */
    public void moverIzquierda() {
        this.coordenadaX -= VELOCIDAD_MOVIMIENTO;
        this.direccion = -1;
    }

    /**
     * Mueve a Pep hacia la derecha.
     * Aumenta la coordenada X y ajusta la dirección.
     */
    public void moverDerecha() {
        this.coordenadaX += VELOCIDAD_MOVIMIENTO;
        this.direccion = 1;
    }

    /**
     * Hace que Pep salte si no está en el aire.
     * Ajusta la velocidad vertical para simular el salto.
     */
    public void saltar() {
        if (!this.enElAire) {
            this.velocidadY = VELOCIDAD_SALTO;
            this.enElAire = true;
        }
    }

    /**
     * Actualiza la posición de Pep y aplica gravedad y colisiones.
     * @param islas Arreglo de islas para verificar colisiones.
     */
    public void actualizar(Isla[] islas) {
        aplicarGravedad();
        verificarColisiones(islas);
        verificarLimites();
    }

    /**
     * Aplica gravedad a Pep si está en el aire.
     * Aumenta la velocidad vertical y ajusta la posición en Y.
     */
    private void aplicarGravedad() {
        if (this.enElAire) {
            this.velocidadY += GRAVEDAD;
            this.coordenadaY += this.velocidadY;
        }
    }

    /**
     * Verifica colisiones de Pep con las islas.
     * Ajusta la posición de Pep si hay colisión y controla su movimiento.
     * @param islas Arreglo de islas para verificar las colisiones.
     */
    private void verificarColisiones(Isla[] islas) {
        boolean colisionAbajo = false;
        boolean colisionArriba = false;

        int limIzq = this.coordenadaX - this.ancho / 2;
        int limDer = this.coordenadaX + this.ancho / 2;
        int limTop = this.coordenadaY - this.alto / 2;
        int limBot = this.coordenadaY + this.alto / 2;

        for (Isla isla : islas) {
            if (isla != null) {
                if (verificarColisionAbajo(isla, limIzq, limDer, limBot, limTop)) {
                    this.coordenadaY = isla.getY() - isla.getAlto() / 2 - this.alto / 2;
                    this.velocidadY = 0;
                    this.enElAire = false;
                    colisionAbajo = true;
                    break;
                }
                if (verificarColisionArriba(isla, limIzq, limDer, limBot, limTop)) {
                    this.coordenadaY = isla.getY() + isla.getAlto() / 2 + this.alto / 2;
                    this.velocidadY = 0;
                    colisionArriba = true;
                }
                if (verificarColisionLateral(isla, limIzq, limDer, limBot, limTop)) {
                    this.coordenadaX += VELOCIDAD_MOVIMIENTO * -direccion;
                }
            }
        }

        if (colisionArriba) {
            this.velocidadY = GRAVEDAD;
        }
        if (!colisionAbajo) {
            this.enElAire = true;
        }
    }

    /**
     * Verifica si Pep colisiona con una isla desde abajo.
     * @param isla La isla a verificar.
     * @param limiteIzquierdo Límite izquierdo de Pep.
     * @param limiteDerecho Límite derecho de Pep.
     * @param limiteInferior Límite inferior de Pep.
     * @param limiteSuperior Límite superior de Pep.
     * @return Verdadero si hay colisión desde abajo, falso si no.
     */
    private boolean verificarColisionAbajo(Isla isla, int limIzq, int limDer, int limBot, int limTop) {
        return limDer > isla.getX() - isla.getAncho() / 2 && limIzq < isla.getX() + isla.getAncho() / 2
                && limBot >= isla.getY() - isla.getAlto() / 2 && limTop < isla.getY();
    }

    /**
     * Verifica si Pep colisiona con una isla desde arriba.
     * @param isla La isla a verificar.
     * @param limiteIzquierdo Límite izquierdo de Pep.
     * @param limiteDerecho Límite derecho de Pep.
     * @param limiteInferior Límite inferior de Pep.
     * @param limiteSuperior Límite superior de Pep.
     * @return Verdadero si hay colisión desde arriba, falso si no.
     */
    private boolean verificarColisionArriba(Isla isla, int limIzq, int limDer, int limBot, int limTop) {
        return limDer > isla.getX() - isla.getAncho() / 2 && limIzq < isla.getX() + isla.getAncho() / 2
                && limTop <= isla.getY() + isla.getAlto() / 2 && limBot > isla.getY();
    }

    /**
     * Verifica si Pep colisiona lateralmente con una isla.
     * @param isla La isla a verificar.
     * @param limiteIzquierdo Límite izquierdo de Pep.
     * @param limiteDerecho Límite derecho de Pep.
     * @param limiteInferior Límite inferior de Pep.
     * @param limiteSuperior Límite superior de Pep.
     * @return Verdadero si hay colisión lateral, falso si no.
     */
    private boolean verificarColisionLateral(Isla isla, int limIzq, int limDer, int limBot, int limTop) {
        boolean colisionLadoDerecho = limDer >= isla.getX() - isla.getAncho() / 2 
                                      && limIzq < isla.getX() 
                                      && limBot > isla.getY() - isla.getAlto() / 2 
                                      && limTop < isla.getY() + isla.getAlto() / 2;

        boolean colisionLadoIzquierdo = limIzq <= isla.getX() + isla.getAncho() / 2 
                                        && limDer > isla.getX() 
                                        && limBot > isla.getY() - isla.getAlto() / 2 
                                        && limTop < isla.getY() + isla.getAlto() / 2;

        return colisionLadoDerecho || colisionLadoIzquierdo;
    }

    /**
     * Verifica si Pep ha llegado a los límites de la pantalla y ajusta su posición en consecuencia.
     */
    private void verificarLimites() {
        int limIzq = this.coordenadaX - this.ancho / 2;
        int limDer = this.coordenadaX + this.ancho / 2;
        int limBot = this.coordenadaY + this.alto / 2;

        if (limIzq <= LIMITE_IZQUIERDO) {
            this.coordenadaX += VELOCIDAD_MOVIMIENTO;
        }
        if (limDer >= LIMITE_DERECHO) {
            this.coordenadaX -= VELOCIDAD_MOVIMIENTO;
        }
        if (limBot >= LIMITE_INFERIOR) {
            this.coordenadaY = POSICION_RESET_Y;
            this.coordenadaX = POSICION_RESET_X;
        }
    }
    
    public boolean colisionConGnomo(Gnomo gnomo) {
        int pepIzquierda = this.coordenadaX - this.ancho / 2;
        int pepDerecha = this.coordenadaX + this.ancho / 2;
        int pepArriba = this.coordenadaY - this.alto / 2;
        int pepAbajo = this.coordenadaY + this.alto / 2;

        double gnomoIzquierda = gnomo.getX() - gnomo.getAncho() / 2;
        double gnomoDerecha = gnomo.getX() + gnomo.getAncho() / 2;
        double gnomoArriba = gnomo.getY() - gnomo.getAlto() / 2;
        double gnomoAbajo = gnomo.getY() + gnomo.getAlto() / 2;

        boolean colisionX = pepDerecha > gnomoIzquierda && pepIzquierda < gnomoDerecha;
        boolean colisionY = pepAbajo > gnomoArriba && pepArriba < gnomoAbajo;

        return colisionX && colisionY;
    }
    
    public boolean colisionConTortuga(Tortuga tortuga) {
        int pepIzquierda = this.coordenadaX - this.ancho / 2;
        int pepDerecha = this.coordenadaX + this.ancho / 2;
        int pepArriba = this.coordenadaY - this.alto / 2;
        int pepAbajo = this.coordenadaY + this.alto / 2;

        int tortugaIzquierda = tortuga.getX() - tortuga.getAncho() / 2;
        int tortugaDerecha = tortuga.getX() + tortuga.getAncho() / 2;
        int tortugaArriba = tortuga.getY() - tortuga.getAlto() / 2;
        int tortugaAbajo = tortuga.getY() + tortuga.getAlto() / 2;

        boolean colisionX = pepDerecha > tortugaIzquierda && pepIzquierda < tortugaDerecha;
        boolean colisionY = pepAbajo > tortugaArriba && pepArriba < tortugaAbajo;

        return colisionX && colisionY;
    }
}
