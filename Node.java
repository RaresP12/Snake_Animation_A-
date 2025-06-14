import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Node extends JButton implements ActionListener{
    Node parent;
    Node child;
    int col;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;
    
    public Node(int col, int row){
        this.col = col;
        this.row = row;

        setBackground(Color.white);
        setForeground(Color.black);
        //addActionListener(this);
    }
    
    public void setAsStart(){
        setBackground(Color.blue);
        setForeground(Color.white);
        setText("start");
        start = true;
    }

    public void setAsGoal(){
        setBackground(Color.yellow);
        setForeground(Color.black);
        setText("goal");
        goal = true;
    }

    public void setAsSolid(){
        setBackground(Color.black);
        setForeground(Color.black);
        solid = true;
    }

    public void setAsChecked(){
        if (start == false && goal == false){
            setBackground(Color.orange);
            setForeground(Color.black);
        }
        checked = true;
    }

    public void setAsOpen(){
        open = true;
    }

    public void setAsPath(){
        setBackground(Color.red);
        setForeground(Color.black);
    }

    public void setSnake(){
        setBackground(Color.green);
        setForeground(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        setBackground(Color.orange);
    }
}
