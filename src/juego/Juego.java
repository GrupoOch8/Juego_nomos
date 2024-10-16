package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    private Isla[] islas;
    private Pep pep;

    public Juego() {
        // Inicializa los objetos
        this.entorno = new Entorno(this, "Al rescate de los Gnomos - Grupo 8 - Igor - Abalde - Choque", 800, 600);
        this.islas = new Isla[15];
        this.pep = new Pep(400, 300, 30, 30, Color.RED);
        
        // Crea el entorno
        crearIslas();
        this.entorno.iniciar();
    }
    
    private void crearIslas() {
        int xCentro = 800 / 2;
        int yInicial = 100;
        int anchoIsla = 120;
        int altoIsla = 30;
        int espaciadoX = 180;
        int espaciadoY = 100;
        int index = 0;

        for (int filas = 0; filas < 5; filas++) {
            int xInicial = xCentro - (filas * espaciadoX / 2);
            for (int i = 0; i <= filas; i++) {
                if (index < islas.length) {
                    int xPos = xInicial + i * espaciadoX;
                    int yPos = yInicial + filas * espaciadoY;
                    islas[index] = new Isla(xPos, yPos, anchoIsla, altoIsla, Color.GREEN);
                    index++;
                }
            }
        }
    }
    
    public boolean estaSobreIsla(Pep pep) {
        for (Isla isla : islas) {
            if (isla != null &&
                pep.getX() > isla.getX() - isla.getAncho() / 2 &&
                pep.getX() < isla.getX() + isla.getAncho() / 2 &&
                pep.getY() >= isla.getY() - isla.getAlto() / 2 &&
                pep.getY() <= isla.getY() + isla.getAlto() / 2) {
                return true;
            }
        }
        return false;
    }
    
    public Isla[] getIslas() {
        return this.islas;
    }
    
    public void tick() {
        // DIBUJADO DEL ENTORNO
        this.entorno.colorFondo(Color.cyan);
        
        for (Isla isla : islas) {
            if (isla != null) {
                isla.dibujar(this.entorno);
            }
        }
        
        pep.dibujar(this.entorno);
        pep.actualizar(this.islas);
        
        // FUNCIONES DE Pep
        if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
            pep.moverIzquierda();
        }
        if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
            pep.moverDerecha();
        }
        if (this.entorno.estaPresionada(this.entorno.TECLA_ESPACIO)) {
            pep.saltar();
        }
        
        // Procesamiento de un instante de tiempo
        // ...
    }
    
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}

