import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    public class Node {
        String key;
        Node left, right;
        boolean color;

        public Node(String key, boolean color) {
            this.key = key;
            this.color = color;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }
    }

    public RedBlackTree() {
        this.root = null;
    }

    public void insert(String key) {
        root = insert(root, key);
        root.color = BLACK;
    }

    private Node insert(Node h, String key) {
        if (h == null) {
            return new Node(key, RED);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = insert(h.left, key);
        } else if (cmp > 0) {
            h.right = insert(h.right, key);
        }

        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        return h;
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void delete(String key) {
        if (root == null) {
            return;
        }
        root = delete(root, key);
        if (root != null) {
            root.color = BLACK;
        }
    }

    private Node delete(Node h, String key) {
        if (h == null) {
            return null;
        }

        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && h.right == null) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (key.compareTo(h.key) == 0) {
                Node min = min(h.right);
                h.key = min.key;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    private Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node moveRedLeft(Node h) {
        if (h != null && h.right != null) {
            flipColors(h);
            if (isRed(h.right.left)) {
                h.right = rotateRight(h.right);
                h = rotateLeft(h);
                flipColors(h);
            }
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        if (h != null && h.left != null) {
            flipColors(h);
            if (isRed(h.left.left)) {
                h = rotateRight(h);
                flipColors(h);
            }
        }
        return h;
    }

    private Node balance(Node h) {
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h=rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        return h;
    }

    public List<String> getCuvinte() {
        List<String> cuvinte = new ArrayList<>();
        getCuvinte(root, cuvinte);
        return cuvinte;
    }

    private void getCuvinte(Node x, List<String> cuvinte) {
        if (x == null) {
            return;
        }
        getCuvinte(x.left, cuvinte);
        cuvinte.add(x.key);
        getCuvinte(x.right, cuvinte);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
