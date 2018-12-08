package de.unikassel.vs.alica.planDesigner.view.filebrowser;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.menu.FileTreeViewContextMenu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public final class FileTreeView extends TreeView<File> {

    private MainWindowController controller;

    private boolean wasDragged;
    private FileTreeItem draggedItem;
    private Cursor originalCursor;
    private String startFolder;
    private VirtualDirectoryTreeItem virtualDirectoryTreeItem;
    private String plansPath;
    private String taskPath;
    private String rolesPath;
    private FileTreeItem plansFileTreeItem;
    private FileTreeItem rolesFileTreeItem;
    private FileTreeItem tasksFileTreeItem;

    public FileTreeView() {
        super(new VirtualDirectoryTreeItem());
        virtualDirectoryTreeItem = (VirtualDirectoryTreeItem) getRoot();

        // Setup Drag support
        addEventHandler(MouseDragEvent.DRAG_DETECTED, new MouseDraggedEventHandler());
        addEventFilter(MouseEvent.MOUSE_RELEASED, new MouseReleasedEventHandler());

        this.setShowRoot(false);
        this.setContextMenu(new FileTreeViewContextMenu());
        this.setEditable(true);

        setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
            @Override
            public TreeCell<File> call(TreeView<File> param) {
                TreeCell<File> fileWrapperTreeCell = new FileTreeCell(controller);
                fileWrapperTreeCell.setContextMenu(new FileTreeViewContextMenu());
                fileWrapperTreeCell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            ((FileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                    .setHintFile(fileWrapperTreeCell.getTreeItem().getValue());
                            ((FileTreeViewContextMenu) fileWrapperTreeCell.getContextMenu())
                                    .setTreeCell(fileWrapperTreeCell);
                        }
                    }
                });

                if (param.getEditingItem() != null) {
                    fileWrapperTreeCell.setText(param.getEditingItem().getValue().getName());
                    fileWrapperTreeCell.setGraphic(param.getEditingItem().getGraphic());
                }
                return fileWrapperTreeCell;
            }
        });

        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            controller.setDeleteDisabled(newValue == null);
        });
    }

    /**
     * Setter for the {@link MainWindowController}
     *
     * @param controller
     */
    public void setController(MainWindowController controller) {
        this.controller = controller;
    }

    /**
     * Inserts a new {@link ViewModelElement} at its corresponding place in the {@link FileTreeView}
     *
     * @param viewModelElement
     */
    public void addViewModelElement(ViewModelElement viewModelElement) {
        FileTreeItem topLevelFolder = findTopLevelFolder(viewModelElement);
        if (topLevelFolder == null) {
            return;
        }

        FileTreeItem folder = findFolder(viewModelElement, topLevelFolder, 0);
//        boolean deleted = false;
        if (folder != null) {
            // TODO: maybe unnecessary if complete view model elements are used.
//            if (folderContainsViewModelElement(viewModelElement, folder)) {
//                removeFromFolder(viewModelElement, folder);
//                deleted = true;
//            }
            FileTreeItem newItem = new FileTreeItem(createFile(viewModelElement), new ImageView(getImage(viewModelElement.getType
                    ())), viewModelElement);
            folder.getChildren().add(newItem);
            folder.getChildren().sort(Comparator.comparing(o -> o.getValue().toURI().toString()));
//            if (deleted) {
//                this.getSelectionModel().select(newItem);
//            }
        } else {
            throw new RuntimeException("Destination folder for PlanElement " + viewModelElement.getName() + " does not exist!");
        }
    }

    private boolean folderContainsViewModelElement(ViewModelElement viewModelElement, FileTreeItem folder) {
        for (TreeItem item : folder.getChildren()) {
            FileTreeItem fileTreeItem = (FileTreeItem) item;
            if (!fileTreeItem.getValue().isDirectory() && fileTreeItem.getViewModelElement().getId() == viewModelElement.getId()) {
                return true;
            }
        }
        return false;
    }

    public void removeViewModelElement(ViewModelElement viewModelElement) {
        FileTreeItem topLevelFolder = findTopLevelFolder(viewModelElement);
        if (topLevelFolder == null) {
            return;
        }
        removeFromFolder(viewModelElement, topLevelFolder);
    }

    public void updateDirectories(Path path) {
        if (path.toString().contains(plansPath)) {
            plansFileTreeItem.updateDirectories();
        } else if (path.toString().contains(rolesPath)) {
            rolesFileTreeItem.updateDirectories();
        } else if (path.toString().contains(taskPath)) {
            tasksFileTreeItem.updateDirectories();
        }
    }

    private void removeFromFolder(ViewModelElement modelElement, FileTreeItem treeItem) {
        for (TreeItem item : treeItem.getChildren()) {
            FileTreeItem itemToDelete = (FileTreeItem) item;
            if (!itemToDelete.getValue().isDirectory()) {
                if (((FileTreeItem) item).getViewModelElement().getId() == modelElement.getId()) {
                    treeItem.getChildren().remove(item);
                    treeItem.getChildren().sort(Comparator.comparing(o -> o.getValue().toURI().toString()));
                    break;
                }
            } else {
                removeFromFolder(modelElement, itemToDelete);
            }
        }
    }

    /**
     * Creates a file by combining to corresponding config path and the information given in a
     * {@link ViewModelElement} (relativeDirectory, name, ending)
     *
     * @param viewModelElement
     * @return
     */
    private File createFile(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.BEHAVIOUR:
                return Paths.get(plansPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".beh").toFile();
            case Types.PLAN:
            case Types.MASTERPLAN:
                return Paths.get(plansPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pml").toFile();
            case Types.PLANTYPE:
                return Paths.get(plansPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".pty").toFile();
            case Types.TASKREPOSITORY:
                return Paths.get(taskPath, viewModelElement.getRelativeDirectory(), viewModelElement.getName() + ".tsk").toFile();
            default:
                System.err.println("FileTreeView: " + viewModelElement.getType() + " not handled!");
                return null;
        }
    }

    /**
     * Finds the fitting top level folder to insert a {@link FileTreeItem}
     *
     * @param viewModelElement
     * @return
     */
    private FileTreeItem findTopLevelFolder(ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.BEHAVIOUR:
            case Types.MASTERPLAN:
            case Types.PLAN:
            case Types.PLANTYPE:
                return plansFileTreeItem;
            case Types.TASKREPOSITORY:
                return tasksFileTreeItem;
            case Types.ROLE:
                return rolesFileTreeItem;
            default:
                System.err.println("FileTreeView: No top level folder for " + viewModelElement.getType() + " available!");
                return null;
        }
    }

    /**
     * Recursively looks for a fitting folder by using the relative path of a {@link ViewModelElement}
     * Returns the found {@link FileTreeItem} representing the folder, else null
     *
     * @param modelElement
     * @param treeItem
     * @param index
     * @return
     */
    private FileTreeItem findFolder(ViewModelElement modelElement, FileTreeItem treeItem, int index) {
        String relativePath = modelElement.getRelativeDirectory();
        String[] folders = relativePath.split(File.pathSeparator);
        if (folders.length == 1 && folders[0].isEmpty()) {
            return treeItem;
        }
        for (Object item : treeItem.getChildren()) {
            FileTreeItem newItem = (FileTreeItem) item;
            if (!(newItem.getValue().toString().endsWith(folders[index]))) {
                continue;
            }
            if (index == folders.length - 1) {
                return newItem;
            }
            return findFolder(modelElement, newItem, index + 1);
        }
        return null;
    }

    /**
     * Gets the fitting optical representation according to the given PlanElement elementType
     *
     * @param type
     * @return
     */

    private Image getImage(String type) {
        switch (type) {
            case Types.BEHAVIOUR:
                return new AlicaIcon("behaviour", AlicaIcon.Size.BIG);
            case Types.MASTERPLAN:
                return new AlicaIcon("masterplan", AlicaIcon.Size.BIG);
            case Types.PLAN:
                return new AlicaIcon("plan", AlicaIcon.Size.BIG);
            case Types.PLANTYPE:
                return new AlicaIcon("plantype", AlicaIcon.Size.BIG);
            case Types.TASKREPOSITORY:
                return new AlicaIcon("tasks", AlicaIcon.Size.BIG);
            case Types.FOLDER:
                return new AlicaIcon("folder", AlicaIcon.Size.BIG);
            default:
                System.err.println("FileTreeView: No image available for " + type + "!");
                return null;
        }
    }


    /**
     * Sets the PlansPath and adds a new {@link FileTreeItem} as top level folder
     * .pml, .pty and .beh files are added to this folder
     *
     * @param plansPath
     */
    public void setupPlansPath(String plansPath) {
        this.plansPath = plansPath;
        plansFileTreeItem = virtualDirectoryTreeItem.addTopLevelFolder(plansPath);
    }

    /**
     * Sets the TaskPath and adds a new {@link FileTreeItem} as top level folder
     * .tsk files are added to this folder
     *
     * @param taskPath
     */
    public void setupTaskPath(String taskPath) {
        this.taskPath = taskPath;
        tasksFileTreeItem = virtualDirectoryTreeItem.addTopLevelFolder(taskPath);
    }

    /**
     * Sets the PlansPath and adds a new {@link FileTreeItem} as top level folder
     * .cdefset, .rdefset, .graph and .rset files are added to this folder
     *
     * @param rolesPath
     */
    public void setupRolesPath(String rolesPath) {
        this.rolesPath = rolesPath;
        rolesFileTreeItem = virtualDirectoryTreeItem.addTopLevelFolder(rolesPath);
    }

    public GuiModificationEvent handleDelete() {
        //TODO: rework
        return null;
//        if (focusedProperty().get()) {
//            DeleteFileMenuItem deleteFileMenuItem = new DeleteFileMenuItem(getSelectionModel()
//                    .getSelectedItem()
//                    .getValue());
//            deleteFileMenuItem.deleteFile();
//            return null;
//        }
    }

    public class MouseDraggedEventHandler implements EventHandler<MouseEvent> {
            @Override
            public void handle(MouseEvent event) {
                originalCursor = FileTreeView.this.getCursor();
                Node node = ((Node) event.getTarget()).getParent();
                if (!(node instanceof FileTreeCell)) {
                    event.consume();
                    return;
                }
                System.out.println("Source: " + event.getSource() + " Target: " + event.getTarget());
                // TODO: Fix in case of Folder
                draggedItem = (FileTreeItem) ((FileTreeCell) node).getTreeItem();
                startFolder = draggedItem.getValue().getAbsolutePath();
                startFolder = startFolder.substring(0, startFolder.lastIndexOf(File.separator));
                switch (draggedItem.getViewModelElement().getType()) {
                    case Types.BEHAVIOUR:
                        FileTreeView.this.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.behaviour));
                        break;
                    case Types.PLAN:
                        FileTreeView.this.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.plan));
                        break;
                    case Types.MASTERPLAN:

                        FileTreeView.this.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.masterplan));
                        break;
                    case Types.PLANTYPE:
                        FileTreeView.this.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.plantype));
                        break;
                    case Types.TASKREPOSITORY:
                        FileTreeView.this.getScene().setCursor(new AlicaCursor(AlicaCursor.Type.tasks));
                        break;
                    default:
                        System.err.println("FileTreeView: " + draggedItem.getViewModelElement().getType() + " not handled!");
                }
                wasDragged = true;
                event.consume();
            }
    }

    private class MouseReleasedEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            FileTreeView.this.getScene().setCursor(originalCursor);
            if (!wasDragged) {
                e.consume();
                return;
            }

            wasDragged = false;

            if (e.getPickResult() == null) {
                e.consume();
                return;
            }

            FileTreeCell treeCell = null;
            if (e.getPickResult().getIntersectedNode().getParent() instanceof Group) {
                for (Node child : e.getPickResult().getIntersectedNode().getParent().getChildrenUnmodifiable()) {
                    if (child.getBoundsInParent().contains(e.getX(), e.getY(), e.getZ())) {
                        treeCell = (FileTreeCell) child;
                        break;
                    }
                }
            } else {
                treeCell = (FileTreeCell) e.getPickResult().getIntersectedNode().getParent();
            }

            File parent = treeCell.getTreeItem().getValue();

            if (!parent.isDirectory()) {
                parent = treeCell.getTreeItem().getParent().getValue();
            }

            String targetFolder = parent.toString();
            if (targetFolder.equals(startFolder) || targetFolder.contains(rolesPath) || targetFolder.contains(taskPath)) {
                startFolder = "";
                e.consume();
                return;
            }

            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.MOVE_ELEMENT, draggedItem.getViewModelElement().getType(),
                    draggedItem.getViewModelElement().getName());
            event.setElementId(draggedItem.getViewModelElement().getId());
            event.setAbsoluteDirectory(parent.toString());
            controller.getGuiModificationHandler().handle(event);
            e.consume();
        }
    }
}