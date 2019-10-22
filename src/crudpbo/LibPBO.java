package crudpbo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mr.GG
 */
public class LibPBO {
    public LibPBO(){
        
    }
    private Connection connect(){
        Connection conn = null;
        try{
            String url = "jdbc:mysql://localhost:3306/db_belajar";
            String usr = "root";
            String pwd = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(url, usr, pwd);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void getData(JTable table, String sql){
        this.getData(table, sql, false);
    }
    
    public void getData(JTable table, String sql, boolean number){
        Connection conn = connect();
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ((DefaultTableModel) table.getModel()).setRowCount(0);
            ((DefaultTableModel) table.getModel()).setColumnCount(0);
            int colom = rs.getMetaData().getColumnCount();
            if(number){
                ((DefaultTableModel) table.getModel()).addColumn("No");
            }
            for (int i = 1; i <= colom; i++) {
                ((DefaultTableModel) table.getModel()).addColumn(rs.getMetaData().getColumnName(i));
            }
            int no=1;
            while(rs.next()){
                Object[] row = new Object[colom];
                for (int i = 1; i <= colom; i++) {
                    row[i-1] = rs.getObject(i);
                }
                ArrayList<Object> arr = new ArrayList<>(Arrays.asList(row));
                arr.add(0, no++);
                Object[] obj = arr.toArray();
                ((DefaultTableModel) table.getModel()).insertRow(rs.getRow()-1, (number)? obj : row);
            }
            rs.close();
            st.close();
            conn.close();
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    private HashMap<String, Integer> getLst(JTable table){
        int leng = ((DefaultTableModel) table.getModel()).getColumnCount();
        HashMap<String, Integer> mp = new HashMap<String, Integer>();
        for(int i = 0; i<leng;i++){
            String nama = ((DefaultTableModel) table.getModel()).getColumnName(i);
            mp.put(nama, i);
        }
        return mp;
    }
    
    public boolean executeSQL(String sql){
        Connection conn = connect();
        boolean hasil = false;
        try{
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            hasil = true;
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return hasil;
    }
    
    public void hidCols(JTable table, String ke){
        String[] colHid = {ke};
        this.hidCols(table, colHid);
    }
    
    public void hidCols(JTable table, String[] ke){
        try{
            int isi = table.getColumnCount();
            for (String i : ke) {
                HashMap<String, Integer> mp = this.getLst(table);
                int a = mp.get(i);
                if(a > isi-1){
                    while(true){
                        if(a<isi){
                            break;
                        }
                        a--;
                    }
                }
                this.hidCol(table, a);
                isi--;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Upps,, Cek Nama Columnya!!");
            System.err.println(e);
        }
    }
    
    public void hidCol(JTable table, int ke){
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(ke));
    }
    
    public String getVal(JTable table, int row, String col){
        String isi = "";
        try{
            HashMap<String, Integer> mp = this.getLst(table);
            isi = table.getModel().getValueAt(row, mp.get(col)).toString();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Upps,, Cek Nama Columnya!!");
            System.err.println(e);
        }
        return isi;
        
    }
    
    public Boolean cekVal(String isi, String apa){
        String[] a = {isi};
        return cekVal(a, apa);
    }
    
    public Boolean cekVal(String[] isi, String apa){
        Boolean cek = false;
        for (int i = 0; i < isi.length; i++) {
            Boolean gimana = Stream.of(isi[i]).anyMatch(s -> s.equals(""));
            if(gimana){
                cek = true;
            }
        }
        return cek;
    }
    
//    public String getVal(JTable table, int row, int col){
//        String isi = table.getModel().getValueAt(row, col).toString();
//        return isi;
//    }
}
