

public class BlottoGameContestants {
	String firstPlayer, secondPlayer;
	
	public BlottoGameContestants(String firstPlayer, String secondPlayer) {
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
	}
	
	@Override
	public String toString() {
		return this.firstPlayer + "-" + this.secondPlayer;
	}
}
