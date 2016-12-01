package CardInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// NEED TO KNOW what to keep track of data wise

public class BJGame {
	
	public static final float END_OF_SCHUTE = .75f;
        public static final float BLACK_JACK_PAYOUT = 1.5f;

	private static final int LOOP_CUTOFF = 10000;
	
	private int numDecks;
	private int numPlayers;
	private int cardsLeft;
	
	private DecisionTable table;
	
	private List<CountMethod> counts;
	
	private Random rand = new Random();
	
	/*
	 * cards represents number of each card left
	 * 10 = cards[0]
	 * Ace = [1]
	 * 2 = [2]
	 * ...
	 * 9 = [9]
	 */
	private int[] cards = new int[10];
	
	public BJGame(int numDecks, int numPlayers, DecisionTable table,List<CountMethod> counts) {
		this.numDecks = numDecks;
		cardsLeft = numDecks*52;
		this.numPlayers = numPlayers;
		
		for (int i=1;i<10;i++) {
			cards[i] = numDecks*4;
		}
		cards[0] = numDecks*16;
		
		this.table = table;
		this.counts = counts;
	}
	
	public void run() {
		BJHand hand,dealerHand;
		List<BJHand> playerHands;
		int i;
		float percWin,winnings,numHands,numWins;
		List<Float> winners = new ArrayList<Float>();
		float endNum = cardsLeft*(1-END_OF_SCHUTE);
		while (cardsLeft > endNum) {
			
			for (CountMethod c : counts) {
				c.getCount(cards,numDecks,cardsLeft);
			}
			
			playerHands = new ArrayList<BJHand>();
			
			dealerHand = new BJHand();
			dealerHand.hit();
			
			for (i=0;i<numPlayers;i++) {
				
				hand = new BJHand();
				hand.hit();
				hand.hit();
				hand.play(dealerHand.hand[0]);
				if (hand.split) {
					addSplits(hand,playerHands);
				}
				else {
					playerHands.add(hand);
				}
				
			}
			
			dealerHand.playDealer();
			
			numWins = 0f;
			numHands = 0;
			for (BJHand pHand : playerHands) {
				if (pHand.doubled) {
					winnings = 2f;
				}
				else {
					winnings = 1f;
				}
				if (pHand.bj && !dealerHand.bj) {
					numWins += BLACK_JACK_PAYOUT;
					numHands += 1f;
				}
				else if (!pHand.bust() && (dealerHand.bust() || pHand.total() > dealerHand.total())) {
					numWins += winnings;
					numHands += winnings;
				}
				else if (pHand.bust() || pHand.total()!=dealerHand.total()) {
					numHands += winnings;
				}
			}
			percWin = numWins/numHands;
			winners.add(percWin);
			
			for (CountMethod c : counts) {
				c.storeData(numWins,numHands);
			}
		}
		int j = 10;
		j++;
	}
	
	// Uses recursion
	public void addSplits(BJHand hand, List<BJHand> list) {
		if (hand.split) {
			addSplits(hand.left,list);
			addSplits(hand.right,list);
		}
		else {
			list.add(hand);
		}
	}
	
	// Returns -1 if deck is empty, -2 if error
	public int deal() {
		int i;
		for (i=0;i<10;i++) {
			if (cards[i] > 0) {
				break;
			}
		}
		if (i==10) {
			return -1;
		}
		if (cardsLeft < 10) {
			i = 3;
		}
		
		// random numbers represent ace to king respectively
		int num,cur = 0;
		
		num = rand.nextInt(cardsLeft)+1;
		while (num > 0) {
			num -= cards[cur++];
		}
		cur--;
		cards[cur]--;
		cardsLeft--;
		
		return cur;
		
/*		while (true) {
			num = rand.nextInt(13);
			if (num > 8) {
				num = 0;
			}
			else {
				num++;
			}
			if (cards[num] > 0) {
				cards[num]--;
				cardsLeft--;
				return num;
			}
			if (++i > LOOP_CUTOFF) {
				System.out.println("ERROR");
				return -2;
			} 
			
		} */
	}
	
	public int valueOf(int card) {
		if (card > 0) {
			return card;
		}
		else {
			return 10;
		}
	}
	
	
	
	private class BJHand {
		private static final int HAND_CAP = 10;

		private int[] hand = new int[HAND_CAP];
		private int cur = 0;
		private int total = 0;
		private boolean hasAce = false;
		public boolean doubled = false;
		public boolean split = false;
		public boolean bj = false;
		
		public BJHand left = null;
		public BJHand right = null;
		
		public int total() {
			return total;
		}
		
		public boolean bust() {
			return total > 21;
		}
		
		public void add(int card) {
			hand[cur] = card;
			if (hand[cur]==1 && total < 11) {
				total += 11;
				hasAce = true;
			}
			else if (card==0) {
				total += 10;
			}
			else {
				total += hand[cur];
			}
			cur++;
			
			if (bust() && hasAce) {
				total -= 10;
				hasAce = false;
			}
		}
		
		public void hit() {
			hand[cur] = deal();
			if (hand[cur]==1 && total < 11) {
				total += 11;
				hasAce = true;
			}
			else if (hand[cur]==0) {
				total += 10;
			}
			else {
				total += hand[cur];
			}
			
			cur++;
			
			if (bust() && hasAce) {
				total -= 10;
				hasAce = false;
			}
			
		}
		
		public void play(int dealerCard) {
			if (cur != 2) {
				System.out.println("ERROR");
				return;
			}
			if (total==21) {
				bj = true;
				return;
			}
			
			if (hand[0] == hand[1]) {
				playPair(dealerCard);
			}
			else if (hasAce) {
				playAce(dealerCard);
			}
			else {
				playNormal(dealerCard);
			}
		}
		
		public void playNormal(int dealerCard) {
			char nextMove = ' ';
			while (nextMove != 'S' && total < 21) {
				nextMove = table.normal[total][dealerCard];
				switch (nextMove) {
					case 'D':
						if (cur==2) {
							hit();
							doubled = true;
							nextMove = 'S';
							break;
						}
					case 'H':
						hit();
						break;
					case 'S':
						break;
					default:
						System.out.println("ERROR invalid play");
						break;
				}
			}
		}
		
		public void playAce(int dealerCard) {
			int nonAce;
			if (hand[0]==1) {
				nonAce = hand[1];
			}
			else {
				nonAce = hand[0];
			}
			char nextMove = ' ';
			while (hasAce && nextMove!='S') {
				nextMove = table.ace[nonAce][dealerCard];
				switch (nextMove) {
					case 'H':
						hit();
						break;
					case 'S':
						break;
					case 'D':
						hit();
						doubled = true;
						break;
					default:
						System.out.println("ERRPR invalid ace play");
						break;
				}
			}
			if (nextMove!='S') {
				playNormal(dealerCard);
			}
		}
		
		public void playPair(int dealerCard) {
			if (cur!=2) {
				System.out.println("ERROR wrng number of cards for split");
				return;
			}
			switch (table.pair[hand[0]][dealerCard]) {
				case 'H':
					hit();
					playNormal(dealerCard);
					break;
				case 'D':
					hit();
					doubled = true;
					break;
				case 'S':
					return;
				case 'P':
					split(dealerCard);
					break;
					
			}
		}
		
		public void playDealer() {
			if (cur!=1) {
				System.out.println("ERROR dealer has more than 1 card");
			}
			hit();
			if (total==21) {
				bj = true;
			}
			while (total < 18 && (hasAce || total < 17)) {
				hit();
			}
		}
		
		public void split(int dealerCard) {
			split = true;
			right = new BJHand();
			left = new BJHand();
			right.add(hand[0]);
			left.add(hand[0]);
			right.hit();
			left.hit();
			right.play(dealerCard);
			left.play(dealerCard);
		}
	}
	
}
