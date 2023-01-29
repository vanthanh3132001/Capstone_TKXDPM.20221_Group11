package utility;

public class SQLCommand {
    //luu cac cau truy van sql
    public static String STATION_QUERY_LAY_THONG_TIN = "SELECT * FROM station";
    public static String SUM_BIKE_QUERY_TONG_THUONG_TRU = "SELECT COUNT(*) FROM bike WHERE ID_Station = ";

    public static String STATION_QUERY_LOAD_DATA = "SELECT b.*, s.StationName FROM `bike` b, `station` s WHERE b.ID_Bike = s.ID_Station";

    public static String BIKE_IN_STATION_QUERY_LAY_THONG_TIN = "SELECT * FROM bike";

    public static String CARD_QUERY_LAY_THONG_TIN = "SELECT * FROM card";

    public static String TRANSACTION_QUERY_INSERT_TRANSACTION="INSERT INTO `transaction`( `CreditCardNumber`, `ID_Bike`, `Date_Transaction`, `TimeStart`, `LatestUnlockTime`, `ContentTransaction`, `TimeRentingBike`) VALUES (?,?,?,?,?,?,?)";

    public static String BIKE_UPDATE_TRANG_THAI_QUERY =  "UPDATE `bike` SET " +

            "`isLocked`=?  WHERE ID_Bike  = ";

    public static String COUNT_BIKE_IN_STATION =  "SELECT * FROM `bike` WHERE ID_Station=";
}


