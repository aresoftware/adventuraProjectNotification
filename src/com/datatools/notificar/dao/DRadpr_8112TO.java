package com.datatools.notificar.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.datatools.databiblio.info.DParte_8143TO;

import static com.datatools.notificar.constante.IConstantes.*;


public class DRadpr_8112TO {

	/**
	 * Crea un objeto Radpr_8112 basado en un objeto ResultSet que contiene una consulta a la tabla radpr_8112
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public DRadpr_8112TO(Connection conn, ResultSet r) throws SQLException{
		asignarValores(r);
	}

	/**
	 * Crea un objeto Radpr_8112 basado en la llave primaria de la tabla radpr_8112
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public DRadpr_8112TO(Connection conn, short ashoParTipoProceso, int aintArpNroProceso, Date adatRprFechaRad)
			throws Exception{
		String query= " SELECT * FROM radpr_8112"+
				" WHERE par_tipo_proceso = ? AND arp_nro_proceso = ? AND rpr_fecha_rad = ?";
		System.out.println("BUSCANDO Radpr_8112:"+query
				+" ? "+ashoParTipoProceso+" ? "+aintArpNroProceso+" ? "+adatRprFechaRad);
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setShort(1, ashoParTipoProceso);
		pstmt.setInt(2, aintArpNroProceso);
		pstmt.setDate(3, adatRprFechaRad);
		ResultSet r = pstmt.executeQuery();
		if(r.next()){
			asignarValores(r);
		}
		else{
			r.close();
			throw new Exception("No se encontró Radpr_8112 ");
		}
		r.close();
	}	

	/**
	 * Traer el detalle de los origenes del proceso
	 * @param stmt Statement
	 * @return Collection
	 * @throws SQLException
	 * @throws InfoNotFoundException
	 */
	public List<DRadtp_8171TO> getRadtp_8171(Connection conn) throws Exception{

		List<DRadtp_8171TO> larrRadtp_8171=new ArrayList<DRadtp_8171TO>();
		String query= " SELECT * FROM radtp_8171"+
				" WHERE par_tipo_proceso = ? AND arp_nro_proceso = ? AND fecha = ?";
		System.out.println("BUSCANDO Radtp_8112:"+query
				+" ? "+this.getParTipoProceso()+" ? "+this.getArpNroProceso()+" ? "+this.getRprFechaRad());
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setShort(1, this.getParTipoProceso());
		pstmt.setInt(2, this.getArpNroProceso());
		pstmt.setDate(3, this.getRprFechaRad());
		ResultSet r = pstmt.executeQuery();
		while(r.next()){
			DRadtp_8171TO lradtp_8171= new DRadtp_8171TO(r );
			larrRadtp_8171.add(lradtp_8171);
		}
		r.close();
		return larrRadtp_8171;
	}


	public DParte_8143TO getParte_8143(Connection conn, Statement stmt) throws SQLException {
		String query= " SELECT * FROM parte_8143"+
				" WHERE 1=1 AND par_tipo_proceso = ? AND arp_nro_proceso = ? AND rpr_fecha_rad = ? AND par_tipo_generador = ?";
			System.out.println("BUSCANDO Parte_8143:"+query
					+" ? "+this.getParTipoProceso()+" ? "+this.getArpNroProceso()+" ? "+this.getRprFechaRad());
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setShort(1, this.getParTipoProceso());
		pstmt.setInt(2, this.getArpNroProceso());
		pstmt.setDate(3, this.getRprFechaRad());
		pstmt.setInt(4, 1);
		ResultSet r = pstmt.executeQuery();
		DParte_8143TO lparte_8143=null;
		if(r.next()){
			lparte_8143=new DParte_8143TO(r);
		}
		r.close();
		return lparte_8143;
	}


	/**
	 * Inserta un registro en la tabla radpr_8112 (Cada variable se corresponde con las columnas de la tabla radpr_8112).
	 * @return true si el registro se insertó exitósamente
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public static boolean insertarRadpr_8112(Connection conn, int ashoParTipoProceso, int aintArpNroProceso, Date adatRprFechaRad, 
			int aintRprDctoOrigen, Date adatRprFechaOrigen, int aintOrtCodigo, int aintInsCodigo, int ashoParTipoDoc, 
			long abigPerNroDoc, int ashoArpPartes, int ashoArpEstado, String astrRprObservacion, String astrUsrLogname, 
			Date adatAudFechaProceso, int aintAudTransaccion, String astrPath)
			throws SQLException{
		//*** INSERTAR REGISTRO ***
		String insert = "INSERT INTO radpr_8112 (par_tipo_proceso, arp_nro_proceso, rpr_fecha_rad, rpr_dcto_origen, rpr_fecha_origen, ort_codigo, ins_codigo, par_tipo_doc, per_nro_doc, arp_partes, arp_estado, rpr_observacion, usr_logname, aud_fecha_proceso, aud_transaccion, path)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement stm = conn.prepareStatement(insert);
		stm.setInt(1, ashoParTipoProceso);
		stm.setInt(2, aintArpNroProceso);
		stm.setDate(3, adatRprFechaRad);
		if (aintRprDctoOrigen == -1){
			stm.setNull(4, Types.INTEGER);
		}
		else {
			stm.setInt(4, aintRprDctoOrigen);
		}
		stm.setDate(5, adatRprFechaOrigen);
		if (aintOrtCodigo == -1){
			stm.setNull(6, Types.INTEGER);
		}
		else {
			stm.setInt(6, aintOrtCodigo);
		}
		if (aintInsCodigo == -1){
			stm.setNull(7, Types.INTEGER);
		}
		else {
			stm.setInt(7, aintInsCodigo);
		}
		if (ashoParTipoDoc == -1){
			stm.setNull(8, Types.SMALLINT);
		}
		else {
			stm.setInt(8, ashoParTipoDoc);
		}
		stm.setLong(9, abigPerNroDoc);
		if (ashoArpPartes == -1){
			stm.setNull(10, Types.SMALLINT);
		}
		else {
			stm.setInt(10, ashoArpPartes);
		}
		if (ashoArpEstado == -1){
			stm.setNull(11, Types.SMALLINT);
		}
		else {
			stm.setInt(11, ashoArpEstado);
		}
		stm.setString(12, astrRprObservacion);
		stm.setString(13, astrUsrLogname);
		stm.setDate(14, adatAudFechaProceso);
		if (aintAudTransaccion == -1){
			stm.setNull(15, Types.INTEGER);
		}
		else {
			stm.setInt(15, aintAudTransaccion);
		}
		stm.setString(16, astrPath);
		stm.executeUpdate();
		return true;
	}

	/**
	 * Actualiza un registro en la tabla radpr_8112 (Cada variable se corresponde con las columnas de la tabla radpr_8112).
	 * @return true si el registro se actualizó exitósamente
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public static boolean actualizarRadpr_8112(Connection conn, int ashoParTipoProceso, int aintArpNroProceso, Date adatRprFechaRad, int aintRprDctoOrigen, Date adatRprFechaOrigen, int aintOrtCodigo, int aintInsCodigo, short ashoParTipoDoc, BigDecimal abigPerNroDoc, short ashoArpPartes, short ashoArpEstado, String astrRprObservacion, String astrUsrLogname, Date adatAudFechaProceso, int aintAudTransaccion, String astrPath)
			throws SQLException{
		String update = "UPDATE radpr_8112 SET(rpr_dcto_origen, rpr_fecha_origen, ort_codigo, ins_codigo, par_tipo_doc, per_nro_doc, arp_partes, arp_estado, rpr_observacion, usr_logname, aud_fecha_proceso, aud_transaccion, path)" +
				" = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
				" WHERE par_tipo_proceso = ? AND arp_nro_proceso = ? AND rpr_fecha_rad = ?";
		PreparedStatement stm = conn.prepareStatement(update);

		if (aintRprDctoOrigen == -1){
			stm.setNull(1, Types.INTEGER);
		}
		else {
			stm.setInt(1, aintRprDctoOrigen);
		}
		stm.setDate(2, adatRprFechaOrigen);
		if (aintOrtCodigo == -1){
			stm.setNull(3, Types.INTEGER);
		}
		else {
			stm.setInt(3, aintOrtCodigo);
		}
		if (aintInsCodigo == -1){
			stm.setNull(4, Types.INTEGER);
		}
		else {
			stm.setInt(4, aintInsCodigo);
		}
		if (ashoParTipoDoc == -1){
			stm.setNull(5, Types.SMALLINT);
		}
		else {
			stm.setShort(5, ashoParTipoDoc);
		}
		stm.setBigDecimal(6, abigPerNroDoc);
		if (ashoArpPartes == -1){
			stm.setNull(7, Types.SMALLINT);
		}
		else {
			stm.setShort(7, ashoArpPartes);
		}
		if (ashoArpEstado == -1){
			stm.setNull(8, Types.SMALLINT);
		}
		else {
			stm.setShort(8, ashoArpEstado);
		}
		stm.setString(9, astrRprObservacion);
		stm.setString(10, astrUsrLogname);
		stm.setDate(11, adatAudFechaProceso);
		if (aintAudTransaccion == -1){
			stm.setNull(12, Types.INTEGER);
		}
		else {
			stm.setInt(12, aintAudTransaccion);
		}
		stm.setString(13, astrPath);
		stm.setShort(14, (short) ashoParTipoProceso);
		stm.setInt(15, aintArpNroProceso);
		stm.setDate(16, adatRprFechaRad);

		stm.executeUpdate();
		return true;
	}

	public short getParTipoProceso(){
		return gshoParTipoProceso;
	}
	public int getArpNroProceso(){
		return gintArpNroProceso;
	}
	public Date getRprFechaRad(){
		return gdatRprFechaRad;
	}
	public int getRprDctoOrigen(){
		return gintRprDctoOrigen;
	}
	public Date getRprFechaOrigen(){
		return gdatRprFechaOrigen;
	}
	public int getOrtCodigo(){
		return gintOrtCodigo;
	}
	public int getInsCodigo(){
		return gintInsCodigo;
	}
	public short getParTipoDoc(){
		return gshoParTipoDoc;
	}
	public BigDecimal getPerNroDoc(){
		return gbigPerNroDoc;
	}
	public short getArpPartes(){
		return gshoArpPartes;
	}
	public short getArpEstado(){
		return gshoArpEstado;
	}
	public String getRprObservacion(){
		return gstrRprObservacion;
	}
	public String getUsrLogname(){
		return gstrUsrLogname;
	}
	public Date getAudFechaProceso(){
		return gdatAudFechaProceso;
	}
	public int getAudTransaccion(){
		return gintAudTransaccion;
	}
	public String getPath(){
		return gstrPath;
	}

	/**
	 * Asigna valores a todas las variables del objeto actual basado en un objeto
	 * ResultSet que contiene una consulta a la tabla radpr_8112
	 * @param r ResultSet que contiene una consulta a la tabla radpr_8112
	 * @throws SQLException Si ocurre un error en la BD
	 */
	private void asignarValores(ResultSet r) throws SQLException{
		gshoParTipoProceso = (r.getObject("par_tipo_proceso") != null)? r.getShort("par_tipo_proceso") : -1;
		gintArpNroProceso = (r.getObject("arp_nro_proceso") != null)? r.getInt("arp_nro_proceso") : -1;
		gdatRprFechaRad = r.getDate("rpr_fecha_rad");
		gintRprDctoOrigen = (r.getObject("rpr_dcto_origen") != null)? r.getInt("rpr_dcto_origen") : -1;
		gdatRprFechaOrigen = r.getDate("rpr_fecha_origen");
		gintOrtCodigo = (r.getObject("ort_codigo") != null)? r.getInt("ort_codigo") : -1;
		gintInsCodigo = (r.getObject("ins_codigo") != null)? r.getInt("ins_codigo") : -1;
		gshoParTipoDoc = (r.getObject("par_tipo_doc") != null)? r.getShort("par_tipo_doc") : -1;
		gbigPerNroDoc = r.getBigDecimal("per_nro_doc");
		gshoArpPartes = (r.getObject("arp_partes") != null)? r.getShort("arp_partes") : -1;
		gshoArpEstado = (r.getObject("arp_estado") != null)? r.getShort("arp_estado") : -1;
		gstrRprObservacion = r.getString("rpr_observacion");
		gstrUsrLogname = r.getString("usr_logname");
		gdatAudFechaProceso = r.getDate("aud_fecha_proceso");
		gintAudTransaccion = (r.getObject("aud_transaccion") != null)? r.getInt("aud_transaccion") : -1;
		gstrPath = r.getString("path");
	}

	private short gshoParTipoProceso;
	private int gintArpNroProceso;
	private Date gdatRprFechaRad;
	private int gintRprDctoOrigen;
	private Date gdatRprFechaOrigen;
	private int gintOrtCodigo;
	private int gintInsCodigo;
	private short gshoParTipoDoc;
	private BigDecimal gbigPerNroDoc;
	private short gshoArpPartes;
	private short gshoArpEstado;
	private String gstrRprObservacion;
	private String gstrUsrLogname;
	private Date gdatAudFechaProceso;
	private int gintAudTransaccion;
	private String gstrPath;


}
