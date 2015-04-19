
public class BinarySearchMIdElementBugTest {

	public static void main(String[] args){
		int low = -1073741824;
		int high = -1073741829;
		int mid = (low + high)/2;
		System.out.println("low: " +low);
		System.out.println("high: " +high);
		System.out.println("mid normal: " +mid);		
		mid = low + (high - low)/2;
		System.out.println("mid fancy: " +mid);
		mid = (low + high)>>>1;
		System.out.println("mid unsign bits: " +mid);
//		
//		int num = -107374182; 
//		System.out.println(num>>1);
//		System.out.println(num>>>1);
	}
}
