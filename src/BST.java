import java.util.LinkedList;
import java.util.Queue;

public class BST<E extends Comparable<E>> {
    class BSTNode<F extends Comparable<F>> {
        private F value;
        private BSTNode<F> left, right;
        private int height; // height of the subtree rooted at this node

        BSTNode() {
            height = 1;
        }
        BSTNode(F value) {
            this.value = value;
            height = 1;
        }

        public BSTNode<F> getLeft() {
            return left;
        }
        public BSTNode<F> getRight() {
            return right;
        }
        public int getHeight() { return height; }
        public F getValue() {
            return value;
        }

        public void setLeft(BSTNode<F> newLeft) {
            left = newLeft;
        }
        public void setRight(BSTNode<F> newRight) {
            right = newRight;
        }
        public void setHeight(int newHeight) { height = newHeight; }
        public void setValue(F newValue) { value = newValue; }

        public boolean isLeaf() {
            return (left == null && right == null);
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

    BSTNode<E> root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public int getSize() { return size; }
    public int height() { return safeHeight(root); }
    // can be called on null nodes
    private int safeHeight(BSTNode<E> rt) {
        if (rt == null) return 0;
        else return rt.getHeight();
    }

    public boolean find(E value) {
        return find(root, value);
    }
    private boolean find(BSTNode<E> rt, E value) {
        if (rt == null) { return false; }
        else if (rt.getValue().compareTo(value) == 0) { return true; }
        else if (rt.getValue().compareTo(value) < 0) { return find(rt.getRight(), value); }
        else return find(rt.getLeft(), value);
    }

    // utility functions used in balancing
    // pivot must have a right with a right
    private BSTNode<E> leftRotate(BSTNode<E> pivot) {
        // this node becomes the parent of pivot
        BSTNode<E> pivotParent = pivot.getRight();
        pivot.setRight(pivotParent.getLeft());
        pivotParent.setLeft(pivot);
        pivot.setHeight(1 + Math.max(safeHeight(pivot.getLeft()), safeHeight(pivot.getRight())));
        pivotParent.setHeight(1 + Math.max(safeHeight(pivotParent.getLeft()), safeHeight(pivotParent.getRight())));
        if (pivot == root) root = pivotParent;
        return pivotParent;
    }
    // pivot must have a left with a left
    private BSTNode<E> rightRotate(BSTNode<E> pivot) {
        // this node becomes the parent of pivot
        BSTNode<E> pivotParent = pivot.getLeft();
        pivot.setLeft(pivotParent.getRight());
        pivotParent.setRight(pivot);
        pivot.setHeight(1 + Math.max(safeHeight(pivot.getLeft()), safeHeight(pivot.getRight())));
        pivotParent.setHeight(1 + Math.max(safeHeight(pivotParent.getLeft()), safeHeight(pivotParent.getRight())));
        if (pivot == root) root = pivotParent;
        return pivotParent;
    }

    // helper function which can be called on null nodes
    private int balanceFactor(BSTNode<E> node) {
        if (node == null) return 0;
        return safeHeight(node.getRight()) - safeHeight(node.getLeft());
    }

    public void insert(E value) {
        if (!find(value)) root = insert(root, value);
    }
    private BSTNode<E> insert(BSTNode<E> rt, E value) {
        // Find the correct insertion spot with recursion
        if (rt == null) {
            size++;
            return (new BSTNode<E>(value));
        }

        // this code only executes if rt is an ancestor of the newly-inserted node
        if (value.compareTo(rt.getValue()) < 0)
            { rt.setLeft(insert(rt.getLeft(), value)); }
        else if (value.compareTo(rt.getValue()) > 0)
            { rt.setRight(insert(rt.getRight(), value)); }

        // we can call setHeight here because the lower nodes are guaranteed to have correct height
        rt.setHeight(1 + Math.max(safeHeight(rt.getLeft()), safeHeight(rt.getRight())));
        // Maintain AVL Balance
        int balanceFactor = balanceFactor(rt);
        if (balanceFactor == 2) {
            // Right right
            if (balanceFactor(rt.getRight()) >= 0)
                return leftRotate(rt);
            // Right left
            else {
                rt.setRight(rightRotate(rt.getRight()));
                return leftRotate(rt);
            }
        }
        if (balanceFactor == -2) {
            // Left left
            if (balanceFactor(rt.getLeft()) <= 0)
                return rightRotate(rt);
            // Left right
            else {
                rt.setLeft(leftRotate(rt.getLeft()));
                return rightRotate(rt);
            }
        }
        // If no balancing necessary, return original node
        return rt;
    }

    public E delete(E value) {
        boolean deleted = find(value);
        delete(root, value);
        if (deleted) {
            size--;
            return value;
        }
        return null;
    }
    private BSTNode<E> delete(BSTNode<E> rt, E value) {
        // find the correct deletion spot with recursion
        if (rt == null)
            { return null;}
        if (rt.getValue().compareTo(value) < 0)
            { rt.setRight(delete(rt.getRight(), value)); }
        else if (rt.getValue().compareTo(value) > 0)
            { rt.setLeft(delete(rt.getLeft(), value)); }
        else {
            // <2 children
            if (rt.getLeft() == null) {
                if (rt == root) root = rt.getRight();
                rt = rt.getRight();
            }
            else if (rt.getRight() == null) {
                if (rt == root) root = rt.getLeft();
                rt = rt.getLeft();
            }
            // 2 children
            else {
                // get the next sequential node and the one before it
                BSTNode<E> replacement = rt.getRight();
                while (replacement.getLeft() != null) replacement = replacement.getLeft();
                // copy replacement into rt then delete rt
                 rt.setValue(replacement.getValue());
                // delete function has to be used in case replacement has children
                // replacement has at most one (right) child, so the recursion will end on this call
                rt.setRight(delete(rt.getRight(), replacement.getValue()));
            }
        }
        // keep balance- this code only executes if there was a deletion
        if (rt == null) {
            return null;
        }
        // the children of rt have correct heights at this point because this code runs on them first
        rt.setHeight(1 + Math.max(safeHeight(rt.getLeft()), safeHeight(rt.getRight())));
        // AVL balancing
        int balanceFactor = balanceFactor(rt);
        if (balanceFactor == 2) {
            // Right left
            if (balanceFactor(rt.getRight()) < 0) {
                rt.setRight(rightRotate(rt.getRight()));
                return leftRotate(rt);
            }
            // Right right
            else {
                return leftRotate(rt);
            }
        }
        if (balanceFactor == -2) {
            // Left right
            if (balanceFactor(rt.getLeft()) > 0) {
                rt.setLeft(leftRotate(rt.getLeft()));
                return rightRotate(rt);
            }
            // Left left
            else {
                return rightRotate(rt);
            }
        }
        // return rt if nothing was changed (rotated)
        return rt;
    }

    public boolean isBalanced() {
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
        return isPerfect(root);
    }
    private boolean isPerfect(BSTNode<E> rt) {
        if (rt == null) return true;
        else {
            if (safeHeight(rt.getLeft()) != safeHeight(rt.getRight()))
                return false;
            return isPerfect(rt.getLeft()) && isPerfect(rt.getRight());
        }
    }

    public String inOrder() {
        return inOrder(root);
    }
    private String inOrder(BSTNode<E> rt) {
        if (rt == null) return "";
        String out = "(";
        out += inOrder(rt.getLeft()) + "-";
        out += rt.getValue() + "-";
        out += inOrder(rt.getRight());
        out += ")";
        return out;
    }

    public String bfOrder() {
        String out = "";
        Queue<BSTNode<E>> nodeQueue = new LinkedList<BSTNode<E>>();
        BSTNode<E> nextNode;
        if (root != null) nodeQueue.add(root);
        while (!nodeQueue.isEmpty()) {
            nextNode = nodeQueue.remove();
            out += nextNode.getValue().toString() + " ";
            if (nextNode.getLeft() != null) nodeQueue.add(nextNode.getLeft());
            if (nextNode.getRight() != null) nodeQueue.add(nextNode.getRight());
        }
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

    public int countParentNodes() {
        return countParentNodes(root);
    }
    private int countParentNodes(BSTNode<E> rt) {
        if (rt == null || rt.isLeaf()) return 0;
        return 1 + countParentNodes(rt.getLeft()) + countParentNodes(rt.getRight());
    }
}
