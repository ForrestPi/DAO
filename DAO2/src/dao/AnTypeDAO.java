/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.AnType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.BaseConnection;

/**
 *
 * @author Win7
 */
public class AnTypeDAO {
    public ArrayList<AnType> getList(){
        ArrayList<AnType> ar = new ArrayList<>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql="select * from antype";
        try {
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {
                AnType an = new AnType();
                an.setAnId(rs.getInt("anid"));
                an.setAnName(rs.getString("anName"));
                ar.add(an);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseConnection.closeRes(conn, ps,rs);
        }
        return ar;
    } 
}
