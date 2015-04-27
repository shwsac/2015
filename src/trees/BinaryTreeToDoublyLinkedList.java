package trees;

public class BinaryTreeToDoublyLinkedList {
	public static BTNode prev;  
	public static BTNode head;

	public static void getInOrderList(BTNode root){
		if(root == null){
			return;
		}
		// Clear out prev before calling this to avoid static mishaps
		//prev = null;
		getInOrderList(root.left);
		if(prev == null){
			head = root;
		}else{
			root.left = prev;
			prev.right = root;
		}
		prev = root;
		
		getInOrderList(root.right);
	}
	
	public static void getPostOrderList(BTNode root){
		if(root == null){
			return;
		}
		// Clear out prev before calling this to avoid static mishaps
		//prev = null;
		
		getPostOrderList(root.left);
		getPostOrderList(root.right);
		if(prev == null){
			head = root;
		}else{
			root.left = prev;
			prev.right = root;
		}
	}
	
	public static void getPreOrderList(BTNode root){
		if(root == null){
			return;
		}
		// Clear out prev before calling this to avoid static mishaps
		//prev = null;
		
		if(prev == null){
			head = root; 
		}else{
			root.left = prev;
			prev.right = root;
		}
		prev = root;
		getPreOrderList(root.left);
		getPreOrderList(root.right);
		
	}
}
