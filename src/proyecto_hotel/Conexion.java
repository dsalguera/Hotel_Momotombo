/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_hotel;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    private String username = 
            "root";
            //"epiz_21886301";
    private String password = 
            "paramore1";
            //"Windowsxp123456";
    private String string_connection = 
            "jdbc:mysql://localhost:3306/Hotel";
            //"jdbc:mysql://sql308.epizy.com:3306/epiz_21886301_Hotel";

    public Conexion() {
    }

    public Conexion(String username, String password, String string_connection) {
        this.username = username;
        this.password = password;
        this.string_connection = string_connection;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getString_connection() {
        return string_connection;
    }
    
    
}
