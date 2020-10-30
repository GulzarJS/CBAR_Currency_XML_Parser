/*
 *  Created by Gulzar Safar on 10/30/2020
 */

package database;


import java.sql.*;

public class Database {

    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    public Database() {
        try {
            this.connection = connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection connect() {

        try {

            DatabaseConnection OracleDataBase = new DatabaseConnection();

            Class.forName(OracleDataBase.getJdbcDriver());


            connection = DriverManager.getConnection(OracleDataBase.getJdbcUrl(), OracleDataBase.getUsername()
                    , OracleDataBase.getPassword());
            connection.setAutoCommit(false);
            return getConnection();

        }catch (ClassNotFoundException e) {
            System.out.println("JDBC driver couldn't find");
            e.printStackTrace();
        }catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("database ile bagli xeta bas verdi, mesaj = " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("this is null connection");
        return null;
    }

    public void close() {
        try {
            if (this.rs != null) {
                this.rs.close();
            }

            if(this.ps != null) {
                this.ps.close();
            }

            if(this.connection != null) {
                this.connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        return connection;
    }



    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
}

