/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitsim;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Swapnesh
 */
public class BITSimTxtFilter extends FileFilter{

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()) {
            return true;
        }
        
        String s = f.getName();
        return s.endsWith(".txt")||s.endsWith(".TXT");  
    }

    @Override
    public String getDescription() {
        return "*.txt,*.TXT";
    }
    
}
