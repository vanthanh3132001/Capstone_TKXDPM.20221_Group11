package controller.station;

import controller.bike.xemBaiXeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import entity.station;
import lombok.SneakyThrows;
import utility.DbUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.bike;
import static utility.SQLCommand.STATION_QUERY_LAY_THONG_TIN;

public class stationController implements Initializable {
    @FXML
    private TableView<station> stationTable;
    @FXML
    private TableColumn<station,Integer> idC;
    @FXML
    private TableColumn<station,String>  tenBaiXeC;

    @FXML
    private TableColumn<station,String>  viTriBaiXeC;

    @FXML
    private TableColumn<station,String>  dienTichBaiXeC;
    @FXML
    private TableColumn<station,String>  soLuongXeTrongBaiC;



    ObservableList<station> stationList = FXCollections.observableArrayList();

    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {

            loadData();


    }

    @FXML
    private void refreshTable() {
        try {



            preparedStatement = connection.prepareStatement(STATION_QUERY_LAY_THONG_TIN);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
            String soLuongXe="1";
            int ids=1;
            stationList.add(new station(
                    resultSet.getInt("ID_Station"),
                    resultSet.getString("StationName"),
                    resultSet.getString("LocationName"),
                    resultSet.getString("StationArea"),
                    soLuongXe));

            stationTable.setItems(stationList);


           }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    @FXML
    private void loadData() throws SQLException {
        connection = DbUtil.getInstance().getConnection();
        refreshTable();
        idC.setCellValueFactory(new PropertyValueFactory<station,Integer>("id"));
        tenBaiXeC.setCellValueFactory(new PropertyValueFactory<station,String>("tenBaiXe"));
        viTriBaiXeC.setCellValueFactory(new PropertyValueFactory<station,String>("viTriBaiXe"));
        dienTichBaiXeC.setCellValueFactory(new PropertyValueFactory<station,String>("dienTichBaiXe"));
        soLuongXeTrongBaiC.setCellValueFactory(new PropertyValueFactory<station,String>("soLuongXeTrongBai"));


       stationTable.setItems(stationList);
    }

    @FXML
    public void viewRentingBikeAction(ActionEvent event) throws  IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/RentingBikeForm2.fxml"));
        Parent RentingBike = loader.load();
        Scene scene = new Scene(RentingBike);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("RentingBike ");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void detailStation(ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/bikeInStation.fxml"));
        Parent thongtinBai = loader.load();
        xemBaiXeController controller = loader.getController();
        station selected = stationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert m = new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Thông báo!");
            m.setHeaderText("Không bai xe nào được chọn.");
            m.setContentText("Vui lòng chọn lại.");
            m.show();
            return;
        }
        controller.setListB(selected);
        Stage stage = new Stage();
//        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Thông tin bai");
        Scene scene = new Scene(thongtinBai);
        stage.setScene(scene);
        stage.show();
    }


}
