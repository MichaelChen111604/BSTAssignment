import java.util.Random;
public class Tester {
    public static void main(String[] args) {
        BST<Character> test = new BST<Character>();
        test.insert('a');
        test.insert('b');
        test.insert('c');
        test.insert('d');
        test.insert('e');
        test.insert('f');
        test.insert('g');
        test.insert('h');
        test.insert('i');
        test.insert('j');
        System.out.println(test.depthFirstString());
        System.out.println(test.breadthFirstString());
    }
    public static void check(BST<Character> input) {
        System.out.println(input.depthFirstString());
        System.out.println("IsBalanced- " + input.isBalanced());
        System.out.println("IsPerfect- " + input.isPerfect());
    }
    public static void generateTests() {
        Random r = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String insertions = "";
        BST<Character> tester = new BST<Character>();
        for (int i = 0; i < 1000; i++) {
            tester = new BST<Character>();
            insertions = "";
            for (int j = 0; j < 12; j++) {
                char newChar = alphabet.charAt(r.nextInt(alphabet.length()));
                tester.insert(newChar);
                insertions = insertions.concat(String.valueOf(newChar) + "i ");
                char newChar2 = alphabet.charAt(r.nextInt(alphabet.length()));
                tester.delete(newChar2);
                insertions = insertions.concat(String.valueOf(newChar2) + "r ");
                if (!tester.isBalanced()) {
                    System.out.println("Ya fucked up");
                    System.out.println(insertions);
                    System.out.println(tester.depthFirstString());
                }
            }
        }
    }
}
