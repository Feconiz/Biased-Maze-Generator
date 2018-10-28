package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze_gen.Maze_generator;

//For future me: 1 png image in the same folder as the program, put its name without extension in the fileName variable, black and white binary image only

public class Main {
	static int height= 501;//for images 451
	static int width = 501;//Math.round(height*1.414141414141414141414141414141f) + (Math.round(height*1.414141414141414141414141414141f)%2==0?1:0);
	static int thickness = 10;
	static final String fileName = "heart";
	static final int noise = 1;//1-10, 1 is straight lines 10 is semi random
	public static void main(String[] args) {
        long lStartTime = System.nanoTime();
		System.out.println("Starting the maze generation!");
		BufferedImage bias;
		try {
			if(new File(fileName + ".png").exists()){
				bias = resize(ImageIO.read(new File(fileName + ".png")),width+1, height+1);
			}else{
				bias = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_RGB);
				for(int i = 0; i < width;i++){
					for(int j = 0; j < height;j++){
						bias.setRGB(i, j, new Color(0,0,0).getRGB());
					}
				}
			}
			generateImage(new Maze_generator(width, height, bias, noise));
		} catch (IOException e) {
			e.printStackTrace();
		}
        long lEndTime = System.nanoTime();
        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time in milliseconds: " + output / 1000000);
	}
	
	private static void generateImage(Maze_generator maze_generator) {
		System.out.println("Starting the image generation!");
		BufferedImage img = new BufferedImage((width+1)*thickness+thickness, (height+1)*thickness+thickness, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) img.getGraphics();
		g2d.setColor(new Color(255,255,255));
		for(int x = 0;x < width;x++){
			for(int y = 0;y < height;y++){
					if(!maze_generator.getGrid()[x][y].isWall()){
						g2d.fillRect((x+1)*thickness+1,(y+1)*thickness+1,thickness,thickness);
					}
			}
		}
		g2d.fillRect(thickness+1, 0, thickness, thickness+1);
		g2d.fillRect(((width)*thickness)+1,((height+1)*thickness),thickness,thickness);
			
		g2d.dispose();
		File outputfile = new File(fileName + "_Maze.png");
	    try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Done!");
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
