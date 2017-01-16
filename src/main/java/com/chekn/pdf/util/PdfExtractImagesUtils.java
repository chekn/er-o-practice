package com.chekn.pdf.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
 
/**
 * Extracts images from a PDF file.
 */
public class PdfExtractImagesUtils {
 
    /** The new document to which we've added a border rectangle. */
    public static final String RESULT = "Img%s.%s";
 
    private Logger logger = LoggerFactory.getLogger(PdfExtractImagesUtils.class);
    private String imgStoreDir="";
    
    /**
     * Parses a PDF and extracts all the images.
     * @param src the source PDF
     * @param dest the resulting PDF
     */
    public void extractImages(String filename, String outdir)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(filename);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        RenderListener listener = new RenderListener(){

			@Override
			public void beginTextBlock() {}

			@Override
			public void renderText(TextRenderInfo renderInfo) {}

			@Override
			public void endTextBlock() {}

			@Override
			public void renderImage(ImageRenderInfo renderInfo) {
				try {
		            String filename;
		            FileOutputStream os;
		            PdfImageObject image = renderInfo.getImage();
		            if (image == null) return;
		            filename = String.format(RESULT, renderInfo.getRef().getNumber(), image.getFileType());
		            os = new FileOutputStream(outdir+ File.separator +filename);
		            os.write(image.getImageAsBytes());
		            os.flush();
		            os.close();
		        } catch (IOException e) {
		            logger.info("deal occur exception, exception:{}", e.getMessage());
		        }
			}
        	
        };
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        	logger.info("current deal No.{} page picture...", i);
            parser.processContent(i, listener);
        }
        reader.close();
    }
 
    /**
     * Main method.
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, DocumentException {
        new PdfExtractImagesUtils().extractImages("c:/pdf/装箱单123123.pdf","c:");
    }
}