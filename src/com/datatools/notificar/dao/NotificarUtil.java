package com.datatools.notificar.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.datatools.notificar.util.DServicioEmail;
import static com.datatools.notificar.constante.IConstantes.*;

import static com.datatools.notificar.constante.EnumEstadoNotifica.*;


/**
 * Registrar los pagos de Comparendos PSE
 * 
 * @author Antonio Jaramillo Fecha: Abr 7 de 2020
 */
public class NotificarUtil {

	private static Logger logger = Logger.getLogger(NotificarUtil.class.getName());

	//String condicion = "com_numero = " + pComNumero + " AND com_fecha_contra = '" +  formatter.format(pFechaCompa)+ "' AND com_estado " + ((anulado) ? "=2" : "<>2");


	/**
	 * Método que revisa los nuevos ingresos a patios, en base a ello, poblar la tabla de noticaciones
	 * @return
	 */
	public static List<DNotificaPatioTO> revisarIngresoAPatio(List<DPatioTO> lstPatio) {
		logger.info("consuta de registros de las entradas a patios");
		Connection gconn = obtenerConexion(); //DBConnectionManager.getInstance().getConnection();
		String envPlaca, notNombre, notDireccion, notEmail, notTelefono,
		ensHora, aboNombre, aboEmail, aboTelefono, dctoOrigen;
		int ensDia=0, patCodigo, seeNumeroEnt, parTipoDoc, aspDia, ortCodigo, notCiudadano=0, notPatio=0, notAbogado=0;
		long perNroDoc;
		DPatioTO patio;
		PreparedStatement pstm = null;
		ResultSet r = null;
		String sql = "";
		List<DNotificaPatioTO> lista= new ArrayList<DNotificaPatioTO>();
		sql = "SELECT e.env_placa, e.ens_dia, e.ens_hora, e.pat_codigo, e.see_numero_ent, "
				+ " e.par_tipo_doc, e.per_nro_doc, sp_nombre50(e.par_tipo_doc,e.per_nro_doc,0) nombre, "
				+ " sp_direccion(e.par_tipo_doc, e.per_nro_doc) direccion, "
				+ " sp_correoelectronico(e.par_tipo_doc, e.per_nro_doc) emal, "
				+ " sp_telefono(e.par_tipo_doc, e.per_nro_doc) telefono, "
				+ " a.asp_dia, e.ort_codigo, ens_numero_concepto, "
				+ "  '' abo, '' abo_email, '' abo_tel "
				//+ "pat_nombre, pat_direccion, pat_email, pat_telefono, asp_dia, abo_nombre, abo_email, abo_telefono, not_ciudadano, not_patio, not_abogado "
				+ "FROM enspa_8035 e, autsp_8037 a "
				+ "WHERE e.env_placa not in (select env_placa from notifica_patio n where e.ens_dia = n.ens_dia) "
				+ " and e.pat_codigo = a.pat_codigo"
				+ " and e.see_numero_ent = a.see_numero_ent"
				+ " and e.ens_dia = a.ens_dia"
				+ " and e.ens_dia = today -1200 " ;
		try {
			System.err.println(sql);
			r = gconn.createStatement().executeQuery(sql);
			while (r.next()) {
				try{
					envPlaca  	= r.getObject(1)==null?"":r.getString(1).trim();
					ensDia      = r.getObject(2)==null?0:obtenerFechaInt(r.getDate(2));
					ensHora     = r.getObject(3)==null?"":r.getString(3);
					patCodigo   = r.getObject(4)==null?0:r.getInt(4);
					seeNumeroEnt= r.getObject(5)==null?0:r.getInt(5);
					parTipoDoc  = r.getObject(6)==null?0:r.getShort(6);
					perNroDoc   = r.getObject(7)==null?0:r.getLong(7);
					notNombre   = r.getObject(8)==null?"":r.getString(8).trim(); 
					notDireccion= r.getObject(9)==null?"":r.getString(9).trim();
					notEmail    = r.getObject(10)==null?"":r.getString(10).trim();
					notTelefono = r.getObject(11)==null?"":r.getString(11).trim();
					aspDia	    = r.getObject(12)==null?0:obtenerFechaInt(r.getDate(12));
					ortCodigo   = r.getObject(13)==null?0:r.getInt(13);
					dctoOrigen  = r.getObject(14)==null?"":r.getString(14);
					aboNombre	= r.getObject(13)==null?"":r.getString(13);
					aboEmail    = r.getObject(14)==null?"":r.getString(14);
					aboTelefono = r.getObject(15)==null?"":r.getString(15);
					//				notCiudadano	= r.getObject(20)==null?0:r.getInt(20); 
					//				notPatio	    = r.getObject(21)==null?0:r.getInt(21);
					//				notAbogado	    = r.getObject(22)==null?0:r.getInt(22);
					patio = getPatio(lstPatio, patCodigo);

					DNotificaPatioTO entradaPatio = new DNotificaPatioTO.Builder(envPlaca, ensDia, ensHora, 
							patio.getCodigo(), seeNumeroEnt, parTipoDoc, perNroDoc, notNombre, 
							notDireccion, notEmail, notTelefono, 
							patio.getPatNombre(), patio.getPatDireccion(), patio.getPatEmail(), patio.getPatTelefono())
							.setAspDia(aspDia).setOrtCodigo(ortCodigo).setDctoOrigen(dctoOrigen)
							.setAboNombre(aboNombre).setAboEmail(aboEmail).setAboTelefono(aboTelefono)
							.setNotCiudadano(notCiudadano).setNotPatio(notPatio).setNotAbogado(notAbogado).build();
					lista.add(entradaPatio);	
				}catch(Exception e){e.printStackTrace();}
			}
		} catch (SQLException e) {
			logger.error("Error al consultar la transacción en la BD");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		finalizaTransaccion(gconn, pstm, r);
		return lista;
	}	
	
	public static DPatioTO getPatio(List<DPatioTO> lstPatio, int codigo){
		DPatioTO patio = null;
		for(DPatioTO to: lstPatio){
			if(to.getCodigo() == codigo){
				patio = to; break;
			}
		}
		if(patio == null){
			patio = new DPatioTO(codigo, "", "", "", "", 1);
		}
		return patio;
	}

	public static List<DPatioTO> cargarListaPatios(){
		logger.info("consuta de registros pendientes por notificar");
		Connection gconn = obtenerConexion(); //DBConnectionManager.getInstance().getConnection();
		List<DPatioTO> lista = new ArrayList<DPatioTO>();
		DPatioTO patio;
		int	patCodigo, estado= 1;  
		String patNombre, patDireccion, patEmail="?", patTelefono;  
		ResultSet r = null;
		String sql = "";
		sql = "select pat_codigo,pat_nombre,pat_direccion,'' email,pat_telefono from patio_6027";
		try{
			r = gconn.createStatement().executeQuery(sql);
			while(r.next()){
				patCodigo   = r.getObject(1)==null?0:r.getInt(1);
				patNombre   = r.getObject(2)==null?"":r.getString(2).trim();
				patDireccion= r.getObject(3)==null?"":r.getString(3).trim();
				patEmail	= r.getObject(4)==null?"":r.getString(4).trim();
				patTelefono = r.getObject(5)==null?"":r.getString(5).trim();				
				patio = new DPatioTO(patCodigo, patNombre, patDireccion, patEmail, patTelefono, estado);
				lista.add(patio);
			}
		} catch (SQLException e) {
			logger.error("Error al consultar la transacción en la BD");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		finalizaTransaccion(gconn, null, r);
		return lista;		
	}

	public static boolean ingresaRegistroPatios(List<DNotificaPatioTO> lista) {
		logger.info("consuta de registros pendientes por notificar");
		Connection gconn = obtenerConexion(); //DBConnectionManager.getInstance().getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		String sql = "";
		try {		
			sql = "INSERT INTO notifica_patio (env_placa, ens_dia, ens_hora, pat_codigo, see_numero_ent, "
					+ "par_tipo_doc, per_nro_doc, not_nombre,  not_direccion, not_email, not_telefono, "
					+ "pat_nombre, pat_direccion, pat_email, pat_telefono, "
					+ "asp_dia, ort_codigo, dcto_origen, "
					+ "abo_nombre, abo_email, abo_telefono, not_ciudadano, not_patio, not_abogado ) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			for(DNotificaPatioTO to: lista){
				pstm = gconn.prepareStatement(sql);
				pstm.setString	(1, to.getEnvPlaca());
				pstm.setString	(2, obtenerFechaDate(to.getEnsDia()));
				pstm.setString	(3, to.getEnsHora());
				pstm.setInt		(4, to.getPatCodigo());
				pstm.setInt		(5, to.getSeeNumeroEnt());
				pstm.setInt		(6, to.getParTipoDoc());
				pstm.setLong	(7, to.getPerNroDoc());
				pstm.setString	(8, to.getNotNombre());
				pstm.setString	(9, to.getNotDireccion());
				pstm.setString	(10, to.getNotEmail());
				pstm.setString	(11, to.getNotTelefono());
				pstm.setString	(12, to.getPatNombre());
				pstm.setString	(13, to.getPatDireccion());
				pstm.setString	(14, to.getPatEmail());
				pstm.setString	(15, to.getPatTelefono());
				pstm.setString	(16, obtenerFechaDate(to.getAspDia()));
				pstm.setInt		(17, to.getOrtCodigo());
				pstm.setString	(18, to.getDctoOrigen());
				pstm.setString	(19, to.getAboNombre());
				pstm.setString	(20, to.getAboEmail());
				pstm.setString	(21, to.getAboTelefono());
				pstm.setInt		(22, to.getNotCiudadano());
				pstm.setInt		(23, to.getNotPatio());
				pstm.setInt		(24, to.getNotAbogado());
				pstm.executeUpdate();
			}			
		} catch (SQLException e) {
			logger.error("Error al consultar la transacción en la BD");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
		finalizaTransaccion(gconn, pstm, r);
		return true;
	}

	public static boolean actualizaRegistroPatios(DNotificaPatioTO to) {
		logger.info("consuta de registros pendientes por notificar");
		Connection gconn = obtenerConexion(); //DBConnectionManager.getInstance().getConnection();
		PreparedStatement pstm = null;
		ResultSet r = null;
		String sql = "";
		try {		
			sql = "UPDATE notifica_patio SET (par_tipo_doc, per_nro_doc, not_nombre, "
				+ "not_direccion, not_email, not_telefono, pat_nombre, pat_direccion, pat_email, pat_telefono, "
				+ "asp_dia, abo_nombre, abo_email, abo_telefono, not_ciudadano, not_patio, not_abogado,"
				+ "par_tipo_proceso, arp_nro_proceso, fec_cita, hora_cita) "
				+ " = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
				+ "Where see_numero_ent=? AND env_placa = ? AND ens_dia = ?";
			pstm = gconn.prepareStatement(sql);
			pstm.setInt		(1, to.getParTipoDoc());
			pstm.setLong	(2, to.getPerNroDoc());
			pstm.setString	(3, to.getNotNombre());
			pstm.setString	(4, to.getNotDireccion());
			pstm.setString	(5, to.getNotEmail());
			pstm.setString	(6, to.getNotTelefono());
			pstm.setString	(7, to.getPatNombre());
			pstm.setString	(8, to.getPatDireccion());
			pstm.setString	(9, to.getPatEmail());
			pstm.setString	(10, to.getPatTelefono());
			pstm.setString	(11, obtenerFechaDate(to.getAspDia()));
			pstm.setString	(12, to.getAboNombre());
			pstm.setString	(13, to.getAboEmail());
			pstm.setString	(14, to.getAboTelefono());
			pstm.setInt		(15, to.getNotCiudadano());
			pstm.setInt		(16, to.getNotPatio());
			pstm.setInt		(17, to.getNotAbogado());
			pstm.setInt		(18, to.getParTipoProceso());
			pstm.setInt		(19, to.getNroProceso());
			pstm.setString	(20, obtenerFechaDate(to.getFecCita()));
			pstm.setString	(21, to.getHoraCita());
			pstm.setInt		(22, to.getSeeNumeroEnt());
			pstm.setString	(23, to.getEnvPlaca());
			pstm.setString	(24, obtenerFechaDate(to.getEnsDia()));
			pstm.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error al consultar la transacción en la BD");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
		finalizaTransaccion(gconn, pstm, r);
		return true;
	}		


	/**
	 * Método que permite consutar en BD los registros pendientes para Notificar
	 * @param basico  - 
	 */
	public static List<DNotificaPatioTO> consultaNotificacionesPendientes() {
		logger.info("consuta de registros pendientes por notificar");
		Connection gconn = obtenerConexion(); //DBConnectionManager.getInstance().getConnection();
		String envPlaca, notNombre, notDireccion, notEmail, notTelefono, patNombre, patDireccion, 
		patEmail, patTelefono, ensHora, aboNombre, aboEmail, aboTelefono;
		int ensDia, patCodigo, seeNumeroEnt, parTipoDoc, aspDia, notCiudadano, notPatio, notAbogado;
		long perNroDoc;
		PreparedStatement pstm = null;
		ResultSet r = null;
		String sql = "";
		List<DNotificaPatioTO> lista= new ArrayList<DNotificaPatioTO>();
		try {			
			sql = "SELECT env_placa, ens_dia, ens_hora, pat_codigo, see_numero_ent, par_tipo_doc, per_nro_doc, not_nombre, "
					+ "not_direccion, not_email, not_telefono, pat_nombre, pat_direccion, pat_email, pat_telefono, "
					+ "asp_dia, abo_nombre, abo_email, abo_telefono, not_ciudadano, not_patio, not_abogado "
					+ "FROM notifica_patio WHERE not_ciudadano = " + NINGUNO.codigo;
			r = gconn.createStatement().executeQuery(sql);
			while (r.next()) {
				envPlaca  	= r.getObject(1)==null?"":r.getString(1);
				ensDia      = r.getObject(2)==null?0:obtenerFechaInt(r.getDate(2));      
				ensHora     = r.getObject(3)==null?"":r.getString(3);
				patCodigo   = r.getObject(4)==null?0:r.getInt(4);
				seeNumeroEnt= r.getObject(5)==null?0:r.getInt(5);
				parTipoDoc  = r.getObject(6)==null?0:r.getShort(6);
				perNroDoc   = r.getObject(7)==null?0:r.getLong(7);
				notNombre   = r.getObject(8)==null?"":r.getString(8).trim(); 
				notDireccion= r.getObject(9)==null?"":r.getString(9).trim();
				notEmail    = r.getObject(10)==null?"":r.getString(10).trim();
				notTelefono = r.getObject(11)==null?"":r.getString(11).trim();
				patNombre   = r.getObject(12)==null?"":r.getString(12).trim();
				patDireccion= r.getObject(13)==null?"":r.getString(13);
				patEmail    = r.getObject(14)==null?"":r.getString(14); 
				patTelefono = r.getObject(15)==null?"":r.getString(15);
				aspDia	    = r.getObject(16)==null?0:obtenerFechaInt(r.getDate(16));
				aboNombre	= r.getObject(17)==null?"":r.getString(17);
				aboEmail    = r.getObject(18)==null?"":r.getString(18);
				aboTelefono = r.getObject(19)==null?"":r.getString(19);
				notCiudadano= r.getObject(20)==null?0:r.getInt(20); 
				notPatio	= r.getObject(21)==null?0:r.getInt(21);
				notAbogado	= r.getObject(22)==null?0:r.getInt(22);

				DNotificaPatioTO patio = new DNotificaPatioTO.Builder(envPlaca, ensDia, ensHora, patCodigo, 
						seeNumeroEnt, parTipoDoc, perNroDoc, notNombre, notDireccion, notEmail, notTelefono, 
						patNombre, patDireccion, patEmail, patTelefono)
						.setAspDia(aspDia)
						.setAboNombre(aboNombre)
						.setAboEmail(aboEmail)
						.setAboTelefono(aboTelefono)
						.setNotCiudadano(notCiudadano)
						.setNotPatio(notPatio)
						.setNotAbogado(notAbogado).build();
				lista.add(patio);	
			}
		} catch (SQLException e) {
			logger.error("Error al consultar la transacción en la BD");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		finalizaTransaccion(gconn, pstm, r);

		return lista;
	}

	public static void finalizaTransaccion(Connection gconn, PreparedStatement pstm, ResultSet r){
		try {
			gconn.close();
			gconn = null;
		} catch (Exception e) {}
		finally {
			if (r != null) {
				try {r.close();} catch (SQLException ex) {}
			}
			if (pstm != null) {
				try {pstm.close();} catch (SQLException ex) {}
			}
			if (gconn != null) {
				try {gconn.close();} catch (SQLException ex) {}
			}
		}
	}

	public static void notificarCorreo(DNotificaPatioTO notifica){
		DServicioEmail email = new DServicioEmail();
		email.sendEmail();
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
