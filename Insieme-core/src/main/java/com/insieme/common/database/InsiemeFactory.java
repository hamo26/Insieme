/**
 * This class is generated by jOOQ
 */
package com.insieme.common.database;

import java.sql.Connection;

import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.util.mysql.MySQLFactory;


/**
 * This class is generated by jOOQ.
 *
 * A Factory for specific use with the <code>insieme</code> schema.
 * <p>
 * This Factory will not render the <code>insieme</code> schema's schema name 
 * in rendered SQL (assuming that you use it as the default schema on your 
 * connection!). Use the more generic {@link org.jooq.util.mysql.MySQLFactory} 
 * or the {@link org.jooq.impl.Factory} instead, if you want to fully qualify 
 * tables, routines, etc.
 */
public class InsiemeFactory extends MySQLFactory {

	private static final long serialVersionUID = -1081114435;

	/**
	 * Create a factory with a connection
	 *
	 * @param connection The connection to use with objects created from this factory
	 */
	public InsiemeFactory(Connection connection) {
		super(connection);

		initDefaultSchema();
	}

	/**
	 * Create a factory with a connection and some settings
	 *
	 * @param connection The connection to use with objects created from this factory
	 * @param settings The settings to apply to objects created from this factory
	 */
	public InsiemeFactory(Connection connection, Settings settings) {
		super(connection, settings);

		initDefaultSchema();
	}

	/**
	 * Initialise the render mapping's default schema.
	 * <p>
	 * For convenience, this schema-specific factory should override any pre-existing setting
	 */
	private final void initDefaultSchema() {
		SettingsTools.getRenderMapping(getSettings()).setDefaultSchema(Insieme.INSIEME.getName());
	}
}