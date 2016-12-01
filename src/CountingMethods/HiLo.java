package CountingMethods;

public class HiLo extends OneDimCount {

	@Override
	public void getCount(int[] cards,int numDecks, int cardsLeft) {
		// TODO Auto-generated method stub
		int total = numDecks*16; // Total # of 10s
		count = 0;
		count -= (total-cards[0]);
		total = numDecks*4;      // Total # of other cards
		count -= (total-cards[1]);
		for (int i=2;i<7;i++) {
			count += (total-cards[i]);
		}
		this.cardsLeft = cardsLeft;
		//count = count/(cardsLeft/52.0f);
	}

}
