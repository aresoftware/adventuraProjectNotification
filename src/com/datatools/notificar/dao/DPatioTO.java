package com.datatools.notificar.dao;

public class DPatioTO {
	private int		codigo   ;  //patio nombre
	private String	patNombre   ;  //patio nombre
	private String	patDireccion;  //patio direccion
	private String	patEmail    ;  //patio email
	private String	patTelefono ;  //patio teléfono
	private int estado;
	
	public DPatioTO(int	codigo, String patNombre, String patDireccion
			, String patEmail, String patTelefono, int estado){
		this.codigo = codigo;
		this.patNombre = patNombre;
		this.patDireccion = patDireccion;
		this.patEmail = patEmail;
		this.patTelefono = patTelefono;
		this.estado = estado;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getPatNombre() {
		return patNombre;
	}
	public void setPatNombre(String patNombre) {
		this.patNombre = patNombre;
	}
	public String getPatDireccion() {
		return patDireccion;
	}
	public void setPatDireccion(String patDireccion) {
		this.patDireccion = patDireccion;
	}
	public String getPatEmail() {
		return patEmail;
	}
	public void setPatEmail(String patEmail) {
		this.patEmail = patEmail;
	}
	public String getPatTelefono() {
		return patTelefono;
	}
	public void setPatTelefono(String patTelefono) {
		this.patTelefono = patTelefono;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
}
