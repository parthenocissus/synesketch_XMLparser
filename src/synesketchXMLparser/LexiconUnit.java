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

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * A class representing an unit of Synesketch Lexicon. This class provides
 * methods for determing weather instance contains only 'word' and
 * 'generalWeight' fields or not, methods for conversion from Element class from
 * dom4j library to the LexiconUnit class, and also method for creating instance
 * of the LexiconUnit class from given String parameter that contains values for
 * predefined list of attributes separated with space.
 * 
 * @author: Nikola Milikic e-mail: nikola.milikic@gmail.com
 * @date: Jul 19, 2008
 * @version: 0.1
 */

public class LexiconUnit {

	Set<Attribute> attributes = new LinkedHashSet<Attribute>();

	/**
	 * Class constructor that sets default values of the class fields.
	 */
	public LexiconUnit() {
		attributes.add(new Attribute<String>("word", ""));
		attributes.add(new Attribute<Double>("generalWeight", -1D));
		attributes.add(new Attribute<Double>("happinessWeight", -1D));
		attributes.add(new Attribute<Double>("sadnessWeight", -1D));
		attributes.add(new Attribute<Double>("angerWeight", -1D));
		attributes.add(new Attribute<Double>("fearWeight", -1D));
		attributes.add(new Attribute<Double>("disgustWeight", -1D));
		attributes.add(new Attribute<Double>("surpriseWeight", -1D));
		attributes.add(new Attribute<Integer>("valence", -1));
		attributes.add(new Attribute<Boolean>("isEmoticon", false));
	}

	/**
	 * Returns the Attribute with the given name.
	 * 
	 * @param aName
	 *            the name of the wanted attribute
	 * @return instance of class Attribute from dom4j library
	 */
	public Attribute getAttribute(String aName) {
		Attribute attrib = new Attribute();
		Iterator<Attribute> it = attributes.iterator();
		while (it.hasNext()) {
			Attribute a = it.next();
			if (a.getName().equals(aName)) {
				attrib = a;
				break;
			}
		}
		return attrib;
	}

	/**
	 * Removes attribute with givven name from the list of attributes. Return
	 * true if removing was complited successfully or false otherwise.
	 * 
	 * @param aName
	 *            the name of the attribute wanted to be removed
	 * @return bolean value
	 */
	public boolean removeAttribute(String aName) {
		Attribute a = getAttribute(aName);
		if (a != null)
			return attributes.remove(a);
		return false;
	}

	/**
	 * Changes the value of the attribute named 'aName' for the new value of
	 * parameter 'aValue'.
	 * 
	 * @param aName
	 *            the name of the attribute wanted to be changed
	 * @param aValue
	 *            the value which the current value of the attribute will be
	 *            changed for
	 */
	public <T> void changeAttributeValue(String aName, T aValue) {
		getAttribute(aName).setValue(aValue);
	}

	/**
	 * Tests weather Lexicon Unit has only attributes 'word' and 'generalWeight'
	 * setted.
	 * 
	 * @return boolean value
	 */
	public boolean isLUnitWithGWeight() {
		Iterator<Attribute> it = attributes.iterator();
		while (it.hasNext()) {
			Attribute a = it.next();
			if (!(a.getName().equals("word") || a.getName().equals(
					"generalWeight")))
				if (!(a.hasDefaultValue()))
					return false;
		}
		return true;
	}

	/**
	 * Converts instance of the Element class, givven as method's parameter, to
	 * the coresponding instance of the LexiconUnit class.
	 * 
	 * @param element
	 *            instance of the Element class that will be converted to
	 *            LexiconUnit instance
	 * @return instance of the LexiconUnit class
	 */
	public static LexiconUnit convertElementToLUnit(Element element) {
		LexiconUnit lUnit = new LexiconUnit();
		Iterator<Element> elementIterator = element.elementIterator();

		while (elementIterator.hasNext()) {
			Element attrib = elementIterator.next();
			lUnit.changeAttributeValue(attrib.getName(), attrib.getText());
		}

		return lUnit;
	}

	/**
	 * Converts instance of the LexiconUnit class to the coresponding instance
	 * of the Element class.
	 * 
	 * @return instance of the Element class
	 */
	public Element convertlUnitToElement() {
		Element newElem = DocumentHelper.createElement("LexiconUnit");

		Iterator<Attribute> i = attributes.iterator();
		while (i.hasNext()) {
			Attribute a = i.next();
			if (!(a.getValue().toString().equals("-1")))
				newElem.addElement(a.getName())
						.addText(a.getValue().toString());
		}

		return newElem;
	}

	/**
	 * Returns instance of the LexiconUnit class from the givven String 'line'
	 * which represents values of the attributes of LexiconUnit separated by
	 * space(" ").
	 * 
	 * @param line
	 *            the string which contains values of the attributes
	 * @return instance of LexiconUnit class
	 */
	public static LexiconUnit getLexiconUnitFromString(String line) {
		LexiconUnit lUnit = new LexiconUnit();

		String[] attributesString = line.trim().split(" ");

		if (attributesString.length > 2) {
			lUnit.attributes.clear();
			lUnit.attributes.add(new Attribute<String>("word",
					attributesString[0]));
			lUnit.attributes.add(new Attribute<Double>("generalWeight", Double
					.parseDouble(attributesString[1])));
			lUnit.attributes.add(new Attribute<Double>("happinessWeight",
					Double.parseDouble(attributesString[2])));
			lUnit.attributes.add(new Attribute<Double>("sadnessWeight", Double
					.parseDouble(attributesString[3])));
			lUnit.attributes.add(new Attribute<Double>("angerWeight", Double
					.parseDouble(attributesString[4])));
			lUnit.attributes.add(new Attribute<Double>("fearWeight", Double
					.parseDouble(attributesString[5])));
			lUnit.attributes.add(new Attribute<Double>("disgustWeight", Double
					.parseDouble(attributesString[6])));
			lUnit.attributes.add(new Attribute<Double>("surpriseWeight", Double
					.parseDouble(attributesString[7])));
			lUnit.attributes.add(new Attribute<Integer>("valence", -1));
			lUnit.attributes.add(new Attribute<Boolean>("isEmoticon", false));

		} else {
			lUnit.removeAttribute("word");
			lUnit.attributes.add(new Attribute<String>("word",
					attributesString[0]));
			lUnit.removeAttribute("generalWeight");
			lUnit.attributes.add(new Attribute<Double>("generalWeight", Double
					.parseDouble(attributesString[1])));
		}

		return lUnit;
	}
}