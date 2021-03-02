import java.util.Random;
public class Tester {
    public static void main(String[] args) {
        BST<Character> test = new BST<Character>();
        // yi rr zi jr zi cr ti qr oi nr ki rr di jr si vr ii ur ui hr qi gr xi gr ki kr gi vr ji dr ai vr ii lr ki sr
        test.insert('y');
        test.delete('r');
        test.insert('z');
        test.delete('j');
        test.insert('z');
        test.delete('c');
        test.insert('t');
        test.delete('q');
        test.insert('o');
        test.delete('n');
        check(test);
        test.insert('k');
        test.delete('r');
        test.insert('d');
        test.delete('j');
        test.insert('s');
        test.delete('v');
        test.insert('i');
        test.delete('u');
        test.insert('u');
        test.delete('h');
        check(test);
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
                insertions = insertions.concat(String.valueOf(newChar2) + "d ");
                if (!tester.isBalanced()) {
                    System.out.println("Ya fucked up");
                    System.out.println(insertions);
                    System.out.println(tester.depthFirstString());
                }
            }
        }
    }
}
