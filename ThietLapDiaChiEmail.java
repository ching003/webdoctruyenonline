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
public class ThietLapDiaChiEmail {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("DANHSACH.in"));
        LinkedHashSet<String> res = new LinkedHashSet<>();
        ArrayList<String> email = new ArrayList<>();
        while(in.hasNextLine()){
            String s = in.nextLine().trim();
            s = s.toLowerCase();
            String temp[] = s.split("\\s+");
            s = "";
            for (String temp1 : temp) {
                s += temp1 + " ";
            }
            res.add(s.trim());
        }
        for(String s: res){
            String temp[] = s.split("\\s+");
            StringBuilder s1 = new StringBuilder();
            s1.append(temp[temp.length - 1]);
            for(int i = 0; i < temp.length - 1; i++){
                s1.append(temp[i].charAt(0));
            }
            String baseMail = s1.toString();
            int cnt = 1;
            while(email.contains(s1.toString())){
                cnt++;
                s1 = new StringBuilder(baseMail);
                s1.append(cnt);
            }
            email.add(s1.toString());
        }
        for(String s: email){
            System.out.println(s + "@ptit.edu.vn");
        }
    }
}
