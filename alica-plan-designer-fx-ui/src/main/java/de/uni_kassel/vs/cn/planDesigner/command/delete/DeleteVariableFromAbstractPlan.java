package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

/**
 * Created by marci on 26.02.17.
 */
public class DeleteVariableFromAbstractPlan extends AbstractCommand<Variable> {

    private final PlanElement parentOfElement;

    public DeleteVariableFromAbstractPlan(Variable toDelete, PlanElement parentOfElement) {
        super(toDelete, parentOfElement);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        if (parentOfElement instanceof Plan) {
            ((Plan)parentOfElement).getVars().remove(getElementToEdit());
        } else {
            ((Behaviour)parentOfElement).getVars().remove(getElementToEdit());
        }
    }

    @Override
    public void undoCommand() {
        if (parentOfElement instanceof Plan) {
            ((Plan)parentOfElement).getVars().add(getElementToEdit());
        } else {
            ((Behaviour)parentOfElement).getVars().add(getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return "Remove Variable " + getElementToEdit().getName() + " from " + parentOfElement.getName();
    }
}
