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
public class SuaLoi {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<Integer> loi = new ArrayList<>();
        for(int i = 0; i < m; i++){
            loi.add(in.nextInt());
        }
        ArrayList<Integer> tot = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            if(!loi.contains(i)){
                tot.add(i);
            }
        }
        System.out.println("Errors: " + tao(loi));
        System.out.println("Correct: " + tao(tot));
    }
    public static String tao(ArrayList<Integer> ds){
        StringBuilder sb = new StringBuilder();
        int n = ds.size();
        int i = 0;
        while(i < n){
            int start = ds.get(i);
            int end = start;
            while(i < n - 1 && ds.get(i+1) == ds.get(i) + 1){
                end = ds.get(i + 1);
                i++;
            }
            if(start == end){
                sb.append(start);
            }
            else
                sb.append(start).append("-").append(end);
                
            if(i < n - 1){
                sb.append(", ");
            }
            else if(i == n - 1 && n > 1){
                sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, " and");
            }
            i++;
        }
        return sb.toString();
    }
}
