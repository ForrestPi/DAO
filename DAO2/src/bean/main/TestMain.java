/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.main;

import bean.AnType;
import bean.Animals;
import dao.AnTypeDAO;
import dao.AnimalsDAO;
import java.util.ArrayList;
import util.BaseDAO;

/**
 *
 * @author Win7
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        AnimalsDAO ad = new AnimalsDAO();
//        ArrayList<Animals> ar = ad.getList();
//        for (Animals an : ar) {
//            System.out.println(an.getName());
//        }
//        
//        AnTypeDAO atd = new AnTypeDAO();
//        ArrayList<AnType> arr = atd.getList();
//        for (AnType an : arr) {
//            System.out.println(an.getAnName());
//        }
        
//        ArrayList<Animals> ar=new BaseDAO().getList(Animals.class);
//        for (Animals an : ar) {
//            System.out.println(an.getName());
//        }
        
        ArrayList<Animals> ar = new AnimalsDAO().getList1();
        for (Animals an : ar) {
            System.out.println(an.getName()+" "+an.getAnName());
        }
    }
    
}
