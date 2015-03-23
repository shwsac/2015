import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
public class SortAnangramsTogether {

  public static String[] sortAnangrams(String[] input) throws Exception {
    if (input == null) {
      throw new NullPointerException("Input can't be null");
    }
    /* LinkedHashMap preserves order w.r.t incoming strings.
     *  We can use HashMap instead of LinkedHashMap for random ordering.
     */
    HashMap<String, LinkedList<String>> outputMap = new LinkedHashMap<String, LinkedList<String>>();
                                                   //new HashMap<String, LinkedList<String>>();
    
    String[] output = new String[input.length];
    for (String s : input) {
      String sortedString = sortString(s);
      if (!outputMap.containsKey(sortedString)) {
        outputMap.put(sortedString, new LinkedList<String>());
      }
      outputMap.get(sortedString).add(s);
      
      int index = 0;
      for(String key : outputMap.keySet()) {
        LinkedList<String> outList = outputMap.get(key);
        for(String outString : outList) {
          output[index++] = outString;
        }
      }
    }
    return output;
  }

  public static String sortString(String s) {
    char[] sArray = s.toCharArray();
    Arrays.sort(sArray);
    return new String(sArray);
  }
}
