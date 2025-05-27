import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DemoPanel extends JPanel {
    //SCREEN SETTINGS
    final int maxCol = 20;
    final int maxRow = 15;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    //NODES
    Node[][] node = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode, snake;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();
    ArrayList<Node> path = new ArrayList<>();

    boolean goalReached = false;

    //Everything placed on the screen will have to be placed inside DemoPanel
    public DemoPanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
        this.requestFocusInWindow();

        //Place Nodes
        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow){
            node[col][row] = new Node(col, row);
            this.add(node[col][row]);
            col++;

            if(col == maxCol){
                col = 0;
                row++;
            }
        }

        //Start and Goal Nodes
        setStartNode(colA, rowA);
        setGoalNode(colA, rowA);
        //5 Solids
        setSolid(colA, rowA);
        setSolid(colA, rowA);
        setSolid(colA, rowA);
        setSolid(colA, rowA);
        setSolid(colA, rowA);
        //set to the cost on the nodes
        setCostOnNodes();
    }
    
    //Randomly generate start and goal
    int colA = (int)(Math.random() * maxCol - 1);
    int rowA = (int)(Math.random() * maxRow - 1);
    int tempA = 0;
    int tempB = 0;
    
    public void setStartNode(int col, int row){
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
        openList.add(startNode);
        tempA = col;

    }
    public void setGoalNode(int col, int row){
        while(col == colA && row == rowA){
            col = (int)(Math.random() * maxCol - 1);
            row = (int)(Math.random() * maxRow - 1);
        }
        node[col][row].setAsGoal();
        goalNode = node[col][row];
        tempB = col;

    }
    //Solid nodes cannot overlap the other 2 nodes
    public void setSolid(int col, int row){
        while(col == tempA || col == tempB){
            col = (int)(Math.random() * maxCol - 1);
            row = (int)(Math.random() * maxRow - 1);
        }
        node[col][row].setAsSolid();

    }

    //Find the fastest path using A* from the closet node(head of the snake)
    //1) calculate the cost of the nodes
    public void getCost(Node node){
        //gCost = distance from current node to start node
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        
        //hCost = distance from current node to goal node
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        //fCost = total distance
        node.fCost = node.gCost + node.hCost;

        //Display the costs on the nodes
        if(node != startNode && node != goalNode) {
            node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost + "</html>");
        }

    }
    private void setCostOnNodes(){
        int col = 0;
        int row = 0;
        while(col < maxCol && row < maxRow){
            getCost(node[col][row]);
            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }
        }
    }
    //2) Search the nodes based on their values
    public void search(){
        if(goalReached == false){
            if(openList.isEmpty()){
                System.out.println("No path found");
                return;
            }
            //Get the current node's position
            int col = currentNode.col;
            int row = currentNode.row;

            //Mark the current node as checked
            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //Order of how the nodes are checked
            
            if(row - 1 >= 0){//Up
                openNode(node[col][row -1]);
            }
            if(col - 1 >=0){//Down
                openNode(node[col - 1][row]);
            }
            if(row + 1 >=0){//Left
                openNode(node[col][row + 1]);
            }
            if(col + 1 >=0){//Right
                openNode(node[col + 1][row]);
            }
            
            
            //Find the best node in the openlist
            int bestNodeIndex = 0;
            int bestNodefCost = Integer.MAX_VALUE;

            for(int i=0; i < openList.size(); i++){
                //Check if the f cost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //If the f cost is equal, check g cost
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            //After the loop get the best node
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                trackPath();
                System.out.println("Goal Reached!");
            }
        }
    }
    //Open the node to be checked and add it to the open list
    private void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
            System.out.println("Added to openList: F:" + node.fCost + " G:" + node.gCost + " (" + node.col + ", " + node.row + ")");
        }
    }
    private void trackPath(){
        //Draw the best path
        Node current = goalNode;
        Node previous = null;
        Node child = null;

        while(current != startNode){
            Node parent = current.parent;
            parent.child = current;
            current = parent;
            if(current != startNode){
                current.setAsPath();
                path.add(current);
            }
        }
        new Thread(() -> {
            Node snakeCurrent = startNode;
            Node s1;
            Node s2;
            Node s3;
            while(snakeCurrent != goalNode){
                snakeCurrent = snakeCurrent.child;
                s1 = snakeCurrent.child;
                s2 = s1.child;
                s3 = s2.child;
                snakeCurrent.setSnake();
                s1.setSnake();
                s2.setSnake();
                s3.setSnake();
                snakeCurrent.setAsPath();

                path.add(snakeCurrent);
                path.add(s1);
                path.add(s2);
                path.add(s3);
                repaint();
                try {
                    Thread.sleep(1000); // 1 second pause
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    /*
    public void snake(){
        //Snake will be made up of 4 nodes
        //Once the fastest path is calculated the snake will move towards the goal
        //The square in front of the head will be the new head and the last node will blend in the background
        Node current = startNode;

        while(current != goalNode){
            current = current.child;
                current.setSnake();
                path.add(current);
                current.parent.setAsPath();

        }         

    }
    */

}
