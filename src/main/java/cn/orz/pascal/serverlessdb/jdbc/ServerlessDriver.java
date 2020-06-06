/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.orz.pascal.serverlessdb.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author koduki
 */
public class ServerlessDriver implements Driver {

    private static final String URI_PREFIX = "jdbc:serverlessdb://";

    static {
        try {
            java.sql.DriverManager.registerDriver(new ServerlessDriver());
        } catch (SQLException ex) {
            throw new RuntimeException("Can't register driver!");
        }

    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!url.startsWith(URI_PREFIX)) {
            return null;
        }

        var xs = url.split("/");
        var protocol = xs[2];
        var hostname = xs[4];
        var dbname = xs[5];
        info.put("dbname", dbname);

        return new ServerlessConnection(protocol + "//" + hostname, info);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean jdbcCompliant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
