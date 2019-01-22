package coursera.algorithms.algotoolbox.week5;

import java.util.Scanner;

public class ChangeDP {
    private static int getChange(int m) {
        
        int[] changes = new int[m + 1];
        int[] coins = {1, 3, 4};
        
        changes[0] = 0;
        
        for(int coin : coins) {
            for(int i = coin; i <= m; i++) {
                if(i == coin) {
                    changes[i] = 1;
                } else if(i > coin) {
                    int change = changes[coin] + changes[i - coin];
                    if(changes[i] == 0 || change < changes[i]) {
                        changes[i] = change;
                    }
                }
            }
        }
        
        return changes[m];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
    }
}