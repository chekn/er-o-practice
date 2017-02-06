package com.chekn.pdf.util;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import org.hibernate.type.ImageType;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

/**
 * Extracts images from a PDF file.
 */
public class PdfExtractImagesUtils {

	/** The new document to which we've added a border rectangle. */
	public static final String RESULT = "%s.%s";

	private static Logger logger = LoggerFactory.getLogger(PdfExtractImagesUtils.class);

	/**
	 * Parses a PDF and extracts all the images.
	 * 
	 * @param src
	 *            the source PDF
	 * @param dest
	 *            the resulting PDF
	 */
	public static void extractImages(String filename, final String outdir) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(filename);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		RenderListener listener = new RenderListener() {

			@Override
			public void beginTextBlock() {
			}

			@Override
			public void renderText(TextRenderInfo renderInfo) {
			}

			@Override
			public void endTextBlock() {
			}

			@Override
			public void renderImage(ImageRenderInfo renderInfo) {
				try {
					String filename;
					FileOutputStream os;
					PdfImageObject image = renderInfo.getImage();
					if (image == null)
						return;
					filename = String.format(RESULT, renderInfo.getRef().getNumber(), image.getFileType());
					os = new FileOutputStream(outdir + File.separator + filename);
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
	 * 生成图片
	 * 
	 * @return
	 */
	private static void pageCompoundImages(String fileName, String path) throws IOException {
		File file = new File(fileName);
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		FileChannel channel = raf.getChannel();
		java.nio.ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
		PDFFile pdffile = new PDFFile(buf);

		for (int i = 1; i <= pdffile.getNumPages(); i++) {
			PDFPage page = pdffile.getPage(i);
			java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, (int) page.getBBox().getWidth(),
					(int) page.getBBox().getHeight());
			java.awt.Image img = page.getImage(rect.width, rect.height, // width & //
					// height
					rect, // clip rect
					null, // null for the ImageObserver
					true, // fill background with white
					true // block until drawing is done
			);
			BufferedImage tag = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height, null);
			FileOutputStream out;
			//if (i > 1) {
				System.out.println(path + File.separator  + i + ".jpg");
				out = new FileOutputStream(path + File.separator  + i + ".jpg");
			/*} else {
				out = new FileOutputStream(path);
			}*/

			/**JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag); // JPEG*/
			//FileOutputStream out = new FileOutputStream(dstName);
			//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			//encoder.encode(dstImage);
			ImageIO.write(tag, /*"GIF"*/ "JPEG" /* format desired */ , out /* target */ );

			out.close();
		}

	}
	
	public static void pageCompoundImagesByIcepdf(String pdfPath, String outDir) throws Exception {
		Document document = new Document();  
		
        document.setFile(pdfPath);
        //determine the scale of the image   
        //and the direction of the image   
          
        float scale = 1f;  
        float rotation = 0f;  
  
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage)
            
            document.getPageImage(i,GraphicsRenderingHints.SCREEN,Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;

            File file = new File(outDir + File.separator + i + ".jpg");  
            ImageIO.write(rendImage, "JPEG", file); 

            image.flush();  
        }  
  
        document.dispose();
	}
	/*
	public static void pageCompoundImages2(String pdfPath) throws IOException {
		PdfReader pageReader = new PdfReader(pdfPath);
		for (int i = 1; i <= pageReader.getNumberOfPages(); i++) {  
			pageReader.get
		}
	}
	*/
	/**
	 * Main method.
	 * 
	 * @param args
	 *            no arguments needed
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		new PdfExtractImagesUtils().extractImages("c:/00318131_1.pdf", "c:/imgs");
		//new PdfExtractImagesUtils().pageCompoundImages("c:/00318131_1.pdf", "c:/imgs-2");
		
        //select the path here   
        //you can use the direct filepath or absolute path  
        //you can change it as you like  
        new PdfExtractImagesUtils().pageCompoundImagesByIcepdf("c:/00318131_1.pdf", "c:/imgs-2");
        
	}
}