package com.chekn.pdf.util;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
 
/**
 * Extracts images from a PDF file.
 */
public class PdfExtractImagesUtils {
 
    /** The new document to which we've added a border rectangle. */
    public static final String RESULT = "Img%s.%s";
 
    /**
     * Parses a PDF and extracts all the images.
     * @param src the source PDF
     * @param dest the resulting PDF
     */
    public void extractImages(String filename)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(filename);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        MyImageRenderListener listener = new MyImageRenderListener(RESULT);
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
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
        //new ImageTypes().createPdf(ImageTypes.RESULT);
        new PdfExtractImagesUtils().extractImages("c:/in.pdf");
    }
}