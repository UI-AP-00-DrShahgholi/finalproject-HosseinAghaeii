package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
    String URL = "jdbc:mysql://localhost/finalProject";
    String userName = "root";
    String password = "123";
    Connection con = DriverManager.getConnection(URL, userName, password);

    public SQLConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public boolean executeSQL(String SQlCmd) {
        boolean result;
        try {
            Statement s = con.prepareStatement(SQlCmd);
            s.execute(SQlCmd);
            result=true;

        } catch (Exception ex) {
            result=false;
        }
        return result;
    }
}
