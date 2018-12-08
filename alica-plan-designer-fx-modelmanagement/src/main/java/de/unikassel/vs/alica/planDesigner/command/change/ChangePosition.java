package de.unikassel.vs.alica.planDesigner.command.change;

import de.unikassel.vs.alica.planDesigner.alicamodel.PlanElement;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.events.UiExtensionModelEvent;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.PmlUiExtension;

public class ChangePosition extends AbstractCommand {

    protected PlanElement planElement;
    protected PmlUiExtension uiElement;
    protected int newX;
    protected int newY;

    protected int oldX;
    protected int oldY;

    public ChangePosition(ModelManager modelManager, PmlUiExtension uiElement, PlanElement planElement, int newX, int newY) {
        super(modelManager);
        this.uiElement = uiElement;
        this.planElement = planElement;
        this.newX = newX;
        this.newY = newY;

        // save old position for undo
        oldX = uiElement.getXPos();
        oldY = uiElement.getYPos();
    }

    @Override
    public void doCommand() {
        uiElement.setXPos(newX);
        uiElement.setYPos(newY);

        UiExtensionModelEvent event = createUiExtensiomModelEvent(newX, newY);
        fireUiExtensionModelEvent(event);
    }

    @Override
    public void undoCommand() {
        uiElement.setXPos(oldX);
        uiElement.setYPos(oldY);

        UiExtensionModelEvent event = createUiExtensiomModelEvent(oldX, oldY);
        fireUiExtensionModelEvent(event);
    }

    private void fireUiExtensionModelEvent(UiExtensionModelEvent event){
        modelManager.fireUiExtensionModelEvent(event);
    }

    private UiExtensionModelEvent createUiExtensiomModelEvent(int x, int y){
        UiExtensionModelEvent event = new UiExtensionModelEvent(ModelEventType.ELEMENT_ATTRIBUTE_CHANGED, planElement, null);
        PmlUiExtension extension = new PmlUiExtension();
        extension.setXPos(x);
        extension.setYPos(y);
        event.setExtension(extension);

        return event;
    }
}