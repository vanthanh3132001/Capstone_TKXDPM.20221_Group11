package repository;
import utility.DbUtil;
import utility.SQLCommand;

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

        try {
            conn = DbUtil.getInstance().getConnection();
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM bike WHERE ID_Station ="+id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                SumBike = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtil.releaseResource(rs, stmt, pstmt, cstmt, conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return SumBike;
    }
}
