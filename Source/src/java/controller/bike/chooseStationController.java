package controller.bike;

import controller.interbank.interbankController;
import entity.station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;
import utility.DbUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utility.SQLCommand.STATION_QUERY_LAY_THONG_TIN;

public class chooseStationController implements Initializable {

    @FXML
    private TableView<station> table;
    @FXML
    private TableColumn<station,Integer> sothutu;
    @FXML
    private TableColumn<station,String>  tenbaixe;

    @FXML
    private TableColumn<station,String>  vitribaixe;


    ObservableList<station> stationList_chooseBike = FXCollections.observableArrayList();

    Connection connection_chooseBike = null ;
    PreparedStatement preparedStatement_chooseBike = null ;
    ResultSet resultSet_chooseBike = null ;



    private int toMins(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    private int money_Renting;

    private String TimeRenting;
    private int min_Renting;

    private int return_Deposit;
    public  void payment(int id_station) throws IOException {


        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * from transaction");
            resultSet = preparedStatement.executeQuery();
            int rsCount = 0;
            while(resultSet.next())
            {
                if (resultSet.isLast()) {
                    int last_transaction = (resultSet.getInt("ID_Transaction"));
                    int id_bike = (resultSet.getInt("ID_Bike"));

                    ///Update ID_Station va isLocked khi ve bai
                    PreparedStatement pS;

                    pS = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
                    pS.setInt(1, id_bike);
                    resultSet = pS.executeQuery();
                    resultSet.next();
                    return_Deposit = (int) resultSet.getDouble("Deposit");
                    String BikeType = resultSet.getString("BikeType");
                    String LockorNot = resultSet.getString("isLocked");
                    ///Neu xe dang mo khoa thi lay cur_time - latestunlock + timerentingbike
                    if (!LockorNot.equals("1") && !LockorNot.equals("2")) {
                        System.out.println("Khoa");
                        pS = connection.prepareStatement("UPDATE transaction SET TimeRentingBike = ADDTIME(SUBTIME(current_time,LatestUnlockTime),TimeRentingBike) where ID_Transaction = ?");
                        pS.setInt(1, last_transaction);
                        pS.execute();

                        pS = connection.prepareStatement("SELECT * from transaction where ID_Transaction = ?");
                        pS.setInt(1, last_transaction);
                        resultSet = pS.executeQuery();
                        resultSet.next();
                        TimeRenting = resultSet.getString("TimeRentingBike");


                        min_Renting = toMins(TimeRenting);

                        if (BikeType.equals("xe đạp")){
                            System.out.println(min_Renting);
//
                            if (min_Renting<=10  && min_Renting>=0){
                                System.out.println("nho hon 10");
                                money_Renting = 0;
                            }
                            else {

                                if (10 < min_Renting && min_Renting <= 30) {
                                    System.out.println("10 30");
                                    money_Renting = 10000;
                                } else {
                                    double minute = (double) min_Renting;
                                    System.out.println(">30");
                                    money_Renting = (int) (10000 + 3000 * Math.ceil((minute - 30) / 15));
                                }
                            }
                        }
                        if (BikeType.equals("xe đạp đôi")){
                            if (min_Renting<=10  && min_Renting>=0){
                                money_Renting = 0;
                            }
                            else {
                                if (10 < min_Renting && min_Renting <= 30) {
                                    money_Renting = 15000;
                                } else {
                                    double minute = (double) min_Renting;
                                    money_Renting = (int) (15000 + 4000 * Math.ceil((minute - 30) / 15));
                                }
                            }
                        }
                        if (BikeType.equals("xe điện")){
                            if (min_Renting<=10  && min_Renting>=0){
                                money_Renting = 0;
                            }
                            else {
                                if (10 < min_Renting && min_Renting <= 30) {
                                    money_Renting = 15000;
                                } else {
                                    double minute = (double) min_Renting;
                                    money_Renting = (int) (15000 + 4000 * Math.ceil((minute - 30) / 15));
                                }
                            }
                        }



                        pS = connection.prepareStatement("UPDATE bike SET ID_Station = ?, isLocked = 2 where ID_Bike = ?");
                        pS.setInt(1, id_station);
                        pS.setInt(2, id_bike);
                        pS.execute();
                        interbankController interbank = new interbankController();
                        interbank.addMoney(return_Deposit);
                        int money_left = interbank.getMoney();


                        if (money_Renting<money_left){
                            interbank.subMoney(money_Renting);
                        }
                        else{
                            interbank.resetMoney();
                            interbank.subMoney(money_Renting);
                        }


//                        JFrame mainFrame = new JFrame();
//                        JOptionPane.showMessageDialog(mainFrame, "Thanh toán thành công");
                        Alert m = new Alert(Alert.AlertType.INFORMATION);
                        m.setTitle("Thông báo!");
                        m.setHeaderText("Thanh toán thành công");
                        m.show();
                        return;
                    }

                    ///Neu dang khoa thi lay timerentingbike
                    else{
                        if (!LockorNot.equals("2")){
                            pS = connection.prepareStatement("SELECT * from transaction where ID_Transaction = ?");
                            pS.setInt(1, last_transaction);
                            resultSet = pS.executeQuery();
                            resultSet.next();
                            TimeRenting = resultSet.getString("TimeRentingBike");


                            int min_Renting = toMins(TimeRenting);
                            System.out.println(min_Renting);
                            if (BikeType.equals("xe đạp")){
//
                                if (min_Renting<10){
                                    System.out.println("Nho hon 10");
                                    money_Renting = 0;
                                }
                                else {
                                    if (10 <= min_Renting && min_Renting <= 30) {
                                        System.out.println("Nho hon 30 lon hon 10");
                                        money_Renting = 10000;
                                    } else {
                                        System.out.println(">30");
                                        double minute = (double) min_Renting;
                                        money_Renting = (int) (10000 + 3000 * Math.ceil((minute - 30) / 15));
                                    }
                                }
                            }
                            if (BikeType.equals("xe đạp đôi")){
                                if (min_Renting<=10  && min_Renting>=0){
                                    money_Renting = 0;
                                }
                                else {
                                    if (10 < min_Renting && min_Renting <= 30) {
                                        money_Renting = 15000;
                                    } else {
                                        double minute = (double) min_Renting;
                                        money_Renting = (int) (15000 + 4500 * Math.ceil((minute - 30) / 15));
                                    }
                                }
                            }
                            if (BikeType.equals("xe điện")){
                                if (min_Renting<=10  && min_Renting>=0){
                                    money_Renting = 0;
                                }
                                else {
                                    if (10 < min_Renting && min_Renting <= 30) {
                                        money_Renting = 15000;
                                    } else {
                                        double minute = (double) min_Renting;
                                        money_Renting = (int) (15000 + 4500 * Math.ceil((minute - 30) / 15));
                                    }
                                }
                            }




                            pS = connection.prepareStatement("UPDATE bike SET ID_Station = ?, isLocked = 2 where ID_Bike = ?");
                            pS.setInt(1, id_station);
                            pS.setInt(2, id_bike);
                            pS.execute();
                            interbankController interbank = new interbankController();

                            int money_left = interbank.getMoney();


                            if (money_Renting<money_left){
                                interbank.subMoney(money_Renting);
                            }
                            else{
                                interbank.resetMoney();
                                interbank.subMoney(money_Renting);
                            }


//                            JFrame mainFrame = new JFrame();
//                            JOptionPane.showMessageDialog(mainFrame, "Thanh toán thành công");
                            Alert m = new Alert(Alert.AlertType.INFORMATION);
                            m.setTitle("Thông báo!");
                            m.setHeaderText("Thanh toán thành công");
                            m.show();
                            return;
                        }
                        else {
//                            JFrame mainFrame = new JFrame();
//                            JOptionPane.showMessageDialog(mainFrame, "Bạn đã trả xe");
                            Alert m = new Alert(Alert.AlertType.INFORMATION);
                            m.setTitle("Thông báo!");
                            m.setHeaderText("Bạn đã trả xe");
                            m.show();
                            return;
                        }
                    }


                }
            }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //    @SneakyThrows
//    public void setDetailBike(bike b) {
//        idBike=b.getIdBike();
//
//        loadData();
//
//
//
//
//
//    }
    @FXML
    private void refreshTable() {
        try {



            preparedStatement_chooseBike = connection_chooseBike.prepareStatement(STATION_QUERY_LAY_THONG_TIN);
            resultSet_chooseBike = preparedStatement_chooseBike.executeQuery();

            while (resultSet_chooseBike.next()){
                String soLuongXe="1";
                int ids=1;
                stationList_chooseBike.add(new station(
                        resultSet_chooseBike.getInt("ID_Station"),
                        resultSet_chooseBike.getString("StationName"),
                        resultSet_chooseBike.getString("LocationName")
                ));

                table.setItems(stationList_chooseBike);


            }


        } catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    @FXML
    private void loadData() throws SQLException {
        connection_chooseBike = DbUtil.getInstance().getConnection();
        refreshTable();
        sothutu.setCellValueFactory(new PropertyValueFactory<station,Integer>("id"));
        tenbaixe.setCellValueFactory(new PropertyValueFactory<station,String>("tenBaiXe"));
        vitribaixe.setCellValueFactory(new PropertyValueFactory<station,String>("viTriBaiXe"));



        table.setItems(stationList_chooseBike);
    }
    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();

    }


    @FXML
    private void chooseStation(ActionEvent e)  throws IOException {

        station selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert m = new Alert(Alert.AlertType.INFORMATION);
            m.setTitle("Thông báo!");
            m.setHeaderText("Không bãi xe nào được chọn.");
            m.setContentText("Vui lòng chọn lại.");
            m.show();
            return;
        }
        System.out.println(selected.getId());


        chooseStationController controller = new chooseStationController();




        try {



            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * from transaction");
            resultSet = preparedStatement.executeQuery();
            int rsCount = 0;
            while (resultSet.next()) {
                if (resultSet.isLast()) {
                    int last_transaction = (resultSet.getInt("ID_Transaction"));
                    int id_bike = (resultSet.getInt("ID_Bike"));
                    String creditCardNumber = resultSet.getString("CreditCardNumber");




                    preparedStatement = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
                    preparedStatement.setInt(1, id_bike);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    System.out.println("Tien coc");
                    System.out.println(return_Deposit);
                    interbankController interbank = new interbankController();
                    interbank.addMoney(return_Deposit);



                    preparedStatement = connection.prepareStatement("SELECT * from card where CreditCardNumber = ?");
                    preparedStatement.setString(1, creditCardNumber);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();


                }
            }

        }
        catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }

        payment(selected.getId());

    }



}