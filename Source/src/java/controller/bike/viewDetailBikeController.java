package controller.bike;

import entity.bike;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.SneakyThrows;
import utility.DbUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import static utility.SQLCommand.BIKE_IN_STATION_QUERY_LAY_THONG_TIN;

public class viewDetailBikeController {
    @FXML
    private Label loaiXeL;
    @FXML
    private Label giaCocL;
    @FXML
    private Label bienSoXeL;
    @FXML
    private Label trangthaiL;
    @FXML
    private Label pinL;

    @FXML
    private ImageView imageView;

    private int idBike;

    private String trangThaiXe="";
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;

    @SneakyThrows
    public void setDetailBike(bike b) {
        idBike=b.getIdBike();

        loadData();





    }

    @FXML
    private void loadData() throws SQLException {
        try{
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(BIKE_IN_STATION_QUERY_LAY_THONG_TIN);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                if(resultSet.getInt("ID_Bike")==idBike){
                    loaiXeL.setText(resultSet.getString("BikeType"));
                    giaCocL.setText(resultSet.getString("Deposit")+" VND");
                    bienSoXeL.setText(resultSet.getString("BikeSerialNumber"));
                    if(resultSet.getString("isLocked").equals("2")){
                        trangThaiXe="Chưa được thuê";
                    }else if (resultSet.getString("isLocked").equals("1")) {
                        trangThaiXe="Xe đang khóa";
                    }else if (resultSet.getString("isLocked").equals("0")) {
                        trangThaiXe="Xe đang được thuê";
                    }
                    trangthaiL.setText(trangThaiXe);
                    pinL.setText(resultSet.getString("Pin"));
                    if (resultSet.getString("BikeType").equals("xe đạp")){
                        imageView.setImage(new Image ("/image/xe_dap.jpg"));
                    } else if (resultSet.getString("BikeType").equals("xe điện")) {

                        imageView.setImage(new Image ("/image/xe_dap_dien.jpg"));
                    } else if (resultSet.getString("BikeType").equals("xe đạp đôi")) {

                        imageView.setImage(new Image ("/image/xe_dap_doi.jpg"));
                    }

                }
//            System.out.println(idStation);






            }
        } catch (SQLException ex) {
            Logger.getLogger(viewDetailBikeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
