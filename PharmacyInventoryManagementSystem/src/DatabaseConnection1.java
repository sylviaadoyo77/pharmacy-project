/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hp
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection1 {
    private static final String URL = "jdbc:mysql://localhost/pharmacy_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection connect(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Database connected successfuly!");
            
        } catch(ClassNotFoundException | SQLException e){
            System.out.println("Connection Failed!" + e.getMessage());
        }
        return conn;
    }
    public static void main(String[] args){
        connect();
    }
    
}
