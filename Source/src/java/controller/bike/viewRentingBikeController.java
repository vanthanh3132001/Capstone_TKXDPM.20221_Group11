package controller.bike;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import entity.station;
import utility.DbUtil;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class viewRentingBikeController{

//    private String TimeRenting;

    ObservableList<station> stationList = FXCollections.observableArrayList();

    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;

    private String TimeRenting;
    private int min_Renting;
    private int money_Renting;
    private int toMins(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }



    public void pauseRentingBikeAction(ActionEvent eventPause) throws IOException {

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

                    PreparedStatement pS = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
                    pS.setInt(1, id_bike);
                    resultSet = pS.executeQuery();
                    resultSet.next();
                    String LockorNot = resultSet.getString("isLocked");
                    if (LockorNot.equals("1")) {

                        Alert m = new Alert(Alert.AlertType.INFORMATION);
                        m.setTitle("Thông báo!");
                        m.setHeaderText("Xe đang khóa");
                        m.show();
                        return;
                    } else {
                        if (!LockorNot.equals("2")){
                            pS = connection.prepareStatement("UPDATE transaction SET TimeRentingBike = ADDTIME(SUBTIME(current_time,LatestUnlockTime),TimeRentingBike) where ID_Transaction = ?");
                            pS.setInt(1, last_transaction);
                            pS.execute();

                            pS = connection.prepareStatement("UPDATE bike SET isLocked = 1 where ID_Bike = ?");
                            pS.setInt(1, id_bike);
                            pS.execute();

//                            JFrame mainFrame = new JFrame();
//                            JOptionPane.showMessageDialog(mainFrame, "Khóa xe thành công");
                            Alert m = new Alert(Alert.AlertType.INFORMATION);
                            m.setTitle("Thông báo!");
                            m.setHeaderText("Khóa xe thành công");
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


    public void openRentingBikeAction(ActionEvent eventOpen) throws IOException {




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

                    PreparedStatement pS = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
                    pS.setInt(1, id_bike);
                    resultSet = pS.executeQuery();
                    resultSet.next();
                    String LockorNot = resultSet.getString("isLocked");
                    if (!LockorNot.equals("1") && !LockorNot.equals("2")) {
                        Alert m = new Alert(Alert.AlertType.INFORMATION);
                        m.setTitle("Thông báo!");
                        m.setHeaderText("Xe đang ở trạng thái mở khóa");
                        m.show();
                        return;
                    } else {
                        if (!LockorNot.equals("2")) {
                            pS = connection.prepareStatement("UPDATE transaction\n" +
                                    "          SET LatestUnlockTime = current_time\n" +
                                    "          where ID_Transaction = ?");
                            pS.setInt(1, last_transaction);
                            pS.execute();

                            pS = connection.prepareStatement("UPDATE bike SET isLocked = 0 where ID_Bike = ?");
                            pS.setInt(1, id_bike);
                            pS.execute();
//                            JFrame mainFrame = new JFrame();
//                            JOptionPane.showMessageDialog(mainFrame, "Mở khóa xe thành công");
                            Alert m = new Alert(Alert.AlertType.INFORMATION);
                            m.setTitle("Thông báo!");
                            m.setHeaderText("Mở khóa xe thành công");
                            m.show();
                            return;
                        }
                        else{
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
//
//    private int money_Renting;
//
//    private int min_Renting;
//
//    private int return_Deposit;
//    public  void payment(int id_station) throws IOException {
//
//
//        try {
//            connection = DbUtil.getInstance().getConnection();
//            preparedStatement = connection.prepareStatement("SELECT * from transaction");
//            resultSet = preparedStatement.executeQuery();
//            int rsCount = 0;
//            while(resultSet.next())
//            {
//                if (resultSet.isLast()) {
//                    int last_transaction = (resultSet.getInt("ID_Transaction"));
//                    int id_bike = (resultSet.getInt("ID_Bike"));
//
//                    ///Update ID_Station va isLocked khi ve bai
//                    PreparedStatement pS;
//
//                    pS = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
//                    pS.setInt(1, id_bike);
//                    resultSet = pS.executeQuery();
//                    resultSet.next();
//                    return_Deposit = (int) resultSet.getDouble("Deposit");
//                    String BikeType = resultSet.getString("BikeType");
//                    String LockorNot = resultSet.getString("isLocked");
//                    ///Neu xe dang mo khoa thi lay cur_time - latestunlock + timerentingbike
//                    if (!LockorNot.equals("1") && !LockorNot.equals("2")) {
//                        System.out.println("Khoa");
//                        pS = connection.prepareStatement("UPDATE transaction SET TimeRentingBike = ADDTIME(SUBTIME(current_time,LatestUnlockTime),TimeRentingBike) where ID_Transaction = ?");
//                        pS.setInt(1, last_transaction);
//                        pS.execute();
//
//                        pS = connection.prepareStatement("SELECT * from transaction where ID_Transaction = ?");
//                        pS.setInt(1, last_transaction);
//                        resultSet = pS.executeQuery();
//                        resultSet.next();
//                        TimeRenting = resultSet.getString("TimeRentingBike");
//
//
//                        min_Renting = toMins(TimeRenting);
//
//                        if (BikeType.equals("xe đạp")){
//                            System.out.println(min_Renting);
////
//                            if (min_Renting<=10  && min_Renting>=0){
//                                System.out.println("nho hon 10");
//                                money_Renting = 0;
//                            }
//                            else {
//
//                                if (10 < min_Renting && min_Renting <= 30) {
//                                    System.out.println("10 30");
//                                    money_Renting = 10000;
//                                } else {
//                                    double minute = (double) min_Renting;
//                                    System.out.println(">30");
//                                    money_Renting = (int) (10000 + 3000 * Math.ceil((minute - 30) / 15));
//                                }
//                            }
//                        }
//                        if (BikeType.equals("xe đạp đôi")){
//                            if (min_Renting<=10  && min_Renting>=0){
//                                money_Renting = 0;
//                            }
//                            else {
//                                if (10 < min_Renting && min_Renting <= 30) {
//                                    money_Renting = 15000;
//                                } else {
//                                    double minute = (double) min_Renting;
//                                    money_Renting = (int) (15000 + 4000 * Math.ceil((minute - 30) / 15));
//                                }
//                            }
//                        }
//                        if (BikeType.equals("xe điện")){
//                            if (min_Renting<=10  && min_Renting>=0){
//                                money_Renting = 0;
//                            }
//                            else {
//                                if (10 < min_Renting && min_Renting <= 30) {
//                                    money_Renting = 15000;
//                                } else {
//                                    double minute = (double) min_Renting;
//                                    money_Renting = (int) (15000 + 4000 * Math.ceil((minute - 30) / 15));
//                                }
//                            }
//                        }
//
//
//
//                        pS = connection.prepareStatement("UPDATE bike SET ID_Station = ?, isLocked = 2 where ID_Bike = ?");
//                        pS.setInt(1, id_station);
//                        pS.setInt(2, id_bike);
//                        pS.execute();
//                        interbankController interbank = new interbankController();
//                        interbank.addMoney(return_Deposit);
//                        int money_left = interbank.getMoney();
//
//
//                        if (money_Renting<money_left){
//                            interbank.subMoney(money_Renting);
//                        }
//                        else{
//                            interbank.resetMoney();
//                            interbank.subMoney(money_Renting);
//                        }
//
//
//                        JFrame mainFrame = new JFrame();
//                        JOptionPane.showMessageDialog(mainFrame, "Thanh toán thành công");
//                    }
//
//                    ///Neu dang khoa thi lay timerentingbike
//                    else{
//                        if (!LockorNot.equals("2")){
//                            pS = connection.prepareStatement("SELECT * from transaction where ID_Transaction = ?");
//                            pS.setInt(1, last_transaction);
//                            resultSet = pS.executeQuery();
//                            resultSet.next();
//                            TimeRenting = resultSet.getString("TimeRentingBike");
//
//
//                            int min_Renting = toMins(TimeRenting);
//                            System.out.println(min_Renting);
//                            if (BikeType.equals("xe đạp")){
////
//                                if (min_Renting<10){
//                                    System.out.println("Nho hon 10");
//                                    money_Renting = 0;
//                                }
//                                else {
//                                    if (10 <= min_Renting && min_Renting <= 30) {
//                                        System.out.println("Nho hon 30 lon hon 10");
//                                        money_Renting = 10000;
//                                    } else {
//                                        System.out.println(">30");
//                                        double minute = (double) min_Renting;
//                                        money_Renting = (int) (10000 + 3000 * Math.ceil((minute - 30) / 15));
//                                    }
//                                }
//                            }
//                            if (BikeType.equals("xe đạp đôi")){
//                                if (min_Renting<=10  && min_Renting>=0){
//                                    money_Renting = 0;
//                                }
//                                else {
//                                    if (10 < min_Renting && min_Renting <= 30) {
//                                        money_Renting = 15000;
//                                    } else {
//                                        double minute = (double) min_Renting;
//                                        money_Renting = (int) (15000 + 4500 * Math.ceil((minute - 30) / 15));
//                                    }
//                                }
//                            }
//                            if (BikeType.equals("xe điện")){
//                                if (min_Renting<=10  && min_Renting>=0){
//                                    money_Renting = 0;
//                                }
//                                else {
//                                    if (10 < min_Renting && min_Renting <= 30) {
//                                        money_Renting = 15000;
//                                    } else {
//                                        double minute = (double) min_Renting;
//                                        money_Renting = (int) (15000 + 4500 * Math.ceil((minute - 30) / 15));
//                                    }
//                                }
//                            }
//
//
//
//
//                            pS = connection.prepareStatement("UPDATE bike SET ID_Station = ?, isLocked = 2 where ID_Bike = ?");
//                            pS.setInt(1, id_station);
//                            pS.setInt(2, id_bike);
//                            pS.execute();
//                            interbankController interbank = new interbankController();
//
//                            int money_left = interbank.getMoney();
//
//
//                            if (money_Renting<money_left){
//                                interbank.subMoney(money_Renting);
//                            }
//                            else{
//                                interbank.resetMoney();
//                                interbank.subMoney(money_Renting);
//                            }
//
//
//                            JFrame mainFrame = new JFrame();
//                            JOptionPane.showMessageDialog(mainFrame, "Thanh toán thành công");
//                        }
//                        else {
//                            JFrame mainFrame = new JFrame();
//                            JOptionPane.showMessageDialog(mainFrame, "Bạn đã trả xe");
//                        }
//                    }
//
//
//                }
//            }
//
//
//        } catch (SQLException ex) {
//            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }



    public void returnBikeAction(ActionEvent eventOpen) throws IOException {


//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/view/ReturnBike.fxml"));
//
//        chooseBikeController controller = new chooseBikeController();
//        loader.setController(controller);
//
//        Parent RentingBike = loader.load();
//        Scene scene = new Scene(RentingBike);
//        Stage stage = new Stage();
//        stage.initStyle(StageStyle.DECORATED);
//        stage.setResizable(false);
//        stage.setTitle("Return Bike ");
//        stage.setScene(scene);
//        stage.show();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ReturnBike.fxml"));
        Parent thongtinBai = loader.load();
        chooseStationController controller = loader.getController();
//        station selected = stationTable.getSelectionModel().getSelectedItem();
//        if (selected == null) {
//            Alert m = new Alert(Alert.AlertType.INFORMATION);
//            m.setTitle("Thông báo!");
//            m.setHeaderText("Không bai xe nào được chọn.");
//            m.setContentText("Vui lòng chọn lại.");
//            m.show();
//            return;
//        }
//        controller.setListB(selected);
        Stage stage = new Stage();
//        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Thông tin bai");
        Scene scene = new Scene(thongtinBai);
        stage.setScene(scene);
        stage.show();


//        payment(1);
//
//
//        FXMLLoader invoiceLoader = new FXMLLoader();
//        invoiceLoader.setLocation(getClass().getResource("/view/Invoice.fxml"));
//
//        invoiceLoader.setController(controller);
//
//        Parent Invoice = invoiceLoader.load();
//        Scene invoiceScene = new Scene(Invoice);
//        Stage invoiceStage = new Stage();
//        invoiceStage.initStyle(StageStyle.DECORATED);
//        invoiceStage.setResizable(false);
//        invoiceStage.setTitle("Return Bike ");
//        invoiceStage.setScene(invoiceScene);
//        invoiceStage.show();


//        try {
//
//
//
//            connection = DbUtil.getInstance().getConnection();
//            preparedStatement = connection.prepareStatement("SELECT * from transaction");
//            resultSet = preparedStatement.executeQuery();
//            int rsCount = 0;
//            while (resultSet.next()) {
//                if (resultSet.isLast()) {
//                    int last_transaction = (resultSet.getInt("ID_Transaction"));
//                    int id_bike = (resultSet.getInt("ID_Bike"));
//                    String creditCardNumber = resultSet.getString("CreditCardNumber");
//
//
//                    Label thoidiembatdauthue = (Label) invoiceScene.lookup("#thoidiembatdauthue");
//                    thoidiembatdauthue.setText(resultSet.getString("TimeStart"));
//
//                    Label ngaythue = (Label) invoiceScene.lookup("#ngaythue");
//                    ngaythue.setText(resultSet.getString("Date_Transaction"));
//
//                    Label Tongthoigianthue = (Label) invoiceScene.lookup("#tongthoigianthue");
//                    Tongthoigianthue.setText(resultSet.getString("TimeRentingBike"));
//
//                    Label Tienthue = (Label) invoiceScene.lookup("#tienthue");
//                    Tienthue.setText(Integer.toString(money_Renting));
//
//
//
//                    preparedStatement = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
//                    preparedStatement.setInt(1, id_bike);
//                    resultSet = preparedStatement.executeQuery();
//                    resultSet.next();
//                    Label loaixe = (Label) invoiceScene.lookup("#loaixe");
//                    loaixe.setText(resultSet.getString("BikeType"));
//
//                    Label giacoc = (Label) invoiceScene.lookup("#tiencoc");
//                    return_Deposit = (int)(resultSet.getDouble("Deposit"));
//                    System.out.println("Tien coc");
//                    System.out.println(return_Deposit);
//                    interbankController interbank = new interbankController();
//                    interbank.addMoney(return_Deposit);
//                    giacoc.setText(resultSet.getString("Deposit"));
//
//
//                    preparedStatement = connection.prepareStatement("SELECT * from card where CreditCardNumber = ?");
//                    preparedStatement.setString(1, creditCardNumber);
//                    resultSet = preparedStatement.executeQuery();
//                    resultSet.next();
//                    Label ten = (Label) Invoice.lookup("#ten");
//                    ten.setText(resultSet.getString("FullName"));
//
//                }
//            }
//
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void showInvoiceAction(ActionEvent actionEvent) throws IOException, SQLException {
        String BikeType;
        try {
            connection = DbUtil.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * from bike where isLocked!=2");
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
//                JFrame mainFrame = new JFrame();
//                JOptionPane.showMessageDialog(mainFrame, "Bạn chưa trả xe");
                Alert m = new Alert(Alert.AlertType.INFORMATION);
                m.setTitle("Thông báo!");
                m.setHeaderText("Bạn chưa trả xe");
                m.show();
                return;
            }
            else {
                FXMLLoader invoiceLoader = new FXMLLoader();
                viewRentingBikeController controller = new viewRentingBikeController();
                invoiceLoader.setLocation(getClass().getResource("/view/Invoice.fxml"));

                invoiceLoader.setController(controller);
                Parent Invoice = invoiceLoader.load();
                Scene invoiceScene = new Scene(Invoice);
                Stage invoiceStage = new Stage();
                invoiceStage.initStyle(StageStyle.DECORATED);
                invoiceStage.setResizable(false);
                invoiceStage.setTitle("Return Bike ");
                invoiceStage.setScene(invoiceScene);
                invoiceStage.show();


                connection = DbUtil.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT * from transaction");
                resultSet = preparedStatement.executeQuery();
                int rsCount = 0;
                while (resultSet.next()) {
                    if (resultSet.isLast()) {
                        int last_transaction = (resultSet.getInt("ID_Transaction"));
                        int id_bike = (resultSet.getInt("ID_Bike"));
                        String creditCardNumber = resultSet.getString("CreditCardNumber");


                        Label thoidiembatdauthue = (Label) invoiceScene.lookup("#thoidiembatdauthue");
                        thoidiembatdauthue.setText(resultSet.getString("TimeStart"));

                        Label ngaythue = (Label) invoiceScene.lookup("#ngaythue");
                        ngaythue.setText(resultSet.getString("Date_Transaction"));

                        Label Tongthoigianthue = (Label) invoiceScene.lookup("#tongthoigianthue");
                        Tongthoigianthue.setText(resultSet.getString("TimeRentingBike"));


                        preparedStatement = connection.prepareStatement("SELECT * from bike where ID_Bike = ?");
                        preparedStatement.setInt(1, id_bike);
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();

                        BikeType = resultSet.getString("BikeType");
                        Label loaixe = (Label) invoiceScene.lookup("#loaixe");
                        loaixe.setText(resultSet.getString("BikeType"));

                        Label giacoc = (Label) invoiceScene.lookup("#tiencoc");
                        giacoc.setText(resultSet.getString("Deposit"));

                        preparedStatement = connection.prepareStatement("SELECT * from card where CreditCardNumber= ? ");
                        preparedStatement.setString(1, creditCardNumber);
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();
//                        System.out.println(resultSet.getString("FullName"));
                        Label ten = (Label) Invoice.lookup("#ten");
                        ten.setText(resultSet.getString("FullName"));



                        preparedStatement = connection.prepareStatement("SELECT * from transaction where ID_Transaction = ?");
                        preparedStatement.setInt(1, last_transaction);
                        resultSet = preparedStatement.executeQuery();
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

                        Label Tienthue = (Label) invoiceScene.lookup("#tienthue");
                        Tienthue.setText(Integer.toString(money_Renting));
                    }
                }






            }
        }
        catch (SQLException ex) {
            Logger.getLogger(station.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}