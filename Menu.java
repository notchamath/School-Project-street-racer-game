package JavaProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame{

	private JButton playButton;							//play button initialized 
	private JButton exitButton;							//Exit button initialized
	private JButton LeaderButton;						//Leaderboard button initialized
	Game game = new Game();								//Game instance game initialized

	//Menu constructor
	public Menu() {
		
		super("The Menu");								//Name window
		setLayout(new FlowLayout());					//set Flow Layout
		
		playButton = new JButton ("Play");				
		add(playButton);								//play button added to the Jframe
		
		LeaderButton = new JButton ("Leaderboard");
		add(LeaderButton);								//Leaderboard button added to the Jframe
		
		exitButton = new JButton ("Exit");
		add(exitButton);								//exit button added to the frame
	
		playButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event)
					{
						Game.main(null);				//when clicked Play button, run the game, called as a static method
					}
				}
				);
		
		LeaderButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event)
					{
						Leaderboard leaderboard=new Leaderboard(game.score,"timmy",Game.frame);
				        leaderboard.run();				//when clicked on Leaderboard button, show leaderboard
					}
				}
				);
		
		exitButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event)
					{
						System.exit(ABORT);				//when clicked exit button, exit window
					}
				}
				);
	}
	
	public static void run()
	{
		Menu start = new Menu();						//instance of Menu class created

		start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.setSize(1000,1000);
		start.setVisible(true);
	}
	
	public static void main(String args[]){
		
		run();											//run program
	}
	
}
