package com.datatools.notificar.dao;

import java.math.BigDecimal;
import java.sql.*;

import static com.datatools.notificar.constante.IConstantes.*;
import com.datatools.databiblio.info.DParte_8143TO;


public class DRadtp_8171TO {

	/**
	 * Crea un objeto Radtp_8171 basado en un objeto ResultSet que contiene una consulta a la tabla radtp_8171
	 * @param r ResultSet que contiene una consulta a la tabla radtp_8171
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public DRadtp_8171TO(ResultSet r) throws SQLException{
		asignarValores(r);
	}

	/**
	 *
	 * @param aintTipoProceso
	 * @param aintNroProceso
	 * @param astrFechaProceso
	 * @throws SQLException
	 */

	public DRadtp_8171TO(Connection conn, int aintTipoProceso, int aintNroProceso, String astrFechaProceso) throws SQLException{

		String lstrSql="SELECT *  FROM radtp_8171"+
		" WHERE par_tipo_proceso="+aintTipoProceso+
		" AND arp_nro_proceso="+aintNroProceso+
		" AND fecha='"+astrFechaProceso+"'";

		System.out.println("CREAR OBJETO PROCESO "+lstrSql);
		ResultSet lresultset = conn.createStatement().executeQuery(lstrSql);
		if(lresultset.next())
			asignarValores(lresultset);
		lresultset.close();

		DParte_8143TO lparte_8143 = null;
		try{
			lparte_8143 = DParte_8143TO.buscarParte_8143(conn, aintTipoProceso,
					aintNroProceso, obtenerFechaDate(astrFechaProceso), 1);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		if(lparte_8143!=null)
			gintCcConductor=lparte_8143.getPerNroDoc();


	}

	public DRadtp_8171TO(Connection conn, int aintTipoProceso, int aintNroProceso, String astrFechaProceso, int aintComparendo)
	throws SQLException{

		String lstrSql="SELECT * "+
		" FROM radtp_8171"+
		" WHERE par_tipo_proceso = "+aintTipoProceso+
		" AND arp_nro_proceso = "+aintNroProceso+
		" AND fecha = '"+astrFechaProceso+"'"+
		" AND comparendo = "+aintComparendo;

		System.out.println("CREAR OBJETO PROCESO "+lstrSql);
		ResultSet lresultset = conn.createStatement().executeQuery(lstrSql);
		if(lresultset.next())
			asignarValores(lresultset);
		lresultset.close();

		DParte_8143TO lparte_8143 = null;
		try{
			lparte_8143 = DParte_8143TO.buscarParte_8143(conn, aintTipoProceso, aintNroProceso,
				obtenerFechaDate(astrFechaProceso), 1);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		if(lparte_8143!=null)
			gintCcConductor=lparte_8143.getPerNroDoc();
	}

	/**
	 *
	 * @param aintNroProceso int
	 * @param adatFechaProceso Date
	 * @param aintComparendo int
	 * @param astmt Statement
	 * @return Radtp_8171
	 * @throws SQLException
	 */

	public static DRadtp_8171TO buscarRadtp_8171(Connection conn,int aintNroProceso, Date adatFechaProceso, int aintComparendo)
	throws SQLException{

		DRadtp_8171TO lradtp_8171=null;

		String lstrSql="SELECT *  FROM radtp_8171"+
		" WHERE arp_nro_proceso = ? AND fecha = ? AND comparendo = ?";
		System.out.println("CREAR OBJETO PROCESO "+lstrSql+" ? "+aintNroProceso+" ? "+adatFechaProceso+" ? "+aintComparendo);

		PreparedStatement lpstm = conn.prepareStatement(lstrSql);
		lpstm.setInt(1,aintNroProceso);
		lpstm.setDate(2,adatFechaProceso);
		lpstm.setLong(3,aintComparendo);

		ResultSet lresultset = lpstm.executeQuery();

		if(lresultset.next())
			lradtp_8171 = new DRadtp_8171TO(lresultset);

		lresultset.close();

		return lradtp_8171;

	}


	/**
	 * Inserta un registro en la tabla radtp_8171 (Cada variable se corresponde con las columnas de la tabla radtp_8171).
	 * @return true si el registro se insertó exitósamente
	 * @throws SQLException Si ocurre un error en la BD
	 */
	public static boolean insertarRadtp_8171(Connection conn, int aintArpNroProceso, int aintComparendo, Date adatFecha, String astrEnvPlaca, short ashoCotCodigo, int aintCcRepLegal, int aintCcPropietario, int aintCcConductor, Date adatComFechaContra, BigDecimal abigComValor, short ashoParTipoProceso, int aintResolSanciona, Date adatFechaResol, BigDecimal abigValCartera)
	throws SQLException{
		String insert = "INSERT INTO radtp_8171 (arp_nro_proceso, comparendo, fecha, env_placa, cot_codigo, cc_rep_legal, cc_propietario, cc_conductor, com_fecha_contra, com_valor, par_tipo_proceso, resol_sanciona, fecha_resol, val_cartera)" +
		" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement stmt = conn.prepareStatement(insert);
		stmt.setInt(1, aintArpNroProceso);
		stmt.setInt(2, aintComparendo);
		stmt.setDate(3, adatFecha);
		stmt.setString(4, astrEnvPlaca);
		if (ashoCotCodigo == -1)
			stmt.setNull(5, Types.SMALLINT);
		else
			stmt.setShort(5, ashoCotCodigo);
		if (aintCcRepLegal == -1)
			stmt.setNull(6, Types.INTEGER);
		else
			stmt.setInt(6, aintCcRepLegal);
		if (aintCcPropietario == -1)
			stmt.setNull(7, Types.INTEGER);
		else
			stmt.setInt(7, aintCcPropietario);
		if (aintCcConductor == -1)
			stmt.setNull(8, Types.INTEGER);
		else
			stmt.setInt(8, aintCcConductor);
		stmt.setDate(9, adatComFechaContra);
		stmt.setBigDecimal(10, abigComValor);
		if (ashoParTipoProceso == -1)
			stmt.setNull(11, Types.SMALLINT);
		else
			stmt.setShort(11, ashoParTipoProceso);
		if (aintResolSanciona == -1)
			stmt.setNull(12, Types.INTEGER);
		else
			stmt.setInt(12, aintResolSanciona);
		stmt.setDate(13, adatFechaResol);
		stmt.setBigDecimal(14, abigValCartera);

		stmt.executeUpdate();
		return true;
	}

	public int getArpNroProceso(){
		return gintArpNroProceso;
	}
	public int getComparendo(){
		return gintComparendo;
	}
	public Date getFecha(){
		return gdatFecha;
	}
	public String getEnvPlaca(){
		return gstrEnvPlaca;
	}
	public short getCotCodigo(){
		return gshoCotCodigo;
	}
	public short getCotOrigen(){
		return gshoCotOrigen;
	}
	public int getCcRepLegal(){
		return gintCcRepLegal;
	}
	public int getCcPropietario(){
		return gintCcPropietario;
	}
	public BigDecimal getCcConductor(){
		return gintCcConductor;
	}
	public Date getComFechaContra(){
		return gdatComFechaContra;
	}
	public BigDecimal getComValor(){
		return gbigComValor;
	}
	public short getParTipoProceso(){
		return gshoParTipoProceso;
	}
	public int getResolSanciona(){
		return gintResolSanciona;
	}
	public Date getFechaResol(){
		return gdatFechaResol;
	}
	public BigDecimal getValCartera(){
		return gbigValCartera;
	}
	public String getComparendoTotal() {
		return gstrComNumeroTotal;
	}

	/**
	 * Asigna valores a todas las variables del objeto actual basado en un objeto
	 * ResultSet que contiene una consulta a la tabla radtp_8171
	 * @param r ResultSet que contiene una consulta a la tabla radtp_8171
	 * @throws SQLException Si ocurre un error en la BD
	 */
	private void asignarValores(ResultSet r) throws SQLException{
		gintArpNroProceso = (r.getObject("arp_nro_proceso") != null)? r.getInt("arp_nro_proceso") : -1;
		gintComparendo = (r.getObject("comparendo") != null)? r.getInt("comparendo") : -1;
		gstrComNumeroTotal = (r.getObject("com_numero_total") != null) ? r.getString("com_numero_total") : "";
		gdatFecha = r.getDate("fecha");
		gstrEnvPlaca = r.getString("env_placa");
		gshoCotCodigo = (r.getObject("cot_codigo") != null)? r.getShort("cot_codigo") : -1;
		gshoCotOrigen = (r.getObject("cot_origen") != null)? r.getShort("cot_origen") : -1;
		gintCcRepLegal = (r.getObject("cc_rep_legal") != null)? r.getInt("cc_rep_legal") : -1;
		gintCcPropietario = (r.getObject("cc_propietario") != null)? r.getInt("cc_propietario") : -1;
		gintCcConductor = (r.getObject("cc_conductor") != null)? BigDecimal.valueOf(Long.parseLong(""+r.getInt("cc_conductor"))) : BigDecimal.valueOf(0);
		gdatComFechaContra = r.getDate("com_fecha_contra");
		gbigComValor = r.getBigDecimal("com_valor");
		gshoParTipoProceso = (r.getObject("par_tipo_proceso") != null)? r.getShort("par_tipo_proceso") : -1;
		gintResolSanciona = (r.getObject("resol_sanciona") != null)? r.getInt("resol_sanciona") : -1;
		gdatFechaResol = r.getDate("fecha_resol");
		gbigValCartera = r.getBigDecimal("val_cartera");
	}

	public void setCcConductor(BigDecimal ccConductor){
	}

	private int gintArpNroProceso;
	private int gintComparendo;
	private String gstrComNumeroTotal;
	private Date gdatFecha;
	private String gstrEnvPlaca;
	private short gshoCotCodigo;
	private short gshoCotOrigen;
	private int gintCcRepLegal;
	private int gintCcPropietario;
	private BigDecimal gintCcConductor;
	private Date gdatComFechaContra;
	private BigDecimal gbigComValor;
	private short gshoParTipoProceso;
	private int gintResolSanciona;
	private Date gdatFechaResol;
	private BigDecimal gbigValCartera;

}
