package app;


import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author koduki
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        var url = "jdbc:serverlessdb://http://172.28.134.216:8080/testdb";

        Class.forName("cn.orz.pascal.serverlessdb.jdbc.ServerlessDriver");
        try (var con = DriverManager.getConnection(url); var st = con.createStatement()) {
            st.execute("INSERT DUMMY SQL");
            try (var rs = st.executeQuery("SELECT name FROM sample_tbl")) {
                while (rs.next()) {
                    System.out.println("rs[1]=" + rs.getString(1));
                }
            }
        }
    }
}
