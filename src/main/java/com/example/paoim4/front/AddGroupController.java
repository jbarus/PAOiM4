package com.example.paoim4.front;

import com.example.paoim4.back.DatabaseManager;
import com.example.paoim4.back.TeacherGroup;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddGroupController {
    @FXML
    private Button addBtn;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField sizeTF;

    @FXML
    public void initialize(){

    }
    @FXML
    void addBtnClicked(MouseEvent event) {
        String name = null;
        int size = 0;

        try {
            name = nameTF.getText();
        } catch (Exception ignored){}
        try {
            size = Integer.parseInt(sizeTF.getText());
        } catch (Exception ignored){}

        if(name != null && size > 0){
            TeacherGroup teacherGroup = new TeacherGroup(name,size);
            DatabaseManager.getInstance().saveGroup(teacherGroup);
            ((Stage)addBtn.getScene().getWindow()).close();
        }
    }
}
