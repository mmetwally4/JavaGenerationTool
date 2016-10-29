/*
 * Parser.java
 *
 * Created on 06 September 2007, 19:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.MGenerator.Parsers;

import com.MGenerator.DataBaseLayer.ColumnBean;
import com.MGenerator.DataBaseLayer.SpecialTypesHandling;
import com.MGenerator.DataBaseLayer.TableBean;
import com.MGenerator.Generators.TemplateGenerator;
import com.MGenerator.metadata.MetaData;
import com.MGenerator.metadata.SettingDetails;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author mohamed.metwally
 */
public class Parser {

    /**
     * Creates a new instance of Parser
     */
    public Parser() {
    }

    public static String parseTableUserKey(Properties prop,String userKey, SettingDetails settingDetails) {
        String result = " ";
        String output = "";
        TableBean tblBean;
        try {
            String value = prop.getProperty(userKey.replace("{", "").replace("}", ""));
            String[] keys = MetaData.getKeysBundl().getString("basicKeys").split(",");
            ArrayList containKeys = new ArrayList();
            for (int i = 0; i < keys.length; i++) {
                if (value.contains(keys[i])) {
                    containKeys.add(keys[i]);
                }
            }
            String key;
            if (userKey.contains("repeat")) {
                for (int i = 0; i < settingDetails.getTbls().size(); i++) {
                    tblBean = settingDetails.getTbls().get(i);
                    result = value;
                    for (int j = 0; j < containKeys.size(); j++) {
                        key = containKeys.get(j).toString();
                        result = result.replace(key, parseSystemKeys(tblBean, key, null, settingDetails, result, j));
                    }
                    output += result + " ";
                }
            } else {
                result = value;
                for (int j = 0; j < containKeys.size(); j++) {
                    key = containKeys.get(j).toString();
                    result = result.replace(key, parseSystemKeys(null, key, null, settingDetails, result, j));
                }
                output = result + "";
            }

        } catch (Exception ex) {
            System.err.println(" error in " + userKey);
            ex.printStackTrace();
        }
        return output;
    }

    public static String parseUserKey(Properties prop, String userKey, TableBean tblBean, SettingDetails settingDetails) {
        String result = " ";
        String output = "";
        try {
            ArrayList cols = tblBean.getColumns();
            ColumnBean col = null;
            String userDefinedKeys = prop.getProperty("userKeys");
//        String value = prop.getProperty(userKey.replace("{", "").replace("}", ""));
            String value = TemplateGenerator.getPropValue(userKey.replace("{", "").replace("}", ""), prop,settingDetails);
            String fkvalue = "";
            String pkvalue = "";
            String[] types = MetaData.getKeysBundl().getString("types").split(",");
            ArrayList<SpecialTypesHandling> specialTypes = new ArrayList<SpecialTypesHandling>();
            if (value == null || value.equals("")) {
                return "";
            }
            if (userDefinedKeys.contains("fk_" + userKey.replace("{", "").replace("}", ""))) {
                fkvalue = TemplateGenerator.getPropValue("fk_" + userKey.replace("{", "").replace("}", ""), prop,settingDetails);
            }
            if (userDefinedKeys.contains("pk_" + userKey.replace("{", "").replace("}", ""))) {
                pkvalue = TemplateGenerator.getPropValue("pk_" + userKey.replace("{", "").replace("}", ""), prop,settingDetails);
            }
            String[] keys = MetaData.getKeysBundl().getString("basicKeys").split(",");
            SpecialTypesHandling sth;
            if (types != null && types.length > 0) {
                for (int i = 0; i < types.length; i++) {
                    if (userDefinedKeys.contains(types[i] + "_" + userKey.replace("{", "").replace("}", ""))) {
                        sth = new SpecialTypesHandling();
                        sth.setType(types[i]);
                        sth.setTypevalue(TemplateGenerator.getPropValue(types[i] + "_" + userKey.replace("{", "").replace("}", ""), prop,settingDetails));
                        if (sth.getTypevalue() != null && !sth.getTypevalue().isEmpty()) {
                            sth.setContainKeys(new ArrayList());
                            for (int c = 0; c < keys.length; c++) {
                                if (sth.getTypevalue().contains(keys[c])) {
                                    sth.getContainKeys().add(keys[c]);
                                }
                            }
                            specialTypes.add(sth);
                        }
                    }
                }
            }

            ArrayList containKeys = new ArrayList();
            for (int i = 0; i < keys.length; i++) {
                if (value.contains(keys[i])) {
                    containKeys.add(keys[i]);
                }
            }
            ArrayList fkcontainKeys = new ArrayList();
            if (fkvalue != null && !fkvalue.isEmpty()) {
                for (int i = 0; i < keys.length; i++) {
                    if (fkvalue.contains(keys[i])) {
                        fkcontainKeys.add(keys[i]);
                    }
                }
            }
            ArrayList pkcontainKeys = new ArrayList();
            if (pkvalue != null && !pkvalue.isEmpty()) {
                for (int i = 0; i < keys.length; i++) {
                    if (pkvalue.contains(keys[i])) {
                        pkcontainKeys.add(keys[i]);
                    }
                }
            }
            String[] out;
            String key;
            String fkParse = "";
            boolean noPK = false;
            boolean flag = false;
            if (!userKey.contains("_tblrepeat")) {
                if (userKey.contains("_repeat")) {
                    System.err.println("repeat tbl cols " + tblBean.getTblName());
                    if (userKey.contains("_nopk")) {
                        noPK = true;
                    }
                    for (int i = 0; i < cols.size(); i++) {
                        col = (ColumnBean) cols.get(i);
                        flag = false;
                        System.err.println("repeat tbl cols " + col.getColName());
                        result = value;
                        if (noPK) {
                            if (col.isIsPk()) {
                                System.err.println("repeat tbl cols " + col.getColName() + " PK");
                                continue;
                            }
                        }
                        if (userKey.startsWith("fk_") && !col.isIsFk()) {
                            continue;
                        } else if (userKey.startsWith("pk_") && !col.isIsPk()) {
                            continue;
                        } else if (fkvalue != null && !fkvalue.isEmpty() && col.isIsFk()) {
                            result = fkvalue;
                            result = handleKeys(fkcontainKeys, result, tblBean, settingDetails, col, i);
                        } else if (pkvalue != null && !pkvalue.isEmpty() && col.isIsPk()) {
                            result = pkvalue;
                            result = handleKeys(pkcontainKeys, result, tblBean, settingDetails, col, i);
                        } else if (specialTypes != null && !specialTypes.isEmpty()) {
                            for (int s = 0; s < specialTypes.size(); s++) {
                                if (specialTypes.get(s).getType().equalsIgnoreCase(col.getJavaType(col.getDataType()))) {
                                    result = specialTypes.get(s).getTypevalue();
                                    result = handleKeys(specialTypes.get(s).getContainKeys(), result, tblBean, settingDetails, col, i);
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                result = handleKeys(containKeys, result, tblBean, settingDetails, col, i);
                            }

                        } else {
                            result = handleKeys(containKeys, result, tblBean, settingDetails, col, i);
                        }
                        output += result + " ";
                    }
                } else {
                    result = value;
                    //System.err.println(col.getColName());
                    if (userKey.startsWith("fk_") && !col.isIsFk()) {
                    } else if (userKey.startsWith("pk_") && !col.isIsPk()) {
                    } else if (fkvalue != null && !fkvalue.isEmpty() && col.isIsFk()) {
                        result = fkvalue;
                        result = handleKeys(fkcontainKeys, result, tblBean, settingDetails, col, 0);
                    } else if (pkvalue != null && !pkvalue.isEmpty() && col.isIsPk()) {
                        result = pkvalue;
                        result = handleKeys(pkcontainKeys, result, tblBean, settingDetails, col, 0);
                    } else if (specialTypes != null && !specialTypes.isEmpty()) {
                        for (int s = 0; s < specialTypes.size(); s++) {
                            if (specialTypes.get(s).getType().equalsIgnoreCase(col.getJavaType(col.getDataType()))) {
                                result = specialTypes.get(s).getTypevalue();
                                result = handleKeys(specialTypes.get(s).getContainKeys(), result, tblBean, settingDetails, col, 0);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            result = handleKeys(containKeys, result, tblBean, settingDetails, col, 0);
                        }

                    } else {
                        result = handleKeys(containKeys, result, tblBean, settingDetails, col, 0);
                    }
                    output = result;
                    //userKeysMap.put(userKey, result);
                }
            }
        } catch (Exception ex) {
            System.err.println(" error in " + userKey + " table " + tblBean.getTblName());
            ex.printStackTrace();
        }
        return output;
    }

    public static String handleKeys(ArrayList containKeys, String result, TableBean tblBean, SettingDetails settingDetails, ColumnBean col, int index) {
        String[] out;
        String key;
        String fkParse = "";
        for (int j = 0; j < containKeys.size(); j++) {
            key = containKeys.get(j).toString();
            System.err.println(key);
            if (key.equals("{isFK}")) {
                out = result.split("isFK");
                fkParse = out[1].substring(1).replace("{/", "");
                result = result.replace("{isFK}" + fkParse + "{/isFK}", parseSystemKeys(tblBean, key, col, settingDetails, result, j));
            } else if (key.equals("{isPK}")) {
                out = result.split("isPK");
                fkParse = out[1].substring(1).replace("{/", "");
                result = result.replace("{iPK}" + fkParse + "{/isPK}", parseSystemKeys(tblBean, key, col, settingDetails, result, j));
            } else {
                result = result.replace(key, parseSystemKeys(tblBean, key, col, settingDetails, result, j));
            }
        }
        return result;

    }

    public static String parseSystemKeys(TableBean tableBean, String key, ColumnBean colBean, SettingDetails settingDetails, String value, int index) {
        String result = " ";
        //{propType},{propNameC},{fkCol},{fkTable}
        ArrayList cols = null;
        if (tableBean != null) {
            cols = tableBean.getColumns();
        }
        String fkParse = "";
        if (key.equals("{driver}")) {
            result = MetaData.getDriver();
        }
        
        if (key.equals("{dbName}")) {
            result = MetaData.getDbName();
        } else if (key.equals("{connStr}")) {
            result = MetaData.getConnectionStr().replace("&", "&amp;");
        } else if (key.equals("{userName}")) {
            result = MetaData.getUserName();
        } else if (key.equals("{password}")) {
            result = MetaData.getPassword();
        } else if (key.equals("{contextpath}")) {
            result = settingDetails.getContextPath();
        } else if (key.equals("{daoPath}")) {
            result = settingDetails.getDaoPackage().replace(".", "/");
        } else if (key.equals("{daoPkName}")) {
            result = settingDetails.getDaoPackage();
        } else if (key.equals("{packageName}")) {
            result = settingDetails.getModelsPackage();
        } else if (key.equals("{className}")) {
            result = Parser.refineName(tableBean.getTblName(), 1);
        } else if (key.equals("{classNamelow}")) {
            result = Parser.refineName(tableBean.getTblName(), 2);
        } else if (key.equals("{tblNamelow}")) {
            result = tableBean.getTblName().toLowerCase();
        } else if (key.equals("{tblName}")) {
            result = tableBean.getTblName();
        } else if (key.equals("{tblIndex}")) {
            result = String.valueOf(tableBean.getTblIndex());
        } else if (key.equals("{colName}")) {
            result = colBean.getColName();
        } else if (key.equals("{colIndex}")) {
            result = String.valueOf(colBean.getColumnIndex());
        } else if (key.equals("{columnsCount}")) {
            if (colBean != null) {
                result = colBean.getTblBean().getColumns().size() + "";
            } else if (tableBean != null) {
                result = tableBean.getColumns().size() + "";
            }
        } else if (key.equals("{fstringpropNameC}")) {
            if (colBean != null && colBean.isFirstString()) {
                result = Parser.refineName(colBean.getColName(), 1);
            } else if (tableBean != null && tableBean.getFirstString() != null) {
                result = Parser.refineName(tableBean.getFirstString(), 1);
            }

        } else if (key.equals("{fstringpropName}")) {
            if (colBean != null && colBean.isFirstString()) {
                result = Parser.refineName(colBean.getColName(), 2);
            } else if (tableBean != null && tableBean.getFirstString() != null) {
                result = Parser.refineName(tableBean.getFirstString(), 2);
            }
        } else if (key.equals("{fstringcolNameC}")) {
            if (tableBean != null && !tableBean.getFirstString().isEmpty()) {
                result = tableBean.getFirstString();
            }
        } else if (key.equals("{fstringcolName}")) {
            if (tableBean != null && !tableBean.getFirstString().isEmpty()) {
                result = tableBean.getFirstString();
            }
        } else if (key.equals("{propName_string}")) {
            if (colBean.getJavaType().equalsIgnoreCase("string")) {
                result = Parser.refineName(colBean.getColName(), 2);
            }
        } else if (key.equals("{propNameC_string}")) {
            if (colBean.getJavaType().equalsIgnoreCase("string")) {
                result = Parser.refineName(colBean.getColName(), 1);
            }
        } else if (key.equals("{preString}")) {
            if (colBean != null) {
                if (colBean.getJavaType().equalsIgnoreCase("string")) {
                    result = "'%";
                }
            } else if (tableBean != null) {
                if (tableBean.getFirstType().equalsIgnoreCase("String")) {
                    result = "'%";
                }
            }
        } else if (key.equals("{afterString}")) {
            if (colBean != null) {
                if (colBean.getJavaType().equalsIgnoreCase("string")) {
                    result = "%'";
                }
            } else if (tableBean != null) {
                if (tableBean.getFirstType().equalsIgnoreCase("String")) {
                    result = "%'";
                }
            }
        } else if (key.equals("{propName_fkcheck}")) {
            if (colBean.isIsFk()) {
                result = Parser.refineName(colBean.getColName(), 2) + ".id";
            } else {
                result = Parser.refineName(colBean.getColName(), 2);
            }
        } else if (key.equals("{propName_getfkcheck}")) {
            if (colBean.isIsFk()) {
                result = Parser.refineName(colBean.getColName(), 2) + ".getId()";
            } else {
                result = Parser.refineName(colBean.getColName(), 2);
            }
        } else if (key.equals("{pkType}")) {
            result = "java.lang.Integer";
        } else if (key.equals("{pkColName}")) {
            if (colBean != null) {
                if (colBean.isIsPk()) {
                    result = colBean.getColName();
                }
            } else {
                for (int i = 0; i < cols.size(); i++) {
                    colBean = (ColumnBean) cols.get(i);
                    if (colBean.isIsPk()) {
                        result = colBean.getColName();
                        break;
                    }
                }
            }

        } else if (key.equals("{pkPropName}")) {
            if (colBean != null) {
                if (colBean.isIsPk()) {
                    result = Parser.refineName(colBean.getColName(), 2);
                }
            } else {
                for (int i = 0; i < cols.size(); i++) {
                    colBean = (ColumnBean) cols.get(i);
                    if (colBean.isIsPk()) {
                        result = Parser.refineName(colBean.getColName(), 2);
                        break;
                    }
                }
            }

        } else if (key.equals("{pkPropNameC}")) {
            if (colBean != null) {
                if (colBean.isIsPk()) {
                    result = Parser.refineName(colBean.getColName(), 1);
                }
            } else {
                for (int i = 0; i < cols.size(); i++) {
                    colBean = (ColumnBean) cols.get(i);
                    if (colBean.isIsPk()) {
                        result = Parser.refineName(colBean.getColName(), 1);
                        break;
                    }
                }
            }
        } else if (key.equals("{propName}")) {
            result = Parser.refineName(colBean.getColName(), 2);
        } else if (key.equals("{propType}")) {
            result = colBean.getJavaType(colBean.getDataType());
        } else if (key.equals("{propNameC}")) {
            result = Parser.refineName(colBean.getColName(), 1);
        } else if (key.equals("{equalorlike}")) {
            if (colBean != null) {
                if (colBean.getJavaType(colBean.getDataType()).equals("String")) {
                    result = "like";
                } else {
                    result = "=";
                }
            } else if (tableBean != null) {
                if (tableBean.getFirstType().equalsIgnoreCase("String")) {
                    result = "like";
                } else {
                    result = "=";
                }
            }
        } else if (key.equals("{fkClass}")) {
            if (colBean.isIsFk()) {
                result = Parser.refineName(colBean.getFkClass().getFkTable(), 1);
            }
        } else if (key.equals("{fkClasslow}")) {
            if (colBean.isIsFk()) {
                result = Parser.refineName(colBean.getFkClass().getFkTable(), 2);
            }
        } else if (key.equals("{fkCol}")) {
            if (colBean.isIsFk()) {
                result = colBean.getFkClass().getFkColumn();
            }
        } else if (key.equals("{fkTable}")) {
            if (colBean.isIsFk()) {
                result = colBean.getFkClass().getFkTable();
            }
        } else if (key.equals("{isFK}")) {
            String[] out = value.split("isFK");
            fkParse = out[1].substring(1).replace("{/", "");
            if (colBean.isIsFk()) {
                String[] keys = MetaData.getKeysBundl().getString("basicKeys").split(",");
                fkParse = fkParse.replace("^", "{").replace("$", "}");
                for (int i = 0; i < keys.length; i++) {
                    if (fkParse.contains(keys[i])) {
                        fkParse = fkParse.replace(keys[i], parseSystemKeys(tableBean, keys[i], colBean, settingDetails, fkParse, -1));
                    }
                }
                result = fkParse;
            } else {
                result = "";
            }
        } else if (key.equals("{isPK}")) {
            String[] out = value.split("isPK");
            fkParse = out[1].substring(1).replace("{/", "");
            if (colBean.isIsFk()) {
                String[] keys = MetaData.getKeysBundl().getString("basicKeys").split(",");
                fkParse = fkParse.replace("^", "{").replace("$", "}");
                for (int i = 0; i < keys.length; i++) {
                    if (fkParse.contains(keys[i])) {
                        fkParse = fkParse.replace(keys[i], parseSystemKeys(tableBean, keys[i], colBean, settingDetails, fkParse, i));
                    }
                }
                result = fkParse;
            } else {
                result = "";
            }
        } else if (key.equals("{empty}")) {
            result = "";
        } else if (key.equals("{commaonnonfirst}")) {
            if (colBean != null) {
                if (colBean.getColumnIndex() > 1) {
                    result = ",";
                }
            } else {
            }
        } else if (key.equals("{commaonnonsecond}")) {
            //System.out.print("index " + index + " add comma");
            if (colBean != null) {
                if (colBean.getColumnIndex() > 2) {
                    result = ",";
                }
                //  System.out.println(" column "+colBean.getColName());
            } else {
                //System.out.println("");
            }

        }

        if (result != null) {
            result = result.replace("open_brace", "{");
            result = result.replace("close_brace", "}");
        }
        return result;
    }

    public static String lowFirstChar(String input) {
        input = input.substring(0, 1).toLowerCase() + input.substring(1);
        return input;
    }

    public static String upFirstChar(String input) {
        input = input.substring(0, 1).toUpperCase() + input.substring(1);
        return input;
    }

    public static String refineName(String name, int type) {
        String refinedName = "";
        String[] names;
        if (type == 1) {
            names = name.replace("_", "#under#").split("#under#");
            if (names.length > 0) {
                for (int i = 0; i < names.length; i++) {
                    refinedName += upFirstChar(names[i].toLowerCase());
                }
            }
            if (refinedName == null || refinedName.isEmpty()) {
                refinedName = upFirstChar(name);
            }
        } else {
            names = name.replace("_", "#under#").split("#under#");
            if (names.length > 0) {
                for (int i = 0; i < names.length; i++) {
                    if (i == 0) {
                        refinedName += names[i].toLowerCase();
                    } else {
                        refinedName += upFirstChar(names[i].toLowerCase());
                    }
                }
            }
            if (refinedName == null || refinedName.isEmpty()) {
                refinedName = lowFirstChar(name);
            }
        }
        return refinedName;
    }

    public static String convertStreamToString(InputStream is) throws IOException {

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}
