/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Win7
 */
public class BaseConnection {
    public static Connection getConnection(){
        Connection conn=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/animal", "root", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void closeRes(Connection conn,PreparedStatement ps){
        try {
            if(ps!=null){
            ps.close();
            }
            if(conn!=null){
                conn.close();
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void closeRes(Connection conn,PreparedStatement ps,ResultSet rs){
        try {
            if(ps!=null){
            ps.close();
            }
            if(conn!=null){
                conn.close();
            }
            if(rs!=null){
            rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}
