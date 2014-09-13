package de.maefvfis.gameoverlay.objects;

public class Output implements Comparable<Output> {
	private int id;
	private String coordinaten;
  
	public Output(int id2, String coordinaten2) {
		id = id2;
		coordinaten = coordinaten2;
	}
	
	public int get_id() {
		return id;
	}
	public String get_coordinaten() {
		return coordinaten;
	}
	
	public int compareTo(Output other) {
		return this.get_id() - other.get_id();
	}
}