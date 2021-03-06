package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 01.12.16.
 */
public class DeleteAbstractPlansFromState extends AbstractCommand<AbstractPlan> {

    private final State parentStateOfElement;

    public DeleteAbstractPlansFromState(AbstractPlan element, State parentStateOfElement) {
        super(element, parentStateOfElement.getInPlan());
        this.parentStateOfElement = parentStateOfElement;
    }

    @Override
    public void doCommand() {
        parentStateOfElement.getPlans().remove(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentStateOfElement.getPlans().add(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Delete Object " + getElementToEdit().getName() + " from State "
                + parentStateOfElement.getName() + " in Plan " + parentStateOfElement.getInPlan().getName();
    }
}
