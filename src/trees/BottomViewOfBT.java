package trees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BottomViewOfBT {

	public static List<Integer> getBottomView(BTNode root) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		HashMap<BTNode, Integer> rMap = new HashMap<BTNode, Integer>();
		HashMap<Integer, Integer> res = new HashMap<Integer, Integer>();
		rMap = getBottomViewUtil(root, new HashMap<BTNode, Integer>(), 0);
		LinkedList<BTNode> q = new LinkedList<BTNode>();
		q.add(root);
		while (!q.isEmpty()) {
			BTNode n = q.remove();
			res.put(rMap.get(n), n.val);
			if (n.left != null) {
				q.add(n.left);
			}
			if (n.right != null) {
				q.add(n.right);
			}
		}

		result.addAll(res.values());
		return result;
	}

	private static HashMap<BTNode, Integer> getBottomViewUtil(BTNode root,
			HashMap<BTNode, Integer> rMap, int idx) {
		if (root != null) {
			rMap.put(root, idx);
			getBottomViewUtil(root.left, rMap, idx - 1);
			getBottomViewUtil(root.right, rMap, idx + 1);
		}

		return rMap;
	}

	public static void main(String[] args) {
		/*
		 * 20 8 22 5 3,4 25 7(right child of 3)
		 */
		BTNode root = new BTNode();
		root.val = 20;
		root.left = new BTNode();
		root.left.val = 8;
		root.right = new BTNode();
		root.right.val = 22;
		root.left.left = new BTNode();
		root.left.left.val = 5;
		root.right.left = new BTNode();
		root.right.left.val = 4;
		root.left.right = new BTNode();
		root.left.right.val = 3;
		root.right.right = new BTNode();
		root.right.right.val = 25;
		root.left.right.right = new BTNode();
		root.left.right.right.val = 7;

		System.out.println(getBottomView(root));
	}

}
