package com.ckn.practice;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

public class ImageOcrDeal {

	
	/**
	 * 下面方法是关于图像的预处理,根据亮度设个阈值
	 * 关于RGB
	 * 计算机图形显示系统中各种颜色的生成，
	 * 是通过对红(R)、绿(G)、蓝(B)三个颜色通道的变化以及它们相互之间的叠加来得到各式各样的颜色的，
	 * RGB即是代表红、绿、蓝三个通道的颜色
	 * @return
	 */
	public boolean isFillUseWhite(int colorIntVal, int threshold){
		Color color=new Color(colorIntVal);
		//System.out.println(color.getRed()+" "+color.getBlue()+" "+color.getGreen());
		if(color.getRed()+color.getGreen()+color.getBlue()>threshold){
			return true;
		}else
			return false;
	}
	
	/**
	 * 二值化
	 * 二值化就是将图像只用两种颜色表示。
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public BufferedImage removeBackground(String filePath, int careX, int careY, int threshold) throws Exception{
		List<Point> careV =JSON.parseArray( FileUtils.readFileToString(new File("c:\\careV.data")) , Point.class);
		
		BufferedImage img=ImageIO.read(new File(filePath));
		int width=img.getWidth();
		int height=img.getHeight();
		for(int x=0;x<width;x++){
			ba:
			for(int y=0;y<height;y++){
				//System.out.println("x [verb] x: "+ x+ "; y:"+y);
				
				//遍历全部像素
				if(x< careX || y<careY || isFillUseWhite(img.getRGB(x, y), threshold)){
					img.setRGB(x, y, Color.WHITE.getRGB());
				}else{
					
					if(x > 1793 && x< 1865)
					for(Point p:careV) {
						if(p.getX() ==x && p.getY() == y) {
							img.setRGB(x, y, Color.WHITE.getRGB());
							continue ba;
						}
					}
					
					img.setRGB(x, y, Color.black.getRGB());
				}
			}
		}
		
		return img;
	}
	
	public List<BufferedImage> splitImage(BufferedImage img){
		List<BufferedImage> subImgs=new ArrayList<BufferedImage>();
		subImgs.add(img.getSubimage(0, 0, 9, 9));
		subImgs.add(img.getSubimage(9, 0, 9, 9));
		subImgs.add(img.getSubimage(18, 0, 9, 9));
		subImgs.add(img.getSubimage(27, 0, 9, 9));
		
		return subImgs;
	}
	
	
	public void saveImageToDisk(BufferedImage im,String fileName) throws Exception{
		File file=new File(fileName);
		String format="JPEG";
		ImageIO.write(im, format, file);
	}
	
	
	public static void main(String[] args){
		
		/*for(int th=606; th<706;th+=8) {
			ImageOcrDeal iod=new ImageOcrDeal();
			try {
				iod.saveImageToDisk(iod.removeBackground("C:\\002.jpg", th), "C:\\gen\\"+th+"_mnf.jpg");
				List<BufferedImage> subImgs=iod.splitImage(ImageIO.read(new File("C:\\002.jpg")));
				int i=0;
				for(BufferedImage bi:subImgs){
					FileUtils.forceMkdir(new File("c:\\gen"));
					iod.saveImageToDisk(bi, "C:\\gen\\"+i+".jpg");
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		ImageOcrDeal iod=new ImageOcrDeal();
		try {
			int th=705;
			iod.saveImageToDisk(iod.removeBackground("C:\\002.jpg", 0 , 0, th), "C:\\gen\\"+ th +"_mnf.jpg");
			/*List<BufferedImage> subImgs=iod.splitImage(ImageIO.read(new File("C:\\002.jpg")));
			int i=0;
			for(BufferedImage bi:subImgs){
				FileUtils.forceMkdir(new File("c:\\gen"));
				iod.saveImageToDisk(bi, "C:\\gen\\"+i+".jpg");
				i++;
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
