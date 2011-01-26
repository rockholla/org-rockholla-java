/**
 * 
 *	This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *	 
 */

package org.rockholla.file.excel;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.NumberFormat;

import org.rockholla.string.StringUtility;


import jxl.Cell;
import jxl.CellType;


/**
 * An excel spreadsheet cell value
 * 
 * @author rockholla
 *
 */
public class CellValue 
{
	
	/** the string representation */
	protected String _string;
	/** the default encoding to use in the creation of the string representation */
	protected String _encoding = "UTF-8";
	
	/**
	 * Constructor
	 * 
	 * @param cell					a jxl.Cell
	 * @param preserveFormatting	if true, will preserve any Excel numeric formatting
	 * @throws UnsupportedEncodingException
	 */
	public CellValue(Cell cell, Boolean preserveFormatting) throws UnsupportedEncodingException 
	{
		this._init(cell, preserveFormatting);
	}
	
	/**
	 * Constructor
	 * 
	 * @param cell	the jxl.Cell
	 * @throws UnsupportedEncodingException
	 */
	public CellValue(Cell cell) throws UnsupportedEncodingException 
	{
		this._init(cell, true);
	}
	
	/**
	 * Sets the string representation (called from Constructor)
	 * 
	 * @param cell					the jxl.Cell
	 * @param preserveFormatting	if true, will preserve any Excel numeric formatting
	 * @throws UnsupportedEncodingException
	 */
	protected void _init(Cell cell, Boolean preserveFormatting) throws UnsupportedEncodingException 
	{
		
		if(cell.getType().equals(CellType.NUMBER)) 
		{
			Double value = ((jxl.NumberCell) cell).getValue();
			NumberFormat numberFormat = ((jxl.NumberCell) cell).getNumberFormat();
			// The default rounding mode should be HALF_UP
			// it appears that jxl is determining it as HALF_EVEN
			numberFormat.setRoundingMode(RoundingMode.HALF_UP);
			if(preserveFormatting == true) 
			{
				numberFormat.setRoundingMode(RoundingMode.HALF_UP);
				if(Math.floor(value) == value) 
				{
					this._string = numberFormat.format(value.intValue());
				} 
				else 
				{
					this._string = numberFormat.format(value);
				}
			} 
			else 
			{
				numberFormat.setMaximumIntegerDigits(1000);
				numberFormat.setMaximumFractionDigits(1000);
				this._string = String.valueOf(numberFormat.format(value));
			}
		} 
		else 
		{
			String in = new String(cell.getContents().getBytes(), "Cp1252");
			this._string = new String(in.getBytes(this._encoding), this._encoding).trim();
			this._string = StringUtility.removeDuplicateWhitespace(this._string).toString();
		}
		
	}
	
	/**
	 * Gets the cell's string representation
	 * 
	 * @return	the string representation
	 */
	public String getString() 
	{
		return this._string;
	}
	
	public static void main(String[] args) throws Exception
	{
		
		/*Spreadsheet ss = new Spreadsheet("/Users/pbf2105/Eclipse/edu.columbia.blackrock.dataimporter/to-import/environmental-monitoring/educational/FT/FT_2008(1).xls");
		Cell cell = ss.getWorkbook().getSheet(0).getCell(4,7);*/
		Spreadsheet ss = new Spreadsheet("/Users/pbf2105/Eclipse/edu.columbia.blackrock.dataimporter/to-import/turtle/educational/Turtle_Cumulative.xls");
		Cell cell = ss.getWorkbook().getSheet(0).getCell(7,59);
		System.out.println(new CellValue(cell, false).getString());
		
	}
	
}
