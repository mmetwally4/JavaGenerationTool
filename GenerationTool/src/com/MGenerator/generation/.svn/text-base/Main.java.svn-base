/*
 * Main.java
 *
 * Created on 03 September 2007, 12:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.MGenerator.generation;

import com.MGenerator.DataBaseLayer.DBBase;
import com.MGenerator.Frams.GenMain;
import com.MGenerator.Parsers.Parser;
import org.w3c.tools.resources.serialization.SerializationException;

/**
 *
 * @author mohamed.metwally
 */
public class Main {

    /** Creates a new instance of Main */
    public Main() {
    }
    public static DBBase dbBase;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {        

        try {
//            String s = "subsDate";
//            Parser.lowFirstChar(s);
//            Parser.upFirstChar(s);
            final GenMain genTool = new GenMain();
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    genTool.setLocation(50, 70);
                    genTool.setSize(600, 600);
                    genTool.setVisible(true);
                }
            });
        } catch (SerializationException ex) {           
        }
    }
}
