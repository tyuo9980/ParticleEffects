// by Peter Li
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// v1.11 - 11/29/11
// - Improved performance
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// v1.1 - 11/27/11
// - Added sound effects
// - Added intro
// - Added commands
// - Instructions and Credits ouputs from file
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// v1.0 - 11/23/11
// - Initial Release
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

import java.awt.*;

public class ParticleEffects
{
    public static PCPC c;                               //PCPC object
    public static Graphics g;                           //gfx object

    public static int sWidth;                           //canvas width
    public static int sHeight;                          //canvas height

    public static int Pmax = 1337;
    public static double prtclX[] = new double [Pmax + 1];  //particle X coord
    public static double prtclY[] = new double [Pmax + 1];  //particle Y coord
    public static int prtclLife[] = new int [Pmax + 1];     //particle life time
    public static double prtclDir[] = new double [Pmax + 1]; //particle direction
    public static Color prtclClr[] = new Color [Pmax + 1];  //particle color
    public static int prtclSpd[] = new int [Pmax + 1];      //particle speed
    public static int prtclSize[] = new int [Pmax + 1];     //particle size

    public static int mouseX, mouseY;                   //mouse coords

    public static String command;                       //command string
    public static char cArray[];                        //command checking array
    public static int varSize = 5;                      //particle size value

    public static void main (String[] args) throws Exception
    {
	c = new PCPC ();
	g = c.getGraphics ();

	//retrieves width and height
	sWidth = c.getCanvasWidth ();
	sHeight = c.getCanvasHeight ();

	instructions ();                                //displays instructions
	credits ();                                     //displays credits
	intro ();                                       //plays intro
	start ();                                       //starts main program
    }


    //generates a new particle with randomized properties
    public static void createParticle (int p)
    {
	prtclX [p] = mouseX;
	prtclY [p] = mouseY;
	prtclLife [p] = c.randInt (100) + 1;
	prtclDir [p] = c.randInt (360);
	prtclDir [p] = Math.toRadians (prtclDir [p]);
	prtclClr [p] = c.randColor ();
	prtclSpd [p] = c.randInt (3) + 1;
	prtclSize [p] = c.randInt (varSize);
    }


    //program intro
    public static void intro () throws Exception
    {
	c.playAudio ("intro.wav");                      //plays audio
	g.setColor (Color.BLACK);
	g.fillRect (0, 0, sWidth, sHeight);
	g.setColor (Color.WHITE);
	g.drawString ("This particle effects program is by Peter Li", 300, 300);
	c.ViewUpdate ();
	c.delay (3000);
    }


    //outputs instructions
    public static void instructions () throws Exception
    {
	String inst;

	for (int i = 1 ; i <= 6 ; i++)
	{
	    //reads from file
	    inst = c.readFile ("PEInstructions.txt", i);
	    System.out.println (inst);
	}

    }


    //outputs credits
    public static void credits () throws Exception
    {
	String cred;

	for (int i = 1 ; i <= 2 ; i++)
	{
	    //reads from file
	    cred = c.readFile ("PECredits.txt", i);
	    System.out.println (cred);
	}
    }


    //checks for commands entered
    public static void commandCheck () throws Exception
    {
	command = c.getText ();

	//converts to char array
	cArray = new char [command.length () + 2];

	for (int i = 0 ; i < command.length () ; i++)
	{
	    cArray [i] = command.charAt (i);
	}

	//if first 4 characters = "size"
	if (cArray [0] == 's' && cArray [1] == 'i' && cArray [2] == 'z' && cArray [3] == 'e')
	{
	    if ((int) cArray [5] - 48 > 0) //allows input values 1 - 9
	    {
		//sets the variable size to the inputted ammount
		varSize = (int) cArray [5] - 48;
	    }
	}
    }


    //main program loop
    public static void start () throws Exception
    {
	while (true)
	{
	    //draws black background
	    c.cls ();
	    g.setColor (Color.BLACK);
	    g.fillRect (0, 0, sWidth, sHeight);

	    //retrieves mouse coords
	    mouseX = c.getMouseX ();
	    mouseY = c.getMouseY ();

	    for (int i = 1 ; i <= Pmax ; i++)
	    {
		//creates new particle when old particle 'dies'
		if (prtclLife [i] == 0)
		{
		    createParticle (i);
		}

		g.setColor (prtclClr [i]);

		//moves the particle based on speed and direction
		prtclX [i] += prtclSpd [i] * Math.cos (prtclDir [i]);
		prtclY [i] += prtclSpd [i] * Math.sin (prtclDir [i]);
		prtclLife [i]--;                        //decreases particlelife

		int x = (int) prtclX [i];
		int y = (int) prtclY [i];

		//draws
		 g.drawOval (x, y, prtclSize [i], prtclSize [i]);
	    }

	    c.ViewUpdate ();                            //updates screen
	    commandCheck ();                            //checks for commands
	    c.delay(8);
	}
    }
}
