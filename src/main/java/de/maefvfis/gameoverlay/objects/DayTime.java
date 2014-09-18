package de.maefvfis.gameoverlay.objects;

import sun.security.ssl.Debug;

public class DayTime {
	public static String getTimeString(Long Time) {
		
		Time = Time + 6000;
		// Kade ist eine Hure
		//Time = Time + 8000;
		
		int Stunden;
		int Minuten;

		String returnStunden;
		String returnMinuten;
		
		while (Time > 24000) {
			Time = Time - 24000;
		}

		Stunden = (int)(Time / 1000);
		Minuten = (int)(((Time - (Stunden * 1000)) * 60) / 1000);
		
		if(Stunden < 10) {
			returnStunden = "0" + String.valueOf(Stunden);
		} else {
			returnStunden = String.valueOf(Stunden);
		}
		
		if(Minuten < 10) {
			returnMinuten = "0" + String.valueOf(Minuten);
		} else {
			returnMinuten = String.valueOf(Minuten);
		}
		
		return returnStunden + ":" + returnMinuten + " Uhr";
		
	}
}
