package org.baderlab.st.internal;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class SimpleColumnPresentaiton {//implements CyColumnPresentation {
    
    private final Icon icon;
    
    public SimpleColumnPresentaiton(String iconFile) {
        URL pngFile = getClass().getClassLoader().getResource(iconFile);
        icon = new ImageIcon(pngFile);
    }
    

//    @Override
//    public Icon getNamespaceIcon() {
//        return icon;
//    }
//
//    @Override
//    public String getNamespaceDescription() {
//        return "Just a 'simple' namespace for testing.";
//    }
    
}
