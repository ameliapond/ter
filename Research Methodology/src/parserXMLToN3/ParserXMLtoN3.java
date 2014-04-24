package parserXMLToN3;

import org.jdom2.Document;
import org.jdom2.Element;
import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.filter.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Iterator;

public class ParserXMLtoN3 {

	 static Document document;
	 static Element racine;
	 
	 public static void afficheTimestamp()
	 {
		   List listData = racine.getChildren("data");
		   Iterator i = listData.iterator();
		   
		   while(i.hasNext())
		   {
		      Element courant = (Element)i.next();
		      Timestamp stamp = new Timestamp(System.currentTimeMillis());
		      Date date = new Date(stamp.getTime());
		      System.out.println(date);
		      System.out.println(courant.getChild("timestamp").getText());
		   }
	 }
	 public static void main(String[] args)
	   {
	      SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         document = sxb.build(new File("dataXML/dataXML.xml"));
	      }
	      catch(Exception e){}

	      racine = document.getRootElement();

	      afficheTimestamp();
	   }
}