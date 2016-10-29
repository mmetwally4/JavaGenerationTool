/*
 * DbBase.java
 *
 * Created on Oct 17, 2007, 3:00:40 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MGenerator.DataBaseLayer;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import snaq.db.ConnectionPool;

/**
 *
 * @author mmetwally
 */
public class DbBase_1 {

    public static ConnectionPool connPool;

    public DbBase_1(String driverStr,String connStr) {
        createConnPool(driverStr,connStr);
    }

    public void createConnPool(String driverStr,String connStr) {
        try {
            if (DbBase_1.connPool == null) {
                Properties props = new Properties();
                //this.getClass().getResourceAsStream("ConnProp.properties")
               // File file = new File("ConnProp.properties");
                Class.forName(driverStr);
                props.load(this.getClass().getResourceAsStream("/ConnProp.properties"));
                int maxPool = Integer.parseInt(props.getProperty("connPool.maxpool"));
                int maxConn = Integer.parseInt(props.getProperty("connPool.maxconn"));
                long expiryTime = Long.parseLong(props.getProperty("connPool.expiry"));
                DbBase_1.connPool = new ConnectionPool("connPool", maxConn, maxPool, expiryTime, connStr, props);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbBase_1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbBase_1.class.getName());
        }
        // connPool = new ConnectionPool(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection conn;        
        conn = DbBase_1.connPool.getConnection();        
        return conn;
    }

    

}