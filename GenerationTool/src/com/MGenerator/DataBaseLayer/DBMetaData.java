/*
 * DBMetaData.java
 *
 * Created on 05 September 2007, 14:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.MGenerator.DataBaseLayer;

import com.MGenerator.metadata.MetaData;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mohamed.metwally
 */
public class DBMetaData {

    DatabaseMetaData dbMetaData;
    String catalog;
    int type;
    HashMap tabelsMap;

    /**
     * Creates a new instance of DBMetaData
     */
    public DBMetaData(DatabaseMetaData dbMetaData, String DBCatalog, int type) {
        this.dbMetaData = dbMetaData;
        catalog = DBCatalog;
        this.type = type;
    }

    public ArrayList collectDBTbls() throws SQLException {
        ArrayList tables = new ArrayList();
        TableBean tblBean = new TableBean();
        String[] types = new String[1];
        tabelsMap = new HashMap();
        types[0] = "TABLE";
        int index = 1;
        ResultSet rs = dbMetaData.getTables(catalog, MetaData.getOracleScheme(), "%", types);
        while (rs.next()) {
            tblBean = new TableBean();
            tblBean.setTblIndex(index);

            tblBean.setTblName(rs.getString("TABLE_NAME"));
            System.err.println("------------------------------------------");
            System.err.println(index + " " + tblBean.getTblName());
            if (!isSystemTable(tblBean.getTblName())) {
                collectColumns(tblBean);
                tabelsMap.put(tblBean.getTblName(), tblBean);
                tables.add(tblBean);
            }
            index++;
        }
        for (int i = 0; i < tables.size(); i++) {
            tblBean = (TableBean) tables.get(i);
            try {
                getFK(tblBean.getTblName());
            } catch (Exception ex) {
                continue;
            }
        }
        return tables;
    }

    public boolean isSystemTable(String tblName) {
        if (this.type == 2 && tblName.equals("dtproperties")) {
            return true;
        }
        return false;
    }

    public void collectColumns(TableBean tblBean) throws SQLException {
        ArrayList columns = new ArrayList();
        ColumnBean colBean;
        HashMap columnsMap = new HashMap();
        ArrayList autIncList = getAutoIncrementColumns(dbMetaData.getConnection(), tblBean.getTblName());
        HashMap fks = getFK(tblBean.getTblName());
        ArrayList pks = getPK(tblBean.getTblName());
        ForignKeyClass fkObj;
        boolean firstString = false;
        ResultSet rs = dbMetaData.getColumns(catalog, MetaData.getOracleScheme(), tblBean.getTblName(), "%");
        int index = 1;
        while (rs.next()) {
            colBean = new ColumnBean();
            colBean.setColName(rs.getString("COLUMN_NAME"));
            colBean.setColumnIndex(index);
            System.err.println(index + " " + colBean.getColName());
            if (colBean.getColName() != null && (colBean.getColName().equalsIgnoreCase("insertiondate") || colBean.getColName().equalsIgnoreCase("insertion_date"))) {
                continue;
            }
            colBean.setDataType(rs.getInt("DATA_TYPE"));
            if (!firstString) {
                if (colBean.getJavaType().equalsIgnoreCase("string")) {
                    firstString = true;
                    colBean.setFirstString(true);
                    tblBean.setFirstString(colBean.getColName());
                    tblBean.setFirstType(colBean.getJavaType());
                }
            }
            colBean.setNullable(rs.getInt("NULLABLE"));
            colBean.setColumnSize(rs.getInt("COLUMN_SIZE"));
            try {
                fkObj = (ForignKeyClass) fks.get(colBean.getColName().toLowerCase());
                if (fkObj != null) {
                    colBean.setIsFk(true);
                    colBean.setFkClass(fkObj);
                }
            } catch (Exception ex) {
            }
            colBean.setIsPk(isInList(pks, colBean.getColName()));
            colBean.setIsAutoIncrement(isInList(autIncList, colBean.getColName()));
            colBean.setTblBean(tblBean);            
            columns.add(colBean);
            columnsMap.put(colBean.getColName(), colBean);            
            index++;
        }
        if (!firstString) {
            if (columns != null && !columns.isEmpty()) {
                ((ColumnBean) columns.get(0)).setFirstString(true);
                tblBean.setFirstString(((ColumnBean) columns.get(0)).getColName());
                tblBean.setFirstType(((ColumnBean) columns.get(0)).getJavaType());
            }
        }
        tblBean.setColumns(columns);
        tblBean.setColumnsMap(columnsMap);
    }

    private ArrayList getAutoIncrementColumns(Connection conn, String tblName)
            throws SQLException {
        java.sql.Statement s = conn.createStatement(
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        if(MetaData.getType() == 3){ //mysql
            tblName = "`"+tblName+"`";
        }
        ResultSet rs = s.executeQuery(
                "SELECT * FROM " + tblName + " WHERE 1 = 0");
        java.sql.ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        ArrayList results = new ArrayList();
        for (int i = 1; i <= columnCount; i++) {
            if (meta.isAutoIncrement(i)) {
                String colName = meta.getColumnName(i);
                results.add(colName);
            }
        }
        return results;
    }

    private HashMap getFK(String tableName) {
        HashMap fKs = new HashMap();
        try {
            ResultSet rs = dbMetaData.getExportedKeys(catalog, MetaData.getOracleScheme(), tableName);
            String result = "";
            ForignKeyClass fkCalss;
            TableBean tblBean;
            ColumnBean colBean;
            while (rs.next()) {
                fkCalss = new ForignKeyClass();
                fkCalss.setFkTable(rs.getString("PKTABLE_NAME"));
                fkCalss.setFkColumn(rs.getString(".PKCOLUMN_NAME"));
                result = rs.getString(".FKTABLE_NAME");
                tblBean = (TableBean) tabelsMap.get(result);
                if (tblBean != null) {
                    colBean = (ColumnBean) tblBean.getColumnsMap().get(rs.getString(".FKCOLUMN_NAME"));
                    if (colBean != null) {
                        colBean.setFkClass(fkCalss);
                        colBean.setIsFk(true);
                    }
                }
            }
        } catch (Exception ex) {
        }
        return fKs;
    }

    private ArrayList getPK(String tableName) throws SQLException {
        ArrayList pKs = new ArrayList();
        ResultSet rs = dbMetaData.getPrimaryKeys(catalog, MetaData.getOracleScheme(), tableName);
        String result = "";
        while (rs.next()) {
            result = rs.getString("COLUMN_NAME");
            pKs.add(result);
        }
        return pKs;
    }

    private boolean isInList(ArrayList list, String colName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().toLowerCase().equals(colName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
