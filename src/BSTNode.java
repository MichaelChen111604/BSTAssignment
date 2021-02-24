public class BSTNode<E extends Comparable<E>> {
    private E value;
    private BSTNode<E> left;
    private BSTNode<E> right;

    BSTNode() {
        value = null;
        left = null;
        right = null;
    }
    BSTNode(E value) {
        this.value = value;
        left = null;
        right = null;
    }

    public BSTNode<E> getLeft() {
        return left;
    }
    public BSTNode<E> getRight() {
        return right;
    }

    public void setLeft(BSTNode<E> newLeft) {
        left = newLeft;
    }
    public void setRight(BSTNode<E> newRight) {
        right = newRight;
    }

    public boolean isLeaf() {

        return (left == null && right == null);
    }
    public E getValue() {
        return value;
    }
    public int countLeaves(BSTNode<E> rt) {
        if (rt.getLeft() == null && rt.getRight() == null)
            return 1;
        if (rt.getLeft() != null && rt.getRight() == null)
            return countLeaves(rt.getLeft());
        if (rt.getLeft() == null && rt.getRight() != null)
            return countLeaves(rt.getRight());
        return countLeaves(rt.getLeft()) + countLeaves(rt.getRight());
    }
}
