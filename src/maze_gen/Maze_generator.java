package maze_gen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Maze_generator {
	Cell[][] grid;
	int currentX;
	int currentY;
	int noise;
	Random r;
	int sizeX,sizeY;
	boolean first = true;
	int counter;
	LinkedList<Cell> backtrack_list = new LinkedList<Cell>();
	BufferedImage bias;
	public Maze_generator(int sizeX, int sizeY, BufferedImage bias, int noise){
		this.sizeX = sizeX + 1;
		this.sizeY = sizeY + 1;
		this.noise = noise;//between 1 and 10
		if(bias != null){
			this.bias =  resize(bias, sizeX, sizeY);
		}else{
			this.bias = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);
			for(int i = 0; i < sizeX;i++){
				for(int j = 0; j < sizeY;j++){
					this.bias.setRGB(i, j, new Color(0,0,0).getRGB());
				}
			}
		}
		this.grid = generateGrid(this.sizeX, this.sizeY);
		this.currentX = 0;
		this.currentY = 0;
		grid[0][0].setWall(false);
		while(true){
			if(canmove(grid[currentX][currentY])){
				move(choose(currentX,currentY));
			}else{
				//System.out.println("bt");
				if(first){
					if(((currentX > sizeX/2 && currentY > sizeY/4) || (currentY > sizeX/2 && currentX > sizeY/4)) && (count(currentX, currentY) == 1 || counter > sizeX)){
						grid[currentX][currentY].setLast(true);
						first = false;
					}
				}
				
				backtrack();
			}
			if(backtrack_list.isEmpty()){
				int x = 0;
				int y = 0;
				while(true && first){
					System.out.println("j");
					x = r.nextInt(sizeX);
					y = r.nextInt(sizeY);
					if(x > sizeX/2 && y > sizeY/2 && !grid[x][y].isWall() && count(x, y) == 1){
						grid[x][y].setLast(true);
						break;
					}
				}
				break;
			}
		}
	}

	private int count(int x, int y) {
		Cell temp = grid[x][y];
		return temp.countConections();
	}

	



	private void backtrack(){
		
		a:
		while(true){
			if(backtrack_list.isEmpty()){
				break;
			}
			int[] temp = backtrack_list.getLast().check();
			for(int i : temp){
				if(i == 1){
					//System.out.println("did");
					currentX = backtrack_list.getLast().getX();
					currentY = backtrack_list.getLast().getY();
					break a;
				}
			}
			backtrack_list.getLast().setBacktracked(true);
			backtrack_list.removeLast();
		}
	}
	private boolean canmove(Cell cell) {
		for(int i : cell.check()){
			if(i == 1){
				return true;
			}
		}
		return false;
	}
	private void move(int choose) {
		backtrack_list.add(grid[currentX][currentY]);
		if(choose == 1){
			grid[currentX-1][currentY].setWall(false);
			grid[currentX-2][currentY].setWall(false);
			currentX -= 2;
		}else if(choose == 2){
			grid[currentX][currentY-1].setWall(false);
			grid[currentX][currentY-2].setWall(false);
			currentY -= 2;
		}else if(choose == 3){
			grid[currentX+1][currentY].setWall(false);
			grid[currentX+2][currentY].setWall(false);
			currentX += 2;
		}else if(choose == 4){
			grid[currentX][currentY+1].setWall(false);
			grid[currentX][currentY+2].setWall(false);
			currentY += 2;
		}
	}
	private int choose(int x, int y) {
		int iterations = 0;
		while(true){
			iterations++;
			r = new Random();
			int ran = r.nextInt(100)+1;
			if(bias.getRGB(x, y) == new Color(255,255,255).getRGB()){
				if(ran <= 0.5f*noise){
					ran = 0;
				}else if(ran <= 50){
					ran = 1;
				}else if(ran <= 50 + 0.5f*noise){
					ran = 2;
				}else{
					ran = 3;
				}
			}else{
				ran = r.nextInt(4);
			}
			
			if(getGrid()[x][y].check()[ran] == 1){
				return ran + 1;
			}else if(iterations>10){
				ran = r.nextInt(4);
				if(getGrid()[x][y].check()[ran] == 1){
					return ran + 1;
				}
			}
		}
		
	}
	private Cell[][] generateGrid(int sizeX, int sizeY) {
		Cell[][] temp = new Cell[sizeX][sizeY];
		for(int i = 0; i < sizeX;i++){
			for(int j = 0; j < sizeY;j++){
				temp[i][j] = new Cell(i ,j, this);
			}
		}
		return temp;
	}
	public Cell[][] getGrid() {
		return grid;
	}
	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  

	
}