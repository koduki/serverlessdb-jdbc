
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author koduki
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        var url = "jdbc:myjdbc://localhost:80/testdb";

        Class.forName("cn.orz.pascal.jdbc.MyDriver");
        try (var con = DriverManager.getConnection(url); var st = con.createStatement()) {
            st.execute("INSERT DUMMY SQL");
            try (var rs = st.executeQuery("SELECT DUMMY SQL")) {
                while (rs.next()) {
                    System.out.println("rs[1]=" + rs.getString(1));
                }
            }
        }
    }
}
