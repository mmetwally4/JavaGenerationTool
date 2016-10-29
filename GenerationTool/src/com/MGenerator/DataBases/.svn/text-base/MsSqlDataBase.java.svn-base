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
public class MsSqlDataBase implements DataBase{
   
    String tblName;
    public String getSp(ArrayList cols,String tblName){
        this.tblName = tblName;                
        String spStr = "";
        
        return spStr;
    }
    
    public String initSp(String tblName){
        this.tblName = tblName; 
        String spStr = "";
        spStr = "USE " + tblName + "\n";
        spStr += "GO\n";
        spStr += "SET QUOTED_IDENTIFIER ON \n";
        spStr += "GO\n";
        spStr += "SET ANSI_NULLS OFF \n";
        spStr += "GO\n";        
        return spStr;
    }
        
    public String insertSp(ArrayList cols){
        String spStr = "CREATE PROCEDURE insert" + this.tblName;
        for(int i = 0; i < cols.size(); i++){
            
        }
        spStr += " AS";
        return spStr;
    }
    
    public String updateSp(ArrayList cols){
        String spStr = "CREATE PROCEDURE update" + this.tblName;
        spStr += " AS";
        return spStr;                
    }
    
    public String FindSp(ArrayList cols){
        String spStr = "CREATE PROCEDURE Find" + this.tblName;
        spStr += " AS";
        return spStr;                
    }
    
    public String getAllSp(ArrayList cols){
        String spStr = "CREATE PROCEDURE getAll" + this.tblName;
        spStr += " AS";
        return spStr;                
    }
    
    public String deleteSp(ArrayList cols){
        String spStr = "CREATE PROCEDURE delete" + this.tblName;
        spStr += " AS";
        return spStr;
    }
    
    public String closeSp(){
        String spStr = "";
        return spStr;        
    }
}
