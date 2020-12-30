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

/**
 * A class representing the attribute of the LexiconUnit instance.
 * 
 * @author: Nikola Milikic e-mail: nikola.milikic@gmail.com
 * @date: Jul 19, 2008
 * @version: 0.1
 */

public class Attribute<T> {
	private String name;
	private T value;

	public Attribute(String aName, T aValue) {
		this.name = aName;
		this.value = aValue;
	}

	/**
	 * Sole slass constructor.
	 */
	public Attribute() {
	}

	/**
	 * @return the name of the attribute
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value of the attribute
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * Tests if the instance of the Attribute class has its default value for
	 * the field 'value'. Default value for field 'value' is '-1' if 'value' has
	 * Integer or Double for type, or it is 'false' if the type of field 'value'
	 * is Boolean
	 * 
	 * @return boolean value
	 */
	public boolean hasDefaultValue() {
		String className = value.getClass().getName();
		if (className.endsWith("Boolean")) {
			if (Boolean.parseBoolean(value.toString()) == false)
				return true;
		} else if (className.endsWith("Integer")) {
			if (Integer.parseInt(value.toString()) == -1)
				return true;
		} else if (Double.parseDouble(value.toString()) == -1D)
			return true;

		return false;
	}

	/**
	 * Overriden equals() method. Two instances of the class Attribute are equal
	 * only if they have the same names
	 * 
	 * @return boolean value
	 */
	public boolean equals(Object o) {
		if (o instanceof Attribute) {
			Attribute attrib = (Attribute) o;
			if (this.name.equals(attrib.getName()))
				return true;
			return false;
		}
		return false;
	}

}
