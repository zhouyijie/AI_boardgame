
public class six_puzzle_state extends State{
	int tilePosition[] = new int[6];
	public void print(){
		System.out.println("current_state: ");
		System.out.print(tilePosition[1]+"|");
		System.out.print(tilePosition[2]+"|");
		System.out.println(tilePosition[3]);
		System.out.print(tilePosition[4]+"|");
		System.out.print(tilePosition[5]+"|");
		System.out.println(tilePosition[6]);
	}
	
}

