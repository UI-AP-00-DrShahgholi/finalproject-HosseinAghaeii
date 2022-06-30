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

    //------------------------------------------------------------------------------

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

    public boolean checkANumber(String accountNumber,int type)throws SQLException{
        boolean check=false;
        if(type==1){
        ResultSet rs1= SQLLoad("SELECT ANUmber FROM CAccount");
            while (rs1.next()){
                if (rs1.getString("ANumber").equals(accountNumber)){
                    check=true;
                    break;
                }
            }
        }
        if(type==2){
        ResultSet rs2= SQLLoad("SELECT ANUmber FROM GHAccount");
            while (rs2.next()){
                if (rs2.getString("ANumber").equals(accountNumber)){
                    check=true;
                    break;
                }
            }
        }
        if(type==3){
        ResultSet rs3= SQLLoad("SELECT ANUmber FROM SAccount");
            while (rs3.next()){
                if (rs3.getString("ANumber").equals(accountNumber)){
                    check=true;
                    break;
                }
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
//--------------------------------------------------------------------------------------------------------------------


    public String findName(String NCode) throws SQLException {
        String name="";
        String SQlCmd = String.format("SELECT name FROM User WHERE nationalCode = %s", NCode);
        ResultSet rs = SQLLoad(SQlCmd);
        while (rs.next()) {
            name = rs.getString("name");
        }
        return name;
    }

    public int findBalance(String ANumber,int type) throws SQLException{
        int balance=0;
        if (type==1) {
            String SQlCmd = String.format("SELECT balance FROM CAccount WHERE ANumber = %s", ANumber);
            ResultSet rs = SQLLoad(SQlCmd);
            while (rs.next()) {
                balance = rs.getInt("balance");
            }
        }
        if (type==2) {
            String SQlCmd = String.format("SELECT balance FROM GHAccount WHERE ANumber = %s", ANumber);
            ResultSet rs = SQLLoad(SQlCmd);
            while (rs.next()) {
                balance = rs.getInt("balance");
            }
        }
        if (type==3) {
            String SQlCmd = String.format("SELECT balance FROM SAccount WHERE ANumber = %s", ANumber);
            ResultSet rs = SQLLoad(SQlCmd);
            while (rs.next()) {
                balance = rs.getInt("balance");
            }
        }
        return balance;
    }

    int getWallet(String nCode) throws Exception{
        int wallet=0;
        String SQLCmd=String.format("SELECT walletMount FROM User WHERE nationalCode = %s  ",nCode );
        ResultSet rs=SQLLoad(SQLCmd);
        while (rs.next()){
            wallet=rs.getInt("walletMount");
        }
        return wallet;

    }

    int getValue(String SACode) throws SQLException {
        int value=0;
        String SQLCmd=String.format("SELECT value FROM Estate WHERE SACode = %s  ",SACode );
        ResultSet rs=SQLLoad(SQLCmd);
        while (rs.next()){
            value=rs.getInt("value");
        }
        return value;
    }


}
