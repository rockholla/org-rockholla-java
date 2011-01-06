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
 * @author Patrick Force <patrickforce@gmail.com>
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
