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
    private boolean juegoTerminado = false;
	private Random random = new Random();
	private int contadorTick = 0;

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
    
    // ------------- CONTROLA EL MARCADOR -----------------------
    public void actualizarTiempo() {
        contadorTick++;
        if(contadorTick >=60) {
        	this.tiempoTranscurrido++;
        	gnomosRescatados++;
        	contadorTick=0;
        }
    }
    public void mostrarEstadoJuego(Entorno entorno) {
        entorno.cambiarFont("Arial", 18, Color.BLACK);
        entorno.escribirTexto("Tiempo: " + this.tiempoTranscurrido + "s", 20, 20);
        entorno.escribirTexto("Gnomos rescatados: " + this.gnomosRescatados, 20, 40);
        entorno.escribirTexto("Gnomos perdidos: " + this.gnomosPerdidos, 20, 60);
        entorno.escribirTexto("Enemigos eliminados: " + this.enemigosEliminados, 20, 80);
    }
    //-------------- MUESTRA LOS CARTELES--------------
    public void mostrarCartelPerdiste(Entorno e) {
    	entorno.colorFondo(Color.BLACK);
    	entorno.cambiarFont("Arial", 58, Color.RED);
    	entorno.escribirTexto("GAME OVER", 150, 300);
    	entorno.cambiarFont("Arial", 18, Color.BLACK);
    	entorno.escribirTexto("TIEMPO: "+ this.tiempoTranscurrido + "s", 350, 340);
    	entorno.escribirTexto("ENEMIGOS ELIMINADOS: "+ this.enemigosEliminados, 285, 370);
    }
    public void mostrarCartelGanaste(Entorno e) {
    	entorno.colorFondo(Color.WHITE);
    	entorno.cambiarFont("Arial", 58, Color.BLUE);
    	entorno.escribirTexto("CONGRATULATIONS", 110, 300);
    	entorno.cambiarFont("Arial", 18, Color.BLACK);
    	entorno.escribirTexto("TIEMPO: "+ this.tiempoTranscurrido + "s", 350, 340);
    	entorno.escribirTexto("ENEMIGOS ELIMINADOS: "+ this.enemigosEliminados, 285, 370);
    }
    //-----------------------------------------------------------
    //-------------- CREA LOS OBJETOS----------------------------
    private void crearTortugas() {
    	int xTortuga;
    	int index = 0;
    	while(index != tortugas.length) {
    		xTortuga = random.nextInt(800);  
        	if(xTortuga > 85 && xTortuga < 315 || xTortuga > 485 && xTortuga < 800) {
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
    //---------------ACTUALIZA LOS OBJETOS----------------------------
    public void actualizarIslas() {
    	for(Isla isla : islas) {
    		isla.dibujar(entorno);
    	}
    }
    public void actualizarPep() {
    	if(pep != null) {
    		pep.dibujar(entorno);
    		pep.verificarColisiones(islas);
    		pep.verificarLimites();
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
            if(pep.getEnElAire()) { pep.aplicarGravedad(); }
            for(Tortuga tortuga : tortugas) {
            	if(pep.colisionConTortuga(tortuga)) {
            		System.out.println(juegoTerminado);
            		this.juegoTerminado = true;
            		System.out.println(juegoTerminado);
            		pep = null;
            		return;
            	}
            }
            for(Gnomo gnomo : gnomos) {
            	if(gnomo != null) {
            		if(pep.colisionConGnomo(gnomo)) {
            			gnomosRescatados++;
            		}
            	}
            }
            if(pep.cayoAlVacio()) {
                juegoTerminado = true;
            }
    	}
    }
    public void actualizarTortugas() {
    	for(int index = 0; index < tortugas.length; index ++) {
    		if(tortugas[index]!=null) {
    			Tortuga tortuga = tortugas[index];
    			tortuga.dibujar(entorno);
    			tortuga.verificarColisiones(islas);
    			if(tortuga.getEnElAire()) {
    				tortuga.aplicarGravedad();
    			} else {
    				tortuga.mover(entorno);
    			}
    			if(bolaDeFuego != null) {
    				if(tortuga.colisionConBolaDeFuego(bolaDeFuego)) {
    					tortuga.respawnear();
    					this.enemigosEliminados++;
    					bolaDeFuego = null;
    				}
    			}
    		}
    	}
    }
    public void actualizarGnomos() {
    	
    }
    public void actualizarBolaDeFuego() {
    	if(bolaDeFuego!=null) {
    		bolaDeFuego.dibujar(entorno);
    		bolaDeFuego.avanzar();
    		if(bolaDeFuego.desaparecer()) {
    			this.bolaDeFuego = null;
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
    	
    	if(juegoTerminado || gnomosPerdidos == 10) {
    		mostrarCartelPerdiste(entorno);
    		return;
    	} else if(gnomosRescatados == 1) {
    		mostrarCartelGanaste(entorno);
    		return;
    	}
    	
    	//MUESTRA EL FONDO
        this.entorno.colorFondo(Color.cyan);
        mostrarEstadoJuego(entorno);
        actualizarTiempo();
        
        //ACTUALIZA LOS OBJETOS
        actualizarIslas();
        actualizarPep();
        actualizarTortugas();
        actualizarGnomos();
        actualizarBolaDeFuego();
    }
    
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}


