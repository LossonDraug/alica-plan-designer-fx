package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Synchronisation;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 22.03.17.
 */
public class AddTransitionToSynchronisation extends AbstractCommand<Synchronisation> {

    private Transition transitionToAdd;

    public AddTransitionToSynchronisation(Synchronisation element, Transition transitionToAdd, PlanElement affectedPlan) {
        super(element, affectedPlan);
        this.transitionToAdd = transitionToAdd;
    }

    @Override
    public void doCommand() {
        getElementToEdit().getSynchedTransitions().add(transitionToAdd);
    }

    @Override
    public void undoCommand() {
        getElementToEdit().getSynchedTransitions().remove(transitionToAdd);
    }

    @Override
    public String getCommandString() {
        return "Add Transition to  Synchronisation";
    }
}
