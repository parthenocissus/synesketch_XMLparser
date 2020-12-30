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
 * A class representing a XML file in context of Synesketch XML Parser. File is stated 
 * by its path and boolean field that express whether the file contains Emoticons or 
 * not.
 * @author: Nikola Milikic
 * e-mail: nikola.milikic@gmail.com
 * @date: Jul 19, 2008
 * @version: 0.1
 */

public class LexiconFile {
	private String filePath;
	private boolean fileWithEmoticon;
	
	/** 
	 * Class constructor that sets class fields values.
	 */
	public LexiconFile(String filePath, boolean emoticon){
		this.filePath = filePath;
		this.fileWithEmoticon = emoticon;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileWithEmoticon
	 */
	public boolean isFileWithEmoticon() {
		return fileWithEmoticon;
	}

	/**
	 * @param fileWithEmoticon the fileWithEmoticon to set
	 */
	public void setFileWithEmoticon(boolean fileWithEmoticon) {
		this.fileWithEmoticon = fileWithEmoticon;
	}

}
