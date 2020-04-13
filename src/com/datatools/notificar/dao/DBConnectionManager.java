package com.datatools.notificar.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.datatools.notificar.util.Configuration;

/**
 * Patron Singleton, para manejo de la conexion
 * 
 * @author Antonio Jaramillo Fecha: Jul 26 de 2010
 */

public class DBConnectionManager {

	/**
	 * Atributo privado que contiene la única instancia de la clase
	 * DBConnectionManager patrón Singleton.
	 */
	private static DBConnectionManager instance = null;

	private static Logger logger = Logger.getLogger(DBConnectionManager.class.getName());
	
	private Context initCtx;
	private Context envContext;
	private DataSource myDs;

	/**
	 * Constructor privado de la clase patrón Singleton.
	 */
	private DBConnectionManager() {
		try {
		initCtx = new InitialContext();
		envContext = (Context) initCtx.lookup("java:/comp/env");

		myDs = (DataSource) envContext.lookup(Configuration.getInstance().getConnName());
		} catch (NamingException e) {
			logger.error("error obteniendo la conexión a la base de datos"
					+ e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @return - Retorna la única instancia de la clase DBConnectionManager
	 */
	public static synchronized DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		
		return instance;
	}

	public Connection getConnection() {
		try {
			return myDs.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("error obteniendo la conexión a la base de datos"
					+ e.getMessage());
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void freeConnection(Connection aConnection) {
		if (aConnection != null) {
			try {
				if (!aConnection.isClosed()) {
				    aConnection.rollback();
					aConnection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}