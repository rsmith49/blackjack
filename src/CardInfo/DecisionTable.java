package CardInfo;

public class DecisionTable {
	
	/*
	 * Key:
	 * H = hit
	 * S = stand
	 * D = double
	 * P = split
	 * I = invalid
	 */
	
	public char[][] normal = new char[21][10];
	public char[][] ace = new char[10][10];
	public char[][] pair = new char[10][10];

	public DecisionTable() {
		int i,j;
		
		// Normal table
		
		// under 5
		for (i=0;i<5;i++) {
			for (j=0;j<10;j++) {
				normal[i][j] = 'I';
			}
		}
		
		// 5-8
		for (i=5;i<=8;i++) {
			for (j=0;j<10;j++) {
				normal[i][j] = 'H';
			}
		}
		
		// 9
		for (j=0;j<=2;j++) {
			normal[i][j] = 'H';
		}
		for (j=3;j<=6;j++) {
			normal[i][j] = 'D';
		}
		for (j=7;j<=9;j++) {
			normal[i][j] = 'H';
		}
		
		// 10 and 11
		for (i=10;i<=11;i++) {
			normal[i][1] = 'H';
			for (j=2;j<=9;j++) {
				normal[i][j] = 'D';
			}
		}
		normal[10][0] = 'H';
		normal[11][0] = 'D';
		
		// 12
		for (j=0;j<10;j++) {
			normal[12][j] = 'H';
		}
		for (j=4;j<=6;j++) {
			normal[12][j] = 'S';
		}
		
		// 13-16
		for (i=13;i<=16;i++) {
			for (j=0;j<10;j++) {
				normal[i][j] = 'H';
			}
			for (j=2;j<=6;j++) {
				normal[i][j] = 'S';
			}
		}
		
		// >= 17
		for (i=17;i<21;i++) {
			for (j=0;j<10;j++) {
				normal[i][j] = 'S';
			}
		}
		
		
		//Ace table
		
		// Ace
		
		for (j=0;j<10;j++) {
			ace[1][j] = 'I';
		}
		
		// 2-6
		for (i=2;i<=6;i++) {
			for (j=0;j<10;j++) {
				ace[i][j] = 'H';
			}
			for (j=5;j<=6;j++) {
				ace[i][j] = 'D';
			}
		}
		for (i=4;i<=6;i++) {
			ace[i][4] = 'D';
		}
		ace[6][3] = 'D';
		
		// 7
		for (j=0;j<10;j++) {
			ace[7][j] = 'H';
		}
		for (j=2;j<=8;j++) {
			ace[7][j] = 'S';
		}
		for (j=3;j<=6;j++) {
			ace[7][j] = 'D';
		}
		
		// >= 8
		for (i=8;i<10;i++) {
			for (j=0;j<10;j++) {
				ace[i][j] = 'S';
				ace[0][j] = 'S';
			}
		}
		
		// Pair table
		
		// 10s and Aces
		for (j=0;j<10;j++) {
			pair[0][j] = 'S';
			pair[1][j] = 'P';
		}
		
		// 2-7
		for (i=2;i<=7;i++) {
			for (j=0;j<10;j++) {
				pair[i][j] = 'H';
			}
			for (j=2;j<=7;j++) {
				pair[i][j] = 'P';
			}
		}
		for (i=4;i<=6;i++) {
			pair[i][7] = 'H';
		}
		for (j=2;j<=4;j++) {
			pair[4][j] = 'H';
		}
		for (j=0;j<10;j++) {
			pair[5][j] = normal[10][j];
		}
		
		// 8 and 9
		for (j=0;j<10;j++) {
			pair[8][j] = 'P';
			pair[9][j] = 'P';
		}
		pair[9][7] = 'S';
		pair[9][0] = 'S';
		pair[9][1] = 'S';
		
	}
	
	public void print() {
		System.out.println("Normal Table:");
		
		for (int i=0;i<normal[0].length;i++) {
			System.out.print(i+" ");
		}
		System.out.println();
		
		for (int i=0;i<normal.length;i++) {
			System.out.print(i+" ");
			for (int j=0;j<normal[0].length;j++) {
				System.out.print(normal[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		
		System.out.println("Aces Table:");
		for (int i=0;i<ace[0].length;i++) {
			System.out.print(i+" ");
		}
		System.out.println();
		for (int i=0;i<ace.length;i++) {
			System.out.print(i+" ");
			for (int j=0;j<ace[0].length;j++) {
				System.out.print(ace[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		
		System.out.println("Pairs Table:");
		for (int i=0;i<pair[0].length;i++) {
			System.out.print(i+" ");
		}
		System.out.println();
		for (int i=0;i<pair.length;i++) {
			System.out.print(i+" ");
			for (int j=0;j<pair[0].length;j++) {
				System.out.print(pair[i][j]+" ");
			}
			System.out.println();
		}
	}
	
}
