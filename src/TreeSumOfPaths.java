import java.util.List;

public class TreeSumOfPaths {

  static class BinaryTreeNode{
    public int value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    
    public BinaryTreeNode() {
      value = 0;
      left = null;
      right = null;
    }
    
    public BinaryTreeNode(int val) {
      value = val;
      left = null;
      right = null;
    }
    
    public BinaryTreeNode(int val, BinaryTreeNode l, BinaryTreeNode r) {
      value = val;
      left = l;
      right = r;
    }    
  }
  
  public static List<Integer> getSumOfAllPaths(BinaryTreeNode root) throws Exception{
    if(root == null) {
      throw new Exception("No null input allowed");
    }
   return null; 
  }
  
  public static void main(String[] args) {
    
    
  }
}
