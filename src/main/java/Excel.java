import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Excel {

    private int oemColumn;
    private int makerColumn;
    private int priceColumn;

    private HSSFWorkbook myExcelBook;
    private HSSFSheet myExcelSheet;
    private HSSFCellStyle[] style=new HSSFCellStyle[3];


    public static void main(String[] args) {
        Excel excel = new Excel();
        excel.init();
    }

    public void init() {
        try {
            FileInputStream file = new FileInputStream("C:\\Users\\shark\\Desktop\\ПРАЙС И ТЕРМАЛ.xls");
            myExcelBook = new HSSFWorkbook(file);
            myExcelSheet = myExcelBook.getSheet("Лист1");
            HSSFRow row = myExcelSheet.getRow(6);
            System.out.println(row.getCell(1));

            saveAndClose(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //перезаписываем и закрваем файл
    public void saveAndClose(FileInputStream file){
        try {
           // myExcelBook.write(new FileOutputStream(new File("C:\\Users\\shark\\Desktop\\ПРАЙС И ТЕРМАЛ.xls")));
            myExcelBook.write(new FileOutputStream(new File("..\\ПРАЙС И ТЕРМАЛ.xls")));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // создаём ячейки в указанном столбце начиная с определённого ряда
    public void createCells(HSSFSheet hssfSheet, int rowStart, int column) {
        int rows = hssfSheet.getPhysicalNumberOfRows();
        for (int i = rowStart; i < rows; i++) {
            HSSFCell cell = hssfSheet.getRow(i).createCell(column);
        }
    }
    // создаём стили для ячеек
    public void createStyles(){
        //Дороже конкурента
        HSSFCellStyle style1 = myExcelBook.createCellStyle();
        style1.setFillBackgroundColor(HSSFColor.RED.index);
        style1.setAlignment(XSSFCellStyle.ALIGN_FILL);
        //Примерно также как у конкурента или равно
        HSSFCellStyle style2 = myExcelBook.createCellStyle();
        style1.setFillBackgroundColor(HSSFColor.YELLOW.index);
        style1.setAlignment(XSSFCellStyle.ALIGN_FILL);
        //меньше чем у конкурента
        HSSFCellStyle style3 = myExcelBook.createCellStyle();
        style1.setFillBackgroundColor(HSSFColor.GREEN.index);
        style1.setAlignment(XSSFCellStyle.ALIGN_FILL);

        style[0]=style1;
        style[1]=style2;
        style[2]=style3;

    }

    public void read(HSSFSheet hssfSheet, int rowStart, int column, HashMap<String,String> hashMap, int column2){
        for(int i=rowStart;i<=hssfSheet.getPhysicalNumberOfRows();i++){
            //HSSFCell cell = hssfSheet.getRow(i).getCell(column);
            HSSFRow row = myExcelSheet.getRow(i);
            if(row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                String name = row.getCell(0).getStringCellValue();


            }
        }
    }

}
