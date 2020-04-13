package com.datatools.notificar.constante;

public enum EnumTipoPersonaNotifica {
	CIUDADANO  	(1, "CIUDADANO"),
	PATIO 		(2, "PATIO"),
	ABOGADO 	(3, "ABOGADO")
	;
	private EnumTipoPersonaNotifica(int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}
	public int codigo;
	public String nombre;

}
