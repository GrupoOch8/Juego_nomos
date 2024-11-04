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
    private Proyectil[] bombas;
    private Isla[] islas;
    private Tortuga[] tortugas;
    private Gnomo[] gnomos;
    private Image fondo;
    private Image casaGnomo;
    private Image imagenPep;
    private Image imagenGnomo;
    private Image imagenTortuga;
    private int tiempoTranscurrido = 0;
    private int gnomosRescatados = 0;
    private int gnomosPerdidos = 0;
    private int enemigosEliminados = 0;
    private boolean juegoTerminado = false;
	private Random random = new Random();
	private int contadorTick = 0;

    public Juego() {
    	//inicializa el objeto entorno
        this.entorno = new Entorno(this, "Al rescate de los Gnomos - Grupo 8 - Igor - Abalde - Choque", 800, 600);
        this.islas = new Isla[15];
        this.gnomos = new Gnomo[5];
        this.tortugas = new Tortuga[5];
        this.bombas = new Proyectil[5];
        this.pep = new Pep(50, 500, 30, 30, Color.RED);
        this.fondo = Herramientas.cargarImagen("Imagenes/Fondo.jpg");
        this.casaGnomo = Herramientas.cargarImagen("Imagenes/Casa.png");
        this.imagenPep = Herramientas.cargarImagen("Imagenes/Pep.png");
        this.imagenGnomo = Herramientas.cargarImagen("Imagenes/Gnomo.png");
        this.imagenTortuga = Herramientas.cargarImagen("Imagenes/Tortuga.png");
        
        crearTortugas();
        crearIslas();
        crearGnomos();
        this.entorno.iniciar();
    }
    
    // ------------- CONTROLA EL MARCADOR -----------------------
    public void actualizarTiempo() {
        contadorTick++;
        if(contadorTick % 60 == 0) {
        	this.tiempoTranscurrido++;
        }
    }
    public void mostrarEstadoJuego(Entorno entorno) {
        entorno.cambiarFont("Arial", 18, Color.BLACK);
        entorno.escribirTexto("TIEMPO: " + this.tiempoTranscurrido + "s", 50, 20);
        entorno.escribirTexto("PERDIDOS: " + this.gnomosPerdidos, 200, 20);
        entorno.escribirTexto("SALVADOS: " + this.gnomosRescatados, 475, 20);
        entorno.escribirTexto("ELIMINADOS: " + this.enemigosEliminados, 625, 20);
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
    private void generarBombas(Tortuga[] tortugas) {
    	for(int index = 0; index < 5; index ++) {
    		if(!tortugas[index].getEnElAire() || bombas[index] == null) {
    			bombas[index] = new Proyectil(tortugas[index].getX() + (tortugas[index].getAncho() / 2 + 10) * tortugas[index].getDireccion(),
    				                      	tortugas[index].getY() + tortugas[index].getAlto() / 2 - 10, 
    				                      	tortugas[index].getDireccion(), Color.GRAY);	
    		}
    	}
    }
    private void crearGnomos() {
		int xInicial = islas[0].getX() -15;
		int yInicial = islas[0].getY() - islas[0].getAlto() / 2 - 15;
		for (int i = 0; i < gnomos.length; i++) {
			gnomos[i] = new Gnomo(xInicial + i * 10, yInicial, 20, 20, Color.MAGENTA);
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
    		pep.dibujar(entorno, imagenPep);
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
            		this.bolaDeFuego = new Proyectil(pep.getX() + (pep.getAncho() / 2 + 10) * pep.getDireccion(), pep.getY() + pep.getAlto() / 2 - 10, pep.getDireccion(), Color.ORANGE);
            	}	
            }
            
            if(pep.getEnElAire()) { 
            	pep.aplicarGravedad(); 
            }
            
            for(Tortuga tortuga : tortugas) {
            	if(pep.colisionConTortuga(tortuga)) {
            		this.juegoTerminado = true;
            		pep = null;
            		return;
            	}
            }
            
            if(pep.cayoAlVacio()) {
                juegoTerminado = true;
            }
            
            for(Proyectil bomba : bombas) {
            	if(bomba != null) {
            		if(pep.colisionConBomba(bomba)) {
            			this.juegoTerminado = true;
            			pep = null;
            			return;
            		}
            	}
            }
    	}
    }
    public void actualizarTortugas() {
    	for(Tortuga tortuga : tortugas) {
    		if(tortuga != null) {
    			tortuga.dibujar(entorno, imagenTortuga);
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
    			if(contadorTick % 300 == 0) {
    				generarBombas(tortugas);
    			}
    		}
    	}
    }
    public void actualizarGnomos() {
        for (int index = 0; index < gnomos.length; index++) {
            Gnomo gnomo = gnomos[index];
            if (gnomo != null) {
                gnomo.dibujar(entorno, imagenGnomo);
                gnomo.verificarColisiones(islas);

                if (gnomo.estaEnElAire()) {
                    gnomo.caer();
                } else {
                    gnomo.mover();
                }

                if (gnomo.caerAlVacio()) {
                    gnomos[index] = null;
                    gnomosPerdidos++;
                }
                for (Proyectil bomba : bombas) {
                    if (bomba != null) {
                        if (gnomo.colisionaConBomba(bomba)) {
                            gnomos[index] = null;
                            gnomosPerdidos++;
                        }
                    }
                }
                for (Tortuga tortuga : tortugas) {
                    if (gnomo.colisionaConTortuga(tortuga)) {
                        gnomos[index] = null;
                        gnomosPerdidos++;
                        break;
                    }

                }

                if (pep != null) {
                    if (gnomo.getY() >= 340) {
                        if (gnomo.colisionPep(pep)) {
                            gnomos[index] = null;
                            gnomosRescatados++;
                        }
                    }
                }
            }
        }
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
    public void actualizarBombas() {
    	for(int index = 0; index < bombas.length; index ++) {
    		if(bombas[index]!=null) {
    			Proyectil bomba = bombas[index];
    			bomba.dibujar(entorno);
    			bomba.avanzar();
    			if(bomba.desaparecer()) {
    				bombas[index] = null;
    			}
    			
    			if(bolaDeFuego != null) {
    				if(bomba.colisionConBolaDeFuego(bolaDeFuego)) {
    					bombas[index]=null;
    					bolaDeFuego = null;
    				}
    			}
    		}
    	}
    }

    
    public void tick() {
    	
    	entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
    	entorno.dibujarImagen(casaGnomo, entorno.ancho() / 2, 50, 0, 0.2);
    	
    	if(juegoTerminado || gnomosPerdidos == 10) {
    		mostrarCartelPerdiste(entorno);
    		return;
    	} else if(gnomosRescatados == 10) {
    		mostrarCartelGanaste(entorno);
    		return;
    	}
    	
    	//MUESTRA EL FONDO
        this.entorno.colorFondo(Color.cyan);
        mostrarEstadoJuego(entorno);
        actualizarTiempo();
        
     // Dibuja y actualiza Pep
        if (pep != null) {
            pep.dibujar(entorno, imagenPep);
        }
        
     // Dibuja y actualiza Gnomos
        for (Gnomo gnomo : gnomos) {
            if (gnomo != null) {
                gnomo.dibujar(entorno, imagenGnomo);
            }
        }
    // Dibuja y actualiza Tortugas
        for (Tortuga tortuga : tortugas) {
            if (tortuga != null) {
                tortuga.dibujar(entorno, imagenTortuga);
            }
        }
        
        //ACTUALIZA LOS OBJETOS
        actualizarIslas();
        actualizarPep();
        actualizarTortugas();
        actualizarGnomos();
        actualizarBolaDeFuego();
        actualizarBombas();
    }
    
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}


