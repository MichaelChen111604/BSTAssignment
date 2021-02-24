public class Tester {
    public static void main(String[] args) {
        BST<String> test = new BST<String>();
        test.insert("Hello");
        test.insert("There!");
        test.insert("General");

        test.insert("Kenobi!");
        test.insert("You");
        test.insert("are");
        test.insert("a bold one.");
        System.out.println(test.inOrderString());
        System.out.println(test.height());
    }
}
