package controller.interbank;
import javax.swing.*;

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
import javafx.scene.control.Label;
public class interbankController{



    ObservableList<station> stationList = FXCollections.observableArrayList();

    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;


    public void addMoney(int money) throws IOException{
        try {


            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * from transaction");
            resultSet = preparedStatement.executeQuery();
            int rsCount = 0;
            while(resultSet.next())
            {
                if (resultSet.isLast()) {
                    String CreditCardNumber = (resultSet.getString("CreditCardNumber"));
                    PreparedStatement pS = connection.prepareStatement("UPDATE card SET money = ? + money where CreditCardNumber = ?");
                    pS.setInt(1, money);
                    pS.setString(2, CreditCardNumber);
                    pS.execute();

                    System.out.println("Add tien thanh cong");

                }
            }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public int getMoney() throws IOException{
        try {


            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * from transaction");
            resultSet = preparedStatement.executeQuery();
            int rsCount = 0;
            while(resultSet.next())
            {
                if (resultSet.isLast()) {
                    String CreditCardNumber = (resultSet.getString("CreditCardNumber"));
                    PreparedStatement pS = connection.prepareStatement("SELECT * from card where CreditCardNumber = ?");
                    pS.setString(1, CreditCardNumber);
                    resultSet = pS.executeQuery();
                    resultSet.next();
                    return resultSet.getInt("Money");


                }
            }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 0;

    }

    public void subMoney(int money) throws IOException{
        try {


            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * from transaction");
            resultSet = preparedStatement.executeQuery();
            int rsCount = 0;
            while(resultSet.next())
            {
                if (resultSet.isLast()) {
                    String CreditCardNumber = (resultSet.getString("CreditCardNumber"));
                    PreparedStatement pS = connection.prepareStatement("UPDATE card SET money = money - ? where CreditCardNumber = ?");
                    pS.setInt(1, money);
                    pS.setString(2, CreditCardNumber);
                    pS.execute();

                    System.out.println("Tru tien thanh cong");

                }
            }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetMoney() {
        try {


            addMoney(1000000);

        } catch (IOException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




}