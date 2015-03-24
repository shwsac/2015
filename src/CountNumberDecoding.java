
//Given a=1, b=2, ... , z=26), compute the number of ways one can decode a given integer.
//Input:  digits[] = "121"
//Output: 3
//// The possible decodings are "ABA", "AU", "LA"
//
//Input: digits[] = "1234"
//Output: 3
//// The possible decodings are "ABCD", "LCD", "AWD"

public class CountNumberDecoding {

  public static int countDecodingDP(String input, int n)
  { 
      char[] digits = input.toCharArray();
      // One more than length of the string as we start fib series from zero.
      int count[] = new int[input.length()]; // A table to store results of subproblems
      count[0] = 1;
      count[1] = 1;
   
      for (int i = 2; i <= n; i++)
      {
          count[i] = 0;
   
          // If the last digit is not 0, then last digit must add to
          // the number of words
          if (digits[i-1] > '0')
              count[i] = count[i-1];
   
          // If second last digit is smaller than 2 and last digit is
          // smaller than 7, then last two digits form a valid character
          if (digits[i-2] < '2' || (digits[i-2] == '2' && digits[i-1] < '7') )
              count[i] += count[i-2];
      }
      // returning last element (n+1)th
      return count[n];
      
  }
}
