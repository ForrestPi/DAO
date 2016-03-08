/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import bean.Animals;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Win7
 */
public class BaseDAO {

    public ArrayList getList(Class cl) {
        ArrayList ar = new ArrayList();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select *  from " + cl.getSimpleName();
        Field[] fi = cl.getDeclaredFields();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object ob = cl.newInstance();
                for (Field ff : fi) {
                    ff.setAccessible(true);
                    ff.set(ob, rs.getObject(ff.getName()));
                }
                ar.add(ob);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(conn, ps, rs);
        }
        return ar;
    }

    public Object getObById(Class cl, int id) {
        Object ob = null;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Field[] fields = cl.getDeclaredFields();
        String sql = "select * from " + cl.getSimpleName() + " where " + fields[0].getName() + " = " + id;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ob = cl.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(ob, rs.getObject(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseConnection.closeRes(conn, ps, rs);
        }
        return ob;
    }

    public ArrayList getListBySome(Class cl, String name, Object value) {
        ArrayList ar = new ArrayList();
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Field[] fields = cl.getDeclaredFields();
        String sql = "select * from " + cl.getCanonicalName() + " where " + name + " = '" + value + "'";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object ob = cl.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(ob, rs.getObject(field.getName()));
                }
                ar.add(ob);
            }
        } catch (Exception e) {
        } finally {
            BaseConnection.closeRes(conn, ps, rs);
        }
        return ar;
    }

    
    
    
    public boolean insert(Object ob) {
        boolean b = false;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        Class cl = ob.getClass();
        Field[] fi = cl.getDeclaredFields();
        //"insert into Animals (name,age,anid) values (?,?,?)";
        String sql = "insert into " + cl.getSimpleName() + " (";
        for (int i = 1; i < fi.length; i++) {
            sql = sql + fi[i].getName();
            if (i != fi.length - 1) {
                sql = sql + ",";
            }
        }
        sql = sql + ") values (";
        for (int i = 1; i < fi.length; i++) {
            sql = sql + " ? ";
            if (i != fi.length - 1) {
                sql = sql + ",";
            }
        }
        sql = sql + ")";
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 1; i < fi.length; i++) {
                fi[i].setAccessible(true);
                ps.setObject(i, fi[i].get(ob));
            }
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

    public boolean insert1(Object ob) {
        boolean b = false;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        Class cl = ob.getClass();
        Field[] fi = cl.getDeclaredFields();
        //"insert into Animals (name,age,anid) values (?,?,?)";
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(cl.getSimpleName());
        sb.append(" (");
        String sql = "insert into " + cl.getSimpleName() + " (";
        for (int i = 1; i < fi.length; i++) {
            sb.append(fi[i].getName());
            if (i != fi.length - 1) {
                sb.append(",");
            }
        }
        sb.append(") values (");
        for (int i = 1; i < fi.length; i++) {
            sb.append("?");
            if (i != fi.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        sql = sb.toString();
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 1; i < fi.length; i++) {
                fi[i].setAccessible(true);
                ps.setObject(i, fi[i].get(ob));
            }
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

    public boolean update(Object ob) {
        boolean b = false;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        Class cl = ob.getClass();
        Field[] fi = cl.getDeclaredFields();
        //"update animals set name = ?,age = ?,anid = ? where id = ?";
        StringBuffer sb = new StringBuffer();
        sb.append("update ");
        sb.append(cl.getSimpleName());
        sb.append(" set ");
        for (int i = 1; i < fi.length; i++) {
            fi[i].setAccessible(true);
            sb.append(fi[i].getName());
            sb.append(" =? ");
            if (i != fi.length - 1) {
                sb.append(" , ");
            }
        }
        sb.append(" where ");
        sb.append(fi[0].getName());
        sb.append("=?");
        try {
            ps = conn.prepareStatement(sb.toString());
            for (int i = 1; i < fi.length; i++) {
                fi[i].setAccessible(true);
                ps.setObject(i, fi[i].get(ob));
            }
            fi[0].setAccessible(true);
            ps.setObject(fi.length, fi[0].get(ob));
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
    
    public boolean delete(Class cl,int id){
        boolean b = false;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        Field[] fi=cl.getDeclaredFields();
        String sql="delete from "+cl.getSimpleName()+" where "+fi[0].getName()+"=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setObject(1, id);
            int a=ps.executeUpdate();
            if(a>0){
                b=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseConnection.closeRes(conn, ps);
        }
        return b;
    }
    
    public boolean deleteBySome(Class cl,String name, Object value){
        boolean b = false;
        Connection conn = BaseConnection.getConnection();
        PreparedStatement ps = null;
        Field[] fi=cl.getDeclaredFields();
        String sql="delete from "+cl.getSimpleName()+" where "+name+"=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setObject(1, value);
            int a=ps.executeUpdate();
            if(a>0){
                b=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseConnection.closeRes(conn, ps);
        }
        return b;
    }

    public static void main(String[] args) {
        BaseDAO bd = new BaseDAO();
//        Animals an = new Animals();
//        an.setName("金九胖");
//        an.setAge(15);
//        an.setAnId(2);
//        an.setId(3);
//        boolean b = bd.update(an);
//        System.out.println(b);
        
        //System.err.println(bd.delete(Animals.class, 3));
        System.out.println(bd.deleteBySome(Animals.class,"name","金三胖"));
//        ArrayList<Animals> ar=bd.getList(Animals.class);
//        for (Animals an : ar) {
//            System.out.println(an.getName());
//        }
//        Animals an = (Animals)bd.getObById(Animals.class, 1);
//        System.out.println(an.getName());
//        ArrayList<Animals> arrayList=bd.getListBySome(Animals.class, "age", "12");
//        for (Animals animal : arrayList) {
//            System.out.println(an.getName());
//        }
    }
}
