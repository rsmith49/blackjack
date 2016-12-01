package CountingMethods;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import CardInfo.CountMethod;
import CardInfo.Pair;

public abstract class OneDimCount implements CountMethod {
	
	// Don't think we want a map, because true count

	float count;
	//Map<Float,Pair<Float,Float>> data = new HashMap<Float,Pair<Float,Float>>();
	int cardsLeft;
	
	public OneDimCount() {
		System.out.println("Count,CardsLeft,HandsWon,HandsPlayed");
	}
	
	@Override
	public abstract void getCount(int[] cards, int numDecks, int cardsLeft);

	@Override
	public void storeData(float wins, float total) {
		// TODO: change this to writing to a specific file --
		// Currently no internet access so forgot how to do that in java
		System.out.println(count+","+cardsLeft+","+
				wins+","+total);
	}

	public void printCountData() {
		
	}

	/*@Override
	public void storeData(float wins,float total) {
		// TODO Auto-generated method stub
		if (data.containsKey(count)) {
			data.get(count).x += wins;
			data.get(count).y += total;
		}
		else {
			data.put(count, new Pair<Float,Float>(wins,total));
		}
	}
	
	public void printCountData() {
		Iterator<Map.Entry<Float,Pair<Float,Float>>> it = data.entrySet().iterator();
		System.out.println("count   win percentage");
		while (it.hasNext()) {
			Map.Entry<Float, Pair<Float,Float>> ob = it.next();
			System.out.println(ob.getKey()+":        "+(ob.getValue().x/ob.getValue().y));
		}
	} */

}
