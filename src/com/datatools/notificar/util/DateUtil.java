package com.datatools.notificar.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase que contiene algunas utilizades para la manipulación de fechas
 * 
 * @author Antonio
 */
public class DateUtil {

  /**
   * @param aFormat - formato en el que se desea obtener la fecha y hora.
   * @return - La fecha y hora actual en el formato especificado
   */
  public static String getCurrentDate(String aFormat)  {
	DateFormat formatter = new SimpleDateFormat(aFormat);
	return new String(formatter.format(Calendar.getInstance().getTime()));
  }
  
  /**
   * @param aInitialHour - Hora inicial
   * @param aFinalHour - Hora Final
   * @return - La diferencia en milisegundos entre dos horas, la operación realizada es
   * aFinalHour - AInitialHour
   */
  public static long getDifference(HoraVO aInitialHour, HoraVO aFinalHour)
  {
    // Recupera la fecha de hoy en formato yyyyMMdd:
	DateFormat myFormatter  = new SimpleDateFormat("yyyyMMdd");
	String myToday = myFormatter.format(Calendar.getInstance().getTime());
	
    // Extrae el año, el mes y el día:
    int myYear = Integer.parseInt(myToday.substring(0,4));
    int myMonth = Integer.parseInt(myToday.substring(4,6));
    int myDay = Integer.parseInt(myToday.substring(6,8));
    
     //  Calcula la diferencia
    GregorianCalendar gCalIni = new GregorianCalendar(myYear, 
                                                      myMonth, 
                                                      myDay, 
                                                      aInitialHour.getHour(), 
                                                      aInitialHour.getMinute(), 
                                                      aInitialHour.getSecond());
                                                      
    GregorianCalendar gCalFin = new GregorianCalendar(myYear, 
                                                      myMonth, 
                                                      myDay, 
                                                      aFinalHour.getHour(), 
                                                      aFinalHour.getMinute(), 
                                                      aFinalHour.getSecond());
   
    return gCalFin.getTime().getTime() - gCalIni.getTime().getTime();
  }
  
  /**
   * Adiciona una cantidad de tiempo determinado a una hora inicial
   * @return  Hora final. La hora después de haber sumado el tiempo determinado
   *          por el parámetro <code>factor</code>
   * @param aFactor Cantidad de tiempo a sumar en milisegundos
   * @param aHour Hora inicial.
   */
  public static HoraVO add(HoraVO aHour, long aFactor) {
    //Recupera la fecha de hoy en formato yyyyMMdd:
    DateFormat myFormatter  = new SimpleDateFormat("yyyyMMdd");
    String hoy = myFormatter.format(Calendar.getInstance().getTime());
    
    //Extrae el año, el mes y el día:
    int myYear = Integer.parseInt(hoy.substring(0,4));
    int myMonth = Integer.parseInt(hoy.substring(4,6));
    int myDay = Integer.parseInt(hoy.substring(6,8));
    
    //Calcula la diferencia
    GregorianCalendar myCalIni = new GregorianCalendar(myYear, 
    		                                          myMonth, 
    		                                          myDay, 
    		                                          aHour.getHour(), 
    		                                          aHour.getMinute(), 
    		                                          aHour.getSecond());
                                                      
    long myFinal = myCalIni.getTime().getTime() + aFactor;
    myCalIni.setTimeInMillis(myFinal);
    return  new HoraVO(myCalIni.get(GregorianCalendar.HOUR_OF_DAY),
    		                      myCalIni.get(GregorianCalendar.MINUTE),
    		                      myCalIni.get(GregorianCalendar.SECOND));
  }


}
