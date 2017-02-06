package com.sun.pdfview.font;

import com.sun.pdfview.PDFObject;
import java.io.IOException;
import java.util.HashMap;

public abstract class PDFCMap
{
  private static HashMap<String, PDFCMap> cache;
  
  public static PDFCMap getCMap(PDFObject map)
    throws IOException
  {
    if (map.getType() == 4) {
      return getCMap(map.getStringValue());
    }
    if (map.getType() == 7) {
      return parseCMap(map);
    }
    throw new IOException("CMap type not Name or Stream!");
  }
  
  public static PDFCMap getCMap(String mapName)
    throws IOException
  {
    if (cache == null) {
      populateCache();
    }
    
    if (!cache.containsKey(mapName)) {
      throw new IOException("Unknown CMap: " + mapName);
    }
    
    return (PDFCMap)cache.get(mapName); //(PDFCMap)cache.get(mapName);
  }
  
  protected static void populateCache()
  {
    cache = new HashMap();
    
    cache.put("Identity-H", new PDFCMap()
    {
      public char map(char src)
      {
    	  //System.out.println(src);
        return src;
      }
    });
  }
  
  protected static PDFCMap parseCMap(PDFObject map)
    throws IOException
  {
    throw new IOException("Parsing CMap Files Unsupported!");
  }
  
  public abstract char map(char paramChar);
  
  public int getFontID(char src)
  {
    return 0;
  }
}
