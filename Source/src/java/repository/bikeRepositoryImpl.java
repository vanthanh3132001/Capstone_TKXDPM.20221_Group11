package repository;
import utility.DbUtil;
import utility.SQLCommand;
import static utility.SQLCommand.COUNT_BIKE_IN_STATION;
import java.sql.*;
public class bikeRepositoryImpl implements  bikeRepository{
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private CallableStatement cstmt = null;
    private Connection conn = null;

    @Override
    public int SumBike(int id) {
        int SumBike = 0;
        String ids= String.valueOf(id);
        try {
            conn = DbUtil.getInstance().getConnection();
            pstmt = conn.prepareStatement(COUNT_BIKE_IN_STATION+ids);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                SumBike = SumBike+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return SumBike;


    }
}
