package com.chekn.tool;  
  
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
  
/******************************************************************************* 
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法 
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true)) 
 */  
 public class CompressPicDemo {   
     private File file = null; // 文件对象   
     private String inputDir; // 输入图路径  
     private String outputDir; // 输出图路径  
     private String inputFileName; // 输入图文件名  
     private String outputFileName; // 输出图文件名  
     private int outputWidth = 100; // 默认输出图片宽  
     private int outputHeight = 100; // 默认输出图片高  
     private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)  
     public CompressPicDemo() { // 初始化变量  
         inputDir = "";   
         outputDir = "";   
         inputFileName = "";   
         outputFileName = "";   
         outputWidth = 100;   
         outputHeight = 100;   
     }   
     public void setInputDir(String inputDir) {   
         this.inputDir = inputDir;   
     }   
     public void setOutputDir(String outputDir) {   
         this.outputDir = outputDir;   
     }   
     public void setInputFileName(String inputFileName) {   
         this.inputFileName = inputFileName;  
     }   
     public void setOutputFileName(String outputFileName) {   
         this.outputFileName = outputFileName;   
     }   
     public void setOutputWidth(int outputWidth) {  
         this.outputWidth = outputWidth;   
     }   
     public void setOutputHeight(int outputHeight) {   
         this.outputHeight = outputHeight;   
     }   
     public void setWidthAndHeight(int width, int height) {   
         this.outputWidth = width;  
         this.outputHeight = height;   
     }   
       
     /*  
      * 获得图片大小  
      * 传入参数 String path ：图片路径  
      */   
     public long getPicSize(String path) {   
         file = new File(path);   
         return file.length();   
     }  
       
     // 图片处理   
     public String compressPic() {   
         try {   
             //获得源文件   
             file = new File(inputDir + inputFileName);   
             if (!file.exists()) {   
                 return "";   
             }   
             Image img = ImageIO.read(file);   
             // 判断图片格式是否正确   
             if (img.getWidth(null) == -1) {  
                 System.out.println(" can't read,retry!" + "<BR>");   
                 return "no";   
             } else {   
                 int newWidth; int newHeight;   
                 // 判断是否是等比缩放   
                 if (this.proportion == true) {   
                     // 为等比缩放计算输出的图片宽度及高度   
                     double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;   
                     double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;   
                     // 根据缩放比率大的进行缩放控制   
                     double rate = rate1 > rate2 ? rate1 : rate2;   
                     newWidth = (int) (((double) img.getWidth(null)) / rate);   
                     newHeight = (int) (((double) img.getHeight(null)) / rate);   
                 } else {   
                     newWidth = outputWidth; // 输出的图片宽度   
                     newHeight = outputHeight; // 输出的图片高度   
                 }   
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
                  
                /* 
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
                 * 优先级比速度高 生成的图片质量比较好 但速度慢 
                 */   
                FileUtils.forceMkdir(new File(outputDir));
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null); 
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);  
                // JPEGImageEncoder可适用于其他图片类型的转换   
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
                encoder.encode(tag);   
                out.close();   
             }   
         } catch (IOException ex) {   
             ex.printStackTrace();   
         }   
         return "ok";   
    }   
    public String compressPic (String inputDir, String outputDir, String inputFileName, String outputFileName) {   
        // 输入图路径   
        this.inputDir = inputDir;   
        // 输出图路径   
        this.outputDir = outputDir;   
        // 输入图文件名   
        this.inputFileName = inputFileName;   
        // 输出图文件名  
        this.outputFileName = outputFileName;   
        return compressPic();   
    }   
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {   
        // 输入图路径   
        this.inputDir = inputDir;   
        // 输出图路径   
        this.outputDir = outputDir;   
        // 输入图文件名   
        this.inputFileName = inputFileName;   
        // 输出图文件名   
        this.outputFileName = outputFileName;   
        // 设置图片长宽  
        setWidthAndHeight(width, height);   
        // 是否是等比缩放 标记   
        this.proportion = gp;   
        return compressPic();   
    }   
      
    // main测试   
    // compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))  
    public static void main(String[] args) {
    	Map<ConArg,String> inParams=new HashMap<ConArg, String>();
    	String[] defArgs= new String[]{".", "*.png", "1366", "1366", "./chekn"};
    	
    	int argsLen=args.length;
    	for (int i = 0; i < defArgs.length; i++) {
    		ConArg ca= ConArg.fromOrder(i);
    		String val=StringUtils.defaultIfBlank(i<argsLen ? args[i]:null, defArgs[i]);
    		
    		if(ca.validate(val))
    			inParams.put( ca , val );
    		else {
    			System.err.print("x [arg error] : arg->"+args[i]);
    			System.exit(0);
    		}
    			
		}
    	
        CompressPicDemo mypic = new CompressPicDemo();    
        
        String inputDirStr=inParams.get(ConArg.SOURCE);
        String outputDirStr=inParams.get(ConArg.TARGET);
        String file=inParams.get(ConArg.FILE);
        String width=inParams.get(ConArg.WIDTH);
        String height=inParams.get(ConArg.HEIGHT);
        for(File pic:new File(inputDirStr).listFiles()){
        	String fname=pic.getName();
        	if( !pic.isDirectory() && fname.matches( file.replace("*", ".*?") ) )
        		mypic.compressPic(inputDirStr, outputDirStr, pic.getName(), pic.getName(), Integer.parseInt(width), Integer.parseInt(height), true);
        	
        	System.out.println(fname);
        }
        
    }   
    
    enum ConArg {
    	SOURCE(0,".",null),
    	FILE(1,"*.png","[^\\/:;<>]*"),
    	WIDTH(2,"1366","[\\d]+"),
    	HEIGHT(3,"1366","[\\d]+"),
    	TARGET(4,"./chekn",null);
    	
    	public static ConArg fromOrder(int order){
    		ConArg arg=null;
    		for(ConArg u: ConArg.values()){
				if( u.getOrder()==order ){
					arg=u;
					break;
				}
			}
    		return arg;
    	}
    	
    	public boolean validate(String val){
    		return this.getRule()==null ? true : val.matches(this.getRule());
    	}
    	
    	private int order;
    	private String defVal;
		private String rule;
    	
    	private ConArg(int order, String defVal, String rule) {
    		this.order=order;
    		this.defVal=defVal;
    		this.rule=rule;
    	}
    	
    	//getter and setter
    	public int getOrder() {
    		return order;
		}
		public void setOrder(int order) {
			this.order = order;
		}
		public String getDefVal() {
			return defVal;
		}
		public void setDefVal(String defVal) {
			this.defVal = defVal;
		}
		public String getRule() {
			return rule;
		}
		public void setRule(String rule) {
			this.rule = rule;
		}
	}
    
 }  