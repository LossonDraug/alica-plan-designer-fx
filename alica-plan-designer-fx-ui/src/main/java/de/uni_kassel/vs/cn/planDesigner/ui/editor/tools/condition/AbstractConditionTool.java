package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition;

import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddConditionToPlan;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 01.03.17.
 */
public abstract class AbstractConditionTool<T extends Condition> extends AbstractTool<T> {

    protected Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    protected Node visualRepresentation;
    private ImageView visualRepresentationForValidRegions;

    public AbstractConditionTool(TabPane workbench) {
        super(workbench);
        visualRepresentationForValidRegions = new ImageView(new AlicaIcon(createNewObject().getClass().getSimpleName()));
    }

    @Override
    public void draw() {
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getConditionHBox().setupConditionVisualisation();
    }

    @Override
    protected Node getWorkbench() {
        return ((PlanTab)(workbench.getSelectionModel().getSelectedItem())).getConditionHBox();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, (EventHandler<MouseDragEvent>) event -> {
                if (event.getTarget() instanceof ConditionHBox && visualRepresentation == null) {
                    visualRepresentation = visualRepresentationForValidRegions;
                    ((ConditionHBox)event.getTarget()).getChildren().add(visualRepresentation);
                }
                event.consume();
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, (EventHandler<MouseDragEvent>) event -> {
                if (visualRepresentation != null) {
                    ((ConditionHBox)event.getSource()).getChildren().remove(visualRepresentation);
                    visualRepresentation = null;
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_OVER, (EventHandler<MouseDragEvent>) event -> {
                Scene currentScene = getWorkbench().getScene();
                if (event.getTarget() instanceof ConditionHBox == false) {
                    currentScene.setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                } else if(currentScene.getCursor().equals(PlanDesigner.FORBIDDEN_CURSOR)) {
                    currentScene.setCursor(toolCursor);
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, (EventHandler<MouseDragEvent>) event -> {
                if (event.getTarget() instanceof ConditionHBox) {
                    ((ConditionHBox)event.getTarget()).getChildren().remove(visualRepresentation);
                    Condition newCondition = createNewObject();
                    newCondition.setPluginName(PluginManager.getInstance().getDefaultPlugin().getName());
                    if (newCondition instanceof PostCondition == false) {
                        AddConditionToPlan command = new AddConditionToPlan(((PlanTab)workbench.getSelectionModel().getSelectedItem()).getEditable(),
                                newCondition);
                        MainController.getInstance()
                                .getCommandStack()
                                .storeAndExecute(command);
                    }
                }
                endPhase();
            });
        }
        return eventHandlerMap;
    }
}
