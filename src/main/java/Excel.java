import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.*;
import java.util.HashMap;

public class Excel {

    private int oemColumn;
    private int makerColumn;
    private int priceColumn;
    private int plusColumn;

    private HSSFWorkbook myExcelBook;
    private HSSFSheet myExcelSheet;
    private HSSFCellStyle[] style = new HSSFCellStyle[3];
    private FileInputStream file;
    private HSSFCellStyle style1, style2, style3;

    //загрузка документа и создания экземпляров
    public void init() {
        try {
            file = new FileInputStream("C:\\Users\\User\\Desktop\\Прайсы\\new.xls");
            myExcelBook = new HSSFWorkbook(file);
            myExcelSheet = myExcelBook.getSheet("Лист1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //перезаписываем и закрваем файл
    public void saveAndClose() {
        try {
            myExcelBook.write(new FileOutputStream(new File("C:\\Users\\User\\Desktop\\Прайсы\\new.xls")));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // создаём ячейки в указанном столбце начиная с определённого ряда
    public void createCells(int rowStart, int column) {
        int rows = myExcelSheet.getLastRowNum();
        createStyles();
        for (int i = rowStart; i < rows; i++) {
            HSSFCell cell = myExcelSheet.getRow(i).createCell(column);
            cell.setCellStyle(style1);
        }
    }
    //устанавливаем стили ячейкам
    public void setStyles(int rowStart, int column) {
        int rows = myExcelSheet.getLastRowNum();
        createStyles();
        for (int i = rowStart; i < rows; i++) {
            HSSFCell cell = myExcelSheet.getRow(i).getCell(column);
            cell.setCellStyle(style1);
        }
    }
    // создаём стили для ячеек
    public void createStyles() {
        //Дороже конкурента
        style1 = myExcelBook.createCellStyle();
        style1.setFillBackgroundColor(HSSFColor.RED.index);
        style1.setAlignment(HSSFCellStyle.ALIGN_FILL);
        style1.setFillForegroundColor(HSSFColor.RED.index);
        style1.setFillPattern( HSSFCellStyle.ALIGN_FILL);
        //Примерно также как у конкурента или равно
        style2 = myExcelBook.createCellStyle();
        style2.setFillBackgroundColor(HSSFColor.YELLOW.index);
        style2.setAlignment(HSSFCellStyle.ALIGN_FILL);
        //меньше чем у конкурента
        style3 = myExcelBook.createCellStyle();
        style3.setFillBackgroundColor(HSSFColor.GREEN.index);
        style3.setAlignment(HSSFCellStyle.ALIGN_FILL);
    }
    //читаем информацию с листа
    public void read(int rowStart, int rowEnd, int column, int column2, int column3) {
        System.out.println(myExcelSheet.getLastRowNum());
        for (int i = rowStart; i < rowEnd; i++) {
            //HSSFCell cell = hssfSheet.getRow(i).getCell(column);
            HSSFRow row = myExcelSheet.getRow(i);

                String name1 = row.getCell(column).getStringCellValue();
                System.out.print("MAKER2: "+name1);

                String name2 = row.getCell(column2).getStringCellValue();
                System.out.print("    oem2 : " + name2);

                double number = row.getCell(column3).getNumericCellValue();
                System.out.print("    price1: " + number);

            System.out.println("\n");
        }
    }

    public boolean readPlus(int rowIndex){
        HSSFRow row = myExcelSheet.getRow(rowIndex);
        if(row.getCell(plusColumn).getStringCellValue().equals("+")){
            return true;
        }
        else {
            return false;
        }
    }

    public String readMaker(int rowIndex){
        HSSFRow row = myExcelSheet.getRow(rowIndex);

            String name = row.getCell(makerColumn).getStringCellValue();
            System.out.print("MAKER: "+name);
            return name;
    }

    public String readOEM(int rowIndex){
        HSSFRow row = myExcelSheet.getRow(rowIndex);
            String name = row.getCell(oemColumn).getStringCellValue();
            System.out.print("OEM: "+name);
            return name;
    }
    public double readPrice(int rowIndex){
        HSSFRow row = myExcelSheet.getRow(rowIndex);
            double price=row.getCell(priceColumn).getNumericCellValue();
            System.out.print("Price: "+price);
            return price;
    }



    //геттеры и сеттеры
    public void setOemColumn(int oemColumn) {
        this.oemColumn = oemColumn;
    }

    public void setMakerColumn(int makerColumn) {
        this.makerColumn = makerColumn;
    }

    public void setPriceColumn(int priceColumn) {
        this.priceColumn = priceColumn;
    }

    public int getOemColumn() {
        return oemColumn;
    }

    public int getMakerColumn() {
        return makerColumn;
    }

    public int getPriceColumn() {
        return priceColumn;
    }

    public HSSFWorkbook getMyExcelBook() {
        return myExcelBook;
    }

    public HSSFSheet getMyExcelSheet() {
        return myExcelSheet;
    }

    public HSSFCellStyle[] getStyle() {
        return style;
    }


    public FileInputStream getFile() {
        return file;
    }

    public void setPlusColumn(int plusColumn) {
        this.plusColumn = plusColumn;
    }
}
//тестовы класс
class Main1 {
    public static void main(String[] args) {
        Excel excel = new Excel();
        excel.init();
        excel.setMakerColumn(1);
        excel.setOemColumn(2);
        excel.setPriceColumn(10);
        excel.createStyles();
        excel.createCells(7,11);
        excel.read( 7, 2216, excel.getMakerColumn(), excel.getOemColumn(), excel.getPriceColumn());
        excel.saveAndClose();
    }


}
