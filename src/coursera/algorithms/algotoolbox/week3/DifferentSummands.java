package coursera.algorithms.algotoolbox.week3;

import java.util.*;

public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<>();
        
        int number = 0;
        
        while(n > 0) {
            number++;
            if(n - number == 0 || n - number > number) {
                summands.add(number);
                n -= number;
            }
        }
        
        return summands;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}