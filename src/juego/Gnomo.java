package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;

public class Gnomo {
	
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Color color;
	private int velocidadY = 0;
	private boolean movimiento;
	private boolean enElAire = false;
	private final double GRAVEDAD = 0.5;
	//private Random random = new Random();
	private int coordenadaY;
	
	
	public Gnomo (int x, int y, int ancho, int alto, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
		this.movimiento = false;
	}
	
    //dibuja al gnomo en el entorno
	public void dibujarGnomo(Entorno e) {
		e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, this.color);
		
	}
    //cuando el gnomo esta en contacto con la isla ejecuta el movimiento
	public void mover (boolean direccion) {
		boolean chocaConIsla = true;
		if (chocaConIsla) {
			if (direccion) {
				int velocidad = 5;
				x = x-velocidad;
			} else {
				int velocidad=5;
				x = x+velocidad;
			}
		}

	}
	
	//usa un booleano para cambiar el movimiento de manera aleatoria
	public void cambiarMovimiento () {
		this.movimiento = Math.random()<5;
	}
	
	//altera la posicion del objeto en el eje Y cuando "esta en el aire" 
	private void aplicarGravedad() {
        if (this.enElAire) {
            this.velocidadY += GRAVEDAD;
            this.coordenadaY += this.velocidadY;
        }
	}
	//verifica si el gnomo esta en contacto con una isla o no
	public void chocaConIsla(Isla[] islas) {
		boolean choca = false;
		
		int LimiteGnomoIzq = getX() - getAncho() / 2;
		int LimiteGnomoDer = getX() + getAncho() / 2;
		int LimiteGnomoSup = getY() - getAlto() / 2;
		int LimiteGnomoInf = getY() + getAlto() / 2;
		
		  for (Isla isla : islas) {
	            if (isla != null) {
	                if (verificarChoca(isla, LimiteGnomoIzq, LimiteGnomoDer, LimiteGnomoInf, LimiteGnomoSup)) {
	                    this.y = isla.getY() - isla.getAlto() / 2 - this.alto / 2;
	                    this.velocidadY = 0;
	                    this.enElAire = false;
	                    choca = true;
	                    if(LimiteGnomoIzq < isla.getX() - isla.getAncho() / 2) {
	                    	movimiento = true;
	                    }
	                    if(LimiteGnomoDer > isla.getX() + isla.getAncho() / 2) {
	                    	movimiento = false;
	                    }
	                    break;
	                }
	            }
	        }
		  if (!choca) {
				enElAire = true;
			}
		}

		private boolean verificarChoca(Isla isla, int limIzq, int limDer, int limBot, int limTop) {
	        return limDer > isla.getX() - isla.getAncho() / 2 && limIzq < isla.getX() + isla.getAncho() / 2
	                && limBot >= isla.getY() - isla.getAlto() / 2 && limTop < isla.getY();
	    }
		
	
	
	public void actualizar(Isla[] islas, Entorno e) {
		aplicarGravedad();
		chocaConIsla(islas);
		mover(this.movimiento);
	}
	
	public void coalisionPep(Pep pep) {
		
		//limites del gnomo
		
		int LimiteGnomoIzq = getX() - getAncho() / 2;
		int LimiteGnomoDer = getX() + getAncho() / 2;
		int LimiteGnomoSup = getY() - getAlto() / 2;
		int LimiteGnomoInf = getY() + getAlto() / 2;
		
		//limites de pep
		
		int LimitePepIzq = pep.getX() - pep.getAncho() / 2;
		int LimitePepDer = pep.getX() + pep.getAncho() / 2;
		int LimitePepSup = pep.getY() - pep.getAlto() / 2;
		int LimitePepInf = pep.getY() + pep.getAlto() / 2;
		
		//verifica choque entre gnomo y pep
		
		if (LimiteGnomoDer > LimitePepIzq && LimiteGnomoIzq < LimitePepDer &&
			LimiteGnomoInf > LimitePepSup && LimiteGnomoSup < LimitePepInf ) {
			//choque ocurrido
			
			
			
		}
	}
	
	public void coalisionTortuga(Tortuga tortuga) {
		
		//limites del gnomo
		
		int LimiteGnomoIzq = getX() - getAncho() / 2;
		int LimiteGnomoDer = getX() + getAncho() / 2;
		int LimiteGnomoSup = getY() - getAlto() / 2;
		int LimiteGnomoInf = getY() + getAlto() / 2;
		
		//limites de tortuga
		
		int LimiteTortugaIzq = tortuga.getX() - tortuga.getAncho() / 2;
		int LimiteTortugaDer = tortuga.getX() + tortuga.getAncho() / 2;
		int LimiteTortugaSup = tortuga.getY() - tortuga.getAlto() / 2;
		int LimiteTortugaInf = tortuga.getY() + tortuga.getAlto() / 2;
		
		//verifica choque entre gnomo y pep
		
		if (LimiteGnomoDer > LimiteTortugaIzq && LimiteGnomoIzq < LimiteTortugaDer &&
			LimiteGnomoInf > LimiteTortugaSup && LimiteGnomoSup < LimiteTortugaInf ) {
			
			//choque ocurrido
		}
		
	}
	
	
	public int getX() {return x;}
	
	public int getY() {return y;}
	
	public int getAncho() {return ancho;}
	
	public int getAlto() {return alto;}
	
	public boolean getenElAire() { return enElAire;}
	
	public boolean seHaPerdido() {return false;}
	

}
