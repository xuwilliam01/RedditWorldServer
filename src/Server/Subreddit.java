package Server;

import java.util.ArrayList;

import Objects.Signpost;

public class Subreddit {

	public final static int SIDE_LENGTH = 1000;
	public final static int TILE_SIZE = 64;

	/**
	 * The radius around the sign (in tiles) where other signs cannot spawn
	 */
	public final static int SIGN_SPACE_RADIUS = 5;

	Signpost[][] signGrid;
	boolean[][] usedTiles;

	ArrayList<Signpost> signs = new ArrayList<Signpost>();

	public Subreddit() {
		signGrid = new Signpost[SIDE_LENGTH][SIDE_LENGTH];
		usedTiles = new boolean[SIDE_LENGTH][SIDE_LENGTH];

	}

	public void placeSigns() {
		for (Signpost sign : signs) {
			int row;
			int column;

			int noOfTries = 0;

			do {
				row = (int) (Math.random() * SIDE_LENGTH);
				column = (int) (Math.random() * SIDE_LENGTH);
				noOfTries++;
			} while (!usedTiles[row][column] && noOfTries < 10000);

			if (noOfTries >= 10000)
			{
				break;
			}
			
			usedTiles[row][column] = true;
			signGrid[row][column] = sign;

			for (int r = row - SIGN_SPACE_RADIUS; r <= row + SIGN_SPACE_RADIUS; r++) {
				for (int c = column - SIGN_SPACE_RADIUS; c <= column + SIGN_SPACE_RADIUS; c++) {

					// Formula for a circle (x-h)^2 + (y-k)^2 = r^2
					if (!(r < 0 || r >= SIDE_LENGTH || c < 0 || c >= SIDE_LENGTH || (r - row) * (r - row)
							+ (c - column) * (c - column) > SIGN_SPACE_RADIUS * SIGN_SPACE_RADIUS)) {

						usedTiles[r][c] = true;

					}
				}
			}
		}
	}

}
