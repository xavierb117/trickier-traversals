public class TreeNode<E> {
  E value;
  TreeNode<E> left;
  TreeNode<E> right;

  public TreeNode(E data,TreeNode<E> left, TreeNode<E> right) {
    value = data;
    this.left = left;
    this.right = right;
  }
  
  public TreeNode(E data) {
    this(data, null, null);
  }
}