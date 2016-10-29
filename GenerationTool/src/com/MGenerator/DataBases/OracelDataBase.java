/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MGenerator.DataBases;

import java.util.ArrayList;

/**
 *
 * @author mmetwally
 */
public class OracelDataBase implements DataBase{
   
    public String getSp(ArrayList cols,String tblName){
        String spStr = "";
        return spStr;
    }
    
    public String initSp(String tblName){
        String spStr = "";
        spStr = "CREATE OR REPLACE PACKAGE" + tblName + " AS";
        spStr += "\nTYPE T_CURSOR IS REF CURSOR;";
        return spStr;        
    }
        
    public String insertSp(ArrayList cols){
        String spStr = "";        
        return spStr;
    }
    
    public String updateSp(ArrayList cols){
        String spStr = "";
        return spStr;                
    }
    
    public String FindSp(ArrayList cols){
        String spStr = "";
        return spStr;                
    }
    
    public String getAllSp(ArrayList cols){
        String spStr = "";
        return spStr;                
    }
    
    public String deleteSp(ArrayList cols){
        String spStr = "";
        return spStr;                
    }
    
    public String closeSp(){
        String spStr = "";
        return spStr;        
    }

}
