import java.awt.*; 
import java.awt.event.*; //Import event libraries 
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Random;

class GameWindow extends JFrame implements ActionListener { 

	Container contentArea = getContentPane(); 

	//ImageIcons
	ImageIcon nought = new ImageIcon("nought1.png");
	ImageIcon cross = new ImageIcon("cross3.png");

	//Buttons
	JButton returnToMenuButton = new JButton("Return to Menu");	
	JButton playAgainButton = new JButton("Play Again");	
	JRadioButton selectEasyButton = new JRadioButton("Easy", false);
	JRadioButton selectHardButton = new JRadioButton("Hard", true);

	//Array for the squares where X's or O's can be placed
	JButton[] tttGridButtons = new JButton[9];							//0-8

	//Changing difficulty
	boolean setToEasy = false;									//true for easy mode. false for hard mode

	//Changing who starts									
	int whoStarts = 1;												//odd for player, even for AI

	int moveCount = 0;											//counts number of player moves for each round 
	int playersFirstMove;										//variable that stores the player's first move
	int aiFirstMove; 											//variable that stores the AI's first move

	//Counting wins
	int playerWins = 0;
	int computerWins = 0;	
	int tie = 0;

	Random random = new Random(); 							//for creating random integers

	//Labels in right panel
	JLabel playerLabel = new JLabel("Player:        " + Integer.toString(computerWins));		
	JLabel computerLabel = new JLabel("Computer: " + Integer.toString(playerWins));
	JLabel tieLabel = new JLabel("Ties:             " + Integer.toString(tie));

	//Label in centerTopPanel
	JLabel winnerLabel = new JLabel("");			

	JPanel centerTopWinnerLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,90,32));		//panel that contains label that tells who wins
	JPanel centerTopFillerPanel = new JPanel();													//a filler panel for the centerTopWinnerLabelPanel
	JPanel centerBottomFillerPanel = new JPanel(new FlowLayout());								//a filler panel for the centerBottomButtonsPanel
	JPanel centerBottomButtonsPanel = new JPanel();												//panel that contains easy and hard buttons

	Color backgroundBlueColor = new Color(50, 152, 217);

	public GameWindow() { 
		super("TicTacToe"); 
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setVisible(true);  												

		//Container		
		contentArea.setLayout(new BorderLayout(8,6));	
		contentArea.setBackground(backgroundBlueColor);

		//Right Panel - score labels
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout()); 
		rightPanel.setBackground(backgroundBlueColor);

		JPanel rightBoxPanel = new JPanel();
		LayoutManager layout = new BoxLayout(rightBoxPanel, BoxLayout.Y_AXIS);
		rightBoxPanel.setLayout(layout);
		rightBoxPanel.setBackground(backgroundBlueColor);

		rightBoxPanel.add(Box.createRigidArea(new Dimension(0,290)));			//the y=290 pushes the labels down to the center of the screen
		rightBoxPanel.add(playerLabel);
		rightBoxPanel.add(computerLabel);
		rightBoxPanel.add(tieLabel);

		rightPanel.add(Box.createRigidArea(new Dimension(25,0)), BorderLayout.EAST);	//the x=25 creates a gap between the labels and the right side of the screen
		rightPanel.add(rightBoxPanel, BorderLayout.WEST);

		contentArea.add(rightPanel, BorderLayout.EAST);

		//Left panel - playAgain and retunrToMenu buttons
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(backgroundBlueColor);

		JPanel leftBoxPanel = new JPanel();
		LayoutManager layout2 = new BoxLayout(leftBoxPanel, BoxLayout.Y_AXIS);
		leftBoxPanel.setLayout(layout2);
		leftBoxPanel.setBackground(backgroundBlueColor);

		leftPanel.add(Box.createRigidArea(new Dimension(213,300)), BorderLayout.NORTH); //the rigid area is here for keeping the layout in place when the buttons are not visible
		leftPanel.add(Box.createRigidArea(new Dimension(25,0)), BorderLayout.WEST);			//gap between left side of screen and buttons

		leftBoxPanel.add(playAgainButton, BorderLayout.CENTER);
		leftBoxPanel.add(Box.createRigidArea(new Dimension(0,10)));
		leftBoxPanel.add(returnToMenuButton, BorderLayout.CENTER);

		leftPanel.add(leftBoxPanel);
		contentArea.add(leftPanel, BorderLayout.WEST);

		//Center panel - the board
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel centerGridPanel = new JPanel();
		centerGridPanel.setLayout(new GridLayout(3,3,20,20));			//3x3 for creating the board. 20 for gap between each grid
		centerGridPanel.setBackground(Color.black);
		centerGridPanel.setBorder(new EmptyBorder(0, -1, 0, -1));	//https://stackoverflow.com/questions/44667914/how-to-remove-gap-between-edge-of-jbutton-rows-and-edge-of-parent-jpanel
																		//hides black edges around 3x3 square, making the edges white
		for(int i = 0;i < tttGridButtons.length;i++) {			//for loop makes grid buttons white, non focusable etc.
			tttGridButtons[i] = new JButton("");
			tttGridButtons[i].setBackground(Color.white);
			tttGridButtons[i].setBorderPainted(false);
			tttGridButtons[i].setFocusable(false);
			centerGridPanel.add(tttGridButtons[i]);
		} 

		centerPanel.add(centerGridPanel, BorderLayout.CENTER);

		//These four panels create empty space and make the tic tac toe board smaller and square - center Right/left/Top/Bottom
		//Y space = 60 each. X space = 100 each 
		JPanel centerRight = new JPanel();
		centerRight.setLayout(new FlowLayout(6,100,1));
		centerRight.setBackground(backgroundBlueColor);

		centerPanel.add(centerRight, BorderLayout.EAST);

		JPanel centerLeft = new JPanel();
		centerLeft.setLayout(new FlowLayout(6,100,1));
		centerLeft.setBackground(backgroundBlueColor);

		centerPanel.add(centerLeft, BorderLayout.WEST);

		JPanel centerTop = new JPanel();
		centerTop.setLayout(new FlowLayout(6,245,10));		//245 for printing winnerLabel in center
		centerTop.setBackground(backgroundBlueColor);

		centerTopWinnerLabelPanel.setOpaque(false);

		centerTopFillerPanel.add(Box.createRigidArea(new Dimension(100,90)));		//a filler panel that is the same size as the winnerLabelPanel
		centerTopFillerPanel.setOpaque(false);										//this filler panel switches out when the winnerLabel needs to be printed

		winnerLabel.setFont((new Font("ROCKWELL", Font.BOLD, 30)));

		centerTopWinnerLabelPanel.add(winnerLabel);
		centerTop.add(centerTopWinnerLabelPanel);
		centerTop.add(centerTopFillerPanel);

		centerPanel.add(centerTop, BorderLayout.NORTH);

		JPanel centerBottom = new JPanel();
		centerBottom.setLayout(new FlowLayout(6,230,10));
		centerBottom.setBackground(backgroundBlueColor);

		//learned from docs.oracle.com/javase/tutorial/uiswing/components/button.html
		ButtonGroup groupForEasyAndHardButtons = new ButtonGroup();							//radiobuttons for selecting difficulty
		groupForEasyAndHardButtons.add(selectEasyButton);									//so that the player can clearly see which button is selected
		groupForEasyAndHardButtons.add(selectHardButton);

		centerBottomButtonsPanel.setOpaque(false);
		centerBottomButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60,35));

		centerBottomFillerPanel.setOpaque(false);
		centerBottomFillerPanel.add(Box.createRigidArea(new Dimension(100,90)));

		centerBottomButtonsPanel.add(selectEasyButton);
		centerBottomButtonsPanel.add(selectHardButton);
		centerBottom.add(centerBottomButtonsPanel);
		centerBottom.add(centerBottomFillerPanel);

		centerPanel.add(centerBottom, BorderLayout.SOUTH);

		contentArea.add(centerPanel, BorderLayout.CENTER); 

		//Fonts and Colours
		Font font = new Font("ROCKWELL", Font.BOLD, 30);
		computerLabel.setFont(font);
		playerLabel.setFont(font);
		tieLabel.setFont(font);

		playerLabel.setForeground(Color.white);
		computerLabel.setForeground(Color.white);
		tieLabel.setForeground(Color.white);

		playAgainButton.setMaximumSize(new Dimension(190,30));
		playAgainButton.setFont(new Font("ROCKWELL", Font.BOLD, 20));
		playAgainButton.setForeground(Color.black);
		playAgainButton.setBackground(Color.white);

		returnToMenuButton.setMaximumSize(new Dimension(190,30));
		returnToMenuButton.setFont(new Font("ROCKWELL", Font.BOLD, 20));
		returnToMenuButton.setForeground(Color.black);
		returnToMenuButton.setBackground(Color.white);

		selectEasyButton.setPreferredSize(new Dimension(100,30));
		selectEasyButton.setOpaque(false);
		selectEasyButton.setFont(new Font("ROCKWELL", Font.BOLD, 25));

		selectHardButton.setPreferredSize(new Dimension(100,30));  
		selectHardButton.setOpaque(false);
		selectHardButton.setFont(new Font("ROCKWELL", Font.BOLD, 25));

		selectHardButton.setBorderPainted(false);
		selectHardButton.setContentAreaFilled(false);
		selectHardButton.setFocusable(false);

		selectEasyButton.setBorderPainted(false);
		selectEasyButton.setContentAreaFilled(false);
		selectEasyButton.setFocusable(false);

		//Actionlisteners
		playAgainButton.addActionListener(this);
		returnToMenuButton.addActionListener(this);
		selectEasyButton.addActionListener(this);

		for(int i = 0;i < tttGridButtons.length;i++) {		//for loop gives all buttons in 3x3 grid actionlisteners
			tttGridButtons[i].addActionListener(this);
		}

		playAgainButton.setVisible(false);
		returnToMenuButton.setVisible(false);
		centerTopWinnerLabelPanel.setVisible(false);
		centerBottomButtonsPanel.setVisible(false);

		//The first move if AI starts
		if(whoStarts % 2 == 0) {					//if whoStarts is even, the AI will start. This variable is incremented after every round
			if(setToEasy) {
				aiStartingEasy();					//calls the method for easy mode
			}
			else {
				aiStartingHard(0,0);					//calls the method for hard mode
			}
		}
	}  
	//checks if a button has an icon. If there isn't an icon(X or O) then it returns true
	private boolean validMoveChecker(JButton selectedButton) {
		if(selectedButton.getIcon()== nought || selectedButton.getIcon() == cross) {
			return false;
		}
		else {
			return true;
		}
	}

	//when the AI starts, the first move will be randomly selected for easy mode
	private void aiStartingEasy() { 

		int randomNumber = random.nextInt(9 - 0);	
		if(moveCount == 0) {
			placePiece(randomNumber, nought);			//for the first move, a random position is selected
		}
		else if(winOrBlockTwoInARow(nought)) {

		}
		//If there are two player's pieces in a row, this will execute
		else if(winOrBlockTwoInARow(cross)) {

		}
		else if(validMoveChecker(tttGridButtons[randomNumber])){
			placePiece(randomNumber,nought);
		}
		else {
			placeInRemainingSpot();
		}

	}
	private void placeInRemainingSpot() {
		for(int i = 0;i < tttGridButtons.length;i++) {
			if(validMoveChecker(tttGridButtons[i])) {
				placePiece(i, nought);
				break;
			}
		}
	}

	//when the AI starts, the first move will be a corner or middle for hard mode
	private void aiStartingHard(int playerLatestMove, int playerFirstMove) { 

		int randomNumberCorner = random.nextInt(5 - 0) * 2;		//0-4 and then multiplied by 2 to get an even number between 0-8 (CORNER)
		int randomNumberSide = random.nextInt(4 - 0) * 2 + 1;		//0-3 then multiplied by 2 and adding 1 to get odd number between 1-7 (SIDE)

		if(moveCount == 0) {								//For the first move, piece is placed in middle or corner
			aiFirstMove = randomNumberCorner;						//the first move is stored in the variable AifirstMove 
			placePiece(randomNumberCorner, nought);
		}
		else if(winOrBlockTwoInARow(nought)) {}				//checks is player has any two in a rows.The method places a piece automatically

		else if(winOrBlockTwoInARow(cross)) {}					//checks is AI has any two in a rows. The method places a piece automatically. That is why nothing is needed in the brackets
		
		//If the AIs first move was the middle 								
		else if(aiFirstMove == 4) {								

			aiFirstMoveMiddleHard(playerLatestMove, playerFirstMove, randomNumberCorner, randomNumberSide);
		}
		//If AIs first move was a corner(since the AI only starts in the middle or corner)
		else {
			aiFirstMoveCornerHard(playerLatestMove, playerFirstMove, randomNumberCorner);
		}
	}
	private void aiFirstMoveCornerHard(int playerLatestMove, int playerFirstMove, int randomNumberCorner) {
		if(playerFirstMove == 4 || playerFirstMove % 2 == 0) {						//players first move is middle OR a corner
			if(validMoveChecker(tttGridButtons[randomNumberCorner])) {						//random corner is chosen
				placePiece(randomNumberCorner, nought);
			}
			else {
				placeInRemainingSpot();						//places in any remaining position
			}
		}
		else {												//player first move is a side
			
			if(validMoveChecker(tttGridButtons[4])) {			
				placePiece(4, nought);
			}
			else if(validMoveChecker(tttGridButtons[randomNumberCorner])) {		//random corner is chosen
				placePiece(randomNumberCorner, nought);
			}
			else {
				placeInRemainingSpot();
			}


		}
	}
	private void aiFirstMoveMiddleHard(int playerLatestMove, int playerFirstMove, int randomNumberCorner, int randomNumberSide) {
		if((playerLatestMove+1) % 2 == 0) {								//if players latest move was a side
			if(validMoveChecker(tttGridButtons[randomNumberCorner])) {		//random corner is chosen
				placePiece(randomNumberCorner, nought);
			}
			else {
				aiStartingHard(playerLatestMove, playerFirstMove);			//calls the method again to get a new randomNumber
			}
		}
		else if(playerLatestMove % 2 == 0 ){								//if players latest move was a corner
			if(validMoveChecker(tttGridButtons[randomNumberSide])) {		//random side is chosen
				placePiece(randomNumberSide, nought);
			}
			else {
				aiStartingHard(playerLatestMove, playerFirstMove);
			}
		}
	}
	//checks if there are 2 x's or 2's O's in a row.
	private boolean checkIfTwoInARow(int position, ImageIcon icon) {
		boolean isVertical = vertical(position, icon);
		boolean isHorizontal = horizontal(position, icon);
		boolean isDiagonal = diagonal(position, icon);
		if(isVertical || isHorizontal || isDiagonal ) {
			return true;
		}
		return false;
	}

	//Places a piece to either get a win for nought, or to block a cross win. Uses the method above that checks if there are any two in a rows
	private boolean winOrBlockTwoInARow(ImageIcon crossOrNought) {
		for(int i = 0;i < tttGridButtons.length;i++) {
			if(checkIfTwoInARow(i, crossOrNought)) { 	
				if(validMoveChecker(tttGridButtons[i])) {
					placePiece(i, nought);
					return true;
				}
			}
		}
		return false;
	}
	//method for placing a piece, either X or O
	private void placePiece(int i, ImageIcon crossOrNought) {
		tttGridButtons[i].setIcon(crossOrNought);
		tttGridButtons[i].setDisabledIcon(crossOrNought);
		tttGridButtons[i].setEnabled(false);
		winnerChecker(i, crossOrNought);			//checks if there is a winner after the piece has been placed
	}
	
	//This method is used for: when player starts and easy is selected OR when AI starts and easy is selected, this method is used for all moves except for the first
	private void aiTurnEasy() {
		//If there are two in a row, this will execute
		int randomForLettingPlayerWin = random.nextInt(6 - 0);	 //a random number between 0-5. This is when there are two X's in a row, but there is a 1/6 the AI doesn't block it
		if(winOrBlockTwoInARow(nought)) {

		}

		//If there are two player's pieces in a row, this will execute
		else if(randomForLettingPlayerWin > 0 && winOrBlockTwoInARow(cross)) {

		}
		else {
			int i = random.nextInt(9 - 0); 				//0-8 
			if(validMoveChecker(tttGridButtons[i])) { 	//a random move is chosen
				placePiece(i, nought);
			}
			else {
				placeInRemainingSpot();			//if i is already a taken position, the method is called to fill another position
			}
		}

	}
	//This method is used when the player starts and hard mode is selected. The AI plays defense since there aren't many ways of winning when not starting
	private void aiTurnHard(int playerFirstMove, int playerCurrentMove) {

		//If there are two in a row, this will execute
		if(winOrBlockTwoInARow(nought)) {

		}

		//If there are two player's pieces in a row, this will execute
		else if(winOrBlockTwoInARow(cross)) {

		}
		else{											//one of three methods is called. Depends on where the player placed their first piece
			if(playerFirstMove == 4) {
				playerMiddleHard();
			}
			else if(playerFirstMove % 2 == 0) {			//if playerFirstMove is 0, 2, 6 or 8 (corner) 
				playerCornerHard();
			}
			else {
				playerSideHard(playerCurrentMove);			//if playerFirstMove is a side
			}
		}


	}
	//if the player plays middle the first move, the computer will play a corner. If corner is taken it will play a side
	private void playerMiddleHard() {		

		int randomNumberCorner = random.nextInt(5 - 0) * 2;			//0-4 then *2
		int randomNumberSide = random.nextInt(4 - 0) * 2 + 1;		//0-3 then *2 then +1

		if(randomNumberCorner == 4) {						//if the random number is 4(middle) then 2 is added so that it becomes a corner 
			randomNumberCorner += 2;
		}

		if(validMoveChecker(tttGridButtons[randomNumberCorner])) {			
			placePiece(randomNumberCorner, nought);

		}
		else if(validMoveChecker(tttGridButtons[randomNumberSide])) {			//if the corner is taken, a side will be chosen instead
			placePiece(randomNumberSide, nought);
		}
		else {
			playerMiddleHard();													//if both the corner and side were taken, method is called again to get new random numbers
		}


	}
	//if the player plays a side the first move, the computer will take the middle, and then will vary between corner and side
	private void playerSideHard(int playerLatestMove) {
		int randomNumberCorner = random.nextInt(5 - 0) * 2;	
		int randomNumberSide = random.nextInt(4 - 0) * 2 + 1;	

		if(validMoveChecker(tttGridButtons[4])) { 			//when playing defense, AI will always try to take the middle
			placePiece(4, nought);
		}
		else if(playerLatestMove % 2 == 0) {				//if player latest move is a corner, the AI will choose a side

			if(validMoveChecker(tttGridButtons[randomNumberSide])) {
				placePiece(randomNumberSide, nought);
			}
			else {
				playerSideHard(playerLatestMove);					//if side is taken, method called again to get new side
			}
		}
		else if((playerLatestMove+1) % 2 == 0) {					//if player latest move is a side, the AI will choose a corner
			if(validMoveChecker(tttGridButtons[randomNumberCorner])) {
				placePiece(randomNumberCorner, nought);
			}
			else {
				playerSideHard(playerLatestMove);						//if corner is taken, method called again to get new corner
			}
		}

	}
	//if the player plays a corner the first move, the computer will take the middle to avoid an instant win for the player.
	//then it will play corners. However, if both opposing corners are filled by the player (0&8 or 2&6) a side will be chosen instead
	private void playerCornerHard() {

		int randomNumberCorner = random.nextInt(5 - 0) * 2;			//0-4 then *2	//for corners 0, 2, 6, 8
		int randomNumberSide = random.nextInt(4 - 0) * 2 + 1;		//0-3 then *2 then +1	//for sides, 1, 3, 5, 7

		if(randomNumberCorner == 4) {						//if the random number is 4(middle) then 2 is added so that it becomes a corner 
			randomNumberCorner += 2;
		}

		if(validMoveChecker(tttGridButtons[4])) { //when playing defense, AI will always try to take the middle
			placePiece(4, nought);
		}
		else if(checkIfPositionIsFilled(0, cross) && checkIfPositionIsFilled(8, cross) || 		//if opposing corners are filled, side is chosen instead of corner
				checkIfPositionIsFilled(2, cross) && checkIfPositionIsFilled(6, cross)) {
			placePiece(randomNumberSide, nought);
		}

		else if(validMoveChecker(tttGridButtons[randomNumberCorner])) {					//random corner chosen
			placePiece(randomNumberCorner, nought);
		}
		else {
			placeInRemainingSpot();
		}
	}
	//checks for a winner. This is called after placing a piece in a position
	private boolean winnerChecker(int position, ImageIcon icon) {
		if(vertical(position, icon) || horizontal(position, icon) || diagonal(position, icon)) {

			if(icon == nought) {
				winnerLabel.setText("Computer wins!");
				computerWins++;
				computerLabel.setText("Computer: " + Integer.toString(computerWins));

			}
			else {
				winnerLabel.setText("Player wins!");
				playerWins++;
				playerLabel.setText("Player:        " + Integer.toString(playerWins));
			}

			for(int i = 0;i < tttGridButtons.length;i++) {
				tttGridButtons[i].setEnabled(false);
			}
		
			playAgainButton.setVisible(true);
			returnToMenuButton.setVisible(true);

			centerTopFillerPanel.setVisible(false);					//hides filler panels and makes the winnerLabel and difficulty buttons visible
			centerTopWinnerLabelPanel.setVisible(true);
			centerBottomFillerPanel.setVisible(false);
			centerBottomButtonsPanel.setVisible(true);
			return true;

		}
		else {
			drawChecker();								//if there isn't a winner, the drawChecker is called
			return false;
		}

	}

	private void drawChecker() {
		boolean availableSpaceExists = false;													//initially availableSpaceExists is false

		for(int i = 0;i < tttGridButtons.length;i++) {											//for loop goes through all buttons for checking if any of the buttons are true(which means they are empty)
			availableSpaceExists = availableSpaceExists || validMoveChecker(tttGridButtons[i]);	//availableSpaceExists is given its previous value or the value of validMoveChecker(tttGridButtons[i]
		}																		//if any of the validMoveCheckers returns true, availableSpaceExistswill be true.	
		
		if(!availableSpaceExists) {												//if availableSpaceExists is not true, the following code will run stating that there is tie. 

			winnerLabel.setText("It's a tie!");
			tie++;
			tieLabel.setText("Ties:             " + Integer.toString(tie));

			for(int i = 0;i < tttGridButtons.length;i++) {
				tttGridButtons[i].setEnabled(false);
			}

			playAgainButton.setVisible(true);
			returnToMenuButton.setVisible(true);

			centerTopFillerPanel.setVisible(false);
			centerTopWinnerLabelPanel.setVisible(true);
			centerBottomFillerPanel.setVisible(false);
			centerBottomButtonsPanel.setVisible(true);
		}


	}

	//The methods vertical, horizontal and diagonal are used by winnerChecker. They check if there are 3 same icons in a row
	/* Vertical combos: 1. 0, 3, 6
						2. 1, 4, 7
						3. 2, 5, 8 */

	private boolean vertical(int position, ImageIcon icon) {									
		if(checkIfAllThreeFilled((position+3)%9, (position+6)%9, icon)) {		//e.g if position = 0, then checkIfAllThreeFilled will check if 3 and 6 are also filled by the same icon
			return true;
		}
		return false;
	}

	/*Horizontal combos: 1. 0, 1, 2
					  	 2. 3, 4, 5
					  	 3. 6, 7, 8 */

	private boolean horizontal(int position, ImageIcon icon) {

		if(position % 3 == 0) {
			return checkIfAllThreeFilled(position+1, position+2, icon);			//e.g if position = 0 then checkIfAllThreeFilled will check if 1 and 2 are also filled by the same icon
		}
		else if(position % 3 == 1) {
			return checkIfAllThreeFilled(position+1, position-1, icon);
		}
		else if(position % 3 == 2) {
			return checkIfAllThreeFilled(position-1, position-2, icon);
		}
		return false;
	}

	/* 	Diagonal combos:1. 0, 4, 8
						2. 2, 4, 6 */
	private boolean diagonal(int position, ImageIcon icon) {
		if(position == 0) {
			return checkIfAllThreeFilled(4, 8, icon);			//e.g if position = 0 then checkIfAllThreeFilled will check if 1 and 2 are also filled by the same icon
		}
		else if(position == 4) {
			return checkIfAllThreeFilled(0, 8, icon) || checkIfAllThreeFilled(2, 6, icon);			//e.g if position = 0 then checkIfAllThreeFilled will check if 1 and 2 are also filled by the same icon
		}
		else if(position == 2) {
			return checkIfAllThreeFilled(4, 6, icon);			//e.g if position = 0 then checkIfAllThreeFilled will check if 1 and 2 are also filled by the same icon
		}
		else if(position == 6) {
			return checkIfAllThreeFilled(2, 4, icon);			//e.g if position = 0 then checkIfAllThreeFilled will check if 1 and 2 are also filled by the same icon
		}
		else if(position == 8) {
			return checkIfAllThreeFilled(0, 4, icon);			//e.g if position = 0 then checkIfAllThreeFilled will check if 1 and 2 are also filled by the same icon
		}
		return false;
	}

	//checks two positions and sees if they are filled with the same icon. It is already known that the first position is filled, since that is where a piece is placed
	private boolean checkIfAllThreeFilled(int positionTwo, int positionThree, ImageIcon icon) {
		if(checkIfPositionIsFilled(positionTwo, icon) == true && checkIfPositionIsFilled(positionThree, icon) == true) {
			return true;
		}
		return false;
	}
	//checks if a single position is filled with the right piece. This is done by getting the button's icon and checking if it matches with the given icon
	private boolean checkIfPositionIsFilled(int i, ImageIcon icon) {
		return tttGridButtons[i].getIcon() == icon;
	}
	
	//Runs when pressing the play again button
	private void playAgainMethod() {
		for(int i = 0;i < tttGridButtons.length;i++) {
			tttGridButtons[i].setIcon(null);					
			tttGridButtons[i].setEnabled(true);						//enables all buttons
		}

		centerTopFillerPanel.setVisible(true);
		centerTopWinnerLabelPanel.setVisible(false);

		centerBottomButtonsPanel.setVisible(false);			//switches the filler panels around with the top and bottom label & button panels
		centerBottomFillerPanel.setVisible(true);
		playAgainButton.setVisible(false);
		returnToMenuButton.setVisible(false);

		moveCount = 0;
		whoStarts++;

		if(whoStarts %2 == 0) {				//if whoStarts is divisible by 2, the AI will start the next round
			aiStarts1st(0);
		}
	}
	
	//method for playing sounds. Called from the menu class
	public void playSound(String soundName) {
		tMenu.playSound(soundName);
	}

	public void actionPerformed(ActionEvent event) { 

		for(int i = 0;i < tttGridButtons.length;i++) {					//when an action is performed, the loop checks if any of the 3x3 buttons were selected
			if(event.getSource() == tttGridButtons[i]) {
				if(validMoveChecker(tttGridButtons[i]) == true) {		//if the selected button is a valid move, an X will be placed there
					placePiece(i, cross);	
					playSound("placePieceSound.wav");
					if(checkIfTwoInARow(i, cross)) {						//if winnerChecker is true for the player, the loop breaks. This is to prevent the AI from placing a piece after the player has won
						break;
					}
					moveCount++;								//the counter for amount of moves player has made is incremented
					if(moveCount == 1) {						//if it was the player's first turn, the position where the player placed a piece is stored in the variable playersFirstMove
						playersFirstMove = i;
					}

					if(whoStarts %2 == 0) {				//if whoStarts is divisible by 2, the AI will start the next round
						aiStarts1st(i);
					}
					else {								//if whoStarts isn't divisible by 2 i.e it is odd, then the player starts the next round
						aiStarts2nd(i);
					}

				}
				break;
			}

		}
		if(event.getSource() == selectEasyButton) {
			setToEasy = true;							//sets difficulty to easy
		}

		if(event.getSource() == selectHardButton) {
			setToEasy = false;							//sets difficulty to hard
		}

		if(event.getSource() == playAgainButton) {
			playSound("menuClickSound.wav");
			playAgainMethod();
		}

		if(event.getSource()== returnToMenuButton) {	
			playSound("menuClickSound.wav");
			ticTacToeMenu ticTacToeMenu = new ticTacToeMenu();				//goes to the main menu window
			ticTacToeMenu.main(null);
			super.dispose();									//gets rid of this window

		}
	}
	private void aiStarts2nd(int i) {
		if(setToEasy == true) {
			aiTurnEasy();				//if easy is selecting 
		}
		else {	
			aiTurnHard(playersFirstMove, i);		//if hard is selected
		}
	}
	private void aiStarts1st(int i) {
		if(setToEasy == true) {
			aiStartingEasy();			//if easy is selected 
		}
		else {
			aiStartingHard(i, playersFirstMove);		//if hard is selected
		}
	} 
}

public class ticTacToeGame { 

	public static void main(String[] args) { 

		GameWindow gameWindow = new GameWindow (); 
	}
}