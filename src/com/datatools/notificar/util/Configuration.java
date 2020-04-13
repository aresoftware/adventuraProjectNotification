package com.datatools.notificar.util;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;



/**
 * @author - Antonio Jaramillo
 * @version - 1.0
 * @fecha - Abril 7 de 2020
 * Clase que permite cargar la configuración del sistema que se encuentra en el archivo 
 * de propiedades denominado notificar.properties
 */
public class Configuration {

	private static Logger logger = Logger.getLogger(Configuration.class.getName());

	/**
	 * Atributo que contiene el apuntador a la única instancia de la clase.
	 */
	private static Configuration intance;

	private boolean rutacargada = false;

	/**
	 * Atributo privado que contiene la ruta del archivo de Log
	 */
	private String rutaLog4j = null;

	/**
	 * Contiene el nombre del archivo de propiedades.
	 */
	private static final String FILE_NAME = "/notificar.properties";

	/**
	 * Atributo que contiene el nombre de la conexión a la Base de datos.
	 */
	private String CONN_NAME = null;

	/**
	 * Atributo que contiene la ruta donde se encuentran los reportes de certificación de pago.
	 */
	private String PAHT_REPORTS = null;

	/**
	 *  Atributo que indica cada cuanto tiempo se debe lanzar la sonda de PSE en milisegundos
	 */
	private String periodoSondaNotifica = "0";

	
	/**
	 * contiene la ruta de retorno al aplicativo Sec De Movilidad
	 */
	private String rutaSDM = "";

	/**
	 * contiene la informacion del NIT empresa de MOvilida
	 */
	private String nitEmpresa = "";

	private String razonSocial;

	private String urlBaseExtranet;

	public static String dbDriverClass;
	public static String dbUrlMisional;
	public static String dbUserNameMisional;
	public static String dbPasswordMisional;



	/**
	 * Método que permite crear la única instancia de la clase patrón Singleton
	 */
	private synchronized static void createInstance() {
		if (intance == null)  
			intance = new Configuration();
	}

	/**
	 * El constructor se declara privado para que la clase no pueda ser creada por
	 * otra clase.
	 */
	private Configuration() {
		loadData();
	}

	/**
	 * @return - La única instancia disponible de la clase.
	 */
	public static Configuration getInstance() {
		if (intance == null)    
			createInstance();
		return intance;  
	}

	

	/**
	 * Carga las propiedades del archivo de propiedades
	 */
	public void loadData()  {
		InputStream myIs = getClass().getResourceAsStream(FILE_NAME);
		Properties myProp = new Properties();
		try  {
			myProp.load(myIs);
			myIs.close();
			CONN_NAME = myProp.getProperty("CONNECTION_NAME");
			PAHT_REPORTS = myProp.getProperty("URL_REPORTS");
			periodoSondaNotifica = myProp.getProperty("PERIODO_SONDA_NOTIFICA");
			rutaSDM = myProp.getProperty("RUTA_SDM");
			setNitEmpresa(myProp.getProperty("NIT"));
			setRazonSocial(myProp.getProperty("RAZON_SOCIAL"));      
			setUrlBaseExtranet(myProp.getProperty("URL_BASE_EXTRANET"));
			dbDriverClass = myProp.getProperty("dbDriverClass");
			dbUrlMisional = myProp.getProperty("dbUrlMisional");
			dbUserNameMisional = myProp.getProperty("dbUserNameMisional") ;
			dbPasswordMisional  = myProp.getProperty("dbPasswordMisional") ;
		} 
		catch (IOException e) {
			System.out.println("Hubo un error accediendo al archivo de propiedades.");
		}
	}

	/**
	 * @return - Retorna el nombre de la conexion a la BD.
	 */
	public String getConnName() {
		return CONN_NAME;
	}

	/**
	 * @return - Retorna la ruta donde debe saltar para retornar a Dype.
	 */
	public String getRutaSDM() {
		return rutaSDM;    
	}

	/**
	 * @return - La url donde se encuentran ubicados los reportes.
	 */
	public String getUrlReports() {
		return PAHT_REPORTS;
	}

	/**
	 * @return - El período en milisegundos que determina la frecuencia con que se debe lanzar la sonda PSE.
	 * que verifica las transacciones pendientes.
	 */
	public long getPeriodoSondaNotifica() {
		return new Long(periodoSondaNotifica).longValue();
	}

	

	/**
	 * Establece la ruta del archivo de Log para Log4j
	 */
	private void rutaArchivoLog4j() {
		try  {
			if ( !this.isRutacargada() )  {
				String nombreOriginal ;
				Appender[] appender = new Appender[1];
				appender[0] = logger.getRootLogger().getAppender("A1");
				nombreOriginal = ((RollingFileAppender) appender[0]).getFile();

				RollingFileAppender apd = (RollingFileAppender) logger.getRootLogger().getAppender("A1");
				apd.setFile(this.rutaLog4j + "/" + nombreOriginal);
				apd.activateOptions(); 
				this.setRutacargada(true);
			}
		}
		catch (Exception e) {
			logger.error("error al intentar procesar el nombre del archivo de LOG: " + e.toString());
		}
	}


	public void setRutaLog4j(String rutaLog4j) {
		this.rutaLog4j = rutaLog4j;
		rutaArchivoLog4j();
	}

	public String getRutaLog4j() {
		return rutaLog4j;
	}

	public void setRutacargada(boolean rutacargada) {
		this.rutacargada = rutacargada;
	}

	public boolean isRutacargada() {
		return rutacargada;
	}

	public void setNitEmpresa(String nitEmpresa) {
		this.nitEmpresa = nitEmpresa;
	}

	public String getNitEmpresa() {
		return nitEmpresa;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public String getUrlBaseExtranet() {
		return urlBaseExtranet;
	}

	public void setUrlBaseExtranet(String urlBaseExtranet) {
		this.urlBaseExtranet = urlBaseExtranet;
	}

}
