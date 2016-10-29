/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smp.frams.models;

import {daoPkName}.dao.*;
import {daoPkName}.model.*;
import com.smp.setting.DefaultSettings;
import com.toedter.calendar.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.sql.Timestamp;

/**
 *
 * @author mmetwally
 */
public class {className}TableModel extends AbstractTableModel {

    Vector<String> columnTitles = new Vector<String>();
    Vector<Object[]> dataEntries = new Vector<Object[]>();
    int rowCount;
    List<Integer> updatedIds = new ArrayList<Integer>();

    public {className}TableModel(Vector<String> columnTitles, Vector<Object[]> dataEntries) {
        this.columnTitles = columnTitles;
        this.dataEntries = dataEntries;
    }

    public int getRowCount() {
        return dataEntries.size();
    }

    public int getColumnCount() {
        return columnTitles.size();
    }

    public Object getValueAt(int row, int column) {
        return dataEntries.get(row)[column];
    }

    public String getColumnName(int column) {
        return columnTitles.get(column);
    }

    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public boolean isCellEditable(int row, int column) {
        if (column == 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int column) {
        if (column != 0) {
            try {
                Integer id = Integer.valueOf(row);
                updatedIds.add(id);
            } catch (Exception ex) {
            }
        }
        dataEntries.get(row)[column] = value;
    }

    public void removeRow(int row) {
        try {
            Integer id = Integer.valueOf(row);
            int index = updatedIds.indexOf(id);
            if (index > 0) {
                updatedIds.remove(index);
            }
            //updatedIds.add(Integer.parseInt(dataEntries.get(row)[1].toString()));
        } catch (Exception ex) {
        }
        dataEntries.removeElementAt(row);
    }

    public List<Integer> getUpdatedIds() {
        return updatedIds;
    }

    public void addRow(Object[] values) {
        dataEntries.add(values);
    }

    public void addRow({className} {classNamelow}) {
        dataEntries.add(getNewRow({classNamelow}));
    }

    public Object[] getNewRow({className} {classNamelow}) {
        Object[] values = new Object[{columnsCount}+1];
        values[0] = new Boolean(false);
		{propsetvalueTablemodel}        
		return values;
    }

   public {className} get{className}(int row) {
	   {className} {classNamelow} = new {className}();
		java.text.SimpleDateFormat writeDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
	   {propgetvalueTablemodel}
        return {classNamelow};
    }

    public void setUpdatedIds(List<Integer> updatedIds) {
        this.updatedIds = updatedIds;
    }

    public static AbstractTableModel loadData() {
        Vector<String> columnTitles = new Vector<String>();
        Vector<Object[]> dataEntries = new Vector<Object[]>();
        columnTitles.add("");
		{columnTitletable}
        {className} {classNamelow};
		List<{className}> {classNamelow}s = {className}DAO.selectAll();
		if({classNamelow}s == null){
			{classNamelow}s = new ArrayList<{className}>();
		}
        Object[] values;
        for (int i = 0; i < {classNamelow}s.size(); i++) {
            {classNamelow} = {classNamelow}s.get(i);
            values = new Object[{columnsCount}+1];
            values[0] = new Boolean(false);
            {objectvaluetable}           
            dataEntries.add(values);
        }
        AbstractTableModel model = new {className}TableModel(columnTitles, dataEntries);
        return model;
    }

    public static void get{className}sTable(JTable jTable1) {

        AbstractTableModel model = {className}TableModel.loadData();
        jTable1.setModel(model);
        jTable1.createDefaultColumnsFromModel();
		{cellEditor}
    }

}
