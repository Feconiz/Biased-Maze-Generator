package maze_gen;

public class Cell {
	private int x;
	private int y;
	private boolean backtracked = false;
	Maze_generator mg;
	private boolean wall= true;
	private boolean last = false;
	
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public Cell(int x, int y, Maze_generator mg){
		this.x = x;
		this.y = y;
		this.mg = mg;
	}	
	public boolean isWall() {
		return wall;
	}
	public void setWall(boolean wall) {
		this.wall = wall;
	}
	public int[] check(){
		int[] temp = new int[4];
		try{
			if(mg.getGrid()[x-2][y].wall == true){
				temp[0] = 1;
			}
		}catch(Exception e){}
		try{
			if(mg.getGrid()[x][y-2].wall == true){
				temp[1] = 1;
			}
		}catch(Exception e){}
		try{
			if(mg.getGrid()[x+2][y].wall == true){
				temp[2] = 1;
			}
		}catch(Exception e){}
		try{
			if(mg.getGrid()[x][y+2].wall == true){
				temp[3] = 1;
			}
		}catch(Exception e){}
		return temp;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isBacktracked() {
		return backtracked;
	}
	public void setBacktracked(boolean backtracked) {
		this.backtracked = backtracked;
	}

	public int countConections(){
		int count = 0;
		try{
			if(mg.getGrid()[x-1][y].wall == false){
				count++;
			}
		}catch(Exception e){}
		try{
			if(mg.getGrid()[x][y-1].wall == false){
				count++;
			}
		}catch(Exception e){}
		try{
			if(mg.getGrid()[x+1][y].wall == false){
				count++;
			}
		}catch(Exception e){}
		try{
			if(mg.getGrid()[x][y+1].wall == false){
				count++;
			}
		}catch(Exception e){}
		return count;
	}
}
