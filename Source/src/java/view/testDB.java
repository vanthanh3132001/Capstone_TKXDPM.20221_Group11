package view;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
class main2 {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost/ebike_capstone","root", "");
            System.out.println(java.time.LocalDate.now());
            System.out.println(java.time.LocalTime.now());
            System.out.print("Database is connected !");
            conn.close();

        }
        catch(Exception e) {
            System.out.print("Do not connect to DB - Error:"+e);
        }
    }
}
