package trees;

class VCNode extends BTNode {
	int vcVal;

	public VCNode() {
		vcVal = -1;
		left = null;
		right = null;
	}

	public VCNode(BTNode n) {
		vcVal = -1;
		left = n.left;
		right = n.right;
	}

	// int val;
	// BTNode right;
	// BTNode left;
}

public class VertexCoverBinaryTree {

	public static int minVertexCover(VCNode root) {
		if (root == null) {
			return 0;
		}
		if ((root.left == null) && (root.right == null)) {
			return 0;
		}
		if (root.vcVal != -1) {
			return root.vcVal;
		}

		int vcIncludesRoot = 1 + minVertexCover(new VCNode(root.left))
				+ minVertexCover(new VCNode(root.right));
		int vcExcludesRoot = 0;
		if (root.left != null) {
			vcExcludesRoot += minVertexCover(new VCNode(root.left.left))
					+ minVertexCover(new VCNode(root.left.right));
		}
		if (root.right != null) {
			vcExcludesRoot += minVertexCover(new VCNode(root.right.left))
					+ minVertexCover(new VCNode(root.right.right));
		}
		root.vcVal = Math.min(vcExcludesRoot, vcIncludesRoot);
		return root.vcVal;
	}
	// public

}
