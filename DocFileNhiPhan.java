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
public class DocFileNhiPhan {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("DAYSO.DAT"));
        ArrayList<Integer> a = (ArrayList<Integer>) in.readObject();
        boolean nt[] = new boolean[1000001];
        snt(nt);
        TreeSet<Integer> res = new TreeSet<>();
        for(Integer x: a){
            if(nt[x] && x >= 100){
                res.add(x);
            }
        }
        for(Integer x: res){
            System.out.println(x);
        }
    }
    public static void snt(boolean nt[]){
        for(int i = 2; i <= 1000000; i++){
            nt[i] = true;
        }
        for(int i = 2; i <= 1000; i++){
            if(nt[i]){
                for(int j = i * i; j <= 1000000; j+=i){
                    nt[j] = false;
                }
            }
        }
    }
}
