import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainType
{
	static final JFrame ballView = new JFrame("Catapult Golf");
	static final DrawingPanel pane = new DrawingPanel();
	static double time = 0;
	static final int baseLand = 160;
	static final int catapultX = 120;
	static final int catapultY = baseLand + 80;
	static final int ballSize = 20;
	static Color color;
	static boolean mouseFlag = false;
	
	public static void main (String [] args)
	{
		ballView.setSize(900,650);
		ballView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ballView.add(pane);
		ballView.setVisible(true);
		
		pane.setBaseLand(baseLand,catapultX,catapultY);
		
		ballView.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			pane.redrawBGR();
			}
		});
		
		ballView.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e){
				if (mouseFlag == true){
					double v_x = (e.getX() - catapultX);
					double v_y = ((ballView.getHeight() - e.getY()) - (catapultY - 30));
					Ball currBall = new Ball(catapultX-12,catapultY-baseLand-10,-v_x,-v_y,ballSize,color,baseLand);
					pane.addComponent(currBall);
					adjustCatapult(catapultX,ballView.getHeight() - catapultY + 30);
					mouseFlag = false;
				}
			}
			public void mousePressed(MouseEvent e){
				if(e.getX() > (catapultX - 15) && e.getX() < (catapultX + 15) && e.getY() > ((ballView.getHeight() - catapultY) + 20) && e.getY() < ((ballView.getHeight() - catapultY) + 50)){
					generateRandomColor();
					pane.setCatapultColor(color);
					adjustCatapult(e.getX(),e.getY());	
					mouseFlag = true;
				}
			}
		});
		ballView.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				if (mouseFlag == true){
					adjustCatapult(e.getX(),e.getY());
				}
			}
		});
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				time+=0.1;
				pane.repaint();
			}
		},0,10);
	}
	
	public static void adjustCatapult(int x,int y)
	{
		pane.translateBallInCatapult(ballSize,ballSize,x - 10,y - 40);
		pane.translateCatapult(catapultX, ballView.getHeight() - catapultY,x,y - 30);
	}
	
	public static void generateRandomColor()
	{
		int red = (int) (150+(Math.random() * 105));
		int green = (int) (150+(Math.random() * 105));
		int blue = (int) (150+(Math.random() * 105));
		color = new Color(red,green,blue);;
	}
}