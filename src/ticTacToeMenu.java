import java.awt.*; 
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import Blackjack.enterName; 

class tMenu extends JFrame implements ActionListener { 

	//Here only variables and objects that need to be accessible within methods and statements are declared
	//Buttons
	JButton playGameButton = new JButton("Play Game"); 						//the three main menu buttons
	JButton howToPlayButton = new JButton("How to Play");
	JButton quitGameButton = new JButton("Quit Game");

	//Images and JLabels
	ImageIcon titleImage = new ImageIcon("tictactoelogo3.png"); 			//the logo
	JLabel title = new JLabel(titleImage);

	ImageIcon backImage = new ImageIcon("redback.png"); 			//image of the back button
	JButton backButton = new JButton(backImage);
	JLabel instructions = new JLabel();								

	JLabel imageLabelAsBackgroundPanel = new JLabel(); 	

	//Container and Panels	
	Container contentArea = getContentPane();	
	JPanel boxCenterPanel = new JPanel();

	Color backgroundBlueColor = new Color(50, 152, 217);

	public tMenu() { 
		super("TicTacToe");  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setExtendedState(JFrame.MAXIMIZED_BOTH);		 //Sets full screen

		setVisible(true); 

		//Here the layout is made with several panels. Objects are added to the panels. 
		//Background panel
		imageLabelAsBackgroundPanel.setLayout(new BorderLayout());

		//Box center panel - where the playGame, howtoPlay and quitGame buttons are placed
		LayoutManager layout = new BoxLayout(boxCenterPanel, BoxLayout.Y_AXIS);  		//setting a box layout
		boxCenterPanel.setLayout(layout);
		boxCenterPanel.setOpaque(false);									//makes the panel transparent, allowing the background image to be seen

		boxCenterPanel.add(title);
		boxCenterPanel.add(instructions);
		boxCenterPanel.add(playGameButton);
		boxCenterPanel.add(Box.createVerticalStrut(20)); 					//Creates space of 20 pixels between each button 
		boxCenterPanel.add(howToPlayButton);
		boxCenterPanel.add(Box.createVerticalStrut(20));
		boxCenterPanel.add(quitGameButton);

		//Box left panel - where the back button is placed
		JPanel boxLeftPanel = new JPanel();
		LayoutManager layout2 = new BoxLayout(boxLeftPanel, BoxLayout.Y_AXIS);
		boxLeftPanel.setLayout(layout2);
		boxLeftPanel.setOpaque(false);
		boxLeftPanel.setBackground(Color.red);

		boxLeftPanel.add(backButton); 

		imageLabelAsBackgroundPanel.add(boxCenterPanel, BorderLayout.CENTER);		//adding boxCenter to the center of the screen
		imageLabelAsBackgroundPanel.add(boxLeftPanel, BorderLayout.WEST);			//adding boxLeft to the left of the screen

		imageLabelAsBackgroundPanel.setBackground(Color.green);

		contentArea.add(imageLabelAsBackgroundPanel);

		contentArea.setBackground(backgroundBlueColor);                   

		//Setting sizes
		playGameButton.setMaximumSize(new Dimension(300,70));
		howToPlayButton.setMaximumSize(new Dimension(300,70));
		quitGameButton.setMaximumSize(new Dimension(300,70));

		//Hiding the button background and borders
		removeButtonBackground(backButton);

		//Setting colors of buttons
		playGameButton.setBackground(Color.white);
		howToPlayButton.setBackground(Color.white);
		quitGameButton.setBackground(Color.white);
		playGameButton.setForeground(Color.black);
		howToPlayButton.setForeground(Color.black);
		quitGameButton.setForeground(Color.black);
		instructions.setForeground(Color.white);

		//Font for buttons
		Font font = new Font("ROCKWELL", Font.BOLD, 30);
		playGameButton.setFont(font);
		howToPlayButton.setFont(font);
		quitGameButton.setFont(font);
		instructions.setFont(new Font("ROCKWELL", Font.BOLD, 18));

		//Aligning the objects in the center of the screen
		title.setAlignmentX(CENTER_ALIGNMENT);
		playGameButton.setAlignmentX(CENTER_ALIGNMENT);
		howToPlayButton.setAlignmentX(CENTER_ALIGNMENT);
		quitGameButton.setAlignmentX(CENTER_ALIGNMENT);
		instructions.setAlignmentX(CENTER_ALIGNMENT);
		backButton.setAlignmentX(CENTER_ALIGNMENT);	


		//stackoverflow.com/questions/685521/multiline-text-in-jlabel		
		//https://www.tutorialspoint.com/how-can-we-implement-a-jlabel-text-with-different-color-and-font-in-java
		//Here the text for the instructions is written. <br> creates a new line and <font> changes the color and size. 
		String text = "<html> <font size= '8' color=black> Starting Off </font> <br> "
				+ "The player and AI take turns starting. You can place a piece anywhere in the 3x3 grid <br> <br>"
				+ "<font size= '8' color=black> Objective </font> <br>"					//A different color and size for the headings
				+ "The objective of Tic Tac Toe is to get 3 pieces in a vertical, horizontal or diagonal row.  <br> <br>"
				+ "<font size= '8' color=black> Outcomes </font> <br>"
				+ "You win if you get 3 in a row<br>"
				+ "You lose if the AI gets 3 in a row <br>"
				+ "A draw occurs when the 3x3 grid is filled without any 3 in a rows <br> <br>"
				+ "<font size= '8' color=black> Selecting difficulty </font> <br>"
				+ " After a round has resulted in a win, loss or draw, the player can change the AI’s difficulty. <br>"
				+ "This is done by selecting either the Easy or Hard buttons and then pressing Play Again. "
				+ "</html>";

		instructions.setText(text);

		//Adds actionListeners for the method actionPerformed
		playGameButton.addActionListener(this);
		howToPlayButton.addActionListener(this);
		quitGameButton.addActionListener(this);
		backButton.addActionListener(this);

		//Setting objects to invisible
		backButton.setVisible(false);
		instructions.setVisible(false);

	}  

	public void actionPerformed(ActionEvent event) { 
		if(event.getSource()== quitGameButton) {
			playSound("menuClickSound.wav");				//plays a sound when the user clicks on the button
			System.exit(0);									//leaves the game
		}
		if(event.getSource()== howToPlayButton) {			//hides objects and shows the howToPlay objects
			playSound("menuClickSound.wav");				
			playGameButton.setVisible(false);
			howToPlayButton.setVisible(false);
			quitGameButton.setVisible(false);
			title.setVisible(false);
			backButton.setVisible(true);
			instructions.setVisible(true);

		}
		if(event.getSource()== playGameButton) {	
			playSound("menuClickSound.wav");
			ticTacToeGame ticTacToeGame = new ticTacToeGame();				//goes to the next window
			ticTacToeGame.main(null);
			super.dispose();									//gets rid of this window

		}
		if(event.getSource()== backButton) {
			instructions.setVisible(false);
			backButton.setVisible(false);
			playGameButton.setVisible(true);
			howToPlayButton.setVisible(true);
			quitGameButton.setVisible(true);
			title.setVisible(true);
		}



	} 


	//http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
	public static void playSound(String soundName){				//this method allows sounds to be played e.g when placing cards
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
			Clip clip = AudioSystem.getClip( );
			clip.open(audioInputStream);
			clip.start();
		}
		catch(Exception ex) {
			System.out.println("");
			ex.printStackTrace();
		}
	}
	//This method removes the background and borders of buttons with images, in order to make them transparent
	public static void removeButtonBackground(JButton button) {
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusable(false);
	}  

}

public class ticTacToeMenu { 

	public static void main(String[] args) { 

		tMenu tMenu = new tMenu(); 


	} 

} 