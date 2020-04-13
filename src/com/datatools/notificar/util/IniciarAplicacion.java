package com.datatools.notificar.util;


import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.datatools.notificar.dao.DCitaTO;
import com.datatools.notificar.dao.DNotificaPatioTO;
import com.datatools.notificar.dao.DPatioTO;

import static com.datatools.notificar.dao.NotificarUtil.*;

import static com.datatools.notificar.dao.InspeccionUtil.*;

/**
 * Iniciar la aplicación de Notificaciones
 * 
 * @author Antonio Jaramillo Fecha: Abr 7 de 2020
 */
public class IniciarAplicacion implements ServletContextListener, Runnable {
  private static Logger logger = Logger.getLogger(Configuration.class.getName());
  
  private ServletContext context = null;
  
  /**
   * Atributo que permite interrumpir la ejecución del hilo.
   */
  private boolean m_Interrupt = false;
  
  /**
   * Hilo que se encarga de  recargar la lista de bancos cada cierto tiempo.
   */
  private Thread m_thread;
  
  /**
   * Intervalo de tiempo en milisegundos a esperar antes de preguntar las 
   * transacciones pendientes.
   */
  private static long periodoSonda = Configuration.getInstance().getPeriodoSondaNotifica();
  
   
  /**
   * Almacena la ruta del contexto.
   */
  private String ruta = null;
   
  
  /**
   * Crea una nueva instancia de la clase Thread e invoca el método <code>start()</code>
   */
  public void startRobot() {
    m_Interrupt = false;
    m_thread = new Thread(this);
    m_thread.start();
  }

  /**
   * Método que se ejecuta cuando se inicia la aplicación. 
   * @param event - evento de incialización del contexto de la aplicación.
   */
  public void contextInitialized(ServletContextEvent event) {
    context = event.getServletContext();
    startRobot();    
  }
  
  
  public static HoraVO hora_actua(){
	  String myCurrentTime = DateUtil.getCurrentDate("HHmmss");  
	  return new HoraVO(Integer.parseInt(myCurrentTime.substring(0, 2)),
			  Integer.parseInt(myCurrentTime.substring(2, 4)),
			  Integer.parseInt(myCurrentTime.substring(4, 6)));
  }  

  /**
   * Método que se ejecuta cuando se inicializa el robot.
   */
  public void run() {
    logger.info("Se inició el hilo");
    boolean myContinued = true;
    List<DNotificaPatioTO> lstNotifica;
    List<DPatioTO> lstPatio;
    //HoraVO myHoraInicial = hora_actua();
    try {
    	while (myContinued)  {
    		
    		logger.info("Activamos el Proceso de Buscar las transacciones pendientes ....");
    		System.out.println("******* Iniciando Ejecucion de la Sonda " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date()) + " *******");
    		
    		//1. obtener los nombres de patios
    		lstPatio = cargarListaPatios();
    		
    		//2. obtener los ingresos a patios
    		lstNotifica = revisarIngresoAPatio(lstPatio);
    		
    		//3. registrar los ingresos de parios
    		ingresaRegistroPatios(lstNotifica);
    		
    		//4. registrar los proceso de inspección
    		lstNotifica = consultaNotificacionesPendientes();
    		for(DNotificaPatioTO placa: lstNotifica) {
    			registrarProceso(null, placa);
    		};

    		//5. notificar a las ciudadanos de los ingresos a patios
    		for(DNotificaPatioTO placa: lstNotifica) {
    			notificarCorreo(placa);
    		};
    		
    		//6. registrar las citas, para atención de los abogados
    		for(DCitaTO cita: consultarCitaDisponible(null, lstNotifica)) {
    			ingresarCita(null, cita);    			
    		};
    		
    		//7. notificar las citas de inspección
    		for(DCitaTO cita: consultarCitaDisponible(null, lstNotifica)) {
    			//notificarCorreo(placa);    			
    		};

    		System.out.println("******* Finalizando Ejecucion de la Sonda " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date()) + " *******");
    		logger.info("Fin del Proceso de transacciones pendientes ....");
    		
    		Thread.sleep(periodoSonda);    		
    	}
    }
    catch (InterruptedException e) {
    	e.printStackTrace();  
    }
  }

  public void contextDestroyed(ServletContextEvent event) {
    m_Interrupt = true;
    m_thread.interrupt();
  }
}