/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MGenerator.DataBaseLayer;

/**
 *
 * @author mmetwally
 */
public class ColumnBean {

    private String colName;
    private int columnIndex;
    private int dataType;
    private int nullable;
    public String javaType;
    private boolean isAutoIncrement;
    private int columnSize;
    private boolean isPk;
    private boolean isFk;
    private boolean firstString;
    private ForignKeyClass fkClass;
    private TableBean tblBean;
    

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
        this.javaType = this.getJavaType(dataType);
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public String getJavaType(int sqlType) {
        switch (sqlType) {
            case java.sql.Types.BIT:
                return "Boolean";
            case java.sql.Types.BOOLEAN:
                return "Boolean";
            case java.sql.Types.TINYINT:
                return "Integer";
            case java.sql.Types.SMALLINT:
                return "Integer";
            case java.sql.Types.INTEGER:
                return "Integer";
            case java.sql.Types.BIGINT:
                return "long";
            case java.sql.Types.REAL:
                return "float";  
            case java.sql.Types.FLOAT:
                return "Double";
            case java.sql.Types.DOUBLE:
                return "Double";
            case java.sql.Types.BINARY:
                return "byte[]";
            case java.sql.Types.VARBINARY:
                return "byte[]";
            case java.sql.Types.LONGVARBINARY:
                return "byte[]";
            case java.sql.Types.DATE:
                return "java.sql.Date";
            case java.sql.Types.TIME:
                return "java.sql.Time";
            case java.sql.Types.TIMESTAMP:                
                return "Timestamp";
            case java.sql.Types.CHAR:
                return "String";
            case java.sql.Types.VARCHAR:
                return "String";
            case java.sql.Types.LONGVARCHAR:
                return "String";
            case java.sql.Types.NCHAR:
                return "String";
            case java.sql.Types.NVARCHAR:
                return "String";
            case java.sql.Types.LONGNVARCHAR:
                return "String";
            case java.sql.Types.NUMERIC:
                return "java.math.BigDecimal";
            case java.sql.Types.DECIMAL:
                return "java.math.BigDecimal";
            case java.sql.Types.CLOB:
                return "Clob";
            case java.sql.Types.BLOB:
                return "Blob";
            case java.sql.Types.REF:
                return "Ref";                
            case java.sql.Types.ARRAY:
                return "Array";
            default:
                return "";
        }

    }

    public String getJavaType(){
        return getJavaType(this.getDataType());
    }
    
    public boolean isIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    /**
     * @return the isPk
     */
    public boolean isIsPk() {
        return isPk;
    }

    /**
     * @param isPk the isPk to set
     */
    public void setIsPk(boolean isPk) {
        this.isPk = isPk;
    }

    /**
     * @return the isFk
     */
    public boolean isIsFk() {
        return isFk;
    }

    /**
     * @param isFk the isFk to set
     */
    public void setIsFk(boolean isFk) {
        this.isFk = isFk;
    }

    /**
     * @return the fkTable
     */
    public ForignKeyClass getFkClass() {
        return fkClass;
    }

    /**
     * @param fkTable the fkTable to set
     */
    public void setFkClass(ForignKeyClass fkClass) {
        this.fkClass = fkClass;
    }

    /**
     * @return the firstString
     */
    public boolean isFirstString() {
        return firstString;
    }

    /**
     * @param firstString the firstString to set
     */
    public void setFirstString(boolean firstString) {
        this.firstString = firstString;
    }

    /**
     * @return the columnIndex
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * @param columnIndex the columnIndex to set
     */
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public TableBean getTblBean() {
        return tblBean;
    }

    public void setTblBean(TableBean tblBean) {
        this.tblBean = tblBean;
    }

    
}
