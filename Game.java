package JavaProject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game extends JPanel{
    
    int car_x, car_y;    								                //x and y location of player's car
    int speedX, speedY;									                //x and y plane speeds of player's car
    int rdx,rdy;										                //location of the road in the x and y planes
    static int score, highScore;  						                //score and highscore variables of type int
    int numOpponents;      								                //the number of enemy vehicles
    int speedOpponent[]; 								                //stores each opponent's speeds
    int enemyX[], enemyY[];  							                //stores location in the x, y plane of enemy cars
    boolean isFinished; 								                //tells if game is over
    boolean isUp, isDown, isRight, isLeft;  			                //variables for arrow keys of type bool
    String imageName[]; 									            //enemy car image names stored here
    static JFrame frame = new JFrame("Car Racing Game");                //creates the JFrame for the game
    static Leaderboard test=new Leaderboard(score,"chamath",frame);
    
    public Game(){
           
        setFocusable(true); 							//player's car is to be focus of the frame
        rdx = rdy = -1999;  							//initial placement of the road
        car_x = car_y = 470;    						//starting point of the player's car
        isUp = isDown = isLeft = isRight = false;  	 	//will be true if player pressed arrow keys
        speedX = speedY = 0;    						//default speed of the car when player has no control
        numOpponents = 0;  								//set the number of opponent cars
        enemyX = new int[20]; 							//stores the x position of all enemy cars
        enemyY = new int[20]; 							//stores the y position of all enemy cars
        imageName = new String[20];						//stores image names of each enemy car
        speedOpponent = new int[20]; 					//stores speed of each enemy car
        isFinished = false; 							//the game starts when this is false
        score = 0;  									//score initialized to 0
        
        highScore= test.getHighScore();					//set highscore
        
        
        //KeyListener to use arrow keys to move the car
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) { 
                stopCar(e);                                     //when key released stop movement of the car
            }
            public void keyPressed(KeyEvent e) {                //when a key is pressed
                moveCar(e);                                     //when the key is pressed move the car in that direction
            }
        });
    }
    
    //paint and repaint methods are used, paint method is overridden
    public void paint(Graphics x){
    	
        super.paint(x);
        Graphics2D obj = (Graphics2D) x;						
        obj.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        try{
            obj.drawImage(getToolkit().getImage("images/st1.png"), 0, 0 ,this); 			//draws road
            if(rdy >= -1999 && rdx >= -1999) 												//once the image has reached a given point redraw the image
                obj.drawImage(getToolkit().getImage("images/cross1.png"),rdx,rdy,this);
            
            obj.drawImage(getToolkit().getImage("images/carNew.png"),car_x,car_y,this);     //players car drawn
            
            if(this.numOpponents > 0){
                for(int i=0;i<this.numOpponents;i++){ 										//enemy cars drawn
                    obj.drawImage(getToolkit().getImage(this.imageName[i]),this.enemyX[i],this.enemyY[i],this);
                }
            }
            
            if(isFinished){																	//explode if collided with another car
                obj.drawImage(getToolkit().getImage("images/explosion.png"),car_x-250,car_y-250,this);
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
    
    //method to determine how the elements move
    void moveElements(int count){
    	
    	car_x += speedX; //update player's car positions
        car_y += speedY;  
         
        if(rdx == -1999 && rdy == -1999){ 						//return image to beginning once past this point  
            if(count%10 == 0){  								//once count hits 10
                rdx = 999;
                rdy = 0;
            }
        }
        
        else{  
            rdx--; 												//if not keep moving the road
        }
        
        if(rdx == -998 && rdy == 0){ 							//return enemy cars to beginning once past a given point
            rdx = rdy = -1999;
        }
        
       // **********************************************************************************************************
        
        if(car_x < 0)  				 //keeps car within the x bounds
            car_x = 0;  
        
        if(car_x >= 900) 			//keeps car within the x bounds
            car_x = 900;
        
        
        if(car_y <= 248)    		//keeps car within the y bounds
            car_y = 248;    		
       
        if(car_y >= 670) 			//keeps car within the y bounds
            car_y = 670; 
        
        
        for(int i=0;i<this.numOpponents;i++){ 
            this.enemyX[i] -= speedOpponent[i];			//moves enemy cars thru the x plane
        }
        
        int index[] = new int[numOpponents];
        for(int i=0;i<numOpponents;i++){
            if(enemyX[i] >= -500){						//determine the point where enemy cars disappear
                index[i] = 1;
            }
        }
        int c = 0;
        for(int i=0;i<numOpponents;i++){				//determines which car to appear and where to appear, randomly
            if(index[i] == 1){
                imageName[c] = imageName[i];		
                enemyX[c] = enemyX[i];
                enemyY[c] = enemyY[i];
                speedOpponent[c] = speedOpponent[i];
                c++;
            }
        }
        score += numOpponents - c; 						//player score
        
        // *****************************************************************************************************
        numOpponents = c;
        int diff = 0;									//distance between cars
        
        for(int i=0;i<numOpponents;i++){				//finish game if car crashes
            diff = car_y - enemyY[i];
            if((enemyY[i] >= car_y && enemyY[i] <= car_y+46) || (enemyY[i]+46 >= car_y && enemyY[i]+46 <= car_y+46)){
                if(car_x+87 >= enemyX[i] && !(car_x >= enemyX[i]+87)){  
                    this.finish();
                }
            }
        }
    }
    
   //Finishes game once car crashes
    void finish(){
    	
    	isFinished = true; 									 //set to true when game has ended	
        this.repaint();    									 //show finish screen
        
        test=new Leaderboard(score,"chamath",frame);
        
    	if(score > highScore)   							 //set up highscore
            highScore = score;  
    	
    	//test.run();
        String str=" ";
        if(score == highScore && score != 0) 
            str = "\nCongratulations!!! Its a high score";   //Show if is a highscore
        JOptionPane.showMessageDialog(this,"GAME OVER!\nYour Score : "+score+str,     "Game Over", JOptionPane.YES_NO_OPTION);    //display finish messages
       
        System.exit(ABORT);									 //close the game
    }
    
    
    //user arrow key controls
    public void moveCar(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){   		//if clicked up go forward
            isUp = true;
            speedX = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){ 		//if clicked down go back
            isDown = true;
            speedX = -2;  
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){		//if clicked right go right
            isRight = true;	
            speedY = 1;   
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){ 		//if clicked left go left
            isLeft = true;
            speedY = -1; 
        }
    }
    
    //user arrow key controls
    public void stopCar(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){   		//if not clicked do not move
            isUp = false;
            speedX = 0; 
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){    //if not clicked do not move
            isDown = false;
            speedX = 0;  
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){   	//if not clicked do not move
            isLeft = false;
            speedY = 0;  
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){   //if not clicked do not move
            isRight = false;
            speedY = 0;  
        }
    }
    
   //runs the game
   public static void run()
   {
	   Game game = new Game(); 									//create instance of Game class
       frame.add(game);											//add Graphics2D class elements to frame
       frame.setSize(1000,1000); 								//set size of the frame
       frame.setVisible(true); 									//show on frame
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//exit when closed
       
       int count = 1, c = 1;									//components needed for looping
       
       while(true){
           game.moveElements(count);   							//set elements in motion
           while(c <= 1){
               game.repaint();     								//keep redrawing the road as needed
               try{
                   Thread.sleep(2);    						
                   }
               catch(Exception e){
                   System.out.println(e);
               }
               c++;
           }
           c = 1;
           count++;
           
           if (game.numOpponents < 4 && count % 400 == 0){ 			//determines how frequently an enemy car is dispatched
        	   
               game.imageName[game.numOpponents] = "images/car_left_"+((int)((Math.random()*100)%3)+1)+".png"; // car image names are randomly assigned to the array imageName
               game.enemyX[game.numOpponents] = 999; 				//where enemy cars start
               
               int p = (int)(Math.random()*100)%5; 					//randomly sets the path of each enemy car
               
               if(p == 0){    										
                   p = 500; 										//path 1
               }
               else if(p == 1){ 
                   p = 600;    										//path 2
               }
               else if(p == 2){ 
                   p = 370;   										//path 3
               }
               else if(p == 3){ 
                   p = 400;  										//path 4
               }
               else{          
                   p = 260;    										//path 5
               }
               
               game.enemyY[game.numOpponents] = p; 					//path assigned to car
               
               game.speedOpponent[game.numOpponents] = (int)(Math.random()*100)%2 + 2; //speed of each enemy car set randomly
               
               game.numOpponents++; 								//increment the number of enemy cars
           }
       }
   }
    
   //main method
	public static void main(String args[])
	{
    	run();   //runs the program
    }
}
