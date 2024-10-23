package juego;

import java.awt.Color;
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

    /**
     * Constructor para inicializar el proyectil.
     * @param coordenadaX Coordenada X inicial del proyectil.
     * @param coordenadaY Coordenada Y inicial del proyectil.
     * @param direccion Dirección del movimiento: 1 para derecha, -1 para izquierda.
     */
    public Proyectil(int coordenadaX, int coordenadaY, int direccion) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.direccion = direccion;
    }

    /**
     * Devuelve la coordenada X actual del proyectil.
     * @return Coordenada X del proyectil.
     */
    public int getX() { 
        return this.coordenadaX; 
    }

    /**
     * Dibuja el proyectil en el entorno.
     * @param e Entorno donde se dibuja el proyectil.
     */
    public void dibujar(Entorno e) {
        e.dibujarRectangulo(this.coordenadaX, this.coordenadaY, ANCHO, ALTO, 0, Color.ORANGE);
    }

    /**
     * Avanza el proyectil en la dirección indicada a una velocidad constante.
     * La coordenada X cambia dependiendo de la dirección.
     */
    public void avanzar() {
        this.coordenadaX += VELOCIDADX * direccion;
    }

    /**
     * Determina si el proyectil debe desaparecer cuando sale de la pantalla
     * o si colisiona con alguna tortuga.
     * @param tortugas Arreglo de tortugas para comprobar colisión.
     * @return true si el proyectil debe desaparecer, false si debe seguir activo.
     */
    public int desaparecer(Tortuga[] tortugas) {
    	if ((direccion == 1 && this.coordenadaX >= 800) || (direccion == -1 && this.coordenadaX <= 0)) {
            return 1;
        }
        
        for(Tortuga tortuga : tortugas) {
            if(direccion == -1) { 
            	if(this.coordenadaX > tortuga.getX()) {
            		if(this.coordenadaX - 5 <= tortuga.getX() + tortuga.getAncho() && this.coordenadaY == tortuga.getY()) { 
            			return 2;
            		}
            	}
            } else if(direccion == 1) {
            	if(this.coordenadaX < tortuga.getX()) {
            		if(this.coordenadaX + 5 >= tortuga.getX() - tortuga.getAncho() && this.coordenadaY == tortuga.getY()) { 
            			return 2;
            		}
            	}
            }
        }
        
        return 0;
    }
}