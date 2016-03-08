/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bean.Animals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.BaseConnection;

/**
 *
 * @author Win7
 */
public class AnimalsDAO {

    public ArrayList<Animals> getList() {
        ArrayList<Animals> ar = new ArrayList<Animals>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select *  from Animals";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Animals an = new Animals();
                an.setId(rs.getInt("id"));
                an.setName(rs.getString("name"));
                an.setAge(rs.getInt("age"));
                an.setAnId(rs.getInt("anid"));
                ar.add(an);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(conn, ps, rs);
        }
        return ar;
    }

    public boolean insert(Animals an) {
        boolean b = false;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        String sql = "insert into Animals (name,age,anid) values (?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, an.getName());
            ps.setInt(2, an.getAge());
            ps.setInt(3, an.getAnId());
            int a = ps.executeUpdate();
            if (a > 0) {
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(conn, ps);
        }
        return b;
    }

    public boolean update(Animals an) {
        boolean b = false;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        String sql = "update animals set name = ?,age = ?,anid = ? where id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, an.getName());
            ps.setInt(2, an.getAge());
            ps.setInt(3, an.getAnId());
            ps.setInt(4, an.getId());
            int a = ps.executeUpdate();
            if (a > 0) {
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(conn, ps);
        }
        return b;
    }

    public ArrayList<Animals> getList1(){
        ArrayList<Animals> ar=new ArrayList<>();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs=null;
        String sql = "select * from animals a, antype t where a.anId=t.anId";
        try {
            ps = conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {
                Animals an=new Animals();
                an.setName(rs.getString("name"));
                an.setAge(rs.getInt("age"));
                an.setAnName(rs.getString("anname"));
                ar.add(an);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseConnection.closeRes(conn, ps,rs);
        }
        return ar; 
    }
    
    public static void main(String[] args) {
        Animals an = new Animals();
        an.setName("金九胖");
        an.setAge(15);
        an.setAnId(2);
        boolean b = new AnimalsDAO().insert(an);
        System.out.println(b);
    }
}
