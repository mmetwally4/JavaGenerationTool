/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MGenerator.DataBaseLayer;

import java.util.ArrayList;

/**
 *
 * @author mmetwally
 */
public class SpecialTypesHandling {
    private String type;
    private String typevalue;
    private ArrayList containKeys = new ArrayList();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypevalue() {
        return typevalue;
    }

    public void setTypevalue(String typevalue) {
        this.typevalue = typevalue;
    }

    public ArrayList getContainKeys() {
        return containKeys;
    }

    public void setContainKeys(ArrayList containKeys) {
        this.containKeys = containKeys;
    }
    
    
}
