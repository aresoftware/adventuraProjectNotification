package com.datatools.notificar.util;

/**
 * Clase que permite almacenar las horas, minutos y segundos.
 * @author Antonio
  */
public class HoraVO
{
  private int m_hour, m_minute, m_second;
	
  public HoraVO(int aHour, int aMinute, int aSecond)
  {
	m_hour = aHour;
	m_minute = aMinute;
	m_second = aSecond;
  }
  
  public String toString()
  {
	return m_hour + ":" + m_minute + ":" + m_second;  
  }
  
  public void setHour(int aHour)
  {
	m_hour = aHour;  
  }
  
  public int getHour()
  {
	return m_hour;  
  }
  
  public void setMinute(int aMinute)
  {
	m_minute = aMinute;
  }

  public int getMinute()
  {
	return m_minute;
  }
  
  public void setSecond(int aSecond)
  {
    m_second = aSecond;	  
  }
  
  public int getSecond()
  {
	return m_second;
  }
  
}