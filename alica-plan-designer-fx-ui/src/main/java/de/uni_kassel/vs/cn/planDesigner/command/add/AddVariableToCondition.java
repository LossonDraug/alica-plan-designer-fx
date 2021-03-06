package de.uni_kassel.vs.cn.planDesigner.command.add;

import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;

import static de.uni_kassel.vs.cn.generator.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 26.02.17.
 */
public class AddVariableToCondition extends AbstractCommand<Variable> {

    private final Condition parentOfElement;

    public AddVariableToCondition(Condition parentOfElement, Plan affectedPlan) {
        super(getAlicaFactory().createVariable(), affectedPlan);
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        parentOfElement.getVars().add(getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.getVars().remove(getElementToEdit());
    }

    @Override
    public String getCommandString() {
        return "Add new variable to" + parentOfElement.getName();
    }


}
