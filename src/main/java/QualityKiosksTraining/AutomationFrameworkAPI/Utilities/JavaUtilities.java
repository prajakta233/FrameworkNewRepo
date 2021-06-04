package QualityKiosksTraining.AutomationFrameworkAPI.Utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JavaUtilities 
{

	//public static List<String>EnvVars=new ArrayList<String>();
	public static HashMap<String,String>EnvVars=new HashMap<String,String>();
	
	public static void ReadEnvVars()
	{
		//String PathOfEnvFile="D:\\QualityKioskTraining\\TestCasesProject\\OrangeHRMTestCases\\EnvVars.txt";
		String PathOfEnvFile=System.getenv("Selenium_ScriptsNew");
		
		try 
		{
			FileReader FR=new FileReader(PathOfEnvFile);
			BufferedReader BR=new BufferedReader(FR);
			String Line=BR.readLine();
			while(Line!=null)
			{
				String []Pieces=Line.split(",");
				String VariableName=Pieces[0];
				String VariableValue=Pieces[1];
				///EnvVars.add(VariableValue);
				EnvVars.put(VariableName, VariableValue);
				Line=BR.readLine();
			}	
			
		} 
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
				System.out.println("Please check if Environment Variable file exists at : "+PathOfEnvFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem in reading Environment Variable file exists at : "+PathOfEnvFile);
		}
		
		
		
	}
	
	private static XSSFWorkbook ExcelWBook;
	private static XSSFSheet ExcelWSheet;
	
	public static String[][] ReadDataFromExcel(String DataFileName)
	{
		
		String DataFilePath=JavaUtilities.EnvVars.get("DataFilesLocation")+"\\"+DataFileName+".xlsx";
		
		String[][] Data = null;
		
		FileInputStream ExcelFile;
		
		
		try 
		{
			ExcelFile = new FileInputStream(DataFilePath);
			ExcelWBook=new XSSFWorkbook(ExcelFile);
			ExcelWSheet=ExcelWBook.getSheet("Sheet1");
			
			int startRow = 0;

			int startCol = 0;

			int totalRows = ExcelWSheet.getLastRowNum();
			int totalCols = 3;
			
			Data=new String[totalRows+1][totalCols];
			
			for(int i=startRow;i<=totalRows;i++)
			{
				for(int j=startCol;j<totalCols;j++)
				{
					Data[i][j]=getCellData(i,j);
					
				}	
				
			}	
			
		
		
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Please check if file at "+DataFilePath+" exists or not");
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			System.out.println("Problem in reading file "+DataFilePath);
		}
		
		return Data;
	}

	public static String getCellData(int Row,int Col)
	{
		//Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
		XSSFCell Cell =ExcelWSheet.getRow(Row).getCell(Col);
		String CellData=Cell.getStringCellValue();
		return CellData;
	}
}
