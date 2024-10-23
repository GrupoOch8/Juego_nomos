package juego;

import java.awt.Color;
import java.util.Random;

public class Gnomo {
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private Color color;
	private double velocidadY = 0;
	private double movimiento;
	private boolean enElAire = false; 
	private final double GRAVEDAD = 0.5;
	private Random random = new Random();
	
	
	public Gnomo (int x, int y, int ancho, int alto, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.color = color;
		this.movimiento = Math.random();
	}
	
	public void mover () {
		x =  movimiento;
	}
	
	public void direccion () {
		movimiento = -movimiento;
	}
	
	public void coalisionPep(Pep pep) {
		
		//limites del gnomo
		
		double LimiteGnomoIzq = getX() - getAncho() / 2;
		double LimiteGnomoDer = getX() + getAncho() / 2;
		double LimiteGnomoSup = getY() - getAlto() / 2;
		double LimiteGnomoInf = getY() + getAlto() / 2;
		
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
		
		double LimiteGnomoIzq = getX() - getAncho() / 2;
		double LimiteGnomoDer = getX() + getAncho() / 2;
		double LimiteGnomoSup = getY() - getAlto() / 2;
		double LimiteGnomoInf = getY() + getAlto() / 2;
		
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
	
	public double getY() {return y;}
	
	public double getAncho() {return ancho;}
	
	public double getAlto() {return alto;}
	

}
