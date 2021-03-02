public class OldBST<E extends Comparable<E>> {
    // problem: if a child of a root gets a child, the root's height doesn't update
    class BSTNode<F extends Comparable<F>> {
        private F value;
        private BSTNode<F> left;
        private BSTNode<F> right;
        private int height; // height of the subtree rooted at this node

        BSTNode() {
            value = null;
            left = null;
            right = null;
            height = 1;
        }
        BSTNode(F value) {
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }

        public BSTNode<F> getLeft() {
            return left;
        }
        public BSTNode<F> getRight() {
            return right;
        }
        public int getHeight() { return height; }

        public void setLeft(BSTNode<F> newLeft) {
            left = newLeft;
        }
        public void setRight(BSTNode<F> newRight) {
            right = newRight;
        }
        public void setHeight(int newHeight) { height = newHeight; }

        // Children of this node must have correct heights
        private void refreshHeight() {
            if (left == null && right == null) setHeight(1);
            else if (left == null) setHeight(1 + right.getHeight());
            else if (right == null) setHeight(1 + left.getHeight());
            else setHeight(1 + Math.max(right.getHeight(), left.getHeight()));
            System.out.println(getValue().toString() + " height = " + height);
        }

        public boolean isLeaf() {
            return (left == null && right == null);
        }

        public F getValue() {
            return value;
        }

        public int countLeaves() {
            return countLeaves(this);
        }
        private int countLeaves(BSTNode<F> rt) {
            if (rt == null)
                return 0;
            if (rt.getLeft() == null && rt.getRight() == null)
                return 1;
            return countLeaves(rt.getLeft()) + countLeaves(rt.getRight());
        }
    }

    private BSTNode<E> root;
    private int size;

    public OldBST() {
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

    // pivot must have a right with a right
    private BSTNode<E> leftRotate(BSTNode<E> pivot) {
        // this node becomes the parent of pivot
        BSTNode<E> pivotParent = pivot.getRight();
        pivot.setRight(pivotParent.getLeft());
        pivotParent.setLeft(pivot);
        pivot.refreshHeight();
        pivotParent.getRight().refreshHeight();
        pivotParent.refreshHeight();
        return pivotParent;
    }
    // pivot must have a left with a left
    private BSTNode<E> rightRotate(BSTNode<E> pivot) {
        // this node becomes the parent of pivot
        BSTNode<E> pivotParent = pivot.getLeft();
        pivot.setLeft(pivotParent.getRight());
        pivotParent.setRight(pivot);
        pivot.refreshHeight();
        pivotParent.getRight().refreshHeight();
        pivotParent.refreshHeight();
        return pivotParent;
    }
    // pivot must have a left with a right
    private BSTNode<E> leftRightRotate(BSTNode<E> pivot) {
        // this node becomes the parent of pivot
        BSTNode<E> pivotParent = pivot.getLeft().getRight();
        // this node becomes the left sibling of pivot
        BSTNode<E> pivotSibling = pivot.getLeft();
        pivotSibling.setRight(pivotParent.getLeft());
        pivotParent.setLeft(pivotSibling);
        pivot.setLeft(pivotParent.getRight());
        pivotParent.setRight(pivot);
        pivot.refreshHeight();
        pivotSibling.refreshHeight();
        pivotParent.refreshHeight();
        return pivotParent;
    }
    // pivot must have a right with a left
    private BSTNode<E> rightLeftRotate(BSTNode<E> pivot) {
        // this node becomes the parent of pivot
        BSTNode<E> pivotParent = pivot.getRight().getLeft();
        // this node becomes the right sibling of pivot
        BSTNode<E> pivotSibling = pivot.getRight();
        pivotSibling.setLeft(pivotParent.getRight());
        pivotParent.setRight(pivotSibling);
        pivot.setRight(pivotParent.getLeft());
        pivotParent.setLeft(pivot);
        pivot.refreshHeight();
        pivotSibling.refreshHeight();
        pivotParent.refreshHeight();
        return pivotParent;
    }
    // Maintain AVL Balance with backtracking
    private void reBalance(BSTNode<E> rt) {
        int balanceFactor = safeHeight(rt.getRight()) - safeHeight(rt.getLeft());
        if (balanceFactor == 2) {
            // right-right imbalance
            if (safeHeight(rt.getRight().getRight()) > safeHeight(rt.getRight().getLeft())) {
                System.out.println("leftRotated " + rt.getValue().toString());
                if (rt == root)
                    root = rt.getRight();
                leftRotate(rt);
            }
            // right-left imbalance
            else {
                System.out.println("rightLeftRotated " + rt.getValue().toString());
                if (rt == root)
                    root = rt.getRight().getLeft();
                rightLeftRotate(rt);
            }
        }
        if (balanceFactor == -2) {
            // left-left imbalance
            if (safeHeight(rt.getLeft().getLeft()) > safeHeight(rt.getLeft().getRight())) {
                System.out.println("rightRotated " + rt.getValue().toString());
                if (rt == root)
                    root = rt.getLeft().getLeft();
                rightRotate(rt);
            }
            // left-right imbalance
            else {
                System.out.println("leftRightRotated " + rt.getValue().toString());
                if (rt == root)
                    root = rt.getLeft().getRight();
                leftRightRotate(rt);
            }
        }
    }

    public void insert(E value) {
        if (!find(value)) {
            if (root == null) root = new BSTNode<E>(value);
            else insert(root, value);
            size++;
        }
    }
    private void insert(BSTNode<E> rt, E value) {
        // Find the correct insertion spot with recursion
        if (value.compareTo(rt.getValue()) > 0) {
            if (rt.getRight() == null) {
                rt.setRight(new BSTNode<E>(value));
                rt.refreshHeight();
            }
            else {
                insert(rt.getRight(), value);
                rt.refreshHeight();
                if (rt.getHeight() > 2)
                    System.out.println("Before: " + inOrderString());
                    reBalance(rt);
                System.out.println("After: " + inOrderString());
            }
        }
        else {
            if (rt.getLeft() == null) {
                rt.setLeft(new BSTNode<E>(value));
                rt.refreshHeight();
            }
            else {
                insert(rt.getLeft(), value);
                rt.refreshHeight();
                if (rt.getHeight() > 2)
                    reBalance(rt);
            }
        }
    }



    public E delete(E value) {
        return value;
    }

    public int height() {
        return safeHeight(root);
    }
    // can be called on null nodes
    private int safeHeight(BSTNode<E> rt) {
        if (rt == null) return 0;
        else return rt.getHeight();
    }

    public boolean isBalanced() {
        if (root == null) return true;
        return isBalanced(root);
    }
    private boolean isBalanced(BSTNode<E> rt) {
        if (rt == null) return true;
        else {
            if (Math.abs(safeHeight(rt.getLeft()) - safeHeight(rt.getRight())) > 1)
                return false;
            return isBalanced(rt.getLeft()) && isBalanced(rt.getRight());
        }
    }

    public boolean isPerfect() {
        if (root == null) return true;
        return isPerfect(root);
    }
    private boolean isPerfect(BSTNode<E> rt) {
        if (rt == null) return true;
        else {
            if (safeHeight(rt.getLeft()) - safeHeight(rt.getRight()) != 0)
                return false;
            return isPerfect(rt.getLeft()) && isPerfect(rt.getRight());
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
        if (rt.getHeight() == 1) return 1;
        return countLeaves(rt.getLeft()) + countLeaves(rt.getRight());
    }

}