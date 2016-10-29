/*
 * DBBase.java
 *
 * Created on 03 September 2007, 12:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.MGenerator.DataBaseLayer;

import com.MGenerator.metadata.MetaData;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author mohamed.metwally
 */
public class DBBase {
    
    public Connection connection;
    public String driverString;
    public String serverName;
    public String dBSchema;
    public String userName;
    public String password;    
    public String portNum;
    public String connectionString;
    public int type;
    /** Creates a new instance of DBBase */
    public DBBase(String serverName, String dbSchema, String userName, String password, String portNum,int type) {
        this.serverName = serverName;
        this.dBSchema = dbSchema;
        this.userName = userName;
        this.password = password;
        this.portNum = portNum;
        this.type = type;        
        MetaData.setUserName(userName);
        MetaData.setPassword(password);
        
    }
    
    public void driverLoader() throws ClassNotFoundException{
        switch(type){
            case 1: // Oracle
                this.driverString = "oracle.jdbc.driver.OracleDriver";
                MetaData.setDriver(driverString);
                this.connectionString = "jdbc:oracle:thin:@"+serverName+":"+portNum+":"+dBSchema;
                MetaData.setConnectionStr(connectionString);
                MetaData.setDbName(dBSchema);
                break;
            case 2: // MS SQL Server
                this.driverString = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
                MetaData.setDriver(driverString);
                this.connectionString = "jdbc:microsoft:sqlserver://"+serverName+":"+portNum+";databaseName="+dBSchema+";";
                MetaData.setConnectionStr(connectionString);
                MetaData.setDbName(dBSchema);                
                break;
            case 3:// mySql
                this.driverString = "com.mysql.jdbc.Driver";
                MetaData.setDriver(driverString);
                this.connectionString = "jdbc:mysql://"+serverName+":"+portNum+"/"+dBSchema+"?useUnicode=true&characterEncoding=utf8";
                MetaData.setConnectionStr(connectionString);
                MetaData.setDbName(dBSchema);
                break;
            case 4: // hSql
                this.driverString = "org.hsqldb.jdbcDriver";
                MetaData.setDriver(driverString);
                this.connectionString = "jdbc:hsqldb:hsql://"+serverName;
                MetaData.setConnectionStr(connectionString);
                MetaData.setDbName(dBSchema);
                break;                
            default:          
                this.driverString = "";
                this.connectionString = "";
                MetaData.setDriver(driverString);
                MetaData.setConnectionStr(connectionString);
                MetaData.setDbName(dBSchema);

                break;
        }
        Class.forName(driverString);        
        
        
    }
    
    public Connection instanceOfConnection() throws SQLException, ClassNotFoundException{        
        if(connection == null || connection.isClosed()){            
            driverLoader();
            connection = DriverManager.getConnection(connectionString,userName,password);
        }
        return connection;
    }
    
    
    public ArrayList getMetaData(Connection conn) throws SQLException{
        DBMetaData dbMetaData;
        dbMetaData = new DBMetaData(conn.getMetaData(),this.dBSchema,this.type);
        return dbMetaData.collectDBTbls();
    }
    
    public DBMetaData getDBMetaData() throws SQLException, ClassNotFoundException{
        DBMetaData dbMetaData;
        if(connection == null || connection.isClosed()){
            connection = instanceOfConnection();
        }
        dbMetaData = new DBMetaData(connection.getMetaData(),this.dBSchema,this.type);
        return dbMetaData;
    }
    
    public void closeConnection() throws SQLException{
        if(connection != null)
            connection.close();
    }
}
