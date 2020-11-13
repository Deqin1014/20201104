import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Main extends JFrame{
	private static GamePanel p1 = new GamePanel();
	private static InformationPanel p2 = new InformationPanel();
	public Main(){
		setLayout(new BorderLayout());
		add(p1,BorderLayout.CENTER);
        add(p2,BorderLayout.EAST);
	}
	public static void main(String[] args){
		JFrame frame = new Main();
		frame.setTitle("Snake");
		frame.setSize(1100, 800);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
		Thread thread1 = new Thread(p1);
		thread1.start();
		Thread thread2 = new Thread(p2);
		thread2.start();
	}
}
class GamePanel extends JPanel implements Runnable{
	public static final int PER_UNIT_LENGTH = 20;
	public static final int MULTIPLE = 15;
	public static final int HALF_SIDE = MULTIPLE * PER_UNIT_LENGTH;
	private boolean isFirstRun = true;
	private boolean isStarted = false;
	private boolean isPaused = false;
	private static int score = 0;
	private static int information = 0;
	private Snake snake = new Snake();
	private Dot dessert = new Dot();
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int xCentre = getWidth() / 2;
		int yCentre = getHeight() / 2;
		int xRandom, yRandom;
		if(isFirstRun){
			isFirstRun = false;
			g.drawRect(xCentre - HALF_SIDE, yCentre - HALF_SIDE, HALF_SIDE * 2, HALF_SIDE * 2);
			snake.getHead().setX(xCentre);
			snake.getHead().setY(yCentre);
			g.setColor(Color.ORANGE);
			g.fillOval(snake.getHead().getX(), snake.getHead().getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);
			do{
				xRandom = xCentre - HALF_SIDE + ((int)(Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
				yRandom = yCentre - HALF_SIDE + ((int)(Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
			}while(xRandom == snake.getHead().getX() && yRandom == snake.getHead().getY());
			dessert.setX(xRandom);
			dessert.setY(yRandom);
			g.setColor(Color.DARK_GRAY);
			g.fillOval(dessert.getX(), dessert.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);
		}
		else{
			
			g.drawRect(xCentre - HALF_SIDE, yCentre - HALF_SIDE, HALF_SIDE * 2, HALF_SIDE * 2);
			g.setColor(Color.MAGENTA);
			for(int i = 0;i < snake.getBody().size();i++){
				g.fillOval(snake.getBody().get(i).getX(), snake.getBody().get(i).getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);
			}
			g.setColor(Color.ORANGE);
			g.fillOval(snake.getHead().getX(), snake.getHead().getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);
			if(isEncountered()){
				do{
					xRandom = xCentre - HALF_SIDE + ((int)(Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
					yRandom = yCentre - HALF_SIDE + ((int)(Math.random() * MULTIPLE * 2)) * PER_UNIT_LENGTH;
				}while(xRandom == snake.getHead().getX() && yRandom == snake.getHead().getY());
				dessert.setX(xRandom);
				dessert.setY(yRandom);
			}
			g.setColor(Color.DARK_GRAY);
			g.fillOval(dessert.getX(), dessert.getY(), PER_UNIT_LENGTH, PER_UNIT_LENGTH);
			if(isCrushed()){
				g.setColor(Color.BLACK);
				FontMetrics fm = g.getFontMetrics();
				int stringWidth = fm.stringWidth("GAME OVER");
				int stringAscent = fm.getAscent();
				int xCoordinate = xCentre - stringWidth / 2;
				int yCoordinate = yCentre - stringAscent / 2;
				g.drawString("GAME OVER", xCoordinate, yCoordinate);
			}
		}
	}
	public GamePanel(){
		setFocusable(true);
		setFont(new Font("Californian FB", Font.BOLD, 80));
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int direction = snake.getDirection();
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						if(isStarted && !isPaused && !isCrushed()){
							if(direction != Snake.DIRECTION_UP && direction != Snake.DIRECTION_DOWN){
							snake.setDirection(Snake.DIRECTION_UP);
							changeSnakeLocation();
							}
						}
						break;
					case KeyEvent.VK_DOWN:
						if(isStarted && !isPaused && !isCrushed()){
							if(direction != Snake.DIRECTION_UP && direction != Snake.DIRECTION_DOWN){
							snake.setDirection(Snake.DIRECTION_DOWN);
							changeSnakeLocation();
							}
						}
						break;
					case KeyEvent.VK_LEFT:
						if(isStarted && !isPaused && !isCrushed()){
							if(direction != Snake.DIRECTION_LEFT && direction != Snake.DIRECTION_RIGHT){
							snake.setDirection(Snake.DIRECTION_LEFT);
							changeSnakeLocation();
							}
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(isStarted && !isPaused && !isCrushed()){
							if(direction != Snake.DIRECTION_LEFT && direction != Snake.DIRECTION_RIGHT){
							snake.setDirection(Snake.DIRECTION_RIGHT);
							changeSnakeLocation();
							}
						}
						break;
					case KeyEvent.VK_ENTER:
						if(isCrushed()){
							snake.setDirection(Snake.DIRECTION_RIGHT);
							snake.setSpeed(Snake.SPEED);
							snake.setBody(new LinkedList<Dot>());
							isFirstRun = true;
							isStarted = false;
							isPaused = false;
							score = 0;
							information = 0;
							repaint();
						}
						else{
							isStarted = true;
						}
						break;
					default:
				}
			}
		});
	}
	public void run(){
		while(true){
			if(isStarted && !isPaused && !isCrushed()){
				changeSnakeLocation();
			}
			try{
				Thread.sleep(snake.getSpeed());
			}
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	public synchronized void changeSnakeLocation(){
		int xPrevious = snake.getHead().getX();
		int yPrevious = snake.getHead().getY();
		switch(snake.getDirection()){
			case Snake.DIRECTION_UP:snake.getHead().setY(yPrevious - PER_UNIT_LENGTH);break;
			case Snake.DIRECTION_DOWN:snake.getHead().setY(yPrevious + PER_UNIT_LENGTH);break;
			case Snake.DIRECTION_LEFT:snake.getHead().setX(xPrevious - PER_UNIT_LENGTH);break;
			case Snake.DIRECTION_RIGHT:snake.getHead().setX(xPrevious + PER_UNIT_LENGTH);break;
			default:
		}
		if(isEncountered()){
			score++;
			snake.getBody().addFirst(new Dot(xPrevious, yPrevious));
		}
		else{
			snake.getBody().addFirst(new Dot(xPrevious, yPrevious));
			snake.getBody().removeLast();
		}
		repaint();
		requestFocus();
	}
	public boolean isEncountered(){
		if(snake.getHead().getX() == dessert.getX() 
		&& snake.getHead().getY() == dessert.getY()){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isCrushed(){
		boolean isCrushedByBorder = snake.getHead().getX() >= getWidth() / 2 + HALF_SIDE  
		|| snake.getHead().getX() < getWidth() / 2 - HALF_SIDE 
		|| snake.getHead().getY() >= getHeight() / 2 + HALF_SIDE 
		|| snake.getHead().getY() < getHeight() / 2 - HALF_SIDE;
		if(isCrushedByBorder){
			information = 1;
			return true;
		}
		boolean isCrushedByItself = false;
		for(int i = 0;i < snake.getBody().size();i++){
			if(snake.getHead().getX() == snake.getBody().get(i).getX() 
			&& snake.getHead().getY() == snake.getBody().get(i).getY() && !isCrushedByItself)
				isCrushedByItself = true;
		}
		if(isCrushedByItself){
			information = 2;
			return true;
		}
		else{
			return false;
		}
	}
	public static int getScore(){
		return score;
	}
	public static int getInformation(){
		return information;
	}
}
class InformationPanel extends JPanel implements Runnable{
	private Box box = Box.createVerticalBox();
	private JLabel[] help = new JLabel[5];
	private JLabel score = new JLabel("Score:");
	private JLabel show = new JLabel();
	public InformationPanel(){
		for(int i = 0;i < help.length;i++)
			help[i] = new JLabel();
		
		Font font1 = new Font("DialogInput", Font.BOLD, 25);
		Font font2 = new Font("DialogInput", Font.BOLD, 25);
		for(int i = 0;i < help.length;i++)
			help[i].setFont(font1);
		score.setFont(font2);
        score.setForeground(Color.BLUE);
		show.setFont(font2);
        show.setForeground(Color.MAGENTA);
		help[0].setText("Enter Start");
		help[0].setForeground(Color.PINK);
		help[1].setText("Up Down Left Right Move");
		help[1].setForeground(Color.PINK);
		help[2].setText("Enter Reset");
		help[2].setForeground(Color.PINK);
		add(box);
		box.add(Box.createVerticalStrut(150));
		for(int i = 0;i < help.length;i++){
			box.add(help[i]);
			box.add(Box.createVerticalStrut(20));
		}
		box.add(Box.createVerticalStrut(20));
		box.add(score);
		box.add(Box.createVerticalStrut(30));
		box.add(show);		
	}
	public void run(){
		while(true){
			String string1 = "Score:" + Integer.toString(GamePanel.getScore());
			score.setText(string1);
			String string2 = null;
			switch(GamePanel.getInformation()){
				case 0:break;
				case 1:string2 = "You hit the wall!";break;
				case 2:string2 = "You've eaten yourself!";break;
				default:
			}
			show.setText(string2);
		}
	}
}
class Snake{
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	public static final int DIRECTION_RIGHT = 4;
	public static final int SPEED = 140;
	private int direction = DIRECTION_RIGHT;
	private int speed = SPEED;
	private Dot head = new Dot();
	private LinkedList<Dot> body = new LinkedList<Dot>();
	public Snake(){
	}
	public Dot getHead(){
		return head;
	}
	public LinkedList<Dot> getBody(){
		return body;
	}
	public int getDirection(){
		return direction;
	}
	public int getSpeed(){
		return speed;
	}
	public void setBody(LinkedList<Dot> body){
		this.body = body;
	}
	public void setDirection(int direction){
		this.direction = direction;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
}
class Dot{
	private int x = 0;
	private int y = 0;
	public Dot(){
	}
	public Dot(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
    }
}