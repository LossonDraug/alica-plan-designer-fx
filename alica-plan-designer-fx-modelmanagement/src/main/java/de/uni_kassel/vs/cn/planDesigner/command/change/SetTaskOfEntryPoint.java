package de.uni_kassel.vs.cn.planDesigner.command.change;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;


public class SetTaskOfEntryPoint extends AbstractCommand {

    private EntryPoint parentOfElement;
    private Task previousTask;

    public SetTaskOfEntryPoint(Task element, EntryPoint parentOfElement) {
        super(element, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
    }

    @Override
    public void doCommand() {
        previousTask = parentOfElement.getTask();
        parentOfElement.setTask((Task) getElementToEdit());
    }

    @Override
    public void undoCommand() {
        parentOfElement.setTask(previousTask);
    }

    @Override
    public String getCommandString() {
        return "Set Task of EntryPoint " + parentOfElement.getId() + " from " + previousTask.getName()
                + " to " + getElementToEdit().getName();
    }
}