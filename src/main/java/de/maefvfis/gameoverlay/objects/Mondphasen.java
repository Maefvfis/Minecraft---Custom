package de.maefvfis.gameoverlay.objects;

public class Mondphasen {
	public static String getPhase(int check)
		{
		switch(check)
		{
		case 0:
			return "Vollmond";
		case 1:
			return "Abnehmender Mond";
		case 2:
			return "Letztes Viertel";
		case 3:
			return "Abnehmender Mond";
		case 4:
			return "Neumond";
		case 5:
			return "Zunehmender Mond";
		case 6:
			return "Erstes Viertel";
		case 7:
			return "Zunehmender Mond";
		default:
			return null;
		}
	}
	
	
	public static int[] getPhase_iconoffset(int check)
	{
		int[] returner = new int[2];;
		int Off = 96;
		switch(check)
		{
		case 0:		//"Vollmond";
			returner[0] = 0;
			returner[1] = 0;
			return returner;
		case 1: // Abnehmender Mond
			returner[0] = Off;
			returner[1] = 0;
			return returner;
		case 2: // Letztes Viertel
			returner[0] = Off * 2;
			returner[1] = 0;
			return returner;
		case 3: // Abnehmender Mond
			returner[0] = Off * 3;
			returner[1] = 0;
			return returner;
		case 4: // Neumond
			returner[0] = 0;
			returner[1] = Off;
			return returner;
		case 5: // Zunehmender Mond
			returner[0] = Off;
			returner[1] = Off;
			return returner;
		case 6: // Erstes Viertel
			returner[0] = Off * 2;
			returner[1] = Off;
			return returner;
		case 7: // Zunehmender Mond
			returner[0] = Off * 3;
			returner[1] = Off;
			return returner;
		default:
			returner[0] = 0;
			returner[1] = 0;
			return returner;
		}
	}
		
	
}
