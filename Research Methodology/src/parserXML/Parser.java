/** 
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    This class is used in the context of Ambiant Assisted Living.

    The document name data.txt located in the folder data is parsed and an output
    file named dataXML.xml is produced.
    In the following source code every activity is considered as an entry.
    Every line in the input file is considered as an activity.
    
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
	/**
	 * @Brief	Displays the result of the parsing 
	 */
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

	/**
	 * @Brief			Save the xml in the file give as parameter.
	 * @Param 	fichier	Name of the output file.	
	*/
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
		String thirdDelimiter = "\\s+";	// delimiter to parse the line currently read.
		String[] tokenFields;			// contains the fields read from the field.txt file.
		String[] tokenEntry;			// contains the value aaociated with the current field.
		BufferedReader br = null;
		String sCurrentLine;			// contains the line currently read from the input file.
		long unixSeconds;				// the second for the timestamp conversion.
		long inputReadLine = 0; 		// contains the number of read lines.
		long outputWroteChildren = 0; 	// contains the number of children wrote.
		long unknowActivityNumber = 0; 	// contains the number of unknow activities.

		
		try
		{
			// Get the fields's name in the file given as parameter.
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
		
		// Split the line read and convert it in a String table containing the fields in
		// every cell.
		tokenFields = fields.split(delimiter);
		
		System.out.println("XML file is generating...");

		try
		{
			br = new BufferedReader(new FileReader("data/data.txt"));
			while ((sCurrentLine = br.readLine()) != null)
			{
				inputReadLine++;
				// first slitting with the commas.
				tokenEntry = sCurrentLine.split(delimiter);
				
				Element data = new Element("entry");
				racine.addContent(data);

				Attribute ligne = new Attribute(tokenFields[0], "e"+tokenEntry[0]);
				data.setAttribute(ligne);
				
				int i = 1;					// The field's number (ex: id is the first field, 
											// timestamp is the second field, etc).
				boolean isNotDescription = true;	// Equals true if the field which is reading is not the fifth.			
				
				// while the fifth field (i.e. activity) is not reached.
				while (i < tokenFields.length && isNotDescription)
				{
					Element element = new Element(tokenFields[i]);
					data.addContent(element);

					// The following cases handle the whole value that can be taken by "activity".
					// The default case handle the unrecognized value of activity.
					switch (tokenEntry[i])
					{
						case "Outside":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Anomaly":
							isNotDescription = false;
							Attribute activityAnomaly = new Attribute("name","Anomaly");
							// Set the attribute inside the markups
							element.setAttribute(activityAnomaly);
							// Second splitting with the white spaces
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
							outputWroteChildren++;
							break;
						case "Medical_atention":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Social_Interactions":
							isNotDescription = false;
							Attribute activitySocialInteractions = new Attribute("name", "Social_Interactions");
							element.setAttribute(activitySocialInteractions);		
							Element subElemSocialInteractions = new Element("description");
							// Add a new node element to the current node.
							element.addContent(subElemSocialInteractions);
							// Set the text between the markups.
							subElemSocialInteractions.setText(tokenEntry[i+1]);
							outputWroteChildren++;
							break;
						case "Visits":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Other":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Inside":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Check_Over":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Recreation":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Mood":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Toilet":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Vitals":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Hygiene":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Medication":
							isNotDescription = false;
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
							outputWroteChildren++;
							break;
						case "Feeding":
							isNotDescription = false;
							Attribute activity4 = new Attribute("name",
									"Feeding");
							element.setAttribute(activity4);
							String[] descriptionField4 = tokenEntry[i + 1]
									.split(thirdDelimiter);
							for (int j = 0; j < descriptionField4.length; j++)
							{
								String[] descEntry = descriptionField4[j]
										.split(secondDelimiter);
		
								if (!descEntry[1].equals("NA"))
								{
									Element subElem = new Element(descEntry[0]);
									element.addContent(subElem);
									subElem.setText(descEntry[1]);
								}
								// if the name of the meal is unknow
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
							outputWroteChildren++;
							break;
						default:
						// if the current field is "caregiver" it is and equals to "NA"
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
						// if the field which is reading is timestamp -> conversion to readable date.
						else if (tokenFields[i].equals("timestamp") )
						{
							unixSeconds = Long.parseLong(tokenEntry[i]);
							Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
							String formatedDate = sdf.format(date);
							element.setText(formatedDate);
						}
						// If neither category has been recognized.
						// Field number % contains the activity.
						else if (i == 5)
						{
							System.out.println("Error, unknow activity: "+sCurrentLine);
						}
						// otherwise the value of the field is added.
						else
						{
							element.setText(tokenEntry[i]);
						}
						break;
					} // enf of switch
					i++;
				}
			}
			
			System.out.println(inputReadLine+" lines read.");
			System.out.println(outputWroteChildren+" children wrote.");
			System.out.println(unknowActivityNumber+" unknow ativity(ies).");
			System.out.println("dataXML.xml has been generated. \n");

		} catch (java.io.FileNotFoundException e)
		{
			e.printStackTrace();
		}
		//affiche();
		enregistre("dataXML/dataXML.xml");	
	}
}