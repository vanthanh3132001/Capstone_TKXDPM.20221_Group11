package controller.bike;

import entity.station;
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
import entity.bike;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import utility.DbUtil;
import controller.payment.depositController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utility.SQLCommand.BIKE_IN_STATION_QUERY_LAY_THONG_TIN;

public class xemBaiXeController implements Initializable{
    @FXML
    private TableView<bike> t;
    @FXML
    private TableColumn<bike, Integer> c1;
    @FXML
    private TableColumn<bike,String>  c2;

    @FXML
    private TableColumn<bike, String> c3;

    @FXML
    private TableColumn<bike, String> c4;

    @FXML
    private TableColumn<bike,String>  c5;
    @FXML
    private TableColumn<bike,String>  c6;
    @FXML
            private TextField ids;




    int idStation;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;

    ObservableList<bike> listBike = FXCollections.observableArrayList();
    @SneakyThrows
    @Override

    public void initialize(URL location, ResourceBundle resources) {
//        initCol();







    }


    @SneakyThrows
    public void setListB(station s) {
        idStation=s.getId();

        loadData();





    }


    @FXML
    private void refreshTable() {
        try {



            preparedStatement = connection.prepareStatement(BIKE_IN_STATION_QUERY_LAY_THONG_TIN);
            resultSet = preparedStatement.executeQuery();
            String trangThaiXe="";
            while (resultSet.next()){
            if(resultSet.getInt("ID_Station")==idStation){
                if(resultSet.getString("isLocked").equals("2")){
                    trangThaiXe="Chưa được thuê";
                }else if (resultSet.getString("isLocked").equals("1")) {
                    trangThaiXe="Xe đang khóa";
                }else if (resultSet.getString("isLocked").equals("0")) {
                    trangThaiXe="Xe đang được thuê";
                }
                listBike.add(new bike(
                        resultSet.getInt("ID_Bike"),
                        resultSet.getString("BikeSerialNumber"),
                        resultSet.getString("BikeType"),
                        resultSet.getString("Deposit"),
                        resultSet.getString("Pin"),
                        trangThaiXe));

                t.setItems(listBike);
            }
//            System.out.println(idStation);






            }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    @FXML
    private void loadData() throws SQLException {
        connection = DbUtil.getInstance().getConnection();
        refreshTable();
        c1.setCellValueFactory(new PropertyValueFactory<bike,Integer>("idBike"));
        c2.setCellValueFactory(new PropertyValueFactory<bike,String>("bikeSerialNumber"));
        c3.setCellValueFactory(new PropertyValueFactory<bike,String>("bikeType"));
        c4.setCellValueFactory(new PropertyValueFactory<bike,String>("deposit"));
        c5.setCellValueFactory(new PropertyValueFactory<bike,String>("pin"));
        c6.setCellValueFactory(new PropertyValueFactory<bike,String>("isLooked"));


        t.setItems(listBike);
    }


    public void thueXeAction(ActionEvent e) throws SQLException, IOException {

        Connection connection4 = null;
        ResultSet resultSet4 = null;
        PreparedStatement preparedStatement4;
        connection4 = DbUtil.getInstance().getConnection();
        preparedStatement4 = connection4.prepareStatement(BIKE_IN_STATION_QUERY_LAY_THONG_TIN);
        resultSet4 = preparedStatement4.executeQuery();

        while (resultSet4.next()) {

            if (Integer.parseInt(resultSet4.getString("isLocked")) < 2) {
                Alert m = new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Thông báo!");
                m.setHeaderText("Bạn đang thuê xe, không thể thuê thêm");
                m.show();
                return;
            }
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/PaymentForm.fxml"));
        Parent deposit = loader.load();
        depositController controller = loader.getController();
        bike selected = t.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert m = new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Thông báo!");
            m.setHeaderText("Không xe nào được chọn.");
            m.setContentText("Vui lòng chọn lại.");
            m.show();
            return;
        }

        String trangthai_Xe=selected.getIsLooked();
        if(trangthai_Xe.equals("Xe đang khóa") ||trangthai_Xe.equals("Xe đang được thuê")  ){
            Alert m2 = new Alert(Alert.AlertType.INFORMATION);
            m2.setTitle("Thông báo!");
            m2.setHeaderText("Không thể thực hiện chức năng do xe đang được thuê");

            m2.show();
            return;

        }else{
            controller.setDepositForm(selected);
            Stage stage = new Stage();
//        stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Giao diện đặt cọc");
            Scene scene = new Scene(deposit);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void viewDetailBike(ActionEvent e) throws  IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/detailBike.fxml"));
        Parent detail = loader.load();
        viewDetailBikeController controller = loader.getController();
        bike selected = t.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert m = new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Thông báo!");
            m.setHeaderText("Không xe nào được chọn.");
            m.setContentText("Vui lòng chọn lại.");
            m.show();
            return;
        }


        controller.setDetailBike(selected);
        Stage stage = new Stage();
//        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Giao diện đặt cọc");
        Scene scene = new Scene(detail);
        stage.setScene(scene);
        stage.show();
    }



}
