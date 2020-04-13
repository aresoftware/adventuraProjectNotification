package com.datatools.notificar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.datatools.databiblio.info.DParte_8143TO;
import static com.datatools.notificar.constante.IConstantes.*;
import static com.datatools.notificar.dao.NotificarUtil.*;

/**
 * Registrar Proceso de Inspección
 * 
 * @author Antonio Jaramillo Fecha: Abr 7 de 2020
 */
public class InspeccionUtil {

	private static Logger logger = Logger.getLogger(InspeccionUtil.class.getName());


	static public void registrarProceso(Connection conn, DNotificaPatioTO notPatio) {
		try {
			if(conn ==null)
				conn = obtenerConexion();

			int nroProceso = traerNumInspeccion(conn, ""+INSPECCION_ENTRADA_PATIOS, "1"); 

			ingresaProcesoInspeccion(conn, notPatio, nroProceso);

			ingresaSeguimiento(conn, nroProceso);
			
			actualizaRegistroPatios(notPatio);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static public void ingresaProcesoInspeccion(Connection conn, DNotificaPatioTO placa, int nroProceso) throws SQLException{
		int dctoOrigen = Integer.valueOf(placa.getDctoOrigen()==null?"0":placa.getDctoOrigen());

		//DRadpr_8112TO rad = new DRadpr_8112TO(conn, null);
		DRadpr_8112TO.insertarRadpr_8112(conn, PROCESO_ENTRADA_PATIOS, nroProceso, fecDateActualSQL, 
				dctoOrigen, obtenerFechaInt(placa.getEnsDia()), placa.getOrtCodigo(), INSPECCION_ENTRADA_PATIOS,
				placa.getParTipoDoc(), placa.getPerNroDoc(),
				1, 1, PROCESO_OBS, usuario, fecDateActualSQL, 1, "");

		DParte_8143TO.insertarParte_8143(conn, PROCESO_ENTRADA_PATIOS, nroProceso, fecDateActualSQL, 
				placa.getParTipoDoc(), placa.getPerNroDoc(), placa.getEnvPlaca(), 
				placa.getNotDireccion(), null, 1, 1, 1, placa.getNotTelefono());
		
		placa.setParTipoProceso(PROCESO_ENTRADA_PATIOS);
		placa.setNroProceso(nroProceso);
	}



	static public void ingresaSeguimiento(Connection conn, int nroProceso) throws SQLException{
		String sql;
		sql = " insert into segex_7058 (par_tipo_proceso,arp_nro_proceso,rpr_fecha_rad,"
				+ " par_estado_actu,sge_fecha_ini,sge_hora_ini,usr_logname,aud_fecha_proceso)"
				+ " values ("+PROCESO_ENTRADA_PATIOS+"," + nroProceso + " ,'" + fecActual + "',"
				+ PROCESO_ESTADO_INICIAL + " ,'" + fecActual + "','" + horaActual+ "','" + usuario + "','"
				+ fecActual + "')";
		logger.info(sql);
		conn.createStatement().executeUpdate(sql);		
	}

	static public int traerNumInspeccion(Connection conn, String astrInspec, String astrEstado) {
		ResultSet lresulset;
		String lstrQuery = "";
		int lstrNewNroProceso = 0;
		try {
			lstrQuery = "select nro_consecutivo+1 from asiin_8177 "
					+ " where ins_codigo=" + astrInspec + " and anno = "+ anno + 
					" and estado = " + astrEstado;
			lresulset = conn.createStatement().executeQuery(lstrQuery);
			if (lresulset.next()) {
				lstrQuery = "update asiin_8177 set nro_consecutivo=" + lresulset.getString(1)
				+ " where ins_codigo=" + astrInspec + " and anno = "+ anno 
				+ " and estado =" + astrEstado;
				conn.createStatement().executeUpdate(lstrQuery);
				lstrNewNroProceso = lresulset.getInt(1);
				return lstrNewNroProceso;
			}	
			lstrNewNroProceso = 1;
			lstrQuery = " insert into asiin_8177 values ("
					+ astrInspec + ",1,"+ anno  + " ," + astrEstado + ",'')";
			try {
				conn.createStatement().executeUpdate(lstrQuery);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Grupo " + astrInspec + " Estado" + astrEstado
						+ " no tiene consecutivo-> tabla asiin_8177");
			}
		} catch (SQLException x) {
			x.printStackTrace();
		}
		return lstrNewNroProceso;
	}	

	public static List<DCitaTO> consultarCitaDisponible(Connection conn, List<DNotificaPatioTO> lista){
		String sql;
		DCitaTO cita;
		int numCita= 1, estadoCita = 1, citaRoom = 1, fecCita = Integer.valueOf(fecActual);
		int hCita=9, mCita=0, tCita=30;
		String funcionario = "Abogado 1", citaStr = "";
		List<DCitaTO> lstCita = new ArrayList<DCitaTO>();
		sql = "select max(idcita) from cita_0001";
		ResultSet r;
		try {
			r = conn.createStatement().executeQuery(sql);
			if(r.next()){
				numCita = r.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(DNotificaPatioTO notPatio: lista){
			citaStr = obtnerHora(hCita, mCita);
			cita = new DCitaTO(numCita++, fecCita, estadoCita);
			cita.setNroProceso(notPatio.getNroProceso());
			cita.setCitRoom(""+citaRoom);
			cita.setEntrevistado(notPatio.getNotNombre());
			cita.setFuncionario(funcionario);
			cita.setHoraCita(citaStr);
			citaStr = obtnerHora(hCita, mCita, tCita);
			cita.setHoraFin(citaStr);
			hCita = Integer.valueOf(citaStr.substring(0,2));
			mCita = Integer.valueOf(citaStr.substring(3));
			lstCita.add(cita);
			
			notPatio.setFecCita(cita.getFecCita());
			notPatio.setHoraCita(cita.getHoraCita());
			actualizaRegistroPatios(notPatio);
		}
		return lstCita;

	}

	public static void ingresarCita(Connection conn, DCitaTO cita){
		String sql; 
		sql = "INSERT INTO cita_0001 (idcita, cit_dia, cit_hora_ini, cit_funcionario, "
				+ "cit_entrevistado, cit_room, par_tipo_proceso, cit_hora_fin, arp_nro_proceso, "
				+ "rpr_fecha_rad, par_estado_actu, cit_estado_cita) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			PreparedStatement stm = conn.prepareStatement(sql);
			stm.setInt   (1, cita.getIdCita());
			stm.setString(2, obtenerFechaDate(cita.getFecCita()));
			stm.setString(3, cita.getHoraCita());
			stm.setString(4, cita.getFuncionario());
			stm.setString(5, cita.getEntrevistado());
			stm.setString(6, cita.getCitRoom());
			stm.setInt	 (7, PROCESO_ENTRADA_PATIOS);
			stm.setString(8, cita.getHoraFin());
			stm.setInt	 (9, cita.getNroProceso());
			stm.setString(10, fecActual);
			stm.setInt	 (11, 1);
			stm.setInt	 (12, 1);
			conn.createStatement().executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Connection obtenerConexion() {
		Connection gconn=null;
		try {
			String usuario = "aliperez", pwd = "BAO2xos7";
			String connURL="jdbc:informix-sqli://192.168.36.11:9221/bas_bogota:INFORMIXSERVER=serlab11_tcp";
			Class c=Class.forName("com.informix.jdbc.IfxDriver");
			java.sql.DriverManager.registerDriver((java.sql.Driver)c.newInstance());
			gconn= java.sql.DriverManager.getConnection( connURL+";user="+usuario+";password="+pwd );
		} catch (Exception e) {
			e.printStackTrace();
		}System.err.println("");
		return gconn;
	}


	//Main de prueba
	public static void main(String[] args) {
		obtenerConexion();
	}
}
