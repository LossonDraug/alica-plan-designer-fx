package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanType;
import de.uni_kassel.vs.cn.planDesigner.alica.util.AllAlicaFiles;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 09.03.17.
 */
public class CreatNewDialogController implements Initializable {

    @FXML
    private Label pathLabel;

    @FXML
    private TextField pathTextField;

    @FXML
    private Button openFileChooserButton;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button createButton;

    private File initialDirectoryHint;

    private EClass alicaType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pathLabel.setText(I18NRepo.getString("label.choose.parentDirectory"));
        openFileChooserButton.setOnAction(e -> {
            Node source = (Node) e.getSource();
            openFileChooser((Stage) source.getScene().getWindow());
        });
        openFileChooserButton.setText(I18NRepo.getString("action.choose"));
        createButton.setText(I18NRepo.getString("action.create"));
        createButton.setOnAction(e -> createFile());
    }

    public void setInitialDirectoryHint(File initialDirectoryHint) {
        this.initialDirectoryHint = initialDirectoryHint;
        if (initialDirectoryHint.isDirectory() == false) {
            this.initialDirectoryHint = initialDirectoryHint.getParentFile();
        }
        pathTextField.setText(this.initialDirectoryHint.getAbsolutePath() + "/");
    }

    private void openFileChooser(Stage stage) {
        DirectoryChooser fileChooser = new DirectoryChooser();

        if (initialDirectoryHint != null) {
            if (initialDirectoryHint.isDirectory()) {
                fileChooser.setInitialDirectory(initialDirectoryHint);
            } else {
                fileChooser.setInitialDirectory(new File(initialDirectoryHint.getParent()));
            }
        }

        File chosenFile = fileChooser.showDialog(stage);
        String parentPath = chosenFile.getAbsolutePath();
        if (parentPath.endsWith("/") == false) {
            parentPath = parentPath + "/";
        }

        pathTextField.setText(parentPath);

    }

    private void createFile() {
        String fileName = nameTextField.getText();
        if (alicaType != null) {
            try {
                if (alicaType.getInstanceClass().equals(Behaviour.class) && fileName.endsWith(".beh") == false ||
                        alicaType.getInstanceClass().equals(Plan.class) && fileName.endsWith(".pml") == false ||
                        alicaType.getInstanceClass().equals(PlanType.class) && fileName.endsWith(".pty") == false) {
                    nameTextField.setStyle("-fx-text-inner-color: red;");
                    nameTextField.setOnKeyPressed(e -> {
                        nameTextField.setStyle("");
                        nameTextField.setOnKeyPressed(null);
                    });
                    return;
                }
                EObject emptyObject = getAlicaFactory().create(alicaType);
                Resource alicaFile = EMFModelUtils.createAlicaFile(emptyObject, new File(pathTextField.getText() + fileName));
                ((AbstractPlan)alicaFile.getContents().get(0)).setName(fileName.replace(".beh","")
                        .replace(".pty","").replace(".pml", ""));
                if (emptyObject instanceof Plan) {
                    AllAlicaFiles.getInstance().getPlans().add(new Pair<>((Plan) emptyObject,
                            Paths.get(pathTextField.getText() + fileName)));
                } else if (emptyObject instanceof PlanType) {
                    AllAlicaFiles.getInstance().getPlanTypes().add(new Pair<>((PlanType) emptyObject,
                            Paths.get(pathTextField.getText() + fileName)));
                } else if (emptyObject instanceof Behaviour) {
                    AllAlicaFiles.getInstance().getBehaviours().add(new Pair<>((Behaviour) emptyObject,
                            Paths.get(pathTextField.getText() + fileName)));
                }
                ((Stage)pathTextField.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createDirectory(new File(pathTextField.getText() + fileName).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setAlicaType(EClass alicaType) {
        this.alicaType = alicaType;
        String nameString = I18NRepo.getString("label.name");
        if (alicaType != null) {
            if (alicaType.getInstanceClass().equals(Behaviour.class)) {
                nameLabel.setText(I18NRepo.getString("label.menu.new.behaviour") + " " + nameString);
            }

            if (alicaType.getInstanceClass().equals(Plan.class)) {
                nameLabel.setText(I18NRepo.getString("label.menu.new.plan") + " " + nameString);
            }

            if (alicaType.getInstanceClass().equals(PlanType.class)) {
                nameLabel.setText(I18NRepo.getString("label.menu.new.plantype") + " " + nameString);
            }
        } else {
            nameLabel.setText(I18NRepo.getString("label.menu.new.folder") + " " + nameString);
        }
    }
}
