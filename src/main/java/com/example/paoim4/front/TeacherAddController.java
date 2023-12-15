package com.example.paoim4.front;

import com.example.paoim4.back.DatabaseManager;
import com.example.paoim4.back.Teacher;
import com.example.paoim4.back.TeacherGroup;
import com.example.paoim4.back.TeacherStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Arrays;

public class TeacherAddController {
    @FXML
    private ChoiceBox<TeacherStatus> conditionCB;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField salaryTF;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField surnameTF;

    @FXML
    private TextField yearOfBirth;

    @FXML
    public void initialize(){
        conditionCB.setItems(FXCollections.observableList(Arrays.stream(TeacherStatus.values()).toList()));
        conditionCB.getSelectionModel().select(TeacherStatus.PRESENT);
    }
    @FXML
    void saveBtnClicked(MouseEvent event) {
        Teacher teacher = new Teacher();
        boolean success = true;
        try {
            teacher.setName(nameTF.getText());
            teacher.setSurname(surnameTF.getText());
            teacher.setSalary(Double.parseDouble(salaryTF.getText()));
            teacher.setYearOfBirth(Integer.parseInt(yearOfBirth.getText()));
            teacher.setStatus(conditionCB.getSelectionModel().getSelectedItem());
            teacher.setTeacherGroup(TeacherGroup.selectedGroup);
        }catch (Exception ignored){success = false;}
        if (success) {
            DatabaseManager.getInstance().saveTeacher(teacher);
            ((Stage)saveBtn.getScene().getWindow()).close();
        }
    }
}
