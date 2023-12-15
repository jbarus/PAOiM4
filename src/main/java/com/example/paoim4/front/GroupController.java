package com.example.paoim4.front;


import com.example.paoim4.back.*;
import com.example.paoim4.utils.Loader;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import static com.example.paoim4.back.TeacherStatus.PRESENT;

public class GroupController {
    @FXML
    private Label delegationLbl;

    @FXML
    private Label nonpresentLbl;

    @FXML
    private Label presentLbl;

    @FXML
    private Label sickLbl;

    @FXML
    private TableView<Teacher> teacherTV;
    @FXML
    private TableColumn<TeacherStatus, String> conditionClmn;

    @FXML
    private TableColumn<Teacher, String> nameClmn;

    @FXML
    private TableColumn<Teacher, Double> salaryClmn;

    @FXML
    private TableColumn<Teacher, String> surnameClmn;

    @FXML
    private TableColumn<Teacher, Integer> yearOfBirthClmn;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    @FXML
    private TextField searchTF;
    @FXML
    private Label spaceLbl;
    @FXML
    private Label rateLbl;
    @FXML
    private Button exportBtn;

    @FXML
    public void initialize(){
        conditionClmn.setCellValueFactory(new PropertyValueFactory<TeacherStatus,String>("status"));
        nameClmn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
        salaryClmn.setCellValueFactory(new PropertyValueFactory<Teacher,Double>("salary"));
        surnameClmn.setCellValueFactory(new PropertyValueFactory<Teacher,String>("surname"));
        yearOfBirthClmn.setCellValueFactory(new PropertyValueFactory<Teacher,Integer>("yearOfBirth"));
        teacherTV.setItems(FXCollections.observableArrayList(DatabaseManager.getInstance().getTeacherListByGroup(TeacherGroup.selectedGroup)));
        teacherTV.getItems().addListener(new ListChangeListener<Teacher>() {
            @Override
            public void onChanged(Change<? extends Teacher> change) {
                update();
            }
        });
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);
        update();

    }

    @FXML
    void addBtnClicked(MouseEvent event) {
        Scene scene = new Scene(Loader.getScene("teacher-add-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        update();
    }


    @FXML
    void deleteBtnClicked(MouseEvent event) {
        Teacher teacher = teacherTV.getSelectionModel().getSelectedItem();
        if(teacher != null){
            teacherTV.getItems().remove(teacher);
            DatabaseManager.getInstance().deleteTeacher(teacher);
            update();
        }
    }

    @FXML
    void editBtnClicked(MouseEvent event) {
        Teacher.selectedTeacher = teacherTV.getSelectionModel().getSelectedItem();
        Scene scene = new Scene(Loader.getScene("teacher-edit-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        update();
    }

    @FXML
    void onItemClicked(MouseEvent event) {
        if(teacherTV.getSelectionModel().getSelectedItem() != null) {
            editBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }
    }

    @FXML
    void keyInSearchPressed(KeyEvent event) {
        ObservableList<Teacher> list = FXCollections.observableArrayList();
        String partialText = "";

        if(event.getCode() == KeyCode.ENTER){
            partialText = searchTF.getText();
            List<Teacher> teacherList = DatabaseManager.getInstance().getTeacherListByGroup(TeacherGroup.selectedGroup);
            for(Teacher t : teacherList)
                if(t.getSurname().startsWith(partialText))
                    list.add(t);

            teacherTV.setItems(list);
        }
    }

    private void update(){
        teacherTV.setItems(FXCollections.observableArrayList(DatabaseManager.getInstance().getTeacherListByGroup(TeacherGroup.selectedGroup)));

        int[] tab = new int[4];
        teacherTV.getItems().forEach(teacher -> {
            switch (teacher.getStatus()){
                case PRESENT -> tab[0]++;
                case SICK -> tab[1]++;
                case DELEGATION -> tab[2]++;
                case NONPRESENT -> tab[3]++;
            }
        });
        presentLbl.setText("Present teachers: " + tab[0]);
        sickLbl.setText("Sick teachers: " + tab[1]);
        delegationLbl.setText("Teachers on delegation: " + tab[2]);
        nonpresentLbl.setText("Nonpresent teachers: " + tab[3]);

        spaceLbl.setText("Percentage of space used: " + new DecimalFormat("##.##").format(((double)teacherTV.getItems().size()/(double)TeacherGroup.selectedGroup.getSize())*100) + "%");
        List<Rate> rates = DatabaseManager.getInstance().getRateListByGroup(TeacherGroup.selectedGroup);
        double avg = rates.stream().mapToDouble(Rate::getStarNumber).sum() / rates.size();
        rateLbl.setText("Avg. Rate: " + new DecimalFormat("##.##").format(avg));
    }

    @FXML
    void backBtnClicked(MouseEvent event) {
        ((Stage)addBtn.getScene().getWindow()).close();
    }

}
