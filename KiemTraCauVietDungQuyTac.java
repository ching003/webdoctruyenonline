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
public class KiemTraCauVietDungQuyTac {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = Integer.parseInt(in.nextLine());
        while(t-- > 0){
            Stack<Character> st = new Stack<>();
            String s = in.nextLine();
            for(int i = 0; i < s.length(); i++){
                char c = s.charAt(i);
                if(!st.isEmpty() && c == ')' && st.peek() == '('){
                    st.pop();
                }
                else if(!st.isEmpty() && c == ']' && st.peek() == '[')
                    st.pop();
                else if(c == '(' || c == ')' || c == '[' || c == ']') st.push(c);
            }
            System.out.println(st.empty()?"YES": "NO");
        }
        
    }
}
