package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private Pep pep;
    private Proyectil bolaDeFuego = null;
    private Isla[] islas;
    private Tortuga[] tortugas;
    private Gnomo[] gnomos;
    //private Image casaGnomo, fondo;
    private int tiempoTranscurrido;
    private int gnomosRescatados;
    private int gnomosPerdidos;
    private int enemigosEliminados;
	private Random random = new Random();

    public Juego() {
    	//inicializa el objeto entorno
        this.entorno = new Entorno(this, "Al rescate de los Gnomos - Grupo 8 - Igor - Abalde - Choque", 800, 600);
        this.islas = new Isla[15];
        this.gnomos = new Gnomo[5];
        this.tortugas = new Tortuga[5];
        this.pep = new Pep(50, 500, 30, 30, Color.RED);
        this.tiempoTranscurrido = 0;
        this.gnomosRescatados = 0;
        this.gnomosPerdidos = 0;
        this.enemigosEliminados = 0;
        //this.casaGnomo = Herramientas.cargarImagen(null);
        //this.fondo = Herramientas.cargarImagen("fondognomos.png");
        
        crearTortugas();
        crearGnomos();
        crearIslas();
        this.entorno.iniciar();
    }
    
    public void actualizarTiempo(int tiempoAnterior) {
        this.tiempoTranscurrido += tiempoAnterior;
    }
    
    public void mostrarEstadoJuego(Entorno entorno) {
        entorno.cambiarFont("Arial", 18, Color.WHITE);
        entorno.escribirTexto("Tiempo: " + this.tiempoTranscurrido + "s", 20, 20);
        entorno.escribirTexto("Gnomos rescatados: " + this.gnomosRescatados, 20, 40);
        entorno.escribirTexto("Gnomos perdidos: " + this.gnomosPerdidos, 20, 60);
        entorno.escribirTexto("Enemigos eliminados: " + this.enemigosEliminados, 20, 80);
    }
    
    private void crearTortugas() {
    	int xTortuga;
    	int index = 0;
    	while(index != tortugas.length) {
    		xTortuga = random.nextInt();  
        	if(xTortuga > 0 && xTortuga < 315 || xTortuga > 485 && xTortuga < 800) {
        		tortugas[index] = new Tortuga(xTortuga, 0, 30, 30, Color.BLUE);
        		index++;
        	}
    	}
    }
    
    private void crearGnomos() {}
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
            if (isla != null && pep!= null &&
                pep.getX() > isla.getX() - isla.getAncho() / 2 &&
                pep.getX() < isla.getX() + isla.getAncho() / 2 &&
                pep.getY() >= isla.getY() - isla.getAlto() / 2 &&
                pep.getY() <= isla.getY() + isla.getAlto() / 2) {
                return true;
            }
        }
        return false;
    }
    
    public void tick() {
        this.entorno.colorFondo(Color.cyan);
        mostrarEstadoJuego(entorno);
        
        for (Isla isla : islas) { isla.dibujar(this.entorno); }
        for (Tortuga tortuga : tortugas) { tortuga.dibujar(entorno);}
        
        if(pep!=null) {
        	pep.dibujar(this.entorno);
            pep.actualizar(this.islas);
        }
        if(bolaDeFuego != null) {
    		bolaDeFuego.dibujar(entorno);
    		bolaDeFuego.avanzar();
    		if(bolaDeFuego.desaparecer(tortugas) == 1) {
    			this.bolaDeFuego = null;
    		} else if(bolaDeFuego.desaparecer(tortugas) == 2) {
    			this.bolaDeFuego = null;
    			this.enemigosEliminados++;
    		}
    	}
        
        for (Gnomo gnomo : gnomos) {
    		if (gnomo != null && pep!= null) { 
    			if (pep.colisionConGnomo(gnomo)) {
    				this.gnomosRescatados++;
    			}
    			if (gnomo.seHaPerdido()) {
    				this.gnomosPerdidos++;
    			}
    		}
    	}
    
        
        for (Tortuga tortuga : tortugas) {
        	tortuga.actualizar(islas, entorno);
        	if (tortuga != null && pep!=null) {
        		if(pep.colisionConTortuga(tortuga)) {
        			pep = null;
        		}
    		}
        }
        
        // FUNCIONES DE Pep
        if(pep!=null) {
        	if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA) || this.entorno.estaPresionada('A')) {
                pep.moverIzquierda();
            }
            if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA) || this.entorno.estaPresionada('D')) {
                pep.moverDerecha();
            }
            if (this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA) || this.entorno.estaPresionada('W')) {
                pep.saltar();
            }
            if (this.entorno.sePresionoBoton(this.entorno.BOTON_IZQUIERDO) || this.entorno.estaPresionada('C')) {
            	if(bolaDeFuego == null && !pep.getEnElAire()) {
            		this.bolaDeFuego = new Proyectil(pep.getX() + (pep.getAncho() / 2 + 10) * pep.getDireccion(), pep.getY(), pep.getDireccion());
            	}	
            }
        }
    }
    
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}


