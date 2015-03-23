import java.util.Arrays;

public class ThreeSumProblem{
  
  public static boolean isThreeSum(int[] input, int checkSum){
      Arrays.sort(input);
      
      for(int i = 0; i < input.length ; i++ ){
        if(isTwoSumWithSortingWithoutRepetition(input, i, checkSum - input[i])){
          return true;
        }
      }
      return false;
  }
  
  private static boolean isTwoSumWithSortingAllowingRepetition(int[] input, int index , int checkSum){
    int start = index;
    int end = input.length - 1;
    while(start < end){
      int tempSum = (input[start] + input[end]);
      System.out.println(tempSum + ":" + checkSum);
      if(tempSum == checkSum){
        return true;
      } 
      else if(tempSum < checkSum){
        start++;
      }
      else{
        end--;
      }
    }
    return false;
  }
  
  private static boolean isTwoSumWithSortingWithoutRepetition(int[] input, int index , int checkSum){
    int start = index + 1;
    int end = input.length - 1;
    while((start < end)){
      int tempSum = (input[start] + input[end]);
      //System.out.println(tempSum + ":" + checkSum);
      if(tempSum == checkSum){
        return true;
      } 
      else if(tempSum < checkSum){
        start++;
      }
      else{
        end--;
      }
    }
    return false;
  }
  
  public static void main(String[] args) {
    int[] input = {1,2,3,4,5,6,5,4,3,2,1};
    System.out.println(isThreeSum(input, 3));
    System.out.println(isThreeSum(input, 4));
    System.out.println(isThreeSum(input, 5));
    System.out.println(isThreeSum(input, 7));
    System.out.println(isThreeSum(input, 8));
    System.out.println(isThreeSum(input, 9));
    System.out.println(isThreeSum(input, 10));
    System.out.println(isThreeSum(input, 11));
  }
}
 
