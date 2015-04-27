package trees;

import java.util.ArrayList;
import java.util.Stack;

public class PrintAllRootToLeafPaths {

	public static void printPaths(BTNode root) {
		if (root == null) {
			return;
		}
		ArrayList<Integer> pathTillNow = new ArrayList<Integer>();
		printUtil(root, pathTillNow, 0);
	}

	private static void printUtil(BTNode root, ArrayList<Integer> pathTillNow,
			int pathLength) {
		if (root == null) {
			return;
		} else {
			pathTillNow.add(pathLength, root.val);
			pathLength++;
			if (root.left == null && root.right == null) {
				for (int i = 0; i < pathLength; i++) {
					System.out.print(pathTillNow.get(i) + " ");
				}
				System.out.println();
			} else {
				printUtil(root.left, pathTillNow, pathLength);
				printUtil(root.right, pathTillNow, pathLength);
			}
		}
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

		printPaths(root);
	}
}
