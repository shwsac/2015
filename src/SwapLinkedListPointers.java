

public class SwapLinkedListPointers {
	
	public static LinkedListNode swapNode(LinkedListNode head, int one, int two){
		return head;
	}

	public static void main(String[] args){
		LinkedListNode head = new LinkedListNode(17);
		LinkedListNode temp = head;
		for(int i = 0 ; i < 11 ; i++){
			LinkedListNode n = new LinkedListNode(i);
			temp.next = n;
			temp = n;
		}
		head.printList();
		System.out.println();
		swapNode(head,1,5);
		
		head.printList();
		
		
	}
}
