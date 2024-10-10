package juego;

import java.awt.Color;
import entorno.Entorno;

public class Pep {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color color;
    private double velocidadY = 0;
    private boolean enElAire = false;
    private final double GRAVEDAD = 0.5;
    private final double VELOCIDAD_SALTO = -11;

    public Pep(int x, int y, int ancho, int alto, Color color) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
    }

    public void moverIzquierda() {
        x -= 5;
    }

    public void moverDerecha() {
        x += 5;
    }

    public void dibujar(Entorno e) {
        e.dibujarRectangulo(x, y, ancho, alto, 0, color);
    }
    
    public void saltar() {
        if (!enElAire) {
            velocidadY = VELOCIDAD_SALTO;
            enElAire = true;
        }
    }

    public void actualizar(Isla[] islas) {
        if (enElAire) {
            velocidadY += GRAVEDAD;
            y += velocidadY;

            boolean colisionArriba = false;
            boolean colisionAbajo = false;

            for (Isla isla : islas) {
                if (isla != null) {
                    int limiteIzquierdoPersonaje = x - ancho / 2;
                    int limiteDerechoPersonaje = x + ancho / 2;
                    int limiteSuperiorPersonaje = y - alto / 2;
                    int limiteInferiorPersonaje = y + alto / 2;

                    // Colisión por debajo
                    if (limiteIzquierdoPersonaje < isla.getX() + isla.getAncho() / 2 &&
                        limiteDerechoPersonaje > isla.getX() - isla.getAncho() / 2 &&
                        limiteInferiorPersonaje >= isla.getY() - isla.getAlto() / 2 &&
                        limiteSuperiorPersonaje <= isla.getY() + isla.getAlto() / 2) {
                        // Coloca al personaje en la parte superior de la isla
                        y = isla.getY() - isla.getAlto() / 2 - (alto / 2);
                        velocidadY = 0;
                        enElAire = false;
                        colisionAbajo = true;
                        break;
                    }

                    // Colisión por encima
                    if (limiteIzquierdoPersonaje < isla.getX() + isla.getAncho() / 2 &&
                        limiteDerechoPersonaje > isla.getX() - isla.getAncho() / 2 &&
                        limiteSuperiorPersonaje <= isla.getY() + isla.getAlto() / 2 &&
                        limiteInferiorPersonaje >= isla.getY() - isla.getAlto() / 2) {
                        colisionArriba = true;
                    }
                }
            }
            
            if (colisionArriba) {
                y += velocidadY;
            }

            if (y > 600) {
                y = 600;
                enElAire = false;
            }
            
        } else {
            boolean sobreIsla = false;
            for (Isla isla : islas) {
                if (isla != null &&
                    x > isla.getX() - isla.getAncho() / 2 &&
                    x < isla.getX() + isla.getAncho() / 2 &&
                    y >= isla.getY() - isla.getAlto() / 2 && 
                    y <= isla.getY() + isla.getAlto() / 2) {
                    sobreIsla = true;
                    break;
                }
            }

            if (!sobreIsla) {
                velocidadY += GRAVEDAD;
                y += velocidadY;
            }
        }

        if (y > 600) {
            y = 600;
            enElAire = false;
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
