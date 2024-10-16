package juego;

import java.awt.Color;
import entorno.Entorno;

public class Pep {
	//Variables
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color color;
    private double velocidadY = 0;
    private boolean enElAire = false;
    private final double GRAVEDAD = 0.5;
    private final double VELOCIDAD_SALTO = -10;

    // Constructor
    public Pep(int x, int y, int ancho, int alto, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
    }

    // Funciones de Pep
    public void moverIzquierda() {
        x -= 5;
    }

    public void moverDerecha() {
        x += 5;
    }
    
    public void saltar() {
        if (!enElAire) {
            velocidadY = VELOCIDAD_SALTO;
            enElAire = true;
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
                if (verificarColisionAbajo(isla, limiteIzquierdoPersonaje, limiteDerechoPersonaje, limiteInferiorPersonaje, limiteSuperiorPersonaje)) {
                    y = isla.getY() - isla.getAlto() / 2 - alto / 2;
                    velocidadY = 0;
                    enElAire = false;
                    colisionAbajo = true;
                    break;
                }
                if (verificarColisionArriba(isla, limiteIzquierdoPersonaje, limiteDerechoPersonaje, limiteInferiorPersonaje, limiteSuperiorPersonaje)) {
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

    private boolean verificarColisionAbajo(Isla isla, int limiteIzquierdo, int limiteDerecho, int limiteInferior, int limiteSuperior) {
        return limiteDerecho > isla.getX() - isla.getAncho() / 2 &&
               limiteIzquierdo < isla.getX() + isla.getAncho() / 2 &&
               limiteInferior >= isla.getY() - isla.getAlto() / 2 &&
               limiteSuperior < isla.getY();
    }

    private boolean verificarColisionArriba(Isla isla, int limiteIzquierdo, int limiteDerecho, int limiteInferior, int limiteSuperior) {
        return limiteDerecho > isla.getX() - isla.getAncho() / 2 &&
               limiteIzquierdo < isla.getX() + isla.getAncho() / 2 &&
               limiteSuperior <= isla.getY() + isla.getAlto() / 2 &&
               limiteInferior > isla.getY();
    }

    private void verificarLímiteSuelo() {
        if (y > 600) {
            y = 600;
            enElAire = false;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
