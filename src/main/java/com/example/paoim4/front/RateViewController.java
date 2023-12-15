package com.example.paoim4.front;

import com.example.paoim4.back.DatabaseManager;
import com.example.paoim4.back.Rate;
import com.example.paoim4.back.TeacherGroup;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class RateViewController {
    @FXML
    private TableColumn<Rate, String> commentClmn;

    @FXML
    private TableColumn<Rate, Date> dateClmn;

    @FXML
    private TableView<Rate> rateTV;

    @FXML
    private TableColumn<Rate, Integer> starNumberClmn;

    @FXML
    public void initialize(){
        commentClmn.setCellValueFactory(new PropertyValueFactory<Rate,String>("comment"));
        dateClmn.setCellValueFactory(new PropertyValueFactory<Rate,Date>("reviewDate"));
        starNumberClmn.setCellValueFactory(new PropertyValueFactory<Rate,Integer>("starNumber"));
        rateTV.setItems(FXCollections.observableArrayList(DatabaseManager.getInstance().getRateListByGroup(TeacherGroup.selectedGroup)));
    }
}
