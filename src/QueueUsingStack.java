import java.util.Stack;

public class QueueUsingStack<E> {
		
	Stack<E> first = new Stack<E>();
	Stack<E> second = new Stack<E>();
	
	public boolean add(E val){
		first.push(val);
		return true;
	}
	
	public E remove(){
		if(second.isEmpty()){
			if(first.isEmpty()){
				return null;
			}else{
				while(first.isEmpty()){
					second.push(first.pop());
				}
			}
		}
		return second.pop();
	}

}
