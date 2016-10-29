/*
 * TemplateGenerator.java
 *
 * Created on 06 September 2007, 19:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.MGenerator.Generators;

import com.MGenerator.DataBaseLayer.TableBean;
import com.MGenerator.Parsers.Parser;
import com.MGenerator.metadata.MetaData;
import com.MGenerator.metadata.SettingDetails;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author mohamed.metwally
 */
public class TemplateGenerator extends Generator {

    HashMap userKeysMap;

    /**
     * Creates a new instance of TemplateGenerator
     */
    public TemplateGenerator(TableBean tblBean, SettingDetails settingDetails) {
    }

    public TemplateGenerator() {
    }

    public static String getPropValue(String propName, Properties prop, SettingDetails settingDetails) {
        String propVal = "";
        StringBuilder filePath = new StringBuilder();
        if (propName.contains("_file")) {
            propVal = prop.getProperty(propName);
            if (propVal == null || propVal.isEmpty()) {
                propVal = "";
            } else {
                filePath.append(getDirPath(settingDetails.getTemplatePath()));
                if(propVal.startsWith("/")){
                    propVal = propVal.substring(1);
                }
                filePath.append(propVal);
                System.err.println("propName is File path = " + filePath.toString());
                File f = new File(filePath.toString());
                if (f.exists()) {
                    try {
                        propVal = Parser.convertStreamToString(new FileInputStream(f));
                    } catch (Exception ex) {
                        propVal = "";
                    }
                } else {
                    propVal = "";
                }
            }

        } else {
            propVal = prop.getProperty(propName);
        }
        return propVal;
    }

    public static String getDirPath(String fileFullPath) {
        String dirPath = "";
        File f = new File(fileFullPath);
        if (f != null && f.isFile()) {
            dirPath = f.getParent();
            if(!dirPath.endsWith("/")){
                dirPath += "/";
            }
        }
        return dirPath;
    }

    public void generate(TableBean tblBean, SettingDetails settingDetails, Properties prop) {
        File file;
        FileOutputStream fos = null;
        userKeysMap = new HashMap();
        String userDefinedKeys = prop.getProperty("userKeys");
        String[] userKeys = userDefinedKeys.split(",");
        for (int i = 0; i < userKeys.length; i++) {
            if (!userKeys[i].contains("_tblrepeat")) {
                userKeysMap.put(userKeys[i].replace("_repeat", "").replace("_file", ""), Parser.parseUserKey(prop, userKeys[i], tblBean, settingDetails));
            }
        }
        String propName = "";
        String propVal = "";
        String dirs = "";
        boolean check = false;
        String[] keys = MetaData.getKeysBundl().getString("basicKeys").split(",");
        Enumeration enNames = prop.propertyNames();
        while (enNames.hasMoreElements()) {
            propName = enNames.nextElement().toString();
            propVal = getPropValue(propName, prop,settingDetails);
            check = false;
            if (!userDefinedKeys.contains(propName) && !propName.equals("userKeys")) {
                if (propName.contains("_repeat")) {
                    try {
                        if (propName.contains("table")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/resources/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("table", tblBean.getTblName()));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                        } else if (propName.toLowerCase().contains("processor.java")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/cxf/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("processor", Parser.refineName(tblBean.getTblName(), 1) + "ManagerProcessor"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //classhandler
                        } else if (propName.toLowerCase().contains("processor")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/cxf/processors/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("processor", Parser.refineName(tblBean.getTblName(), 1) + "ManagerProcessor"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //classhandler
                        } else if (propName.toLowerCase().contains("manager")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/cxf/handler/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("manager", Parser.refineName(tblBean.getTblName(), 1) + "Managment"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //cxf_manager
                        } else if (propName.toLowerCase().contains("requestbean")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/cxf/handler/inputmodel/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("RequestBean", Parser.refineName(tblBean.getTblName(), 1) + "RequestBean"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                ex.printStackTrace();
//LoggingClass.logInfo(ex.toString());
                            }
                            //cxf_manager
                        } else if (propName.toLowerCase().contains("responsebean")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/cxf/handler/outputmodel/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("ResponseBean", Parser.refineName(tblBean.getTblName(), 1) + "ResponseBean"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //cxf_manager
                        } else if (propName.toLowerCase().contains("classdaobase")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/base/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("classDAOBase", Parser.refineName(tblBean.getTblName(), 1) + "DAOBase"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("classdao")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/dao/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("classDAO", Parser.refineName(tblBean.getTblName(), 1) + "DAO"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("classhandler")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/servlets/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("classhandler", Parser.refineName(tblBean.getTblName(), 1) + "Handler"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("class.jsp")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/Pages/displaytable/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("class.jsp", Parser.refineName(tblBean.getTblName(), 1) + ".jsp"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("class_jquery.jsp")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/Pages/jquery/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("class_Jquery.jsp", Parser.refineName(tblBean.getTblName(), 2) + ".jsp"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("classtable.jsp")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/Pages/jquery/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("classTable.jsp", Parser.refineName(tblBean.getTblName(), 2) + "Table.jsp"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("modelpanel")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/panels/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("modelPanel.java", Parser.refineName(tblBean.getTblName(), 1) + "Panel.java").replace("modelPanel.form", Parser.refineName(tblBean.getTblName(), 1) + "Panel.form"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("modeltablemodel")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/panels/models/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("modelTableModel", Parser.refineName(tblBean.getTblName(), 1) + "TableModel"));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                            //gwtclass
                        } else if (propName.toLowerCase().contains("gwtclass")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/gwt/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("gwtclass", "Gwt" + Parser.refineName(tblBean.getTblName(), 1)));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                        } else if (propName.toLowerCase().contains("class.java")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/model/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("class", Parser.refineName(tblBean.getTblName(), 1)));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                        } else if (propName.toLowerCase().contains("class")) {
                            try {
                                dirs = settingDetails.getOutPath() + "/" + settingDetails.getDaoPackage().replace(".", "/") + "/";
                                file = new File(dirs);
                                file.mkdirs();
                                file = new File(dirs + propName.replace("_repeat", "").replace("_file", "").replace("class", Parser.refineName(tblBean.getTblName(), 1)));
                                file.createNewFile();
                                fos = new FileOutputStream(file);
                                check = true;
                            } catch (IOException ex) {
                                //LoggingClass.logInfo(ex.toString());
                            }
                        }
                        if (check) {
                            for (int i = 0; i < keys.length; i++) {
                                if (propVal.contains(keys[i])) {
                                    propVal = propVal.replace(keys[i], Parser.parseSystemKeys(tblBean, keys[i], null, settingDetails, propVal, i));
                                }
                            }
                            for (int i = 0; i < userKeys.length; i++) {
                                if (!userKeys[i].contains("_tblrepeat")) {
                                    if (propVal.contains(userKeys[i].replace("_repeat", "").replace("_file", ""))) {
                                        propVal = propVal.replace(userKeys[i].replace("_repeat", "").replace("_file", ""), userKeysMap.get(userKeys[i].replace("_repeat", "").replace("_file", "")).toString());
                                    }
                                }
                            }
                            if (fos != null) {
                                propVal = getStringToFile(propVal);
                                fos.write(propVal.getBytes());
                                fos.flush();
                                try {
                                    fos.close();
                                } catch (Exception ex) {
                                    //LoggingClass.logInfo(ex.toString());
                                }
                            }
                        }
                    } catch (IOException ex) {
                        //LoggingClass.logInfo(ex.toString());
                    }
                }
            }
        }

    }

    public String getStringToFile(String strToFile) {
//        strToFile = strToFile.replace(";", ";\n");
//        strToFile = strToFile.replace("{", "{\n");
//        strToFile = strToFile.replace("}", "}\n");
//        strToFile = strToFile.replace(">", ">\n");
//        strToFile = strToFile.replace("*/", "*/\n");
//        strToFile = strToFile.replace("\\*", "\\*\n");

        //
        return strToFile;
    }

    public void generate(SettingDetails settingDetails, Properties prop) {
        File file;
        FileOutputStream fos = null;
        userKeysMap = new HashMap();
        String userDefinedKeys = prop.getProperty("userKeys");
        String[] userKeys = userDefinedKeys.split(",");
        for (int i = 0; i < userKeys.length; i++) {
            if (userKeys[i].contains("_tblrepeat") || !userKeys[i].contains("repeat")) {
                userKeysMap.put(userKeys[i].replace("_tblrepeat", ""), Parser.parseTableUserKey(prop, userKeys[i], settingDetails));
            }
        }
        String propName = "";
        String propVal = "";
        boolean check = false;
        String[] keys = MetaData.getKeysBundl().getString("basicKeys").split(",");
        Enumeration enNames = prop.propertyNames();
        while (enNames.hasMoreElements()) {
            propName = enNames.nextElement().toString();
            propVal = getPropValue(propName, prop,settingDetails);
            propName = propName.replace("_file", "");
            check = false;
            if (!userDefinedKeys.contains(propName) && !propName.equals("userKeys")) {
                if (!propName.contains("_repeat")) {
                    try {
                        file = new File(settingDetails.getOutPath() + "/" + propName.replace("_file", ""));
                        file.createNewFile();
                        fos = new FileOutputStream(file);
                    } catch (IOException ex) {
                        //LoggingClass.logInfo(ex.toString());
                    }
                    for (int i = 0; i < keys.length; i++) {
                        if (propVal.contains(keys[i])) {
                            try {
                                propVal = propVal.replace(keys[i], Parser.parseSystemKeys(null, keys[i], null, settingDetails, propVal, i));
                            } catch (Exception ex) {
                                System.err.println("propName = " + propName);
                                ex.printStackTrace();
                            }
                        }
                    }
                    for (int i = 0; i < userKeys.length; i++) {
                        if (userKeys[i].contains("_tblrepeat") || !userKeys[i].contains("repeat")) {
                            if (propVal.contains(userKeys[i].replace("_tblrepeat", ""))) {
                                propVal = propVal.replace(userKeys[i].replace("_tblrepeat", ""), userKeysMap.get(userKeys[i].replace("_tblrepeat", "")).toString());
                            }
                        }
                    }
                    if (fos != null) {
                        try {
                            propVal = getStringToFile(propVal);
                            fos.write(propVal.getBytes());
                            fos.flush();
                            fos.close();
                        } catch (IOException ex) {
                            //LoggingClass.logInfo(ex.toString());
                        }
                    }
                }
            }
        }

    }
}
