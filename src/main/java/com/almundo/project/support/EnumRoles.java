package com.almundo.project.support;

/**
 * Enum encargado de tener los roles del trabajador.
 * @author Johnattan
 *
 */
public enum EnumRoles {
	
	OPERADOR(1), SUPERVISOR(2), DIRECTOR(3);
	
	
	private int nivel;
	
	/**
	 * Constructor del enum que recibe un entero como identificador del rol
	 * @param nivel
	 */
	EnumRoles(int nivel){
		this.nivel = nivel;
	}
	
	/**
	 * Retorna nivel del rol.
	 * @return nivel
	 */
	public int getNivel(){
		return this.nivel;
	}
	
}


