import javax.swing.JFrame;

public class main {
/*
1 Make the snake by colouring 4 nodes in a line

2 Find the fastest path using A* from the closet node(head of the snake)

3 Once the fastest path was found, snake the snake move

4 Colour in the node in front of the head to the snake colour and change the last node to the colour of the background

5 Once goal reached change the goal and repeat
*/
  public static void main(String[] args) {
    // window
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.add(new DemoPanel());
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);

  }
}
