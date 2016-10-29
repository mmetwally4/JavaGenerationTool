/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package com.MGenerator.metadata;

import com.MGenerator.DataBaseLayer.TableBean;
import java.util.ArrayList;

/**
 *
 * @author m00309430
 */
public class SettingDetails {

    private ArrayList<TableBean> tbls;
    private String templatePath;
    private String daoPackage;
    private String modelsPackage;
    private String outPath;
    private boolean isDefault;
    private String contextPath;

    /**
     * @return the tbls
     */
    public ArrayList<TableBean> getTbls() {
        return tbls;
    }

    /**
     * @param tbls the tbls to set
     */
    public void setTbls(ArrayList<TableBean> tbls) {
        this.tbls = tbls;
    }

    /**
     * @return the templatePath
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * @param templatePath the templatePath to set
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * @return the daoPackage
     */
    public String getDaoPackage() {
        return daoPackage;
    }

    /**
     * @param daoPackage the daoPackage to set
     */
    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    /**
     * @return the modelsPackage
     */
    public String getModelsPackage() {
        return modelsPackage;
    }

    /**
     * @param modelsPackage the modelsPackage to set
     */
    public void setModelsPackage(String modelsPackage) {
        this.modelsPackage = modelsPackage;
    }

    /**
     * @return the outPath
     */
    public String getOutPath() {
        return outPath;
    }

    /**
     * @param outPath the outPath to set
     */
    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    /**
     * @return the isDefault
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the contextPath
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * @param contextPath the contextPath to set
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    
    
}
