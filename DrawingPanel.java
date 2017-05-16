import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawingPanel extends JPanel
{
	// Background Variables
	ArrayList<Integer> backdrops = new ArrayList<Integer>();
	ArrayList<Integer> awan = new ArrayList<Integer>();
	Color catapultColor = Color.WHITE;
	int baseLand = 0;
	int catapultX = 0;
	int catapultY = 0;
	Rectangle2D landSand;

	Line2D line = new Line2D.Double(0,0,0,0);
	Ellipse2D.Double ballInCatapult = new Ellipse2D.Double(0,0,0,0);
	ArrayList<Ball> ball = new ArrayList<Ball>();
	
	int golfHolePositionX;
	int golfHolePositionY;
	int golfHoleSize = 30;
	
	int totalScore = 0;
	int foulPlay = 0;
	int missedBall = 0;
	
	double timeBG = 0;
	double awanLapse = 0;
	
	public DrawingPanel()
	{
		setOpaque(false);
		generateBackdrops();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D d = (Graphics2D)g;
		d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		drawSky(d);
		drawBackdrops(d);
		drawLand(d);
		drawGolfFlag(d);
		drawSea(d);
		drawBall(d);
		drawCatapult(d);
		drawText(d);
		
		timeBG+=1;
	}
	
	public void generateBackdrops()
	{
		for(int i = 0; i < 28; i++){
			backdrops.add((int) (70 + Math.random() * 100));
			if(i < 12){
				double awanCDev = Math.abs( 0 - Math.sin( Math.toRadians((i+1)*43) ) * 40 );
				awan.add((int) ((70+awanCDev) + Math.random() * (80+awanCDev)));
			}
		}
	}
	public void drawSky(Graphics2D d)
	{
		GradientPaint gp = new GradientPaint(0, 0, new Color(207,226,243), 0, MainType.ballView.getHeight(), new Color(230,243,255));
		d.setPaint(gp);
		d.fillRect(0,0,MainType.ballView.getWidth(),MainType.ballView.getHeight());
	}
	public void drawBackdrops(Graphics2D d)
	{
		int devAwanX = 0;
		int devAwanY = 0;
		for(int i = 0; i < backdrops.size(); i++){
			Ellipse2D.Double currBack = new Ellipse2D.Double((i-1) * 50,(MainType.ballView.getHeight() + 50) - (baseLand + backdrops.get(i)),backdrops.get(i),backdrops.get(i));
			d.setColor(new Color((int)(142/1.3),(int)(224/1.3),(int)(90/1.3)));
			d.draw(currBack);
			d.setColor(new Color(142,224,90));
			d.fill(currBack);
			d.setColor(new Color((int)(142/1.05),(int)(224/1.05),(int)(90/1.05)));
			d.fill(new Ellipse2D.Double((i-1) * 50,(MainType.ballView.getHeight() + 70) - (baseLand + backdrops.get(i)),backdrops.get(i),backdrops.get(i)));
			// Draw Awan
			if(i<12){
				if(i % 4 == 0){
					devAwanX = devAwanX+1000;
					devAwanY = devAwanY+100;
				}
				double awanX = (i-1) * 50 + (timeBG-awanLapse) -  (3650)+ devAwanX;
				double awanY = (MainType.ballView.getHeight()) - (300+(int)(awan.get(i)/1.5)) - devAwanY;
				if(awanX > 3*1300)
					awanLapse = timeBG;
				Ellipse2D.Double currAwan = new Ellipse2D.Double(awanX,awanY,(int)(awan.get(i)/1.5),(int)(awan.get(i)/1.5));
				d.setColor(new Color(234,242,249));
				d.fill(currAwan);
				Ellipse2D.Double currAwanShadow = new Ellipse2D.Double(awanX+1,awanY+10,(int)(awan.get(i)/1.7),(int)(awan.get(i)/1.7));
				d.setColor(new Color((int)(234/1.03),(int)(242/1.03),(int)(249/1.03)));
				d.fill(currAwanShadow);
			}
		}
	}
	public void drawLand(Graphics2D d)
	{
		d.setStroke(new BasicStroke(5));
		landSand = new Rectangle2D.Double(0,MainType.ballView.getHeight() - baseLand,MainType.ballView.getWidth(),baseLand);
		d.setColor(new Color(228,200,116));
		d.draw(landSand);
		d.setColor(new Color(240,221,164));
		d.fill(landSand);
	}
	public void drawGolfFlag(Graphics2D d)
	{
		golfHolePositionX = MainType.ballView.getWidth() - 100;
		golfHolePositionY = MainType.ballView.getHeight() - baseLand;
		d.setColor(new Color(210,37,37));
		int flagDev = (int)(( 0 - Math.sin( Math.toRadians(timeBG*20) ) * 5 ) + 5);
		int [] xTri = new int[3];
		int [] yTri = new int[3];
		xTri[0]=golfHolePositionX+15; 
		yTri[0]=golfHolePositionY - 100; 
		xTri[1]=golfHolePositionX+15; 
		yTri[1]=golfHolePositionY - 60; 
		xTri[2]=golfHolePositionX+55 + flagDev;
		yTri[2]=golfHolePositionY - 100 + flagDev; 
		int polygonTri = 3;
		d.fillPolygon(new Polygon(xTri, yTri, polygonTri));
		d.setColor(Color.GRAY);
		d.draw( new Line2D.Double(golfHolePositionX+15,golfHolePositionY,golfHolePositionX+15,golfHolePositionY-100) );
		d.fill( new Ellipse2D.Double(golfHolePositionX,golfHolePositionY,golfHoleSize,7));
	}
	public void drawSea(Graphics2D d)
	{
		double waterDev0 = ( 0 - Math.sin( Math.toRadians(timeBG + 320) ) * 25 ) + 25;
		double waterDev1 = ( 0 - Math.sin( Math.toRadians(timeBG + 15) ) * 25 ) + 25;
		double waterDev2 = ( 0 - Math.sin( Math.toRadians(timeBG) ) * 20 ) + 20;
		for(int i = 0; i < 5; i++)
		{
			d.setColor(new Color((int)(240/1.1),(int)(221/1.1),(int)(164/1.1)));
			d.fill(new Ellipse2D.Double(i * 280,(MainType.ballView.getHeight() + 100) - (baseLand) - (waterDev0),300,50));
			d.setColor(new Color(255,255,255));
			d.fill(new Ellipse2D.Double(i * 280,(MainType.ballView.getHeight() + 100) - (baseLand) - (waterDev1),300,50));
			d.setColor(new Color(207,226,243));
			d.fill(new Ellipse2D.Double(i * 280,(MainType.ballView.getHeight() + 105) - (baseLand) - (waterDev2),300,50));
		}
		d.fill(new Rectangle2D.Double(0,(MainType.ballView.getHeight() + 120) - (baseLand) - (waterDev2),MainType.ballView.getWidth(),100));
	}
	public void drawBall(Graphics2D d)
	{
		int ballArraySize = ball.size();
		for (Iterator n = ball.listIterator(); n.hasNext();) {
			Ball i = (Ball) n.next(); 
			i.calculate(MainType.time);
			d.setColor(i.getColorShadow());
			d.fill(i.getBallShadow());
			d.setColor( new Color((int)((i.getColor()).getRed() / 1.3),(int)((i.getColor()).getGreen() / 1.3),(int)((i.getColor()).getBlue()  / 1.3)) );
			Ellipse2D.Double currBall = i.getBall();
			d.draw(currBall);
			d.setColor(i.getColor());
			d.fill(currBall);
			
			if(i.getPositionX() > golfHolePositionX && i.getPositionX() < golfHolePositionX + golfHoleSize && i.getPositionY() >= 0 && i.getPositionY() < 4 && i.getVelocityX(MainType.time) < 20 && i.getVelocityX(MainType.time) > 1){
				n.remove();
				totalScore++;
			}
			else if(i.getPositionX() > golfHolePositionX && i.getPositionX() < golfHolePositionX + golfHoleSize && i.getPositionY() >= 0 && i.getPositionY() < 4 && i.getVelocityX(MainType.time) < 1){
				n.remove();
				foulPlay++;
			}
			else if(i.getPositionX() > MainType.ballView.getWidth() && i.getPositionX() < 0){
				missedBall++;
				n.remove();
			}
		}
		if (ball.size() > 50){
			ball.remove(0);
		}
	}
	public void drawCatapult(Graphics2D d)
	{
		Color darkedColor = new Color((int)((catapultColor).getRed() / 1.3),(int)((catapultColor).getGreen() / 1.3),(int)((catapultColor).getBlue()  / 1.3));
		d.setColor(darkedColor);
		d.draw(ballInCatapult);
		d.setColor(catapultColor);
		d.fill(ballInCatapult);
		d.setColor(darkedColor);
		d.setStroke(new BasicStroke(15));
		d.draw(line);
	}
	public void drawText(Graphics2D d)
	{
		d.setColor(new Color(61,133,198));
		d.setFont(new Font("Arial", Font.PLAIN, 20)); 
		d.drawString(totalScore+" ball(s) scored", golfHolePositionX - 100, 30);
		d.setColor(new Color(210,37,37));
		d.setFont(new Font("Arial", Font.PLAIN, 20)); 
		d.drawString(foulPlay+" foul(s) play", golfHolePositionX - 100, 50);
		d.setColor(new Color(73,73,73));
		d.setFont(new Font("Arial", Font.PLAIN, 20)); 
		d.drawString(((ball.size() + missedBall) > 49 ? "many" : (ball.size() + missedBall))+" missed", golfHolePositionX - 100, 70);
	}
	
	public void redrawBGR()
	{
		translateBallInCatapult(MainType.ballSize,MainType.ballSize,catapultX - 10,MainType.ballView.getHeight() - catapultY - 10);
		translateCatapult(catapultX,MainType.ballView.getHeight() - catapultY,catapultX,MainType.ballView.getHeight() - catapultY);
	}
	public void setBaseLand(int baseLand,int catapultX,int catapultY)
	{
		this.baseLand = baseLand;
		this.catapultX = catapultX;
		this.catapultY = catapultY;
		redrawBGR();
	}
	
	public void setCatapultColor(Color catapultColor)
	{
		this.catapultColor = catapultColor;
	}
	public Color getCatapultColor()
	{
		return catapultColor;
	}
	
	public void translateBallInCatapult(double w, double h, double x, double y)
	{
		ballInCatapult = new Ellipse2D.Double(x,y,w,h);
	}
	public void translateCatapult(double x1, double x2, double x3, double x4)
	{
		line = new Line2D.Double(x1,x2,x3,x4);
	}
	
	public void addComponent(Ball b)
	{
		ball.add(b);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(MainType.ballView.getWidth(), MainType.ballView.getHeight());
	}
}