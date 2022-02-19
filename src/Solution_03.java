import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuyu Fu
 * @version 1.0
 * @description
 * @date 2022/2/19 19:34
 */
public class Solution_03 {
    public static void main(String[] args) {
        String s = "bbbbb";
        System.out.println(lengthOfLongestSubstring(s));
    }
    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        int left = -1;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                left = Math.max(map.get(s.charAt(i)),left);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left);
        }
        return max;
    }
}
