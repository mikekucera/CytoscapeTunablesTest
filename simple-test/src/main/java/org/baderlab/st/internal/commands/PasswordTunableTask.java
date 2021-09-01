package org.baderlab.st.internal.commands;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class PasswordTunableTask extends AbstractTask {

    
    @Tunable(description="Enter Password", params="password=true")
    public String password;
    
    @Tunable(description="Regular Input")
    public String regular;
    
    @Tunable(description="Enter Password", params="password=true")
    public String password2 = "asdf";
    
    @Tunable(description="Regular Input", params="password=false")
    public String regular2 = "asdf";
    
    
    public PasswordTunableTask() {
    }
    
    @Override
    public void run(TaskMonitor tm) {
        System.out.println("The password is: '" + password + "'");
        System.out.println("The regular  is: '" + regular + "'");
    }

}
