package parserXML;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Parser {

	static Element racine = new Element("Element");
	static org.jdom2.Document document = new Document(racine);
	static void affiche()
	{
		try
		{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		} 
		catch (java.io.IOException e)
		{
		}
	}

	static void enregistre(String fichier)
	{
		try
		{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(fichier));
		} catch (java.io.IOException e)
		{
		}
	}

	public static void main(String[] args) throws IOException
	{
		String fields = null;
		String delim = "[,]";
		String secondDelim = "[:]";
		String thirdDelim = "\\s+";
		String[] tokenFields;
		String[] tokenEntry;
		BufferedReader br = null;
		String sCurrentLine;

		// Get the fields's name in the file given as parameter.
		try
		{
			br = new BufferedReader(new FileReader("data/fields.txt"));
			fields = br.readLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (br != null)
					br.close();
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		
		tokenFields = fields.split(delim);
		
		try
		{
			br = new BufferedReader(new FileReader("data/data2.txt"));
			while ((sCurrentLine = br.readLine()) != null)
			{
				tokenEntry = sCurrentLine.split(delim);
				Element data = new Element("data");
				racine.addContent(data);
				Attribute ligne = new Attribute(tokenFields[0], tokenEntry[0]);
				data.setAttribute(ligne);
				int i = 1;
				boolean descripOn = true;
				while (i < tokenFields.length && descripOn)
				{

					Element element = new Element(tokenFields[i]);
					data.addContent(element);
					switch (tokenEntry[i])
					{
						case "Vitals":
							descripOn = false;
							Attribute activity = new Attribute("activity", "Vitals");
							element.setAttribute(activity);
							String[] descfield = tokenEntry[i + 1]
									.split(thirdDelim);
							for (int j = 0; j < descfield.length; j++)
							{
	
								String[] descEntry = descfield[j]
										.split(secondDelim);
	
								Element subElem = new Element(descEntry[0]);
								// Add a new node element to the current node.
								element.addContent(subElem);
								// Set the text between the markups.
								subElem.setText(descEntry[1]);
							}
							break;
						case "Hygiene":
							descripOn = false;
							Attribute activity2 = new Attribute("activity",
									"Hygiene");
							// Set the attribute inside the markups
							element.setAttribute(activity2);
							String[] descfield2 = tokenEntry[i + 1]
									.split(thirdDelim);
							for (int j = 0; j < descfield2.length; j++)
							{
								if (descfield2[j].contains(":"))
								{
									Element node = new Element("Temp");
									String[] descEntry = descfield2[j]
											.split(secondDelim);
									if (descEntry[0].equals("Mict")
											|| descEntry[0].equals("Evac"))
									{
										node = new Element(descEntry[0]);
										element.addContent(node);
										Attribute nodeStatus = new Attribute(
												"status", descEntry[1]);
										// Set the attribute inside the markups
										node.setAttribute(nodeStatus);
									} 
									else
									{
										Element subElem = new Element(descEntry[0]);
										node.addContent(subElem);
										subElem.setText(descEntry[1]);
									}
								} 
								else
								{
									Element subElem = new Element("hygiene" + j);
									element.addContent(subElem);
									subElem.setText(descfield2[j]);
								}
							}
							break;
						case "Medication":
							descripOn = false;
							Attribute activity3 = new Attribute("activity",
									"Medication");
							element.setAttribute(activity3);
							String[] descfield3 = tokenEntry[i + 1]
									.split(thirdDelim);
							for (int j = 0; j < descfield3.length; j++)
							{
								String[] descEntry = descfield3[j]
										.split(secondDelim);
								Attribute medic = new Attribute("id", descEntry[0]);
								Element subElem = new Element("Medication" + j + 1);
								element.addContent(subElem);
								subElem.setAttribute(medic);
								subElem.setText(descEntry[1]);
							}
							break;
						case "Feeding":
							descripOn = false;
							Attribute activity4 = new Attribute("activity",
									"Feeding");
							element.setAttribute(activity4);
							String[] descfield4 = tokenEntry[i + 1]
									.split(thirdDelim);
							for (int j = 0; j < descfield4.length; j++)
							{
								String[] descEntry = descfield4[j]
										.split(secondDelim);
								Element subElem = new Element(descEntry[0]);
								element.addContent(subElem);
								subElem.setText(descEntry[1]);
							}
							break;
						default:
						element.setText(tokenEntry[i]);
					}
					i++;
				}
			}
		} catch (java.io.FileNotFoundException e)
		{
			e.printStackTrace();
		}
		//affiche();
		enregistre("dataXML/dataXML.xml");
		System.out.println("dataXML.xml has been generated.");
	}
}