public class Tester {
    public static void main(String[] args) {
        BST<String> test = new BST<String>();
        for (char c = 'a'; c <= 'z'; c++) {
            test.insert(String.valueOf(c));
        }
        for (char c = 'z'; c >= 'a'; c--) {
            test.insert(String.valueOf(c));
        }
        System.out.println(test.inOrderString());
        System.out.println(test.countLeaves());
    }
}
