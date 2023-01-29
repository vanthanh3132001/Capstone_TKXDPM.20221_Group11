package controller.station;
import javafx.scene.control.Label;
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
import controller.bike.viewRentingBikeController;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.bike;
import javax.swing.*;

import static utility.SQLCommand.COUNT_BIKE_IN_STATION;
import static utility.SQLCommand.STATION_QUERY_LAY_THONG_TIN;
import repository.bikeRepository;
import repository.bikeRepositoryImpl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    bikeRepository bikeRepository = new bikeRepositoryImpl();
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
                System.out.println(String.valueOf(bikeRepository.SumBike(resultSet.getInt("ID_Station"))));

                stationList.add(new station(
                        resultSet.getInt("ID_Station"),
                        resultSet.getString("StationName"),
                        resultSet.getString("LocationName"),
                        resultSet.getString("StationArea")+" ha",
                        String.valueOf(bikeRepository.SumBike(resultSet.getInt("ID_Station")))));

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
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * from bike where isLocked<2");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                FXMLLoader Rentingloader = new FXMLLoader();
                Rentingloader.setLocation(getClass().getResource("/view/RentingBikeForm2.fxml"));
//                viewRentingBikeController controller = new viewRentingBikeController();
//                loader.setController(controller);
                System.out.println((Rentingloader));
                Parent RentingBike = Rentingloader.load();


                Scene Rentingscene = new Scene(RentingBike);


                Stage stage = new Stage();
                stage.initStyle(StageStyle.DECORATED);
                stage.setResizable(false);
                stage.setTitle("RentingBike ");
                stage.setScene(Rentingscene);
                stage.show();

                try {

                    connection = DbUtil.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("SELECT * from transaction", ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    resultSet = preparedStatement.executeQuery();
                    int rsCount = 0;
//                    resultSet.afterLast();

//                        if resultSet.next()
//                        System.out.println(resultSet.next());
                    resultSet.last();


                    if (resultSet.isLast()) {

                        int last_transaction = (resultSet.getInt("ID_Transaction"));
                        int id_bike = (resultSet.getInt("ID_Bike"));
                        System.out.println(id_bike);
                        Label thoidiembatdauthue = (Label) Rentingscene.lookup("#thoidiembatdauthue");
                        thoidiembatdauthue.setText(resultSet.getString("TimeStart"));

                        Label ngaythue = (Label) Rentingscene.lookup("#ngaythue");
                        ngaythue.setText(resultSet.getString("Date_Transaction"));

                        preparedStatement = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
                        preparedStatement.setInt(1, id_bike);
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();

                        ImageView anhxe = (ImageView) Rentingscene.lookup("#anhxe");
//                        Image card = new Image(resultSet.getString("linkImage"));
//                        anhxe.setImage(card);

                        if (resultSet.getString("BikeType").equals("xe đạp")){
                            anhxe.setImage(new Image ("/image/xe_dap.jpg"));
                        } else if (resultSet.getString("BikeType").equals("xe điện")) {

                            anhxe.setImage(new Image ("/image/xe_dap_dien.jpg"));
                        } else if (resultSet.getString("BikeType").equals("xe đạp đôi")) {

                            anhxe.setImage(new Image ("/image/xe_dap_doi.jpg"));
                        }


                        Label loaixe = (Label) Rentingscene.lookup("#loaixe");
                        loaixe.setText(resultSet.getString("BikeType"));

                        Label giacoc = (Label) Rentingscene.lookup("#giacoc");
                        giacoc.setText(resultSet.getString("Deposit"));

                        Label biensoxe = (Label) Rentingscene.lookup("#biensoxe");
                        biensoxe.setText(resultSet.getString("BikeSerialNumber"));

                        Label trangthai = (Label) Rentingscene.lookup("#trangthai");

                        if (resultSet.getString("isLocked").equals("0")) {
                            trangthai.setText("Xe đang mở khóa");
                        }

                        if (resultSet.getString("isLocked").equals("1")) {
                            trangthai.setText("Xe đang khóa");
                        }

                        if (resultSet.getString("isLocked").equals("2")) {
                            trangthai.setText("Xe đang được thuê");
                        }






                        preparedStatement = connection.prepareStatement("SELECT * from Station where ID_Station = ?");
                        preparedStatement.setInt(1, resultSet.getInt("Id_Station"));
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        Label baidathue = (Label) Rentingscene.lookup("#baidathue");
                        baidathue.setText(resultSet.getString("StationName"));



                    }


                } catch (SQLException ex) {
                    Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
//                JFrame mainFrame = new JFrame();
//                JOptionPane.showMessageDialog(mainFrame, "Bạn chưa thuê xe nào");
                Alert m = new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Thông báo!");
                m.setHeaderText("Bạn chưa thuê xe nào");
                m.show();
                return;
            }
        }


        catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            m.setHeaderText("Không bãi xe nào được chọn.");
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
