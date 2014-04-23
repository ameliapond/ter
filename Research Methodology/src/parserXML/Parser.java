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
		String Delimiter = "[,]";
		String secondDelimiter = "[:]";
		String thirdDelimiter = "\\s+";
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
		
		tokenFields = fields.split(Delimiter);
		
		try
		{
			br = new BufferedReader(new FileReader("data/data.txt"));
			while ((sCurrentLine = br.readLine()) != null)
			{
				tokenEntry = sCurrentLine.split(Delimiter);
				Element data = new Element("data");
				racine.addContent(data);
				Attribute ligne = new Attribute(tokenFields[0], tokenEntry[0]);
				data.setAttribute(ligne);
				int i = 1;
				// Variable to detect where we are in one of the four case following.
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
							String[] descriptionField = tokenEntry[i + 1]
									.split(thirdDelimiter);
							for (int j = 0; j < descriptionField.length; j++)
							{
								String[] descEntry = descriptionField[j]
										.split(secondDelimiter);
								Element subElem = new Element(descEntry[0]);
								// Add a new node element to the current node.
								element.addContent(subElem);
								// Set the text between the markups.
								subElem.setText(descEntry[1]);
							}
							break;
						case "Hygiene":
							descripOn = false;
							String mictEvac = null;
							Attribute activity2 = new Attribute("activity","Hygiene");
							// Set the attribute inside the markups
							element.setAttribute(activity2);
							String[] descriptionField2 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							System.out.println(sCurrentLine);
							if (descriptionField2[0].contains(":"))
							{
								for (int j = 0; j < descriptionField2.length; j++)
								{
									String[] descEntry = descriptionField2[j]
											.split(secondDelimiter);
									Element subElem = new Element(descEntry[0]);
									// Add a new node element to the current node.
									element.addContent(subElem);
									// Set the text between the markups.
									subElem.setText(descEntry[1]);			
								}
							}
							else
							{
								Element subElem = new Element("description");
								element.addContent(subElem);
								subElem.setText(descriptionField2[0]);
							}
							break;
						case "Medication":
							descripOn = false;
							Attribute activity3 = new Attribute("activity","Medication");
							element.setAttribute(activity3);

							String[] descriptionField3 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							for (int j = 0; j < descriptionField3.length; j++)
							{
									int numeroMedication = j+1;
									String[] descEntry = descriptionField3[j]
										.split(secondDelimiter);
									if (!descriptionField3[0].equals("NA"))
									{
										Attribute medic = new Attribute("id", descEntry[0]);
										Element subElem = new Element("medication" + numeroMedication);
										element.addContent(subElem);
										subElem.setAttribute(medic);
										subElem.setText(descEntry[1]);
									}
									else
									{
										Element subElem = new Element("Medication");
										element.addContent(subElem);
										subElem.setText("NA");
									}
							}
							break;
						case "Feeding":
							descripOn = false;
							Attribute activity4 = new Attribute("activity",
									"Feeding");
							element.setAttribute(activity4);
							String[] descriptionField4 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							for (int j = 0; j < descriptionField4.length; j++)
							{
								String[] descEntry = descriptionField4[j]
										.split(secondDelimiter);
								if (descEntry[0].equals("?"))
								{
									Element subElem = new Element("unknow");
									element.addContent(subElem);
									subElem.setText(descEntry[1]);
								}
								else
								{
									Element subElem = new Element(descEntry[0]);
									element.addContent(subElem);
									subElem.setText(descEntry[1]);
								}	
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