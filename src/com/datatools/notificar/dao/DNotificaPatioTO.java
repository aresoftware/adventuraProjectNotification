package com.datatools.notificar.dao;

public class DNotificaPatioTO {
	private final String 	envPlaca;      //placa
	private final int    	ensDia;        //día de entrada a patios
	private final String 	ensHora;       //hora entrada a patios
	private final int		patCodigo   ;  //patio de entrada
	private final int		seeNumeroEnt;  //número de entrada 
	private final int		aspDia      ;  //día de salida de patios
	private final int 		parTipoDoc  ;  //id ciudadano
	private final long 		perNroDoc;     //documento ciudadano
	private final String	notNombre   ;  //nombre ciudadano
	private final String	notDireccion;  //dirección ciudadano
	private final String	notEmail    ;  //email ciudadano
	private final String	notTelefono ;  //telefono ciudadano
	private String	patNombre   ;  //patio nombre
	private String	patDireccion;  //patio direccion
	private String	patEmail    ;  //patio email
	private String	patTelefono ;  //patio teléfono
	private String	aboNombre   ;  //abogado nombre
	private String	aboEmail    ;  //abogado email
	private String	aboTelefono ;  //abogado teléfono
	private int   	notCiudadano;  //ciudadano notificado, 0=ninguno, 1=notificado, 2=se debe notificar
	private int   	notPatio    ;  //patio notificado    , 0=ninguno, 1=notificado, 2=se debe notificar
	private int   	notAbogado  ;  //abogado notificado  , 0=ninguno, 1=notificado, 2=se debe notificar
	
	private final int    	ortCodigo;     //Organismo de tránsito
	private final String 	dctoOrigen;    //documeto que origina la entrada a patios
	private int    	parTipoProceso;//Tipo de proceso de inspección
	private int    	nroProceso;    //Número de proceso de inspección
	private int    	fecCita;       //fecha citado
	private String    horaCita;	   //hora citado
	
	
	public static class Builder {
		private final String 	envPlaca;      
		private final int    	ensDia;        
		private final String 	ensHora;       
		private final int	patCodigo;  
		private final int	seeNumeroEnt;  
		private final int 	parTipoDoc;  
		private final long 	perNroDoc;     
		private final String	notNombre;  
		private final String	notDireccion;  
		private final String	notEmail;  
		private final String	notTelefono;  
		private String	patNombre;  
		private String	patDireccion;  
		private String	patEmail;  
		private String	patTelefono;  
		private int	aspDia;  
		private String	aboNombre;  
		private String	aboEmail;  
		private String	aboTelefono;  
		private int   	notCiudadano;  
		private int   	notPatio;  
		private int   	notAbogado;  
		
		private int    	ortCodigo;     
		private String 	dctoOrigen;	  
		
		public Builder(String envPlaca, int ensDia, String ensHora, int patCodigo, int seeNumeroEnt, 
					int parTipoDoc, long perNroDoc, String notNombre, String notDireccion, 
					String notEmail, String notTelefono, String patNombre, String patDireccion, 
					String patEmail, String patTelefono){			
			this.envPlaca=      envPlaca;    
			this.ensDia=        ensDia;     
			this.ensHora=       ensHora;    
			this.patCodigo=     patCodigo;  
			this.seeNumeroEnt=  seeNumeroEnt;
			this.parTipoDoc=    parTipoDoc; 
			this.perNroDoc=     perNroDoc;  
			this.notNombre=     notNombre;  
			this.notDireccion=  notDireccion;
			this.notEmail=      notEmail;  
			this.notTelefono=   notTelefono;
			this.patNombre=     patNombre;  
			this.patDireccion=  patDireccion;
			this.patEmail=      patEmail;  
			this.patTelefono=   patTelefono;			
		}
		public Builder setAspDia(int aspDia) {
			this.aspDia = aspDia; return this;
		}
		public Builder setAboNombre(String aboNombre) {
			this.aboNombre = aboNombre; return this;
		}
		public Builder setAboEmail(String aboEmail) {
			this.aboEmail = aboEmail; return this;
		}	
		public Builder setAboTelefono(String aboTelefono) {
			this.aboTelefono = aboTelefono; return this;
		}
		public Builder setNotCiudadano(int notCiudadano) {
			this.notCiudadano = notCiudadano; return this;
		}
		public Builder setNotPatio(int notPatio) {
			this.notPatio = notPatio; return this;
		}
		public Builder setNotAbogado(int notAbogado) {
			this.notAbogado = notAbogado; return this;
		}
		public Builder setOrtCodigo(int ortCodigo) {
			this.ortCodigo = ortCodigo; return this;
		}
		public Builder setDctoOrigen(String dctoOrigen) {
			this.dctoOrigen = dctoOrigen; return this;
		}
		public DNotificaPatioTO build(){
			return new DNotificaPatioTO(this);
		}
	}	
	
	public DNotificaPatioTO(Builder build){
		this.envPlaca=      build.envPlaca;    
		this.ensDia=        build.ensDia;     
		this.ensHora=       build.ensHora;    
		this.patCodigo=     build.patCodigo;  
		this.seeNumeroEnt=  build.seeNumeroEnt;
		this.parTipoDoc=    build.parTipoDoc; 
		this.perNroDoc=     build.perNroDoc;  
		this.notNombre=     build.notNombre;  
		this.notDireccion=  build.notDireccion;
		this.notEmail=      build.notEmail;  
		this.notTelefono=   build.notTelefono;
		this.patNombre=     build.patNombre;  
		this.patDireccion=  build.patDireccion;
		this.patEmail=      build.patEmail;  
		this.patTelefono=   build.patTelefono;
		this.aspDia=        build.aspDia;  
		this.aboNombre=     build.aboNombre;  
		this.aboEmail=      build.aboEmail;  
		this.aboTelefono=   build.aboTelefono;
		this.notCiudadano=  build.notCiudadano;
		this.notPatio=      build.notPatio;  
		this.notAbogado=    build.notAbogado; 
		this.ortCodigo=		build.ortCodigo;
		this.dctoOrigen= 	build.dctoOrigen;
	}
	
	public void setParTipoProceso(int parTipoProceso) {
		this.parTipoProceso = parTipoProceso;
	}
	public void setNroProceso(int nroProceso) {
		this.nroProceso = nroProceso;
	}
	public void setFecCita(int fecCita) {
		this.fecCita = fecCita;
	}
	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}
	public void setAboEmail(String aboEmail) {
		this.aboEmail = aboEmail;
	}
	public void setAboNombre(String aboNombre) {
		this.aboNombre = aboNombre;
	}
	public void setAboTelefono(String aboTelefono) {
		this.aboTelefono = aboTelefono;
	}
	public void setNotAbogado(int notAbogado) {
		this.notAbogado = notAbogado;
	}
	public void setNotCiudadano(int notCiudadano) {
		this.notCiudadano = notCiudadano;
	}
	public void setNotPatio(int notPatio) {
		this.notPatio = notPatio;
	}
	public void setPatDireccion(String patDireccion) {
		this.patDireccion = patDireccion;
	}
	public void setPatEmail(String patEmail) {
		this.patEmail = patEmail;
	}
	public void setPatNombre(String patNombre) {
		this.patNombre = patNombre;
	}
	public void setPatTelefono(String patTelefono) {
		this.patTelefono = patTelefono;
	}
	
	public String getEnvPlaca() {
		return envPlaca;
	}
	public int getEnsDia() {
		return ensDia;
	}
	public String getEnsHora() {
		return ensHora;
	}
	public int getPatCodigo() {
		return patCodigo;
	}
	public int getSeeNumeroEnt() {
		return seeNumeroEnt;
	}
	public int getAspDia() {
		return aspDia;
	}
	public int getParTipoDoc() {
		return parTipoDoc;
	}
	public long getPerNroDoc() {
		return perNroDoc;
	}
	public String getNotNombre() {
		return notNombre;
	}
	public String getNotTelefono() {
		return notTelefono;
	}
	public String getPatNombre() {
		return patNombre;
	}
	public String getPatDireccion() {
		return patDireccion;
	}
	public String getPatEmail() {
		return patEmail;
	}
	public String getPatTelefono() {
		return patTelefono;
	}
	public String getAboNombre() {
		return aboNombre;
	}
	public String getAboEmail() {
		return aboEmail;
	}
	public String getAboTelefono() {
		return aboTelefono;
	}
	public int getNotCiudadano() {
		return notCiudadano;
	}
	public int getNotPatio() {
		return notPatio;
	}
	public int getNotAbogado() {
		return notAbogado;
	}
	public String getNotDireccion() {
		return notDireccion;
	}
	public String getNotEmail() {
		return notEmail;
	}
	
	public int getOrtCodigo() {
		return ortCodigo;
	}
	public String getDctoOrigen() {
		return dctoOrigen;
	}
	public int getFecCita() {
		return fecCita;
	}
	public String getHoraCita() {
		return horaCita;
	}
	public int getParTipoProceso() {
		return parTipoProceso;
	}

	public int getNroProceso() {
		return nroProceso;
	}
	
	@Override
	public String toString() {
		return "'"+envPlaca+"',"+ensDia+",'"+ensHora+"',"+patCodigo+","+seeNumeroEnt+","+
				parTipoDoc+","+perNroDoc+",'"+notNombre+"','"+notDireccion+"','"+notEmail+"','"+
				notTelefono+"','"+patNombre+"','"+patDireccion+"','"+patEmail+"','"+patTelefono+"',"+
				aspDia+",'"+aboNombre+"','"+aboEmail+"','"+aboTelefono+"',"+notCiudadano+","+
				notPatio+","+notAbogado;
	}

}
