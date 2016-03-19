package com.ckn.practice;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QrCodeImageCopy {
    
    private static final String outFilePath="C:\\Users\\CHEKN\\Desktop\\TRANSFILE\\QR_CODE\\";
    private static final int width=300;
    private static final int height=300;
    
    private static final int blackRGB=0xFF000000;
    private static final int whiteRGB=0xFFFFFFFF;
    
    private boolean writeQrCodeImage(String code, String fileName) throws WriterException, IOException{
        //编码配置
        Map<EncodeHintType,String> map=new HashMap<EncodeHintType,String>();
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        
        //文本数据转换成图像矩阵数据
        BitMatrix bitMatrix=new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height,map);
        
        //输出
        System.out.println("width->"+bitMatrix.getWidth()+" height->"+bitMatrix.getHeight());
        File file=new File(outFilePath+fileName+".png");
        BufferedImage bufImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                bufImage.setRGB(x, y, bitMatrix.get(x, y)?blackRGB:whiteRGB);
            }
        }
        
        return ImageIO.write(bufImage, "png", file);
    }
    
    private String readQrCodeImage(File file) throws IOException, NotFoundException{
        //读图
        BufferedImage bufImage=ImageIO.read(file);
        
        //转成位图
        LuminanceSource source=new BufferedImageLuminanceSource(bufImage);
        BinaryBitmap bitmap=new BinaryBitmap(new HybridBinarizer(source));
        
        //解码配置
        Map<DecodeHintType,String> map=new HashMap<DecodeHintType,String>();
        map.put(DecodeHintType.CHARACTER_SET, "utf-8");
        String parseCode= new MultiFormatReader().decode(bitmap,map).getText();
        
        return parseCode;
    }
    
    public static void main(String[] args){
        QrCodeImageCopy qci=new QrCodeImageCopy();
        try {
            System.out.println(Color.GREEN.getRGB()+" black: "+blackRGB+" white: "+whiteRGB);
            for(int i=0;i<20;i++){
                String uuidStr=UUID.randomUUID().toString().substring(0, 15);
                qci.writeQrCodeImage(uuidStr, Integer.toString(i));
                String parseStr=qci.readQrCodeImage(new File(outFilePath+i+".png"));
                System.out.println(i+": uuidStr->"+uuidStr+", parseStr->"+parseStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}