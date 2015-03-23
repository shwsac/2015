import java.util.Arrays;;


class Pair {
  int one;
  int two;
  
  public Pair(int o, int t) {
    one = o;
    two = t;
  }
  
  @Override
  public String toString() {
    return "("+ one + " , " + two + ")";
  }
}


public class ClosestTwoSum {

  public static Pair getClosetSumPair(int[] input, int target){
    int minDiff = Integer.MAX_VALUE;
    Pair result = new Pair(-1 , -1);
    Arrays.sort(input);
    int start = 0;
    int end = input.length -1;
    while(start <  end){
      int tempSum = input[start] + input[end];
      if(tempSum  == target){
        return new Pair(input[start], input[end]);
      }
      else{
      int tempDiff = Math.abs(tempSum - target);
        if(tempDiff < minDiff){
          minDiff = tempDiff;
          result = new Pair(input[start], input[end]);
        }
        if(tempSum < target){
          start++;
        }else{
          end--;
        }
      }
    }
    return result;    
  }
  
  public static void main(String[] args) {
    int[] input = {1 , 3, 8, 11 ,15};
    System.out.println(getClosetSumPair(input , 10));
    System.out.println(getClosetSumPair(input , 11));
    System.out.println(getClosetSumPair(input , 9));
  }

}
