package strings;
public class ReverseWords {

  public static String reverseWords(String input) {
    char[] inputArray = input.toCharArray();
    int lindex = 0;
    int rindex = inputArray.length - 1;
    if (rindex > 1) {
      //reverse complete phrase
      inputArray = reverse(inputArray, 0, rindex);

      //reverse each word in resultant reversed phrase
      for (rindex = 0; rindex <= inputArray.length; rindex++) {
        if (rindex == inputArray.length || inputArray[rindex] == ' ') {
          inputArray = reverse(inputArray, lindex, rindex - 1);
          lindex = rindex + 1;
        }
      }
    }
    return new String(inputArray);
  }

  public static char[] reverse(char[] input, int start, int end) {
    while (start < end) {
      char temp = input[start];
      input[start++] = input[end];
      input[end--] = temp;
    }
    return input;
  }

  public static void main(String[] args) {
    System.out.println(reverseWords("Hello World"));
    System.out.println(reverseWords("Hello! My name's     Monish ..   . Bhagat"));
    System.out.println(reverseWords("Hello World "));
    System.out.println(reverseWords(" Hello World"));
    System.out.println(reverseWords("1 Hello World "));

  }
}
