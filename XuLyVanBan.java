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
public class XuLyVanBan {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("VANBAN.in"));
        TreeSet<String> set = new TreeSet<>();
        while(in.hasNextLine()){
            String[] temp = in.nextLine().trim().split("\\s+");
            for(String x: temp){
                boolean check = false;
                for(int i = 0; i < x.length(); i++){
                    char c = x.charAt(i);
                    if(c >= '0' && c <= '9'){
                        check = true;
                    }
                    else if(c == '.' || c == ',' || c == '?' || c == ':' || c == '!'){
                        check = false;
                        break;
                    }
                }
                if(check) set.add(x);
            }
        }
        for(String x: set){
            System.out.println(x);
        }
    }
}
