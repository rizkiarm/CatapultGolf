import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Ball
{	
	private double r 		= 0;
	
	private final double G 	= -9.81;
	
	private double a 		= 0;
	private double x_0 	= 0;
	private double y_0 	= 0;
	private double vx_0 	= 0;
	private double vy_0 	= 0;
	
	private double x_1 	= 0;
	private double y_1 	= 0;
	
	private double lapse 	= 0;
	
	private int baseLand 	= 0;
	
	private Color color;
	
	public Ball(double x_0,double y_0,double vx_0,double vy_0, double r, Color color, int baseLand)
	{
		this.r = r;
		this.x_0 = x_0;
		this.y_0 = y_0;
		this.vx_0 = vx_0;
		this.vy_0 = vy_0;
		this.color = color;
		this.baseLand = baseLand;
		this.lapse = MainType.time;
	}
	
	public void calculate(double t)
	{
		setPositionX(t);
		setPositionY(t);
	}
	
	public Ellipse2D.Double getBall(){
		double BASE = MainType.ballView.getHeight() - (baseLand + r);
		return new Ellipse2D.Double(x_1,BASE - y_1,r,r);
	}
	
	public Ellipse2D.Double getBallShadow(){
		double BASE = MainType.ballView.getHeight() - (baseLand);
		return new Ellipse2D.Double(x_1-((y_1*0.3)/2) , BASE , (y_1*0.3)+r , (y_1*0.05)+5);
	}
	
	public Color getColor(){
		return color;
	}
	public Color getColorShadow(){
		return new Color(128,128,128,(int)(255/((y_1*0.05)+1)));
	}
	
	public double getRadius(){
		return r;
	}
	public double getVelocityX(double t)
	{
		return vx_0 + a * (t - lapse);
	}
	public double getVelocityY(double t)
	{
		return vy_0 + G * (t - lapse);
	}
	public double getPositionX()
	{
		return x_1;
	}
	
	public double getPositionY()
	{
		return y_1;
	}
	
	public void setPositionX(double t)
	{
		x_1 = x_0 + (vx_0*(t - lapse)) + (0.5 * a * (t - lapse) * (t - lapse));
	}
	
	public void setPositionY(double t)
	{
		y_1 = y_0 + (vy_0*(t - lapse)) + (0.5 * G * (t - lapse) * (t - lapse));
		if (y_1 <= 0 && t != 0){
			x_0 = x_1;
			y_0 = 0;
			
			lapse = t;
			
			double vy_temp = getVelocityY(t);
			if(vy_temp >= 0){
				vy_0 = vy_temp/1.5;
			}
			else
			{
				vy_0 = -vy_temp/1.5;
			}
			vx_0 = getVelocityX(t)/ 1.5;
		}
		if(y_1 < 0){
			y_1 = 0;
		}
	}
}