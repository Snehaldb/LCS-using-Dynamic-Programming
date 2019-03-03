import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

/**
 * Given two strings, find the longest comment sub-sequence (LCS).
 * The class calculates and returns the LCS.
 * Example:
 * For "AGGTAB" and "GXTXAYB", the LCS is "GTAB", return the longest common subsequence and compute the length of LCS
 * Here "AGGTAB" is sequence 1 and "GXTXAYB" is the sequence 2
 */
public class LCS {

    /**
     * @param str1 - sequence 1
     * @param str2 - sequence 2
     * @return length of LCS for X[0..m-1], Y[0..n-1]. Where m and n are length of two sequences.
     */
    public String lcs(String str1, String str2) {

        int seq1 = str1.length();
        int seq2 = str2.length();

        // 2D array is initialised to form a matrix to store the solution of subproblem for further computation
        int[][] arr = new int[seq1 + 1][seq2 + 1];

        // Loop through every element in Seq 1 and Seq 2 respectively. Uses bottom up approach
        for (int i = seq1 - 1; i >= 0; i--) {
            for (int j = seq2 - 1; j >= 0; j--) {
                // If the last character of both sequences match then increment the
                // current value in the matrix at that position else execute teh else loop
                if (str1.charAt(i) == str2.charAt(j))
                    arr[i][j] = arr[i + 1][j + 1] + 1;
                else
                    arr[i][j] = Math.max(arr[i + 1][j], arr[i][j + 1]);
            }
        }

        int i = 0, j = 0;
        StringBuffer sb = new StringBuffer();

        // Loop to return the longest common subsequence
        while (i < seq1 && j < seq2) {
            if (str1.charAt(i) == str2.charAt(j)) {
                sb.append(str1.charAt(i));
                i++;
                j++;
            } else if (arr[i + 1][j] >= arr[i][j + 1])
                i++;
            else
                j++;
        }
        return sb.toString();
    }

    /**
     * Start the program and provide two protein sequences to lcs method.
     */
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();

        // try catch to give the exception if the file does not exist
        try {
            FileInputStream fstream = new FileInputStream("sequences.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            String value = "";
            String key = "";
            // Loop through each line un the sequence.txt file
            while ((strLine = br.readLine()) != null) {
                // Store the key in the Hashmap. Key is the name of the protein. For eg: key = "MMTLAC"
                if (strLine.contains(">")) {
                    value = "";
                    key = strLine.replace(">", "");

                } else {
                    // Store the value in the Hashmap. Value is the sequence of that respective protein. For eg: value = "MQRLCAHVLILVLALAAFCEASWKPHSHLQDLPVAPRANRGQEPLRMTRLGPASNPRRQLGLQDPPHMVADLSNKQGPWVGEEEAAYGWMDFGRRMAEGGDQHP*"
                    String newline = strLine;
                    value = value + newline;
                }

                map.put(key, value);
            }
            br.close();

            // Choose two random sequences of proteins
            Object randomName = map.keySet().toArray()[new Random().nextInt(map.keySet().toArray().length)];
            String key1 = randomName.toString();
            String sequence1 = map.get(randomName);

            Object randomName2 = map.keySet().toArray()[new Random().nextInt(map.keySet().toArray().length)];
            String key2 = randomName2.toString();
            String sequence2 = map.get(randomName2);

            System.out.println("1st Sequence is: " + sequence1 + "\n");
            System.out.println("2st Sequence is: " + sequence2 + "\n");


            String str1 = sequence1;
            String str2 = sequence2;

            LCS newlcs = new LCS();
            String result = newlcs.lcs(str1, str2);
            double lcslength = result.length();
            double str1length = str1.length();
            double similarityPercentage = (lcslength / str1length) * 100;


            System.out.println("Longest Common Subsequence for Protein: " + key1 + " and Protein: " + key2 + " is : " + result);
            System.out.println("Length of Longest Common Subsequence : " + result.length());
            System.out.println("Similarity percentage of Longest Common Subsequence : " + similarityPercentage);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

