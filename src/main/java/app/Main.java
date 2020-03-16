package app;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author koduki
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        var url = "jdbc:serverlessdb://http://172.28.134.216:8080/mydb";

        Class.forName("cn.orz.pascal.serverlessdb.jdbc.ServerlessDriver");
        try (var con = DriverManager.getConnection(url); var st = con.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS sample_tbl (name varchar(255))");
            st.executeUpdate("INSERT INTO sample_tbl(name) values('Nanoha')");

            try (var rs = st.executeQuery("SELECT name FROM sample_tbl")) {
                while (rs.next()) {
                    System.out.println("rs[1]=" + rs.getString(1));
                }
            }

            try (var rs = st.executeQuery("SELECT count(1) FROM sample_tbl")) {
                while (rs.next()) {
                    System.out.println("count=" + rs.getInt(1));
                }
            }
        }
    }
}
