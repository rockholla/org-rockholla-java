package org.rockholla.file.excel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.rockholla.file.FileUtility;



import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/**
 * Representation of a spreadsheet document, particularly for use in a data import.  Uses JExcel
 * library for interacting with the spreadsheet document structure.
 * 
 * @author Patrick Force
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
	
	public static void main(String[] args) throws Exception
	{
		
		String folder = "/Users/pbf2105/Eclipse/edu.columbia.blackrock.dataimporter/to-import/turtle/educational";
		File folderFile = new File(folder);
		System.out.println(folderFile.getParent());
		
		int total = 0;
		ArrayList<File> files = FileUtility.getFolderFiles(folder, FileUtility.OPTION.EXCLUDE_DIRECTORIES, FileUtility.OPTION.RECURSIVE);
		for(File file : files)
		{
			if(FileUtility.getFileExtension(file).equals("xls") && !FileUtility.folderExistsInPath("raw", file.getPath()))
			{
				Spreadsheet ss = new Spreadsheet(file);
				for(int atSheet = 0; atSheet < ss.getWorkbook().getSheets().length; atSheet++)
				{
					ss.setActiveSheet(ss.getWorkbook().getSheet(atSheet));
					// from row 2 to last row
					for(int atRow = 2; atRow < ss.getActiveSheet().getRows(); atRow++)
					{
						if(!ss.isActiveSheetEmptyRow(atRow))
						{
							total++;
							System.out.println(total);
							/*String row = "{";
							
							for(int atCol = 0; atCol < ss.getActiveSheet().getColumns(); atCol++)
							{
								row += " " + ss.getActiveSheet().getCell(atCol, atRow).getContents();
							}
							
							row += " }";
							System.out.println(row);*/
						}
					}
				}
			}
		}
		
		/*Spreadsheet ss = new Spreadsheet(new File("/Users/pbf2105/Eclipse/edu.columbia.blackrock.dataimporter/to-import/turtle/educational/Turtle_Cumulative.xls"));
		ss.setActiveSheet(ss.getWorkbook().getSheet(0));
		for(int atRow = 0; atRow < ss.getActiveSheet().getRows(); atRow++)
		{
			System.out.println(new CellValue(ss.getActiveSheet().getCell(7, atRow)).getString() + " --> " + ss.getActiveSheet().getCell(7, atRow).getType());
		}*/
		
		
	}
	
}
