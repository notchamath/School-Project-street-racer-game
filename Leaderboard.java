package JavaProject;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;

 public class Leaderboard extends JFrame{
 private static final int ABORT = 0;
 static private int[] scores= new int [5];
 static private String [] names=new String[5] ;
 static private int[] bigger = new int[scores.length+1];
 static private String[]namesbigger= new String[names.length+1];
 static private File save;
 static private JFrame scoreframe;
 static private int newscore;
 static private String newname;
 
 public Leaderboard (int score,String name,JFrame frame) {

	 scores = new int[]{25,20,15,10,5};
	 names=new String[]{"matt","Dave","mike","sam","Abe"};
	 
	 try {
			save= new File("savefile.txt");
	        
			if(!save.exists()) {
					save.createNewFile();	
			}		
			
			//System.out.println("Done");
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	 this.newscore=score;
	 this.newname=name;
	 this.scoreframe=frame;
   
	}

public void addScore(int a, String name) {
	 
	    //Declare an int array with length = x.length+1;

	    /** Define a variable to indicate that if a property location is found.*/
	    boolean found = false;
	    /** Define a variable to store an index for insert*/
	    int indexToInsert = 0;
	    for (int i = 0; i < scores.length; i++){
	         if ( !found && a >= scores[i]){
	             found = true;
	             indexToInsert = i;
	             bigger[indexToInsert] = a;
	             namesbigger[indexToInsert]=name;
	             i--;
	         }
	         else{
	             if(found)
	             {
	                 bigger[i+1] = scores[i]; 
	                 namesbigger[i+1]=names[i];
	             }else
	             {
	                 bigger[i] = scores[i];
	                 namesbigger[i]=names[i];
	             }

	         }
	    }

	    /*
	     * If a property index is not found. Then put the value at last. 
	     */
	    if(!found)
	    {
	        indexToInsert = scores.length;//
	        bigger[indexToInsert] = a;
	        namesbigger[indexToInsert]=name;
	    }
	    for (int i=0;i<5;i++) {
	    	scores[i]=bigger[i];
	    	names[i]=namesbigger[i];
	    }
	    
}
public int getHighScore(){
	return scores[0];
}
 
public void display()
{
	JFrame frame = new JFrame("Car Racing Game");
	frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	frame.setUndecorated(true);
	frame.setVisible(true);
	
	
	//for(int x: array2)
		//System.out.println(x);
	//JOptionPane.minimumSize(100,100);
	UIManager.put("OptionPane.minimumSize", new Dimension(1000,1000));
	
	
	    String msg="Game Over!!! \nLeaderboard: \n"+names[0]+" "+scores[0]+"\n"+names[1]+" "+scores[1]+"\n"+names[2]+" "+scores[2]+"\n"+names[3]+" "+scores[3]+"\n"+names[4]+" "+scores[4];
	    JLabel label = new JLabel(msg);
	    label.setFont(new Font("Arial", Font.BOLD, 100));
	    UIManager.put("OptionPane.messageFont",new Font("Arial",Font.ITALIC,60));
		JOptionPane.showMessageDialog(label, msg);
		
		//label.setFont(new Font ("Arial",Font.ITALIC, 84));
		//JOptionPane.showMessageDialog(frame,label);

	    //displays the congratulations message and a message saying game over and the users score and the high score
	
	
	/*for(int i=0;i<5;i++) {
		
		System.out.println(names[i]);
		System.out.println(scores[i]);
	}*/
	
}
public void readfromFile() throws FileNotFoundException 
{
	Scanner s= new Scanner(save);
	int i=0;
	while (s.hasNextLine())
	{   
		scores[i]=s.nextInt();
		
	}
}

public void writetoFile() throws FileNotFoundException 
{
	PrintWriter pw = new PrintWriter(save);
	for (int z=0;z<5;z++) {
	pw.println(names[z]);
	pw.println(scores[z]);
	
	}
	pw.close();
}
 
 public void run(){
	 //Leaderboard test=new Leaderboard();
	 this.addScore(newscore,newname);
	 this.display();
	 try {
		this.writetoFile();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	 System.exit(ABORT);
 }
	
	
	
}