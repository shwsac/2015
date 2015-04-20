package strings;

/*Given a list of word lists How to print all sentences possible 
 * taking one word from a list at a time via recursion?
 * */
public class RecPrintSentencesFormed {

	public static void recPrint(String[][] arr, int k, String sen){
		if(k == arr.length){
			System.out.println(sen);
			return;
		}

			for(int j = 0; j < arr[k].length; j++){
				String s  = sen + " " + arr[k][j];
				recPrint(arr, k+1, s);
			}
			
		
	}
	
	public static void main(String[] args) {
		String arr[][] = { { "you", "we" }, { "have", "are" },
				{ "sleep", "eat", "drink" } };
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr[i].length; j++) {
//				System.out.print(arr[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		recPrint(arr, 0 , "");

	}

}
