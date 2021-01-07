package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManage {
    //Local
    private static String url = "jdbc:mysql://localhost:3307/userregistration";
    private static String username = "root";
    private static String password = "";
    //Online DB

//     private static String url ="jdbc:mysql://johnny.heliohost.org:3306/fathiim_iHealth?serverTimezone=UTC#";
//     private static String username = "fathiim_root";
//     private static String password = "rootroot";

    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static Connection con;




    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
        //private Connection con = null;
        //private Statement st = null;
        //private ResultSet rs = null;
        //
        //con = ConnectionManage.getConnection();
        //st = con.createStatement();
        //rs = st.executeQuery(sql);
}

}
