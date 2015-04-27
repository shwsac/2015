package trees;

class ResultPS{
	BTNode pre;
	BTNode suc;
}

public class GetPredSuccessor {
	public static ResultPS getPredecessorNSuccessor(BTNode root, int key){
		BTNode pre = null;
		BTNode suc = null;
		return util(root, pre, suc, key);
	}
	
	private static ResultPS util(BTNode root, BTNode pre, BTNode suc, int key){
		ResultPS r = new ResultPS();
		r.pre = pre;
		r.suc = suc;
		if(root == null){			
			return r;
		}
		if(root.val == key){
			if(root.left!=null){
				r.pre = root.left;
				while(r.pre.right!=null){
					r.pre = r.pre.right;
				}
			}
			if(root.right!=null){
				r.suc = root.right;
				while(r.suc.left!=null){
					r.suc = r.suc.left;
				}
			}
			return r;
		}
		if(root.val > key){
			suc = root;
			return util(root.left, pre, suc, key);
		}else{
			pre = root;
			return util(root.right, pre, suc, key);
		}
	}
}
