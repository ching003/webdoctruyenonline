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
public class TinhDiemTrungBinh {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double res = 0;
        int n = in.nextInt();
        ArrayList<Double> score = new ArrayList<>();
        for(int i = 0; i < n; i++){
            score.add(in.nextDouble());
        }
        Collections.sort(score);

        int cnt = 0;
        double min = score.get(0), max = score.get(n-1);
        for(int i = 1; i < score.size() - 1; i++){
            if(score.get(i) > min && score.get(i) < max){
                res += score.get(i);
                cnt++;
            }
        }
        res /= cnt;
        System.out.printf("%.2f", res);
    }
}
