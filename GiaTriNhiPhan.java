/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ThucHanhBuoi1;

/**
 *
 * @author PC
 */
import java.util.*;
import java.io.*;
public class GiaTriNhiPhan {
   public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("DATA.in"));
        ArrayList<String> a = (ArrayList<String>) in.readObject();
        ArrayList<String> res = new ArrayList<>();
        for(int i = 0; i < a.size(); i++){
            StringBuilder s = new StringBuilder();
            for(int j = 0; j < a.get(i).length(); j++){
                if(a.get(i).charAt(j) == '0' || a.get(i).charAt(j) == '1')
                    s.append(a.get(i).charAt(j));
            }
            res.add(s.toString());
        }
        for(String x: res){
            int decimal = Integer.parseInt(x, 2);
            System.out.println(x + " " + decimal);   
        }
    }
}
