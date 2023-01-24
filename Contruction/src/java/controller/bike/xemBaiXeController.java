package controller.bike;

import entity.station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import entity.bike;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;
import utility.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utility.SQLCommand.BIKE_IN_STATION_QUERY_LAY_THONG_TIN;

public class xemBaiXeController {
    @FXML
    private TableView<bike> bikeTable;
    @FXML
    private TableColumn<bike,Integer> idBikeC;
    @FXML
    private TableColumn<bike,String>  bienSoXeC;

    @FXML
    private TableColumn<bike, Integer> giaCocC;

    @FXML
    private TableColumn<bike,String>  loaiXeC;

    @FXML
    private TableColumn<bike,String>  pinC;
    @FXML
    private TableColumn<bike,String>  statusC;



    private int idStation;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    @SneakyThrows
    @FXML
    public void setListB(station s)  {

        idStation=s.getId();

        loadData();

    }


    private void loadData() {
        ObservableList<bike> listBike=FXCollections.observableArrayList();
        try {




            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(BIKE_IN_STATION_QUERY_LAY_THONG_TIN);
            resultSet = preparedStatement.executeQuery();
            System.out.println(idStation);
            while (resultSet.next()){
                if(resultSet.getInt("ID_Station")==idStation){
                    String soLuongXe="1";
                    System.out.println(resultSet.getInt("ID_Bike"));
                    listBike.add(new bike(
                            resultSet.getInt("ID_Bike"),
                            "2",
                            "2",
                            2,
                            "2",
                            "2"));


                    bikeTable.setItems(listBike);
                    System.out.println(listBike);


                }



            }


        } catch (SQLException ex) {
            Logger.getLogger(xemBaiXeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        idBikeC.setCellValueFactory(new PropertyValueFactory<bike,Integer>("idBike"));
        bienSoXeC.setCellValueFactory(new PropertyValueFactory<bike,String>("bikeSerialNumber"));
        loaiXeC.setCellValueFactory(new PropertyValueFactory<bike,String>("bikeType"));
        giaCocC.setCellValueFactory(new PropertyValueFactory<bike,Integer>("deposit"));
        pinC.setCellValueFactory(new PropertyValueFactory<bike,String>("pin"));
        statusC.setCellValueFactory(new PropertyValueFactory<bike,String>("isLooked"));


        bikeTable.setItems(listBike);



    }









}
