package synesketchXMLparser;

/**
 * Synesketch XML Parser
 * Copyright (C) 2008  Nikola Milikic
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * This class represent the XML parser for Synesketch library. Synesketch XML
 * Parser is used with Synesketch to read from Synesketch Lexicon, represented
 * in XML format, and to add new words to overmentioned Lexicon. It also
 * provides methods for conversion from lexicons stored in .txt format to the
 * XML file suitable for use of the Synesketch library. Also, methods provided
 * by this class can extract elements from Synesketsh Lexicon that represent
 * only Emoticons, as well as words which have only generalWeight attribute
 * setted.
 * 
 * @author: Nikola Milikic e-mail: nikola.milikic@gmail.com
 * @date: Jul 19, 2008
 * @version: 0.1
 */

public class SynesketchParse {

	public Document doc;

	/**
	 * Add the list of instances of LexiconUnit to the XML document , both
	 * recived as arguments of the method.
	 * 
	 * @param lUnits
	 *            the list of LexiconUnit class instances that will be added to
	 *            the 'lexDoc' file
	 * @param lexDoc
	 *            the document in which the LexiconUnit-s will be added to
	 * @return instance of the Document class from dom4j library
	 */
	public Document addLexiconUnits(List<LexiconUnit> lUnits, Document lexDoc) {
		if (lexDoc == null) {
			lexDoc = DocumentHelper.createDocument();
			Element root = lexDoc.addElement("Lexicon");
		}

		for (int i = 0; i < lUnits.size(); i++) {
			lexDoc.getRootElement().add(lUnits.get(i).convertlUnitToElement());
		}

		return lexDoc;
	}

	/**
	 * Returns from the given LexiconFile instance the list of instances of
	 * class LexiconUnit.
	 * 
	 * @param lFile
	 *            file from which the LexiconUnits-s will be extracted
	 * @return list of LexiconUnit-s
	 */
	public List<LexiconUnit> getLexiconUnits(LexiconFile lFile) {
		List<LexiconUnit> lUnits = new LinkedList<LexiconUnit>();
		try {
			SAXReader xmlReader = new SAXReader();
			Document currDoc = xmlReader.read(lFile.getFilePath());

			Element root = currDoc.getRootElement();
			Iterator<Element> elementIterator = root.elementIterator();
			while (elementIterator.hasNext()) {
				Element element = elementIterator.next();
				lUnits.add(LexiconUnit.convertElementToLUnit(element));
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return lUnits;
	}

	/**
	 * Returns from the given LexiconFile instancethe list of instances of class
	 * LexiconUnit which represent emoticons.
	 * 
	 * @param lFile
	 *            file from which the LexiconUnits-s that are Emoticons will be
	 *            extracted
	 * @return list of LexiconUnit-s
	 */
	public List<LexiconUnit> getEmoticons(LexiconFile lFile) {
		List<LexiconUnit> lUnits = new LinkedList<LexiconUnit>();
		try {
			SAXReader xmlReader = new SAXReader();
			Document currDoc = xmlReader.read(lFile.getFilePath());

			Element root = currDoc.getRootElement();
			Iterator<Element> elementIterator = root.elementIterator();
			while (elementIterator.hasNext()) {
				Element element = elementIterator.next();
				LexiconUnit temp = LexiconUnit.convertElementToLUnit(element);

				Iterator<Attribute> it = temp.attributes.iterator();
				while (it.hasNext()) {
					Attribute a = it.next();
					if (a.getName().equals("isEmoticon")
							&& Boolean.parseBoolean(a.getValue().toString()) == true) {
						lUnits.add(temp);
						break;
					}

				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return lUnits;
	}

	/**
	 * Returns from the given LexiconFile instance the list of instances of
	 * class LexiconUnit which has only 'word' and 'generalWeight' attributes
	 * setted.
	 * 
	 * @param lFile
	 *            file from which the LexiconUnits-s with only 'word' and
	 *            'generalWeight' attributes setted will be extracted
	 * @return list of LexiconUnit-s
	 */
	public List<LexiconUnit> getLUnitsWithGWeight(LexiconFile lFile) {
		List<LexiconUnit> lUnits = new LinkedList<LexiconUnit>();
		try {
			SAXReader xmlReader = new SAXReader();
			Document currDoc = xmlReader.read(lFile.getFilePath());

			Element root = currDoc.getRootElement();
			Iterator elementIterator = root.elementIterator();
			while (elementIterator.hasNext()) {
				Element element = (Element) elementIterator.next();
				LexiconUnit temp = LexiconUnit.convertElementToLUnit(element);
				if (temp.isLUnitWithGWeight())
					lUnits.add(temp);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return lUnits;
	}

	/**
	 * Returns an instance of the Element class from the list of strings which
	 * represent the values of subelements of retuned instance.
	 * 
	 * @param attribs
	 *            the list of strings representing subelement of the returned
	 *            Element instance
	 * @return instance of the Element class from dom4j library
	 */
	public Element createElementfromString(String[] attribs) {
		Element newElem = DocumentHelper.createElement("LexiconUnit");
		Iterator<Attribute> it = (new LexiconUnit()).attributes.iterator();

		for (int i = 0; i < attribs.length; i++) {
			newElem.addElement(it.next().getName()).addText(attribs[i]);
		}
		return newElem;
	}

	/**
	 * Returns an XML document (instance of Document class) from the LexiconFile
	 * instance.
	 * 
	 * @param lFile
	 *            file from which the Synesketch Lexicon will be created
	 * @return instance of the Document class from dom4j library
	 */
	public Document convertTXTtoXML(LexiconFile lFile) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("Lexicon");

		try {
			BufferedReader in = new BufferedReader(new FileReader(lFile
					.getFilePath()));
			List<LexiconUnit> lUnits = new LinkedList<LexiconUnit>();

			String line = "";

			while ((line = in.readLine()) != null) {
				LexiconUnit lUnit = LexiconUnit.getLexiconUnitFromString(line);

				if (lFile.isFileWithEmoticon()) {
					lUnit.changeAttributeValue("isEmoticon", true);
				} else {
					lUnit.changeAttributeValue("isEmoticon", false);
				}

				lUnits.add(lUnit);
			}
			in.close();

			document = addLexiconUnits(lUnits, document);
		} catch (FileNotFoundException fnfe) {
		} catch (IOException ioe) {
		}

		return document;
	}

	/**
	 * Returns an XML document (instance of Document class) from the list of
	 * instances of the LexiconFile class.
	 * 
	 * @param lFiles
	 *            list of filea from which the Synesketch Lexicon will be
	 *            created
	 * @return instance of the Document class from dom4j library
	 */
	public Document convertTXTtoXML(LexiconFile[] lFiles) {
		Document document = DocumentHelper.createDocument();

		document = convertTXTtoXML(lFiles[0]);

		for (int i = 1; i < lFiles.length; i++) {
			if (document.getRootElement() != null) {
				Document temp = convertTXTtoXML(lFiles[i]);
				document.getRootElement().appendContent(temp.getRootElement());
			}
		}

		return document;
	}

	/**
	 * Serializes the field doc with given OutputStream and sets the encoding
	 * towards parameter 'aEncodingScheme'.
	 * 
	 * @param out
	 *            output stream which will be used for writing into file
	 * @param aEncodingScheme
	 *            encoding scheme that will be used (e.g "UTF-8").
	 * @throws Exception
	 */
	public void serializetoXML(OutputStream out, String aEncodingScheme)
			throws Exception {
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding(aEncodingScheme);
		XMLWriter writer = new XMLWriter(out, outformat);
		writer.write(doc);
		writer.flush();
	}
}
