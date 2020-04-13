package com.datatools.databiblio.info;

import java.sql.*;
import java.math.BigDecimal;

public class DParte_8143TO {

	/**
	 * Crea un objeto Parte_8143 basado en un objeto ResultSet que contiene una consulta a la tabla parte_8143
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public DParte_8143TO(ResultSet r) throws SQLException{
		asignarValores(r);
	}

	/**
	 * Crea un objeto Parte_8143 basado en la llave primaria  de la tabla parte_8143
	 * @throws InfoNotFoundException Si ocurre un error en la BD
	 */
	public DParte_8143TO(Connection conn, short ashoParTipoProceso, short ashoParTipoDoc, BigDecimal abigPerNroDoc, short ashoParTipoGenerador, int aintNroGenerador)
			throws Exception{
		String query= " SELECT par_tipo_proceso, arp_nro_proceso, rpr_fecha_rad, par_tipo_doc, per_nro_doc, env_placa, per_direccion, plo_empresa_trans, par_tipo_generador, par_tipo_parte, nro_generador, par_telefono FROM parte_8143"+
				" WHERE 1=1 AND par_tipo_proceso = ? AND par_tipo_doc = ? AND per_nro_doc = ? AND par_tipo_generador = ? AND nro_generador = ?";
		System.out.println("BUSCANDO Parte_8143:"+query
				+" ? "+ashoParTipoProceso+" ? "+ashoParTipoDoc+" ? "+abigPerNroDoc+" ? "+ashoParTipoGenerador+" ? "+aintNroGenerador);

		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setShort(1, ashoParTipoProceso);
		pstmt.setShort(2, ashoParTipoDoc);
		pstmt.setBigDecimal(3, abigPerNroDoc);
		pstmt.setShort(4, ashoParTipoGenerador);
		pstmt.setInt(5, aintNroGenerador);
		ResultSet r = pstmt.executeQuery();
		if(r.next()){
			asignarValores(r);
		}
		else{
			r.close();
			throw new Exception("No se encontró Parte_8143 ");
		}
		r.close();
	}

	/**
	 * @throws SQLException
	 */
	public static DParte_8143TO buscarParte_8143(Connection conn, int aintTipoProceso, int aintNroProceso, Date adatFechaProceso,
			int aintTipoGenerador) throws Exception{
		DParte_8143TO lparte_8143=null;
		String lstrSql="SELECT par_tipo_proceso, arp_nro_proceso, rpr_fecha_rad, par_tipo_doc, per_nro_doc, env_placa, per_direccion, plo_empresa_trans, par_tipo_generador, par_tipo_parte, nro_generador, par_telefono "+
				" FROM parte_8143"+
				" WHERE par_tipo_proceso = ? AND arp_nro_proceso= ? "+
				" AND rpr_fecha_rad = ? AND par_tipo_generador = ?";

		System.out.println("BUSCAR CONDUCTOR "+lstrSql+" ? "+aintTipoProceso+" ? "+aintNroProceso+" ? "+adatFechaProceso);

		PreparedStatement pstmt = conn.prepareStatement(lstrSql);
		pstmt.setInt(1, aintTipoProceso);
		pstmt.setInt(2, aintNroProceso);
		pstmt.setDate(3, adatFechaProceso);
		pstmt.setInt(4, aintTipoGenerador);
		ResultSet r = pstmt.executeQuery();
		if(r.next()){
			lparte_8143=new DParte_8143TO( r);
		}
		return lparte_8143;
	}

	/**
	 * Inserta un registro en la tabla parte_8143 (Cada variable se corresponde con las columnas de la tabla parte_8143).
	 * @return true si el registro se insertó exitósamente
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public static boolean insertarParte_8143(Connection conn, int ashoParTipoProceso, int aintArpNroProceso, Date adatRprFechaRad, int ashoParTipoDoc, long abigPerNroDoc, String astrEnvPlaca, String astrPerDireccion, String astrPloEmpresaTrans, int ashoParTipoGenerador, int ashoParTipoParte, int aintNroGenerador, String astrParTelefono)
			throws SQLException{
		PreparedStatement stm;
		
		String insert = "INSERT INTO parte_8143 (par_tipo_proceso, arp_nro_proceso, rpr_fecha_rad, par_tipo_doc, per_nro_doc, env_placa, per_direccion, plo_empresa_trans, par_tipo_generador, par_tipo_parte, nro_generador, par_telefono)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		stm = conn.prepareStatement(insert);
		stm.setInt(1, ashoParTipoProceso);
		stm.setInt(2, aintArpNroProceso);
		stm.setDate(3, adatRprFechaRad);
		stm.setInt(4, ashoParTipoDoc);
		stm.setLong(5, abigPerNroDoc);
		stm.setString(6, astrEnvPlaca);
		stm.setString(7, astrPerDireccion);
		stm.setString(8, astrPloEmpresaTrans);
		if (ashoParTipoGenerador == -1){
			stm.setNull(9, Types.SMALLINT);
		}
		else {
			stm.setInt(9, ashoParTipoGenerador);
		}
		if (ashoParTipoParte == -1){
			stm.setNull(10, Types.SMALLINT);
		}
		else {
			stm.setInt(10, ashoParTipoParte);
		}
		if (aintNroGenerador == -1){
			stm.setNull(11, Types.INTEGER);
		}
		else {
			stm.setInt(11, aintNroGenerador);
		}
		stm.setString(12, astrParTelefono);

		stm.executeUpdate();
		return true;
	}

	/**
	 * Actualiza un registro en la tabla parte_8143 (Cada variable se corresponde con las columnas de la tabla parte_8143).
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public static boolean actualizarParte_8143(Connection conn, short ashoParTipoProceso, int aintArpNroProceso, Date adatRprFechaRad, short ashoParTipoDoc, BigDecimal abigPerNroDoc, String astrEnvPlaca, String astrPerDireccion, String astrPloEmpresaTrans, short ashoParTipoGenerador, short ashoParTipoParte, int aintNroGenerador, String astrParTelefono)
			throws SQLException{
		PreparedStatement stm;
		String update = "UPDATE parte_8143 SET(arp_nro_proceso, rpr_fecha_rad, env_placa, per_direccion, plo_empresa_trans, par_tipo_parte, par_telefono)" +
				" = (?, ?, ?, ?, ?, ?, ?)" +
				" WHERE par_tipo_proceso = ? AND par_tipo_doc = ? AND per_nro_doc = ? AND par_tipo_generador = ? AND nro_generador = ?";

		stm = conn.prepareStatement(update);
		stm.setInt(1, aintArpNroProceso);
		stm.setDate(2, adatRprFechaRad);
		stm.setString(3, astrEnvPlaca);
		stm.setString(4, astrPerDireccion);
		stm.setString(5, astrPloEmpresaTrans);
		if (ashoParTipoParte == -1){
			stm.setNull(6, Types.SMALLINT);
		}
		else {
			stm.setShort(6, ashoParTipoParte);
		}
		stm.setString(7, astrParTelefono);
		stm.setShort(8, ashoParTipoProceso);
		stm.setShort(9, ashoParTipoDoc);
		stm.setBigDecimal(10, abigPerNroDoc);
		if (ashoParTipoGenerador == -1){
			stm.setNull(11, Types.SMALLINT);
		}
		else {
			stm.setShort(11, ashoParTipoGenerador);
		}
		if (aintNroGenerador == -1){
			stm.setNull(12, Types.INTEGER);
		}
		else {
			stm.setInt(12, aintNroGenerador);
		}

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
	public short getParTipoDoc(){
		return gshoParTipoDoc;
	}
	public BigDecimal getPerNroDoc(){
		return gbigPerNroDoc;
	}
	public String getEnvPlaca(){
		return gstrEnvPlaca;
	}
	public String getPerDireccion(){
		return gstrPerDireccion;
	}
	public String getPloEmpresaTrans(){
		return gstrPloEmpresaTrans;
	}
	public short getParTipoGenerador(){
		return gshoParTipoGenerador;
	}
	public short getParTipoParte(){
		return gshoParTipoParte;
	}
	public int getNroGenerador(){
		return gintNroGenerador;
	}
	public String getParTelefono(){
		return gstrParTelefono;
	}

	/**
	 * Asigna valores a todas las variables del objeto actual basado en un objeto
	 * ResultSet que contiene una consulta a la tabla parte_8143
	 * @param r ResultSet que contiene una consulta a la tabla parte_8143
	 * @throws SQLException Si ocurre un error en la BD
	 */
	private void asignarValores(ResultSet r) throws SQLException{
		gshoParTipoProceso = (r.getObject("par_tipo_proceso") != null)? r.getShort("par_tipo_proceso") : -1;
		gintArpNroProceso = (r.getObject("arp_nro_proceso") != null)? r.getInt("arp_nro_proceso") : -1;
		gdatRprFechaRad = r.getDate("rpr_fecha_rad");
		gshoParTipoDoc = (r.getObject("par_tipo_doc") != null)? r.getShort("par_tipo_doc") : -1;
		gbigPerNroDoc = r.getBigDecimal("per_nro_doc");
		gstrEnvPlaca = r.getString("env_placa");
		gstrPerDireccion = r.getString("per_direccion");
		gstrPloEmpresaTrans = r.getString("plo_empresa_trans");
		gshoParTipoGenerador = (r.getObject("par_tipo_generador") != null)? r.getShort("par_tipo_generador") : -1;
		gshoParTipoParte = (r.getObject("par_tipo_parte") != null)? r.getShort("par_tipo_parte") : -1;
		gintNroGenerador = (r.getObject("nro_generador") != null)? r.getInt("nro_generador") : -1;
		gstrParTelefono = r.getString("par_telefono");
	}

	private short gshoParTipoProceso;
	private int gintArpNroProceso;
	private Date gdatRprFechaRad;
	private short gshoParTipoDoc;
	private BigDecimal gbigPerNroDoc;
	private String gstrEnvPlaca;
	private String gstrPerDireccion;
	private String gstrPloEmpresaTrans;
	private short gshoParTipoGenerador;
	private short gshoParTipoParte;
	private int gintNroGenerador;
	private String gstrParTelefono;


}
