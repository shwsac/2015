package strings;

import java.util.ArrayList;
import java.util.List;

public class Phonemnemonics {
    public static String[] phoneReference = { "0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ" };

    // assuming input number is String.
    public static List<String> getMnenomics(String number) {
	ArrayList<String> resultList = new ArrayList<String>();
	// below check will not work for values out of Integer range.
//	if(Integer.parseInt(number) < 0){
//	    return resultList;
//	}
	StringBuilder sb = new StringBuilder();
	helper(number , resultList, sb, 0);
	return resultList;
    }
    
    public static void helper(String number, List<String> resultList, StringBuilder result, int offset){
	if(offset == number.length()){
	    resultList.add(new String(result));
	}else{
	    String ref = phoneReference[number.charAt(offset) - '0'];
	    for(int i = 0; i < ref.length(); i++){
		result.append(ref.charAt(i));
		helper(number, resultList, result, offset+1);
		result.deleteCharAt(offset);
	    }
	}
    }
    
    public static void main(String[] args){
	List<String> resultList = getMnenomics("22");
	for(String s:  resultList){
	    System.out.println(s);
	}
	System.out.println(resultList.size());
	
    }
    
}
