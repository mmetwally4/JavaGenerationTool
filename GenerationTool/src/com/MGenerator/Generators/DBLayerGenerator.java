/*
 * DBLayerGenerator.java
 *
 * Created on 06 September 2007, 18:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.MGenerator.Generators;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mohamed.metwally
 */
public class DBLayerGenerator extends Generator {
    
    /** Creates a new instance of DBLayerGenerator */
    public DBLayerGenerator() {        
    }
    
    public void generate(ArrayList cols,String path,String tblName){
        FileOutputStream fos = null;
        File file;
        FileWriter fWrite = null;
        BufferedWriter bw = null;
        try {
            file = new File(path + "/");
            file.createNewFile();            
            fWrite = new FileWriter(file);
            bw = new BufferedWriter(fWrite);
            
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(DBLayerGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(DBLayerGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try{
                bw.close();
            }catch(IOException exp){
                
            }
        }
    }        
}
