/** TODO
 * supprimer les NA de caregiver, mood, feeding
 */
package parserXML;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

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
		String delimiter = "[,]";
		String secondDelimiter = "[:]";
		String thirdDelimiter = "\\s+";
		String[] tokenFields;
		String[] tokenEntry;
		BufferedReader br = null;
		String sCurrentLine;
		long unixSeconds;

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
		
		tokenFields = fields.split(delimiter);
		
		System.out.println("XML file is generating...");
		try
		{
			br = new BufferedReader(new FileReader("data/data.txt"));
			while ((sCurrentLine = br.readLine()) != null)
			{
				tokenEntry = sCurrentLine.split(delimiter);
				Element data = new Element("entry");
				racine.addContent(data);
				Attribute ligne = new Attribute(tokenFields[0], "e"+tokenEntry[0]);
				data.setAttribute(ligne);
				int i = 1;
				// Variable to detect if we reached the last field.
				boolean descripOn = true;
				while (i < tokenFields.length && descripOn)
				{
					Element element = new Element(tokenFields[i]);
					data.addContent(element);
					switch (tokenEntry[i])
					{
						case "Outside":
							descripOn = false;
							Attribute activityOutside = new Attribute("name", "Outside");
							element.setAttribute(activityOutside);		
							Element subElemOutside = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemOutside);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{
								subElemOutside.setText(tokenEntry[i+1]);
							}
							break;
						case "Anomaly":
							descripOn = false;
							Attribute activityAnomaly = new Attribute("name","Anomaly");
							// Set the attribute inside the markups
							element.setAttribute(activityAnomaly);
							String[] descriptionFieldAnomaly = tokenEntry[i + 1]
									.split(thirdDelimiter);
							if (descriptionFieldAnomaly[0].equals("NA"))
							{
								Element subElem = new Element("description");
								element.addContent(subElem);
							}
							else
							{
								for (String tokenFromDescriptionFieldAnomaly: descriptionFieldAnomaly)
								{
									Element subElem = new Element("description");
									element.addContent(subElem);
									subElem.setText(tokenFromDescriptionFieldAnomaly);
								}
							}
							break;
						case "Medical_atention":
							descripOn = false;
							Attribute activityMedicalAtention = new Attribute("name", "Medical_atention");
							element.setAttribute(activityMedicalAtention);		
							Element subElemMedicalAtention = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemMedicalAtention);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{
								subElemMedicalAtention.setText(tokenEntry[i+1]);
							}
							break;
						case "Social_Interactions":
							descripOn = false;
							Attribute activitySocialInteractions = new Attribute("name", "Social_Interactions");
							element.setAttribute(activitySocialInteractions);		
							Element subElemSocialInteractions = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemSocialInteractions);
							// Set the text between the markups.
							subElemSocialInteractions.setText(tokenEntry[i+1]);
							break;
						case "Visits":
							descripOn = false;
							Attribute activityVisits = new Attribute("name", "Visits");
							element.setAttribute(activityVisits);		
							Element subElemVisits = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemVisits);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{
								subElemVisits.setText(tokenEntry[i+1]);
							}
							break;
						case "Other":
							descripOn = false;
							Attribute activityOther = new Attribute("name", "Other");
							element.setAttribute(activityOther);		
							Element subElemOther = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemOther);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{
								subElemOther.setText(tokenEntry[i+1]);
							}
							break;
						case "Inside":
							descripOn = false;
							Attribute activityInside = new Attribute("name", "Inside");
							element.setAttribute(activityInside);		
							Element subElemInside = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemInside);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{	
								subElemInside.setText(tokenEntry[i+1]);
							}
							break;
						case "Check_Over":
							descripOn = false;
							Attribute activityCheckOver = new Attribute("name", "Check_Over");
							element.setAttribute(activityCheckOver);		
							Element subElemCheckOver = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemCheckOver);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{
								subElemCheckOver.setText(tokenEntry[i+1]);
							}
							break;
						case "Recreation":
							descripOn = false;
							Attribute activityRecreation = new Attribute("name", "Recreation");
							element.setAttribute(activityRecreation);		
							Element subElemRecreation = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemRecreation);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{
								subElemRecreation.setText(tokenEntry[i+1]);
							}
							break;
						case "Mood":
							descripOn = false;
							Attribute activityMood = new Attribute("name", "Mood");
							element.setAttribute(activityMood);		
							Element subElem1 = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElem1);
							// Set the text between the markups.
							if(!tokenEntry[i+1].equals("NA"))
							{
								subElem1.setText(tokenEntry[i+1]);
							}
							break;
						case "Toilet":
							descripOn = false;
							Attribute activity2 = new Attribute("name","Toilet");
							// Set the attribute inside the markups
							element.setAttribute(activity2);
							String[] descriptionField2 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							if (descriptionField2[0].equals("NA"))
							{
								Element subElem = new Element("description");
								element.addContent(subElem);
							}
							else if (descriptionField2[0].contains(":"))
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
								for (String tokenFromDescriptionField2: descriptionField2)
								{
									Element subElem = new Element("description");
									element.addContent(subElem);
									subElem.setText(tokenFromDescriptionField2);
								}
							}
							break;
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
								if (!descEntry[1].equals("NA"))
								{
									// Set the text between the markups.
									subElem.setText(descEntry[1]);
								}
							}
							break;
						case "Hygiene":
							descripOn = false;
							Attribute activity5 = new Attribute("name","Hygiene");
							// Set the attribute inside the markups
							element.setAttribute(activity5);
							String[] descriptionField5 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							
							if (descriptionField5[0].contains(":"))
							{
								for (int j = 0; j < descriptionField5.length; j++)
								{
									String[] descEntry = descriptionField5[j]
											.split(secondDelimiter);
									Element subElem = new Element(descEntry[0]);
									// Add a new node element to the current node.
									element.addContent(subElem);
									// Set the text between the markups.
									subElem.setText(descEntry[1]);			
								}
							}
							else if (!tokenEntry[i + 1].equals("NA"))
							{
								for (String tokenFromDescriptionField5: descriptionField5)
								{
									Element subElem = new Element("hygiene");
									element.addContent(subElem);
									subElem.setText(tokenFromDescriptionField5);
								}
							}
							else
							{
								Element subElem = new Element("hygiene");
								element.addContent(subElem);
							}
							break;
						case "Medication":
							descripOn = false;
							Attribute activity3 = new Attribute("name","Medication");
							element.setAttribute(activity3);

							String[] descriptionField3 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							if (descriptionField3[0].equals("NA"))
							{
								Element subElem = new Element("medication");
								element.addContent(subElem);
							}
							else
							{
								for (int j = 0; j < descriptionField3.length; j++)
								{
										String[] descEntry = descriptionField3[j]
											.split(secondDelimiter);
										Attribute medic = new Attribute("name", descEntry[0]);
										Element subElem = new Element("medication");
										element.addContent(subElem);
										subElem.setAttribute(medic);
										
										if (!descEntry[1].equals("NA"))
										{
											subElem.setText(descEntry[1]);
										}
								}
							}
							break;
						case "Feeding":
							descripOn = false;
							Attribute activity4 = new Attribute("name",
									"Feeding");
							element.setAttribute(activity4);
							String[] descriptionField4 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							for (int j = 0; j < descriptionField4.length; j++)
							{
								String[] descEntry = descriptionField4[j]
										.split(secondDelimiter);
								System.out.println(sCurrentLine);
								if (!descEntry[1].equals("NA"))
								{
									Element subElem = new Element(descEntry[0]);
									element.addContent(subElem);
									subElem.setText(descEntry[1]);
								}
								else if(descEntry[0].equals("?"))
								{
									Element subElem = new Element("feeding");
									element.addContent(subElem);
									subElem.setText(null);
								}
								else
								{
									Element subElem = new Element(descEntry[0]);
									element.addContent(subElem);
									subElem.setText(null);
								}	
							}
							break;
						default:
						// if the current field is "caregiver" and equals to "NA"
						if (tokenFields[i].equals("caregiver"))
						{
							//System.out.println("caregiver: "+tokenEntry[i]);
							if (tokenEntry[i].equals("NA"))
							{
								element.setText(null);	
							}
							else
							{
								element.setText(tokenEntry[i]);
							}
						}
						// if the field which is reading is timestamp -> conversion to readable date,
						else if (tokenFields[i].equals("timestamp"))
						{
							unixSeconds = Long.parseLong(tokenEntry[i]);
							Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
							String formatedDate = sdf.format(date);
							element.setText(formatedDate);
						}
						// otherwise the value of the field is added.
						else
						{
							element.setText(tokenEntry[i]);
						}
					}
					i++;
				}
			}
			
			System.out.println("dataXML.xml has been generated.");
			
		} catch (java.io.FileNotFoundException e)
		{
			e.printStackTrace();
		}
		//affiche();
		enregistre("dataXML/dataXML.xml");	
	}
}