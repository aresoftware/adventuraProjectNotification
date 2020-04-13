package com.datatools.notificar.dao;

public class DCitaTO {
	private int nroProceso;
	private int idCita;
	private int fecCita;
	private String funcionario;
	private String entrevistado;
	private String citRoom;
	private String horaCita;
	private String horaFin;
	private int estado;
	
	public DCitaTO(int idCita, int fecCita, int estado) {
		this.idCita = idCita;
		this.fecCita = fecCita;
		this.estado = estado;
	}
	
	
	public int getIdCita() {
		return idCita;
	}
	public void setIdCita(int idCita) {
		this.idCita = idCita;
	}
	public int getFecCita() {
		return fecCita;
	}
	public void setFecCita(int fecCita) {
		this.fecCita = fecCita;
	}
	public String getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	public String getCitRoom() {
		return citRoom;
	}
	public void setCitRoom(String citRoom) {
		this.citRoom = citRoom;
	}
	public String getHoraCita() {
		return horaCita;
	}
	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return idCita+",'"+fecCita+"','"+funcionario+"','"+citRoom+"','"+
				horaCita+"','"+horaFin+"',"+estado;
	}
	public String getEntrevistado() {
		return entrevistado;
	}
	public void setEntrevistado(String entrevistado) {
		this.entrevistado = entrevistado;
	}


	public int getNroProceso() {
		return nroProceso;
	}


	public void setNroProceso(int nroProceso) {
		this.nroProceso = nroProceso;
	}

}
