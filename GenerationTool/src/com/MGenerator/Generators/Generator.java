/*
 * Generator.java
 *
 * Created on 06 September 2007, 18:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.MGenerator.Generators;

import com.MGenerator.DataBaseLayer.TableBean;
import com.MGenerator.metadata.SettingDetails;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author mohamed.metwally
 */
public class Generator {

    DBStoredProcedureGenerator spGen;
    DBLayerGenerator dbGen;
    ControlLayerGenerator controlGen;
    VisualLayerGenerator visualGen;
    TemplateGenerator templateGen;

    /**
     * Creates a new instance of Generator
     */
    public Generator() {
        // spGen = new DBStoredProcedureGenerator();
        //dbGen = new DBLayerGenerator();
        //controlGen = new ControlLayerGenerator();
        //visualGen = new VisualLayerGenerator();
    }

    public Generator(TableBean tblBean, SettingDetails settingDetails) {
        if (!settingDetails.isIsDefault()) {
            InputStream is = null;
            try {
                templateGen = new TemplateGenerator();

                is = new FileInputStream(settingDetails.getTemplatePath());
                Properties prop = new Properties();
                prop.load(is);
                templateGen.generate(tblBean, settingDetails, prop);
            } catch (IOException ex) {
                //LoggingClass.logInfo(ex.toString());
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        //LoggingClass.logInfo(ex.toString());
                    }
                }
            }
        } else {
            spGen.generate(tblBean, settingDetails);
        }
    }

    public void generate(TableBean tblBean, SettingDetails settingDetails) {
       
        if (!settingDetails.isIsDefault()) {
            InputStream is = null;
            try {
                templateGen = new TemplateGenerator();

                is = new FileInputStream(settingDetails.getTemplatePath());
                Properties prop = new Properties();
                prop.load(is);
                templateGen.generate(tblBean, settingDetails, prop);
            } catch (IOException ex) {
                ex.printStackTrace();
                //LoggingClass.logInfo(ex.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                //LoggingClass.logInfo(ex.toString());
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        //LoggingClass.logInfo(ex.toString());
                    }
                }
            }
        } else {
            spGen.generate(tblBean, settingDetails);
        }
    }

    public void generate(SettingDetails settingDetails) {
        if (!settingDetails.isIsDefault()) {
            InputStream is = null;
            try {
                templateGen = new TemplateGenerator();

                is = new FileInputStream(settingDetails.getTemplatePath());
                Properties prop = new Properties();
                prop.load(is);
                templateGen.generate(settingDetails, prop);
            } catch (IOException ex) {
                ex.printStackTrace();
                //LoggingClass.logInfo(ex.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                //LoggingClass.logInfo(ex.toString());
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        //LoggingClass.logInfo(ex.toString());
                    }
                }
            }
        } else {
            //spGen.generate(tblBean, templatePath, daoPackage, modelsPackage);
        }
    }
}
