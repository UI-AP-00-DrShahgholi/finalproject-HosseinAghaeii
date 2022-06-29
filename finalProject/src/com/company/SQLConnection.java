package com.company;

import java.sql.*;

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
            result = true;

        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public ResultSet SQLLoad(String SQlCmd) throws SQLException {

        ResultSet rs = null;
        Statement s = con.prepareStatement(SQlCmd);
        rs = s.executeQuery(SQlCmd);
        return rs;
    }

    public boolean checkNCode(String nationalCode) throws SQLException {
        boolean check=false;
        ResultSet rs= SQLLoad("SELECT nationalCode FROM User");
        while (rs.next()){
            if (rs.getString("nationalCode").equals(nationalCode)){
                check=true;
                break;
            }
        }
        return check;
    }

    public boolean checkNCodeAndSACode(String nationalCode,String SACode) throws SQLException{
        boolean check=false;
        ResultSet rs=SQLLoad("SELECT ownerNCode , SACode FROM Estate ");
        while (rs.next()){
            if (rs.getString("ownerNCode").equals(nationalCode) && rs.getString("SACode").equals(SACode)){
                check=true;
                break;
            }
        }
        return check;
    }

    public String findName(String NCode) throws SQLException {
        String name="";
        String SQlCmd = String.format("SELECT name FROM User WHERE nationalCode = %s", NCode);
        ResultSet rs = SQLLoad(SQlCmd);
        while (rs.next()) {
            name = rs.getString("name");
        }
        return name;
    }
}
