/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MGenerator.metadata;

import java.util.ResourceBundle;

/**
 *
 * @author mmetwally
 */
public class MetaData {

    private static ResourceBundle keysBundl;
    private static String connectionStr;
    private static String driver;
    private static String userName;
    private static String password;
    private static String dbName;
    private static Integer type;
    private static String oracleScheme = null;

    //{driver},{connStr},{userName},{password}
    static {
        setKeysBundl(ResourceBundle.getBundle("keys"));

    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        MetaData.dbName = dbName;
    }

    /**
     * @return the keysBundl
     */
    public static ResourceBundle getKeysBundl() {
        return keysBundl;
    }

    /**
     * @param aTemplateBundl the keysBundl to set
     */
    public static void setKeysBundl(ResourceBundle keysBundl) {
        MetaData.keysBundl = keysBundl;
    }

    /**
     * @return the connectionStr
     */
    public static String getConnectionStr() {
        return connectionStr;
    }

    /**
     * @param aConnectionStr the connectionStr to set
     */
    public static void setConnectionStr(String aConnectionStr) {
        connectionStr = aConnectionStr;
    }

    /**
     * @return the driver
     */
    public static String getDriver() {
        return driver;
    }

    /**
     * @param aDriver the driver to set
     */
    public static void setDriver(String aDriver) {
        driver = aDriver;
    }

    /**
     * @return the userName
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * @param aUserName the userName to set
     */
    public static void setUserName(String aUserName) {
        userName = aUserName;
        setOracleScheme(userName);
    }

    /**
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @param aPassword the password to set
     */
    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    /**
     * @return the type
     */
    public static Integer getType() {
        return type;
    }

    /**
     * @param aType the type to set
     */
    public static void setType(Integer aType) {
        type = aType;
    }

    /**
     * @return the oracleScheme
     */
    public static String getOracleScheme() {
        return oracleScheme;
    }

    /**
     * @param aOracleScheme the oracleScheme to set
     */
    public static void setOracleScheme(String aOracleScheme) {
        oracleScheme = aOracleScheme;
    }

}
