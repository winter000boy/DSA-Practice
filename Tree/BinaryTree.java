class Node{
    int data;
    Node left;
    Node right;

    public Node(int data){
        this.data = data;
    }
}



public class BinaryTree {

    Node root;

    public void insert(int data){
        if (root == null) {
            root = new Node(data);
        }
        else if(data < root.data){
            root.left.data = data;
        }
    }
}
