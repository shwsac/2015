
public class LinkedListNode {
	int val;
	LinkedListNode next;
	
	public LinkedListNode(){
		val = -1;
		next = null;
	}
	
	public LinkedListNode(int v){
		val = v;
		next = null;
	}
	
	public void printList(){
		LinkedListNode temp = this;
		while(temp!=null){
			System.out.print(temp.val+ " ");
			temp = temp.next;
		}
	}
	
	
}
