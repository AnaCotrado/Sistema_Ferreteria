package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    static String Login = "uk43ty6mn2lmvkz9";
    static String Password = "h9hRm9nEYT3sr7pIru7s";
    static String url = "jdbc:mysql://btnqhyzqrjxmq3zqrjpo-mysql.services.clever-cloud.com:3306/btnqhyzqrjxmq3zqrjpo?zeroDateTimeBehavior=CONVERT_TO_NULL";
    
    public Conexion(){
    }
    
    public Connection Conectar(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, Login, Password);
        }catch(ClassNotFoundException | SQLException ex){
           JOptionPane.showMessageDialog(null,"Error en la conexion con la BD: "+ex.getMessage());
        }     
        return conn;
    }
}
