

import java.util.ArrayList;

public class BlottoGameList {
	ArrayList<BlottoGame> gameList;
	
	public void addGame(int result, String name1, String name2) {
		gameList.add(new BlottoGame(result, name1, name2));
	}
	
	public void addGame(BlottoGame game) {
		gameList.add(game);
	}
	
	public BlottoGameList() {
		this.gameList = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("games:\n");
		int pointSum = 0;
		for (BlottoGame game : this.gameList) {
			result.append(game.toString() + "\n");
			pointSum += game.result;
		}
		result.append("combined result: ");
		result.append(pointSum);
		
		return result.toString();
	}
}
