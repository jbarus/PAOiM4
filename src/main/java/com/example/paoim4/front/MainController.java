package com.example.paoim4.front;

import com.example.paoim4.back.DatabaseManager;
import com.example.paoim4.back.Teacher;
import com.example.paoim4.back.TeacherGroup;
import com.example.paoim4.utils.Loader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.loader.ast.spi.Loadable;

public class MainController {
    @FXML
    private TableView<TeacherGroup> groupTV;
    @FXML
    private TableColumn<TeacherGroup,String> groupClmn;
    @FXML
    private TableColumn<TeacherGroup,Integer> sizeClmn;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button viewBtn;
    @FXML
    private Button rateBtn;
    @FXML
    private Button viewRateBtn;




    @FXML
    public void initialize(){
        groupClmn.setCellValueFactory(new PropertyValueFactory<TeacherGroup,String>("groupName"));
        sizeClmn.setCellValueFactory(new PropertyValueFactory<TeacherGroup,Integer>("size"));
        groupTV.setItems(FXCollections.observableArrayList(DatabaseManager.getInstance().getAllGroups()));
        viewBtn.setDisable(true);
        deleteBtn.setDisable(true);
        rateBtn.setDisable(true);
        viewRateBtn.setDisable(true);
    }

    @FXML
    void addBtnClicked(MouseEvent event) {
        Scene scene = new Scene(Loader.getScene("add-group-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        groupTV.setItems(FXCollections.observableArrayList(DatabaseManager.getInstance().getAllGroups()));
    }

    @FXML
    void deleteBtnClicked(MouseEvent event) {
        TeacherGroup selectedGroup = groupTV.getSelectionModel().getSelectedItem();
        if(selectedGroup != null){
            groupTV.getItems().remove(selectedGroup);
            DatabaseManager.getInstance().deleteGroup(selectedGroup);
        }
    }

    @FXML
    void viewBtnClicked(MouseEvent event) {
        Scene scene = new Scene(Loader.getScene("group-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void itemSelected(MouseEvent mouseEvent) {
        if(groupTV.getSelectionModel().getSelectedItem() != null) {
            TeacherGroup.selectedGroup = groupTV.getSelectionModel().getSelectedItem();
            viewBtn.setDisable(false);
            deleteBtn.setDisable(false);
            rateBtn.setDisable(false);
            viewRateBtn.setDisable(false);
        }
    }

    public void viewRateBtnClicked(MouseEvent mouseEvent) {
        Scene scene = new Scene(Loader.getScene("rate-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void rateBtnClicked(MouseEvent mouseEvent) {
        Scene scene = new Scene(Loader.getScene("add-rate-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void exportBtnClicked(MouseEvent mouseEvent) {
        DatabaseManager.getInstance().exportToCSV();
    }
}