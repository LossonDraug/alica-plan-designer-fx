package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;

/**
 * Created by marci on 08.02.17.
 */
public class DeleteEntryPointInPlan extends AbstractCommand<EntryPoint> {

    private State associatedState;
    private final PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension pmlUiExtension;

    public DeleteEntryPointInPlan(EntryPoint element, PlanModelVisualisationObject parentOfElement) {
        super(element, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
        this.pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(getElementToEdit());
        this.associatedState = getElementToEdit().getState();
    }

    @Override
    public void doCommand() {
        parentOfElement.getPlan().getEntryPoints().remove(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(null);
        }
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getEntryPoints().add(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().put(getElementToEdit(), pmlUiExtension);
        if (associatedState != null) {
            associatedState.setEntryPoint(getElementToEdit());
        }
    }

    @Override
    public String getCommandString() {
        return null;
    }
}
