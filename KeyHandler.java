import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

    DemoPanel dp;

    public KeyHandler(DemoPanel dp){
        this.dp = dp;
    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_ENTER){
            while (dp.goalReached == false){
                dp.search();
                System.out.println("Search initiated!");
            }
        }
       /* 
        if(code == KeyEvent.VK_K){
            if(dp.goalReached == true){
                dp.snake();
                System.out.println("You pressed k!");
            }
        }
        */
        
    }

    @Override
    public void keyReleased(KeyEvent e){

    }
    
}