package com.example.paoim4.front;

import com.example.paoim4.back.DatabaseManager;
import com.example.paoim4.back.Rate;
import com.example.paoim4.back.Teacher;
import com.example.paoim4.back.TeacherGroup;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;

public class AddRateController {
    @FXML
    private Button addBtn;
    @FXML
    private TextField commentTF;
    @FXML
    private ChoiceBox<Integer> starChcBx;

    public void initialize(){
        starChcBx.setItems(FXCollections.observableArrayList(List.of(1,2,3,4,5,6)));
        starChcBx.getSelectionModel().select(0);
    }
    public void saveBtnClicked(MouseEvent mouseEvent) {
        Rate rate = new Rate();
        boolean success = true;
        try {
            rate.setStarNumber(starChcBx.getSelectionModel().getSelectedItem());
            rate.setReviewDate(new Date());
            rate.setTeacherGroup(TeacherGroup.selectedGroup);
            if(commentTF.getText().isEmpty())
                rate.setComment("Not specified");
            else
                rate.setComment(commentTF.getText());
        }catch (Exception ignored){success = false;}
        if (success) {
            DatabaseManager.getInstance().saveRate(rate);
            ((Stage)addBtn.getScene().getWindow()).close();
        }
    }
}
