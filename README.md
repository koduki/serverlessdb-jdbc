README
=======

This is JDBC driver for [serverlessdb](https://github.com/koduki/serverlessdb).

How to use
---

```java
var url = "jdbc:serverlessdb://http://localhost:8080/mydb";

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
```


MEMO
---

```bash
mvn com.github.johnpoth:jshell-maven-plugin:1.1:run
SQL="SELECT name FROM sample_tbl" jshell query.java
```
