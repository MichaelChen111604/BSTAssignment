public class BST<E extends Comparable<E>> {
    private BSTNode<E> root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public boolean find(E value) {
        return find(root, value);
    }
    private boolean find(BSTNode<E> rt, E value) {
        if (rt == null) return false;
        else if (rt.getValue() == value) return true;
        return (find(rt.getLeft(), value) || find(rt.getRight(), value));
    }

    public int height() {
        return height(0, root);
    }
    private int height(int prevHeight, BSTNode<E> rt) {
        if (rt == null) return prevHeight;
        else return Math.max(height(1 + prevHeight, rt.getLeft()),
                height(1 + prevHeight, rt.getRight()));
    }

    public void insert(E value) {
        if (!find(value)) {
            root = insert(root, value);
            size++;
        }
    }
    private BSTNode<E> insert(BSTNode<E> rt, E value) {
        if (rt == null) return new BSTNode<E>(value);
        else {
            if(value.compareTo(rt.getValue()) > 0) rt.setRight(insert(rt.getRight(), value));
            else rt.setLeft(insert(rt.getLeft(), value));
            return rt;
        }
    }

    public String inOrderString() {
        return inOrderString(root);
    }
    private String inOrderString(BSTNode<E> rt) {
        if (rt == null) return "";
        String out = "(";

        out += inOrderString(rt.getLeft()) + "-";
        out += rt.getValue() + "-";
        out += inOrderString(rt.getRight());
        out += ")";
        return out;
    }

    public int countLeaves() {
        return countLeaves(root);
    }
    private int countLeaves(BSTNode<E> rt) {
        if (rt == null) return 0;
        if (rt.getLeft() == null && rt.getRight() == null) return 1;
        return countLeaves(rt.getLeft()) + countLeaves(rt.getRight());
    }

}