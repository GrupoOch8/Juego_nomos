package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;

public class Gnomo {
	
	private double x;
	private int y;
	private int ancho;
	private int alto;
	private Color color;
	private double velocidadY = 0;
	private boolean movimiento;
	private boolean enElAire = false;
	private final double GRAVEDAD = 0.3;
	private Random random = new Random();
	private int coordenadaY;
	
	
	public Gnomo (int x, int y, int ancho, int alto, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
		this.movimiento = random.nextBoolean();
	}


	public void dibujar(Entorno e) {
			e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, this.color);
	}


	public void verificarColisiones(Isla[] islas) {
		boolean colisionAbajo = false;

		double limIzq = this.x - this.ancho / 2;
        double limDer = this.x + this.ancho / 2;
        int limTop = this.y - this.alto / 2;
        int limBot = this.y + this.alto / 2;

        for (Isla isla : islas) {
            if (isla != null) {
                if (verificarColisionAbajo(isla, limIzq, limDer, limBot, limTop)) {
                    this.y = isla.getY() - isla.getAlto() / 2 - this.alto / 2;
                    if(enElAire) {
                    	movimiento = random.nextBoolean();
                    }
                    this.enElAire = false;
                    colisionAbajo = true;
                    break;
                }
            }
        }
		if (!colisionAbajo) {
			enElAire = true;
		}
	}
	private boolean verificarColisionAbajo(Isla isla, double limIzq, double limDer, int limBot, int limTop) {
        return limDer > isla.getX() - isla.getAncho() / 2 && limIzq < isla.getX() + isla.getAncho() / 2
                && limBot >= isla.getY() - isla.getAlto() / 2 && limTop < isla.getY();
    }


	public void mover() {
		if(this.movimiento) {
			x += 0.8;
		} else {
			x -= 0.8;
		}
		
	}


	public void caer() {
		if (enElAire) {
			velocidadY += GRAVEDAD;
			y += velocidadY;
		}
	}


	public boolean colisionaConTortuga() {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean colisionaConBomba() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean caerAlVacio() {
		// TODO Auto-generated method stub
		return false;
	}


	public void eliminarse() {
		// TODO Auto-generated method stub
		
	}

	public boolean estaEnElAire() {
		return this.enElAire;
	}

	public boolean coalisionPep(Pep pep) {
		
		//limites del gnomo
		
		double LimiteGnomoIzq = getX() - getAncho() / 2;
		double LimiteGnomoDer = getX() + getAncho() / 2;
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
			return true;
		} else {
			return false;
		}
	}
	
	public void coalisionTortuga(Tortuga tortuga) {
		
		//limites del gnomo
		
		double LimiteGnomoIzq = getX() - getAncho() / 2;
		double LimiteGnomoDer = getX() + getAncho() / 2;
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
	
	
	public double getX() {return x;}
	
	public int getY() {return y;}
	
	public int getAncho() {return ancho;}
	
	public int getAlto() {return alto;}
	
	public boolean getenElAire() { return enElAire;}
	
	public boolean seHaPerdido() { return false;}
}
