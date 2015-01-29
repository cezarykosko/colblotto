

public class BlottoGame {
	BlottoGameContestants contestants;
	int result;
	
	public BlottoGame(int result, String name1, String name2) {
		this.result = result;
		this.contestants = new BlottoGameContestants(name1, name2);
	}
	
	@Override
	public String toString() {
		return contestants.toString() + ": " + this.result;
	}
}
