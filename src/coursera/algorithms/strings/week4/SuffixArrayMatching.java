package coursera.algorithms.strings.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SuffixArrayMatching {
    class fastscanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        fastscanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextint() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static final int NUMBER_OF_CHARACTERS = 5;

    private int letterToInt(char letter) {
        switch(letter) {
            case '$':
                return 0;
            case 'A':
                return 1;
            case 'C':
                return 2;
            case 'G':
                return 3;
            case 'T':
                return 4;
            default:
                assert(false);
                return -1;
        }
    }
    
    private int[] sortCharacters(String text) {
        int textLength = text.length();
        int[] order = new int[textLength];
        int[] count = new int[NUMBER_OF_CHARACTERS];
        
        for(int i = 0; i < textLength; i++) {
            int letterIndex = letterToInt(text.charAt(i));
            count[letterIndex] = count[letterIndex] + 1;
        }
        
        for(int j = 1; j < count.length; j++) {
            count[j] = count[j] + count[j - 1];
        }
        
        for(int i = textLength - 1; i >= 0; i--) {
            int currentLetterIndex = letterToInt(text.charAt(i));
            count[currentLetterIndex] = count[currentLetterIndex] - 1;
            order[count[currentLetterIndex]] = i;
        }
        
        return order;
    }
    
    private int[] computeCharClasses(String text, int[] order) {
        int textLength = text.length();
        int[] charClass = new int[textLength];
        charClass[order[0]] = 0;
        
        for(int i = 1; i < textLength; i++) {
            if(text.charAt(order[i]) != text.charAt(order[i - 1])) {
                charClass[order[i]] = charClass[order[i - 1]] + 1;
            } else {
                charClass[order[i]] = charClass[order[i - 1]];
            }
        }
        
        return charClass;
    }
    
    private int[] sordDoubled(String text, int currentLength, int[] order, int[] charClass) {
        int textLength = text.length();
        int[] count = new int[textLength];
        int[] newOrder = new int[textLength];
        
        for(int i = 0; i < textLength; i++) {
            count[charClass[i]] = count[charClass[i]] + 1;
        }
        
        for(int j = 1; j < textLength; j++) {
            count[j] = count[j] + count[j - 1];
        }
        
        for(int i = textLength - 1; i >= 0; i--) {
            int start = (order[i] - currentLength + textLength) % textLength;
            int currentClass = charClass[start];
            count[currentClass] = count[currentClass] - 1;
            newOrder[count[currentClass]] = start;
        }
        
        return newOrder;
    }
    
    private int[] updateClasses(int[] newOrder, int[] charClass, int currentLength) {
        int n = newOrder.length;
        int[] newClass = new int[n];
        newClass[newOrder[0]] = 0;
        
        for(int i = 1; i < n; i++) {
            int current = newOrder[i];
            int previous = newOrder[i - 1];
            
            int mid = (current + currentLength) % n;
            int midPrev = (previous + currentLength) % n;
            if(charClass[current] != charClass[previous] || charClass[mid] != charClass[midPrev]) {
                newClass[current] = newClass[previous] + 1;
            } else {
                newClass[current] = newClass[previous];
            }
        }
        
        return newClass;
    }
    
    private int compareStrings(String pattern, String text, int startFrom) {
        int patternLength = pattern.length();
        int textLength = text.length();
        int i = startFrom;
        int j = 0;
        
        while(i < textLength && j < patternLength) {
            int textSymbol = text.charAt(i);
            int patternSymbol = pattern.charAt(j);
            
            if(textSymbol != patternSymbol) {
                return patternSymbol - textSymbol;
            }
            i++;
            j++;
        }
        return 0;
    }

    public int[] computeSuffixArray(String text) {
        int[] suffixArray = sortCharacters(text);
        int[] charClass = computeCharClasses(text, suffixArray);
        
        int currentLength = 1;
        int textLength = text.length();
        
        while(currentLength < textLength) {
            suffixArray = sordDoubled(text, currentLength, suffixArray, charClass);
            charClass = updateClasses(suffixArray, charClass, currentLength);
            currentLength *= 2;
        }
        
        return suffixArray;
    }

    public List<Integer> findOccurrences(String pattern, String text, int[] suffixArray) {
        List<Integer> result = new ArrayList<>();
        int textLength = text.length();
        
        int minIndex = 0;
        int maxIndex = textLength;
        
        while(minIndex < maxIndex) {
            int midIndex = (minIndex + maxIndex) / 2;
            if(compareStrings(pattern, text, suffixArray[midIndex]) > 0) {
                minIndex = midIndex + 1;
            } else {
                maxIndex = midIndex;
            }
        }
        int start = minIndex;
        maxIndex = textLength;
        
        while(minIndex < maxIndex) {
            int midIndex = (minIndex + maxIndex) / 2;
            if(compareStrings(pattern, text, suffixArray[midIndex]) < 0) {
                maxIndex = midIndex;
            } else {
                minIndex = midIndex + 1;
            }
        }
        int end = maxIndex;
        for(int i = start; i < end; i++) {
            result.add(suffixArray[i]);
        }
        
        return result;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayMatching().run();
    }

    public void print(boolean[] x) {
        StringBuilder sb = new StringBuilder(x.length * 2);
        for (int i = 0; i < x.length; ++i) {
            if (x[i]) {
                sb.append(i);
                sb.append(' ');
            }
        }
        System.out.println(sb.toString());
    }

    public void run() throws IOException {
        fastscanner scanner = new fastscanner();
        String text = scanner.next() + "$";
        int[] suffixArray = computeSuffixArray(text);
        int patternCount = scanner.nextint();
        boolean[] occurs = new boolean[text.length()];
        for (int patternIndex = 0; patternIndex < patternCount; ++patternIndex) {
            String pattern = scanner.next();
            List<Integer> occurrences = findOccurrences(pattern, text, suffixArray);
            for (int x : occurrences) {
                occurs[x] = true;
            }
        }
        print(occurs);
    }
}