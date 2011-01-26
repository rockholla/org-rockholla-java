/*
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

package org.rockholla.file.xls;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/**
 * Representation of a spreadsheet document, particularly for use in a data import.  Uses JExcel
 * library for interacting with the spreadsheet document structure.
 * 
 * @author rockholla
 * 
 * @see jxl.Workbook
 * @see jxl.WorkbookSettings
 * @see jxl.Sheet
 * @see jxl.Cell
 *
 */
public class Spreadsheet 
{

	/** the jxl.Workbook for this spreadsheet document object */
	protected Workbook _workbook;
	/** the jxl.WorkbookSettings for this spreadsheet document object */
	protected WorkbookSettings _workbookSettings;
	/** the name of the file for this spreadsheet document */
	protected String _fileName;
	/** the java.io.File object for this spreadsheet document */
	protected File _file;
	/** the active sheet in this spreadsheet document */
	protected Sheet _activeSheet;
	
	/**
	 * Constructor
	 * 
	 * @param filePath	the local path to the spreadsheet file
	 * @throws BiffException
	 * @throws IOException
	 */
	public Spreadsheet(String filePath) throws BiffException, IOException 
	{
		
		this._file = new File(filePath);
		this._workbookSettings = new WorkbookSettings();
		this._workbookSettings.setAutoFilterDisabled(true);
		this._workbookSettings.setCellValidationDisabled(true);
		this._workbook = Workbook.getWorkbook(this._file);
		this._fileName = this._file.getName();
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param file	the java file object for the spreadsheet file
	 * @throws BiffException
	 * @throws IOException
	 */
	public Spreadsheet(File file) throws BiffException, IOException 
	{
		
		this._file = file;
		this._workbookSettings = new WorkbookSettings();
		this._workbookSettings.setAutoFilterDisabled(true);
		this._workbookSettings.setCellValidationDisabled(true);
		this._workbook = Workbook.getWorkbook(this._file);
		this._fileName = this._file.getName();
		
	}
	
	/**
	 * for returning the jxl.Workbook
	 * 
	 * @return	the jxl.Workbook for this spreadsheet document
	 */
	public Workbook getWorkbook() 
	{
		return this._workbook;
	}
	
	/**
	 * finds the first cell in the workbook that is not empty
	 * 
	 * @return	the jxl.Cell
	 */
	public Cell getFirstPopulatedCell() 
	{
		return getFirstPopulatedCell(this.getWorkbook().getSheet(0));
	}
	
	/**
	 * finds the first cell that is not empty in a given sheet
	 * 
	 * @param sheet	jxl.Sheet to search
	 * @return	the jxl.Cell
	 */
	public static Cell getFirstPopulatedCell(Sheet sheet) 
	{
		
		for(int atRow = 0; atRow < sheet.getRows(); atRow++) 
		{
			for(int atColumn = 0; atColumn < sheet.getColumns(); atColumn++) 
			{
				if(!sheet.getCell(atColumn, atRow).getContents().trim().equals("")) 
				{
					return sheet.getCell(atColumn, atRow);
				}
			}
		}
		return null;
		
	}
	
	/**
	 * returns the name of the file for this spreadsheet document
	 * 
	 * @return	the file name
	 */
	public String getFileName() 
	{
		return this._fileName;
	}
	
	/**
	 * returns the Java file object for this spreadsheet document
	 * 
	 * @return	the java.io.File
	 */
	public File getFile() 
	{
		return this._file;
	}
	
	/**
	 * Used for cleaning up and closing jxl and file objects
	 * 
	 */
	public void close() 
	{
		
		this._workbook.close();
		this._workbook = null;
		this._file = null;
		
	}
	
	/**
	 * Sets the active sheet for this spreadsheet document object.  Certain shortcut operations
	 * can be performed if this object is aware of an active sheet.
	 * 
	 * @param sheet	the jxl.Sheet to set active
	 */
	public void setActiveSheet(Sheet sheet) 
	{
		
		for(Sheet workbookSheet : this._workbook.getSheets()) 
		{
			if(workbookSheet == sheet) 
			{
				this._activeSheet = sheet;
				return;
			}
		}
		this._activeSheet = null;
		
	}
	
	/**
	 * returns the currently-active sheet
	 * 	
	 * @return	the jxl.Sheet
	 */
	public Sheet getActiveSheet() 
	{
		return this._activeSheet;
	}
	
	public Boolean isActiveSheetEmpty()
	{
		for(int i = 0; i < this._activeSheet.getRows(); i++)
		{
			if(this.isActiveSheetEmptyRow(i) == false)
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * determines if a given row in the currently-active sheet is empty
	 * 
	 * @param row	the number of the row to check
	 * @return		whether or not the active sheet row is empty
	 */
	public Boolean isActiveSheetEmptyRow(int row) 
	{
		
		Cell[] cells = this._activeSheet.getRow(row);
		if(this._activeSheet.getRow(row).length == 0) 
		{
			return true;
		} 
		else 
		{
			Boolean foundValue = false;
			for(int i = 0; i < cells.length; i++) 
			{
				String contents = cells[i].getContents();
				if(contents != null && contents != "") 
				{
					foundValue = true;
				}
			}
			if(!foundValue) return true;
		}
		return false;
		
	}
	
	/**
	 * returns the last non-empty cell in the currently-active sheet
	 * 
	 * @return	the jxl.Cell
	 */
	public Cell getActiveSheetLastCell() 
	{
		return getSheetLastCell(this._activeSheet);
	}
	
	/**
	 * returns the last non-empty cell in a given sheet
	 * 
	 * @param sheet	the sheet to check
	 * @return		the jxl.Cell
	 */
	public static Cell getSheetLastCell(Sheet sheet) 
	{
		
		int lastRow = 0;
		int lastColumn = 0;
		Cell cell;
		int blankRowsInARow = 0;
		int blankColsInARow = 0;
		boolean allColsBlank;
		if(sheet.getRows() <= 0 || sheet.getColumns() <= 0) 
		{
			return null;
		}
		for(int atRow = 0; atRow < sheet.getRows(); atRow++) 
		{
			allColsBlank = true;
			blankColsInARow = 0;
			for(int atCol = 0; atCol < sheet.getColumns(); atCol++) 
			{
				cell = sheet.getCell(atCol, atRow);
				if(!cell.getContents().trim().equals("")) 
				{
					if(atCol > lastColumn) 
					{
						lastColumn = atCol;
					}
					allColsBlank = false;
					lastRow = atRow;
					blankColsInARow = 0;
				} 
				else 
				{
					blankColsInARow++;
				}
				cell = null;
				if(blankColsInARow > 1000) 
				{
					break;
				}
			}
			if(allColsBlank == true) 
			{
				blankRowsInARow++;
			} 
			else 
			{
				blankRowsInARow = 0;
			}
			if(blankRowsInARow > 1000) 
			{
				break;
			}
		}
		return sheet.getCell(lastColumn, lastRow);
		
	}
	
	/**
	 * Searches a given sheet for a particular value in a single cell
	 * 
	 * @param sheet	the sheet to search
	 * @param value	the value to search for
	 * @return		the jxl.Cell containing the value
	 * @throws UnsupportedEncodingException
	 */
	public static Cell findCellContaining(Sheet sheet, String value) throws UnsupportedEncodingException 
	{
		
		Cell lastCell = getSheetLastCell(sheet);
		for(int atRow = 0; atRow < lastCell.getRow(); atRow++) 
		{
			for(int atCol = 0; atCol < lastCell.getColumn(); atCol++) 
			{
				Cell cell = sheet.getCell(atCol, atRow);
				String cellValue = new CellValue(cell).getString();
				if(cellValue.indexOf(value) >= 0) 
				{
					return cell;
				}
				cell = null;
			}
		}
		
		return null;
		
	}
	
}
