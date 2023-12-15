package com.example.paoim4.front;

import com.example.paoim4.back.DatabaseManager;
import com.example.paoim4.back.Teacher;
import com.example.paoim4.back.TeacherStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Arrays;

public class TeacherEditController {

    @FXML
    private Button saveBtn;

    @FXML
    private ChoiceBox<TeacherStatus> conditionCB;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField salaryTF;

    @FXML
    private TextField surnameTF;

    @FXML
    private TextField yearOfBirth;

    @FXML
    public void initialize(){
        Teacher selectedTeacher = Teacher.selectedTeacher;
        conditionCB.setItems(FXCollections.observableList(Arrays.stream(TeacherStatus.values()).toList()));
        nameTF.setText(selectedTeacher.getName());
        surnameTF.setText(selectedTeacher.getSurname());
        salaryTF.setText(String.valueOf(selectedTeacher.getSalary()));
        yearOfBirth.setText(String.valueOf(selectedTeacher.getYearOfBirth()));
        conditionCB.getSelectionModel().select(selectedTeacher.getStatus());

    }

    @FXML
    void saveBtnClicked(MouseEvent event) {
        Teacher TeacherselectedTeacher = new Teacher();
        boolean success = true;
        try {
            Teacher.selectedTeacher.setName(nameTF.getText());
            Teacher.selectedTeacher.setSurname(surnameTF.getText());
            Teacher.selectedTeacher.setSalary(Double.parseDouble(salaryTF.getText()));
            Teacher.selectedTeacher.setYearOfBirth(Integer.parseInt(yearOfBirth.getText()));
            Teacher.selectedTeacher.setStatus(conditionCB.getSelectionModel().getSelectedItem());
        }catch (Exception ignored){success = false;}
        if (success) {
            DatabaseManager.getInstance().updateTeacher(Teacher.selectedTeacher);
            ((Stage)saveBtn.getScene().getWindow()).close();
        }
    }
}
