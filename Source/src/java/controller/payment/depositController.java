package controller.payment;

import entity.bike;
import entity.card;
import controller.interbank.interbankController;
import entity.station;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import utility.DbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utility.SQLCommand.CARD_QUERY_LAY_THONG_TIN;
import static utility.SQLCommand.TRANSACTION_QUERY_INSERT_TRANSACTION;
import static utility.SQLCommand.BIKE_UPDATE_TRANG_THAI_QUERY;


public class depositController {
    @FXML
    private javafx.scene.control.TextField FullName;
    @FXML
    private DatePicker ExpireDate;
    @FXML
    private javafx.scene.control.TextField CreditCardNumber;
    @FXML
    private javafx.scene.control.TextField BankName;
    @FXML
    private javafx.scene.control.TextField SecurityCode;


    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;


    Connection connection2 = null;
    ResultSet resultSet2 = null;
    PreparedStatement preparedStatement2;

    Connection connection3 = null;
    ResultSet resultSet3 = null;
    PreparedStatement preparedStatement3;

    private int idBike;


    private String deposit_value;
    private int card_money;
    @SneakyThrows
    public void setDepositForm(bike b) {
        idBike=b.getIdBike();
        deposit_value= b.getDeposit();
        System.out.println(deposit_value);
        deposit_value=	deposit_value.substring(0, deposit_value.length()-2);
        System.out.println(deposit_value);










    }
    @SneakyThrows
    @FXML
    private void saveAction(MouseEvent event) {


        String fullName_value = FullName.getText();
        LocalDate ExpireDate_value = ExpireDate.getValue();
        String CreditCardNumber_value = CreditCardNumber.getText();
        String BankName_value = BankName.getText();
        String SecurityCode_value = SecurityCode.getText();
        String ExpireDate_valueString= String.valueOf(ExpireDate_value);

        if(fullName_value.equals("")||ExpireDate_valueString.equals("")||CreditCardNumber_value.equals("")||BankName_value.equals("")||SecurityCode_value.equals("")){
            Alert m = new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Thông báo!");
            m.setHeaderText("Bạn cần nhập đầy đủ các trường");
            m.setContentText("Vui lòng nhập lại");
            m.show();
            return;
        }
        try {

//          check info card
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(CARD_QUERY_LAY_THONG_TIN);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                card_money=resultSet.getInt("money");
                System.out.println(card_money);
                if(!fullName_value.equals(resultSet.getString("FullName"))){
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Thông báo!");
                    m.setHeaderText("Sai thông tin thẻ tên");
                    m.setContentText("Vui lòng nhập lại");
                    m.show();
                    return;
                }
                if(!ExpireDate_valueString.equals(resultSet.getString("ExpireDate"))){
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Thông báo!");
                    m.setHeaderText("Sai thông tin ngày hết hạn thẻ");
                    m.setContentText("Vui lòng nhập lại");
                    m.show();
                    return;
                }
                if( !CreditCardNumber_value.equals(resultSet.getString("CreditCardNumber"))){
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Thông báo!");
                    m.setHeaderText("Sai thông tin thẻ mã thẻ");
                    m.setContentText("Vui lòng nhập lại");
                    m.show();
                    return;
                }
                if(  !SecurityCode_value.equals(resultSet.getString("SecurityCode")) ){
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Thông báo!");
                    m.setHeaderText("Sai thông tin thẻ mã bảo mật");
                    m.setContentText("Vui lòng nhập lại");
                    m.show();
                    return;
                }
                if(   !BankName_value.equals(resultSet.getString("BankName"))){
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Thông báo!");
                    m.setHeaderText("Sai thông tin tên ngân hàng");
                    m.setContentText("Vui lòng nhập lại");
                    m.show();
                    return;
                }







                int doposit_int_value= Integer.parseInt(deposit_value);
                interbankController interbank = new interbankController();
                if(card_money<doposit_int_value){
                    Alert m = new Alert(Alert.AlertType.INFORMATION);
                    m.setTitle("Thông báo!");
                    m.setHeaderText("Tai khoan khong du de dat coc");
                    m.setContentText("Vui lòng nhập nap them");
                    m.show();
                    return;
                }else{
                    // save info transaction

                    connection2 = DbUtil.getInstance().getConnection();
                    preparedStatement2 = connection.prepareStatement(TRANSACTION_QUERY_INSERT_TRANSACTION);
                    resultSet2 = preparedStatement.executeQuery();

                    preparedStatement2.setString(1, CreditCardNumber_value);
                    preparedStatement2.setString(2, String.valueOf(idBike));
                    preparedStatement2.setString(3, String.valueOf(LocalDate.now()));
                    preparedStatement2.setString(4, String.valueOf(java.time.LocalTime.now()));
                    preparedStatement2.setString(5, String.valueOf(java.time.LocalTime.now()));
                    preparedStatement2.setString(6, "");
                    preparedStatement2.setString(7, "00:00:00");
                    preparedStatement2.execute();
                    Alert alert_TC = new Alert(Alert.AlertType.INFORMATION);
                    alert_TC.setHeaderText(null);
                    alert_TC.setContentText("Thuê thành công");
                    alert_TC.showAndWait();
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
// update info status bike
                    connection3 = DbUtil.getInstance().getConnection();
                    preparedStatement3 = connection.prepareStatement(BIKE_UPDATE_TRANG_THAI_QUERY+idBike);
                    resultSet3 = preparedStatement.executeQuery();
                    preparedStatement3.setString(1, "0");
                    preparedStatement3.execute();
//update doposit card
                    interbank.subMoney(doposit_int_value);
                }










                break;
            }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }


    }








}
