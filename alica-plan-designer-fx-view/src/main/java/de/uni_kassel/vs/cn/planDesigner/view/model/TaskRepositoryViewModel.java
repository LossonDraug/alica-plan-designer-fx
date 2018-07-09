package de.uni_kassel.vs.cn.planDesigner.view.model;

import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.taskRepoTab.TaskRepositoryTab;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TaskRepositoryViewModel {
    private ObservableList<ViewModelElement> tasks;
    private TaskRepositoryTab taskRepositoryTab;
    private boolean isDirty;

    public TaskRepositoryViewModel() {
        tasks = FXCollections.observableArrayList(new ArrayList<>());
        tasks.addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                if (taskRepositoryTab == null)
                {
                    return;
                }
                taskRepositoryTab.clearGuiContent();
                taskRepositoryTab.addElements(tasks);
                if (isDirty) {
                    taskRepositoryTab.setDirty(isDirty);
                }
            }
        });
        isDirty = false;
    }

    public void setTaskRepositoryTab (TaskRepositoryTab taskRepositoryTab) {
        this.taskRepositoryTab = taskRepositoryTab;
        initGuiContent();
    }

    public void initGuiContent() {
        if (taskRepositoryTab == null) {
            return;
        }

        taskRepositoryTab.clearGuiContent();
        taskRepositoryTab.addElements(tasks);
        taskRepositoryTab.setDirty(this.isDirty);
    }

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
        if (this.taskRepositoryTab != null) {
            this.taskRepositoryTab.setDirty(isDirty);
        }
    }

    public void addTask(ViewModelElement task) {
        this.tasks.add(task);
    }

    public void removeTask(ViewModelElement task) {
        tasks.remove(task);
    }

    public void clearTasks() {
        tasks.clear();
    }
}