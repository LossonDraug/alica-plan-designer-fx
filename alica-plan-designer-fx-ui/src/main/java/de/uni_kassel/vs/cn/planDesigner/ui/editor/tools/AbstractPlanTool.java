package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddAbstractPlanToState;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddStateInPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangePosition;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.StateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 17.03.17.
 */
public class AbstractPlanTool extends AbstractTool<AbstractPlan> {

    private AbstractPlan activeElement;
    private Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();

    private Node visualRepresentation;

    public AbstractPlanTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public AbstractPlan createNewObject() {
        return null;
    }

    @Override
    public void draw() {
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().setupPlanVisualisation();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    visualRepresentation.setLayoutX(event.getX());
                    visualRepresentation.setLayoutY(event.getY());
                    System.out.println("X: " + event.getX() + " Y: " + event.getY());
                    event.consume();
                }
            });
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getGestureSource() != workbench && visualRepresentation == null) {
                        ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (workbench.getSelectionModel().getSelectedItem() != null) {
                        ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().remove(visualRepresentation);
                    }
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() != null && ((Node)event.getTarget()).getParent() instanceof StateContainer) {
                        StateContainer stateContainer = (StateContainer) ((Node) event.getTarget()).getParent();
                        ((PlanTab) workbench.getSelectionModel().getSelectedItem()).getPlanEditorPane().getChildren().remove(visualRepresentation);
                        AddAbstractPlanToState command = new AddAbstractPlanToState(activeElement, stateContainer.getContainedElement());
                        MainController.getInstance()
                                .getCommandStack()
                                .storeAndExecute(command);
                        draw();
                    }

                    endPhase();
                }
            });
        }
        return eventHandlerMap;
    }

    public AbstractPlan getActiveElement() {
        return activeElement;
    }

    public void setActiveElement(AbstractPlan activeElement) {
        this.activeElement = activeElement;
    }

    public void setVisualRepresentation(Node visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }
}