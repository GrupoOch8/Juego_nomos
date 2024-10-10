package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private static final int ANCHO_ENTORNO = 800;
	private static final int ALTO_ENTORNO = 600;
	private Entorno entorno;
	private Isla[] islas;
	// Variables y métodos propios de cada grupo
	// ...
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al rescate de los Gnomos - Grupo 8 - Igor - Abalde - Choque", ANCHO_ENTORNO, ALTO_ENTORNO);
		this.islas = new Isla[15];
		crearIslas();
		
		// Inicializar lo que haga falta para el juego
		// ...
		// Inicia el juego!
		this.entorno.iniciar();
		
	}
	
	private void crearIslas() {
        int xCentro = ANCHO_ENTORNO / 2;
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
	
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		this.entorno.colorFondo(Color.cyan);
		
		for (Isla isla : islas) {
            entorno.dibujarRectangulo(isla.getX(), isla.getY(), isla.getAncho(), isla.getAlto(), 0, isla.getColor());
        }
		// Procesamiento de un instante de tiempo
		// ...
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
