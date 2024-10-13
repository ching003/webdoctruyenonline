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
public class TinhTongSoLong {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("DATA.in"));
        long sum = 0;
        while(in.hasNext()){
            String s = in.next();
            try{
                int x = Integer.parseInt(s);
            }catch(NumberFormatException ex){
                try{
                    long y = Long.parseLong(s);
                    sum += y;
                }
                catch(NumberFormatException ex1){
                    
                }
            }
        }
        System.out.println(sum);
    }
}
