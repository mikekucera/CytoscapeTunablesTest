package org.baderlab.st.internal.actions;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.annotations.Annotation;
import org.cytoscape.view.presentation.annotations.AnnotationFactory;
import org.cytoscape.view.presentation.annotations.AnnotationManager;
import org.cytoscape.view.presentation.annotations.ShapeAnnotation;
import org.cytoscape.view.presentation.annotations.ShapeAnnotation.ShapeType;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class TestAnnotationGradientPaint extends AbstractCyAction {

    public static enum Gradient {
        LINEAR, RADIAL
    }
    
    @Inject private CyApplicationManager appManager;
    @Inject private AnnotationManager annotationManager;
    @Inject private AnnotationFactory<ShapeAnnotation> shapeFactory;
    
    private Gradient gradient = Gradient.LINEAR;
    
    
    @Inject
    public TestAnnotationGradientPaint(CyApplicationManager applicationManager) {
        super("Create Annotation", applicationManager, null, null);
    }
    
    public TestAnnotationGradientPaint setGradient(Gradient gradient) {
        this.gradient = gradient;
        setName("Create Annotation: " + gradient);
        return this;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetworkView networkView = appManager.getCurrentNetworkView();
        
        Map<String,String> shapeArgs = getArgMap();
        ShapeAnnotation shape = shapeFactory.createAnnotation(ShapeAnnotation.class, networkView, shapeArgs);
        
        Paint p = gradient == Gradient.RADIAL ? getRadialGradientPaint() : getLinearGradientPaint();
        shape.setFillColor(p);        
        
        annotationManager.addAnnotation(shape);
    }

    private LinearGradientPaint getLinearGradientPaint() {
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(1, 0);
        float[] fractions = {0, 1};
        Color[] colors = {Color.WHITE, Color.BLACK};
        return new LinearGradientPaint(start, end, fractions, colors);   
    }
    
    private RadialGradientPaint getRadialGradientPaint() {
        Point2D center = new Point2D.Float(.5f, .5f);
        float radius = .5f;
        float[] fractions = {0, 1};
        Color[] colors = {Color.WHITE, Color.BLACK};
        return new RadialGradientPaint(center, radius, fractions, colors);
    }
    
    public Map<String,String> getArgMap() {
        Map<String,String> argMap = new HashMap<>();
        argMap.put(Annotation.X, String.valueOf(100));
        argMap.put(Annotation.Y, String.valueOf(100));
        argMap.put(Annotation.CANVAS, Annotation.FOREGROUND);
        argMap.put(Annotation.NAME, "Test: " + name);
        argMap.put(ShapeAnnotation.WIDTH, String.valueOf(200));
        argMap.put(ShapeAnnotation.HEIGHT, String.valueOf(200));
        argMap.put(ShapeAnnotation.SHAPETYPE, ShapeType.RECTANGLE.toString());
        return argMap;
    }
}
