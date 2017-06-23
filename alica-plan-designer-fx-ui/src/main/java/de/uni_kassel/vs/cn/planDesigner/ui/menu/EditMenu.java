package de.uni_kassel.vs.cn.planDesigner.ui.menu;

import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.*;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.EntryPointImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.StateImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.SynchronisationImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.TransitionImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.controller.UsagesWindowController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.*;
import de.uni_kassel.vs.cn.planDesigner.ui.repo.RepositoryTab;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by marci on 17.03.17.
 */
public class EditMenu extends Menu {

    private MenuItem deleteElementItem;
    private MenuItem undoItem;
    private MenuItem redoItem;
    private final MenuItem configItem;

    public EditMenu(CommandStack commandStack, EditorTabPane editorTabPane) {
        super(I18NRepo.getString("label.menu.edit"));
        deleteElementItem = new MenuItem(I18NRepo.getString("label.menu.edit.delete"));
        undoItem = new MenuItem(I18NRepo.getString("label.menu.edit.undo"));
        redoItem = new MenuItem(I18NRepo.getString("label.menu.edit.redo"));
        configItem = new MenuItem(I18NRepo.getString("label.menu.edit.config"));

        commandStack.addObserver((a,b) -> {
            if (((CommandStack)a).getUndoStack().isEmpty()) {
                undoItem.setDisable(true);
            } else {
                undoItem.setDisable(false);
            }
        });

        commandStack.addObserver((a,b) -> {
            if (((CommandStack)a).getRedoStack().isEmpty()) {
                redoItem.setDisable(true);
            } else {
                redoItem.setDisable(false);
            }
        });

        redoItem.setDisable(false);
        undoItem.setDisable(false);

        deleteElementItem.setOnAction(event -> delete(commandStack, editorTabPane));
        redoItem.setOnAction(event -> redo(commandStack, editorTabPane));
        undoItem.setOnAction(event -> undo(commandStack, editorTabPane));
        configItem.setOnAction(event -> openConfigMenu());

        getItems().addAll(undoItem, redoItem, deleteElementItem, configItem);
        defineAccelerator();
    }

    private void undo(CommandStack commandStack, EditorTabPane editorTabPane) {
        commandStack.undo();
        Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof PlanTab) {
            ((PlanTab)selectedItem).getPlanEditorPane().setupPlanVisualisation();
            ((PlanTab)selectedItem).getConditionHBox().setupConditionVisualisation();
        } else if (selectedItem instanceof PlanTypeTab) {
            ((PlanTypeTab)selectedItem).refresh();
        } else if (selectedItem instanceof TaskRepositoryTab) {
            ((TaskRepositoryTab)selectedItem).createContentView();
        }
    }

    private void redo(CommandStack commandStack, EditorTabPane editorTabPane) {
        commandStack.redo();
        Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof PlanTab) {
            ((PlanTab)selectedItem).getPlanEditorPane().setupPlanVisualisation();
            ((PlanTab)selectedItem).getConditionHBox().setupConditionVisualisation();
        } else if (selectedItem instanceof PlanTypeTab) {
            ((PlanTypeTab)selectedItem).refresh();
        } else if (selectedItem instanceof TaskRepositoryTab) {
            ((TaskRepositoryTab) selectedItem).createContentView();
        }
    }

    private void openConfigMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("configurationWindow.fxml"));

        try {
            Parent window = fxmlLoader.load();
            //ConfigurationWindowController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(I18NRepo.getString("label.config.title"));
            stage.setScene(new Scene(window));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delete(CommandStack commandStack, EditorTabPane editorTabPane) {
        // TODO refactor
        if (editorTabPane.focusedProperty().get()) {
            Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            if (selectedItem instanceof PlanTab) {
                PlanTab planTab = (PlanTab) selectedItem;
                PlanElement selectedPlanElement = planTab.getSelectedPlanElement().getValue().getKey();

                if(selectedPlanElement != null) {
                    deletePlanElement(commandStack, planTab, selectedPlanElement);
                }
            } else if (selectedItem instanceof TaskRepositoryTab) {
                TaskRepositoryTab taskRepositoryTab = (TaskRepositoryTab) selectedItem;
                if (taskRepositoryTab.getSelectedPlanElement() != null) {
                    commandStack.storeAndExecute(new DeleteTaskFromRepository(taskRepositoryTab.getEditable(), (Task) taskRepositoryTab.getSelectedPlanElement().getValue().getKey()));
                    taskRepositoryTab.createContentView();
                }
            }
        } else {
            boolean isRepoFocused = MainController.getInstance().getRepositoryTabPane().getTabs().stream()
                    .anyMatch(e -> ((RepositoryTab) e).getContentsListView().focusedProperty().get());
            if (isRepoFocused) {
                // TODO make delete task possible here too
                AbstractPlan selectedAbstractPlan = (AbstractPlan)
                        ((RepositoryTab<PlanElement>) MainController.getInstance()
                                .getRepositoryTabPane()
                                .getSelectionModel()
                                .getSelectedItem())
                                .getContentsListView()
                                .getSelectionModel().getSelectedItem().getObject();
                editorTabPane.getTabs()
                        .stream()
                        .filter(e -> ((AbstractEditorTab<PlanElement>)e).getEditable().equals(selectedAbstractPlan))
                        .forEach(e -> editorTabPane.getTabs().remove(e));
                commandStack.storeAndExecute(new DeleteAbstractPlan(selectedAbstractPlan));
                MainController.getInstance().getRepositoryTabPane().init();
                return;
            }

            if (MainController.getInstance().getFileTreeView().focusedProperty().get()) {

                DeleteFileMenuItem deleteFileMenuItem = new DeleteFileMenuItem(MainController.getInstance().getFileTreeView()
                        .getSelectionModel()
                        .getSelectedItem()
                        .getValue().unwrap());
                deleteFileMenuItem.setCommandStack(commandStack);
                deleteFileMenuItem.deleteFile();
                return;

            }

            if (editorTabPane.getSelectionModel().getSelectedItem() instanceof TaskRepositoryTab) {
                TaskRepositoryTab repositoryTab = (TaskRepositoryTab) editorTabPane.getSelectionModel().getSelectedItem();
                Task taskToBeDeleted = repositoryTab
                        .getTaskListView()
                        .getSelectionModel()
                        .getSelectedItem();
                if (EMFModelUtils.getUsages(taskToBeDeleted).size() > 0) {
                    FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
                    try {
                        Parent infoWindow = fxmlLoader.load();
                        UsagesWindowController controller = fxmlLoader.getController();
                        controller.createReferencesList(EMFModelUtils.getUsages(taskToBeDeleted));
                        Stage stage = new Stage();
                        stage.setTitle(I18NRepo.getString("label.usage.nodelete"));
                        stage.setScene(new Scene(infoWindow));
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(PlanDesigner.getPrimaryStage());
                        stage.showAndWait();
                    } catch (IOException ignored) {
                    }
                } else {
                    commandStack.storeAndExecute(new DeleteTaskFromRepository(repositoryTab.getEditable(), taskToBeDeleted));
                }
            }
        }
    }

    private void deletePlanElement(CommandStack commandStack, PlanTab planTab, PlanElement selectedPlanElement) {
        if(selectedPlanElement instanceof StateImpl) {
            commandStack.storeAndExecute(new DeleteStateInPlan((State) selectedPlanElement,
                    planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
        } else if (selectedPlanElement instanceof TransitionImpl) {
            commandStack.storeAndExecute(new DeleteTransitionInPlan((Transition) selectedPlanElement,
                    planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
        } else if (selectedPlanElement instanceof EntryPointImpl) {
            commandStack.storeAndExecute(new DeleteEntryPointInPlan((EntryPoint) selectedPlanElement,
                    planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
        } else if (selectedPlanElement instanceof AbstractPlan && planTab.getSelectedPlanElement().getValue().getValue() != null) {
            State state = (State) planTab.getSelectedPlanElement().getValue().getValue().getContainedElement();
            commandStack.storeAndExecute(new DeleteAbstractPlansFromState((AbstractPlan) selectedPlanElement, state));
        } else if(selectedPlanElement instanceof SynchronisationImpl) {
            commandStack.storeAndExecute(new DeleteSynchronisationFromPlan((Synchronisation) selectedPlanElement,
                    planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
        } else if (selectedPlanElement instanceof Condition) {
            Condition condition = (Condition) planTab.getSelectedPlanElement().getValue().getKey();
            commandStack.storeAndExecute(new DeleteConditionFromPlan(planTab.getPlanEditorPane().getPlanModelVisualisationObject().getPlan(), condition));
        }
        planTab.getPlanEditorPane().setupPlanVisualisation();
        planTab.getConditionHBox().setupConditionVisualisation();
    }

    private void defineAccelerator() {
        this.deleteElementItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        this.undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        this.redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
    }
}
