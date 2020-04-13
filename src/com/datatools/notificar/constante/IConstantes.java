package com.datatools.notificar.constante;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.datatools.notificar.util.Configuration;

import java.sql.Date;

public interface IConstantes {
	SimpleDateFormat fecFormat = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat fecFormatD = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat fecFormatH = new SimpleDateFormat("hh:mm");
	java.util.Date fecDateActual = new java.util.Date();
	java.sql.Date fecDateActualSQL = new java.sql.Date(fecDateActual.getTime());
	String fecActual = fecFormat.format(fecDateActual);
	String horaActual = fecFormatH.format(fecDateActual);
	String usuario = Configuration.getInstance().dbUserNameMisional;
	int anno = Integer.parseInt(fecActual.substring(0,4));
	int PROCESO_ENTRADA_PATIOS = 6;
	int INSPECCION_ENTRADA_PATIOS = 5;
	int PROCESO_ESTADO_INICIAL = 1;
	String PROCESO_OBS = "Proceso por Entrada a Patios";
	
	
	public static String obtnerHora(int hora, int minuto){
		return (hora<10?"0":"")+hora+(minuto<10?"0":"")+minuto;
	}
	public static String obtnerHora(int hora, int minuto, int tiempo){
		minuto = minuto + tiempo;
		hora = hora  + minuto/60;
		minuto = minuto%60;		
		return obtnerHora(hora, minuto);
	}
	
	public static int obtenerFechaInt(Date fecha){
		return Integer.parseInt(fecFormat.format(fecha));
	}
	
	public static String obtenerFechaStr(String fecha){
		return fecFormatD.format(fecha);
	}
	
	public static Date obtenerFechaDate(String fecha){
		//String str = fecFormatD.format(fecha);
		try {
			return new Date(fecFormatD.parse(fecha).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date obtenerFechaInt(int fecha){
		String str;
		try {
			str = fecFormatD.format(fecFormat.parse(""+fecha));
			return obtenerFechaDate(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String obtenerFechaDate(int fecha){
		try {
			return fecFormatD.format(fecFormat.parse(""+fecha));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
