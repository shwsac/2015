public class AddTwoBinaryStrings{
  public static String addBinaryStrings(String one, String two) throws Exception{
    if(one == null || two == null){
      throw new Exception("Invalid Input");
    }
    String result = "";
    int oneIndex = one.length() - 1;
    int twoIndex = two.length() - 1;
    int carry = 0;
    while((oneIndex >= 0) && (twoIndex >= 0)){
      char oneCharacter = one.charAt(oneIndex);
      char twoCharacter = two.charAt(twoIndex);
      
      if((oneCharacter == '1') && (twoCharacter == '1')){
        if(carry == 1){
          result  = '1' + result;
        }
        else{
          carry = 1;
          result  = '0' + result;
        }
      }
      else if ((oneCharacter == '1') || (twoCharacter == '1')){
        if(carry == 1){
          result  = '0' + result;
        }
        else{
          result  = '1' + result;
        }
      }
      else{
        if(carry == 1){
          result  = '1' + result;
          carry = 0;
        }
        else{
          result  = '0' + result;
        }
      }
      oneIndex--;
      twoIndex--;
    }
    if(oneIndex > 0){
      result = one.substring(0, oneIndex) + result;
    }
    if(twoIndex > 0){
      result = two.substring(0, twoIndex) + result;
    }
    return result;
  }
  
  public static void main(String[] args) throws Exception {
    String one = "1";
    String two = "1";
    System.out.println(addBinaryStrings(one,two));
    
  }
}