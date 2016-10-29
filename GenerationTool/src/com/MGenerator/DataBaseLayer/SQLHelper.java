/*
 * SQLHelper.java
 *
 * Created on Sep 25, 2007, 10:55:32 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MGenerator.DataBaseLayer;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author mmetwally
 */
public class SQLHelper {

    public SQLHelper() {
    }
    //--------------------- methods with Variable-Length Argument------------------

    /** execute stored procedure
     *@param conn : the connection that got the Stored Procedure
     *@param spName : stored peocedure call query e.g.: {call sp(?)}
     *@param sqlParameters : list filled with the stored procedure parameters
     *@return : resultSet filled with the stored Procedure output
     */
    public static int executeUpdateSP(Connection conn, String spName, Object... sqlParameters) throws SQLException {
        //setNullableParameters(sqlParameters);
        spName = getQueryString(spName, sqlParameters);
        int rs = 0;
        CallableStatement cs = null;
        if (conn != null) {
            cs = conn.prepareCall(spName);
            if (sqlParameters != null) {
                for (int i = 1; i <= sqlParameters.length; i++) {
                    cs.setObject(i, sqlParameters[i - 1]);
                }
            }
            rs = cs.executeUpdate();
        }
        return rs;
    }

    /** execute stored procedure
     *@param conn : the connection that got the Stored Procedure
     *@param spName : stored peocedure call query e.g.: {call sp(?)}
     *@param sqlParameters : arrayList filled with the stored procedure parameters
     *@return : list filled with the stored Procedure output
     */
    public static ResultSet executeSP(Connection conn, String spName, Object... sqlParameters) throws SQLException {
        //setNullableParameters(sqlParameters);
        spName = getQueryString(spName, sqlParameters);
        ResultSet rs = null;
        CallableStatement cs = null;
        if (conn != null) {
            cs = conn.prepareCall(spName);
            if (sqlParameters != null) {
                for (int i = 1; i <= sqlParameters.length; i++) {
                    cs.setObject(i, sqlParameters[i - 1]);
                }
            }
            rs = cs.executeQuery();
        }
        return rs;
    }

    /** execute stored procedure
     *@param conn : the connection that got the Stored Procedure
     *@param spName : stored peocedure call query e.g.: {call sp(?)}
     *@param sqlParameters : arrayList filled with the stored procedure parameters
     *@return : list filled with the stored Procedure output
     */
    public static ResultSet executeSPWithPaging(Connection conn, String spName, Object... sqlParameters) throws SQLException {
        //setNullableParameters(sqlParameters);
        spName = getQueryString(spName, sqlParameters);
        ResultSet rs = null;
        CallableStatement cs = null;
        if (conn != null) {
            cs = conn.prepareCall(spName, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (sqlParameters != null) {
                for (int i = 1; i <= sqlParameters.length; i++) {
                    cs.setObject(i, sqlParameters[i - 1]);
                }
            }
            rs = cs.executeQuery();
        }
        return rs;
    }

    /** execute prepared sql statment
     *@param conn : the connection that the prepared statment will exceute in
     *@param preparedStm : prepared statment query e.g.: select * from tbl where col1= ?...
     *@param sqlParameters : arrayList filled with the prepared statment parameters
     *@return : list filled with the prepared statment output
     */
    public static ResultSet executePreparedStm(Connection conn, String preparedStm, Object... sqlParameters) throws SQLException {
        //setNullableParameters(sqlParameters);        
        ResultSet rs = null;
        PreparedStatement prepStm = null;
        if (conn != null) {
            prepStm = conn.prepareStatement(preparedStm);
            if (sqlParameters != null) {
                for (int i = 1; i <= sqlParameters.length; i++) {
                    prepStm.setObject(i, sqlParameters[i - 1]);
                }
            }
            rs = prepStm.executeQuery();
        }
        return rs;
    }

    /** execute prepared sql statment
     *@param conn : the connection that the prepared statment will exceute in
     *@param preparedStm : prepared statment query e.g.: select * from tbl where col1= ?...
     *@param sqlParameters : arrayList filled with the prepared statment parameters
     *@return : list filled with the prepared statment output
     */
    public static ResultSet executePreparedStmWithPaging(Connection conn, String preparedStm, Object... sqlParameters) throws SQLException {
        //setNullableParameters(sqlParameters);
        ResultSet rs = null;
        PreparedStatement prepStm = null;
        if (conn != null) {
            prepStm = conn.prepareStatement(preparedStm, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (sqlParameters != null) {
                for (int i = 1; i <= sqlParameters.length; i++) {
                    prepStm.setObject(i, sqlParameters[i - 1]);
                }
            }
            rs = prepStm.executeQuery();
        }
        return rs;
    }

    /** execute prepared sql statment
     *@param conn : the connection that the prepared statment will exceute in
     *@param preparedStm : prepared statment query e.g.: select * from tbl where col1= ?...
     *@param sqlParameters : list filled with the prepared statment parameters
     *@return : int with number of updated rows
     */
    public static int executeUpdatePreparedStm(Connection conn, String preparedStm, Object... sqlParameters) throws SQLException {
        //setNullableParameters(sqlParameters);
        int rs = 0;
        PreparedStatement prepStm = null;
        if (conn != null) {
            prepStm = conn.prepareStatement(preparedStm);
            if (sqlParameters != null) {
                for (int i = 1; i <= sqlParameters.length; i++) {
                    prepStm.setObject(i, sqlParameters[i - 1]);
                }
            }
            rs = prepStm.executeUpdate();
        }
        return rs;
    }
    
    public static String getQueryString(String spName,Object[] sqlParameters){
                String spName1 = "{call  "+ spName +"(";        
        for (int i = 0; i < sqlParameters.length; i++) {
            spName1 += "?";
            if(i < sqlParameters.length -1){
                spName1 += ",";                
            }
        }
        spName1 += ")}";
        return spName1;
    }
}