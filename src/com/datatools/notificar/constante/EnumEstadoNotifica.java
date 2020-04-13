package com.datatools.notificar.constante;

public enum EnumEstadoNotifica {
	NINGUNO    (0, "NINGUNO"),
	PENDIENTE  (1, "PENDIENTE"),
	NOTIFICADO (2, "NOTIFICADO"),
	NO_NOTIFICADO (3, "SIN INFORMACION PARA NOTIFICAR"),
	
	;
	private EnumEstadoNotifica(int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public int codigo;
	public String nombre;

}
