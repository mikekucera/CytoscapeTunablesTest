package org.baderlab.tt.internal.action;

import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.baderlab.tt.internal.tunables.YesNoMaybe;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.swing.AbstractGUITunableHandler;

public class YesNoMaybeHandler extends AbstractGUITunableHandler {

    private ButtonGroup buttonGroup;
    
    public YesNoMaybeHandler(Field field, Object instance, Tunable tunable) {
        super(field, instance, tunable);
        initGUI();
    }

    public YesNoMaybeHandler(Method getter, Method setter, Object instance, Tunable tunable) {
        super(getter, setter, instance, tunable);
        initGUI();
    }

    
    
    @Override
    public void handle() {
        YesNoMaybe value = YesNoMaybe.valueOf(buttonGroup.getSelection().getActionCommand());
        
        try {
            setValue(value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // this should not happen
            e.printStackTrace();
        }
    }
    
    
    private void initGUI() {
        JRadioButton yesButton = new JRadioButton("Yes");
        yesButton.setActionCommand(YesNoMaybe.YES.toString());
        
        JRadioButton noButton = new JRadioButton("No");
        noButton.setActionCommand(YesNoMaybe.NO.toString());
        
        JRadioButton maybeButton = new JRadioButton("Maybe");
        maybeButton.setActionCommand(YesNoMaybe.MAYBE.toString());
        
        buttonGroup = new ButtonGroup();
        buttonGroup.add(yesButton);
        buttonGroup.add(noButton);
        buttonGroup.add(maybeButton);
        
        yesButton.setSelected(true);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(maybeButton);
        
        JLabel label = new JLabel(getDescription());
        
        this.panel = new TunableFieldPanel(label, buttonPanel);
    }

}
