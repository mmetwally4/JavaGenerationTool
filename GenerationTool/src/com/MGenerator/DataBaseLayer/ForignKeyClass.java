/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MGenerator.DataBaseLayer;

/**
 *
 * @author mmetwally
 */
public class ForignKeyClass {

    private String fkTable;
    private String fkColumn;

    /**
     * @return the fkTable
     */
    public String getFkTable() {
        return fkTable;
    }

    /**
     * @param fkTable the fkTable to set
     */
    public void setFkTable(String fkTable) {
        this.fkTable = fkTable;
    }

    /**
     * @return the fkColumn
     */
    public String getFkColumn() {
        return fkColumn;
    }

    /**
     * @param fkColumn the fkColumn to set
     */
    public void setFkColumn(String fkColumn) {
        this.fkColumn = fkColumn;
    }
    
}
