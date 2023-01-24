package utility;

public class SQLCommand {
    //luu cac cau truy van sql
    public static String STATION_QUERY_LAY_THONG_TIN = "SELECT * FROM station";
    public static String SUM_BIKE_QUERY_TONG_THUONG_TRU = "SELECT COUNT(*) FROM bike WHERE ID_Station = ";

    public static String STATION_QUERY_LOAD_DATA = "SELECT b.*, s.StationName FROM `bike` b, `station` s WHERE b.ID_Bike = s.ID_Station";

    public static String BIKE_IN_STATION_QUERY_LAY_THONG_TIN = "SELECT * FROM bike";
}
