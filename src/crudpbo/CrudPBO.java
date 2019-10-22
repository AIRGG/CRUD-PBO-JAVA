package crudpbo;
import javax.swing.*;
public class CrudPBO {

    public static void main(String[] args) {
//        JOptionPane.showMessageDialog(null, "Hiii");
//        int inp = JOptionPane.showConfirmDialog(null, "Yakin??", "Pesan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//        System.out.println(inp);
        String isi = JOptionPane.showInputDialog(null, "Masukkan Nama Kamu..", "Welcome to DARK WEB", JOptionPane.QUESTION_MESSAGE);
        JOptionPane.showMessageDialog(null, "Haii "+isi, "Pesan", JOptionPane.INFORMATION_MESSAGE);
        
    }
}
