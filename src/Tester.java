import java.util.Random;
public class Tester {
    public static void main(String[] args) {
        generateTests();
    }
    static int addSize(BST<Character> bst, BST<Character>.BSTNode<Character> rt) {
        if (rt != null) {
            return 1 + addSize(bst, rt.getLeft()) + addSize(bst, rt.getRight());
        }
        return 0;
    }
    public static void check(BST<Character> input) {
        System.out.println(input.inOrder());
        System.out.println(input.getSize());
        System.out.println("IsBalanced- " + input.isBalanced());
        System.out.println("IsPerfect- " + input.isPerfect());
    }
    public static void generateTests() {
        Random r = new Random();
        boolean passed = true;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String insertions = "";
        BST<Character> tester = new BST<Character>();
        for (int i = 0; i < 100000; i++) {
            tester = new BST<Character>();
            insertions = "";
            for (int j = 0; j < 20; j++) {
                char newChar = alphabet.charAt(r.nextInt(alphabet.length()));
                tester.insert(newChar);
                insertions = insertions.concat(String.valueOf(newChar) + "i ");
                char newChar2 = alphabet.charAt(r.nextInt(alphabet.length()));
                tester.delete(newChar2);
                insertions = insertions.concat(String.valueOf(newChar2) + "d ");
                if (!tester.isBalanced()) {
                    System.out.println("Oof!");
                    System.out.println(insertions);
                    System.out.println(tester.inOrder());
                    passed = false;
                }
            }
        }
        if (passed) System.out.println("All tests passed.");
    }
}
