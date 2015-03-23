public class RearrangeZeros {

  public static void rearrangeZeros(int[] input) {
    int start = 0;
    int end = input.length - 1;
    while (start < end) {
      while (input[end] == 0) {
        end--;
      }
      while (input[start] != 0) {
        start++;
      }
      if (start < end) {
        int temp = input[start];
        input[start++] = input[end];
        input[end--] = temp;
      }
    }
  }

  public static void moveZeros(int[] input) {
    int count = 0;
    for (int i = 0; i < input.length; i++) {
      if (input[i] != 0) {
        input[count++] = input[i];
      }

    }
    while (count < input.length) {
      input[count++] = 0;
    }
  }

}
