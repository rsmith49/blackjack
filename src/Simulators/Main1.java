package Simulators;

import java.util.ArrayList;
import java.util.List;

import CardInfo.BJGame;
import CardInfo.DecisionTable;
import CardInfo.CountMethod;
import CountingMethods.HiLo;
import CountingMethods.OneDimCount;

public class Main1 {
	
	public static final int NUM_DECKS = 6;
	public static final int CHUTES = 10000;
	public static final int NUM_PLAYERS = 4;

	public static void main(String[] args) {
		
		DecisionTable table = new DecisionTable();
		List<CountMethod> counts = new ArrayList<CountMethod>();
		
		counts.add(new HiLo());
		
		BJGame game = new BJGame(NUM_DECKS,NUM_PLAYERS,table,counts);
		
		long time = System.currentTimeMillis();
		for (int i=0;i<CHUTES;i++) {
			game = new BJGame(NUM_DECKS,NUM_PLAYERS,table,counts);
			game.run();
		}
		System.out.println("Simulation took " + 
                        (System.currentTimeMillis()-time) + " seconds");
		
		for (CountMethod c : counts) {
			if (c instanceof OneDimCount) {
				((OneDimCount) c).printCountData();
			}
		}
		
	}

}
