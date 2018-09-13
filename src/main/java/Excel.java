import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.*;
import java.util.HashMap;

public class Excel {

    private int oemColumn;
    private int makerColumn;
    private int priceColumn;
    private int plusColumn;

    private int ourPriceColumn;
    private int firstPriceColumn;
    private int ourPositionColumn;
    private int absPriceColumn;
    private int firstCompanyColumn;

    private HSSFWorkbook myExcelBook;
    private HSSFSheet myExcelSheet;
    private HSSFCellStyle[] style = new HSSFCellStyle[3];
    private FileInputStream file;
    private HSSFCellStyle style1, style2, style3, style4;

    //загрузка документа и создания экземпляров
    public void init() {
        try {
            file = new FileInputStream("C:\\Users\\User\\Desktop\\Прайсы\\new2.xls");
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
            myExcelBook.write(new FileOutputStream(new File("C:\\Users\\User\\Desktop\\Прайсы\\new2.xls")));
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
            //cell.setCellStyle(style1);
        }
    }
    //устанавливаем стили ячейкам
    public void setStyles(int rowStart,int rowEnd, int ...arg) {
        for (int i = rowStart; i < rowEnd; i++) {
//            for (int x:arg) {
//                HSSFCell cell = myExcelSheet.getRow(i).getCell(arg[x]);
//                cell.setCellStyle(styleAlign);
//            }
            for (int y=arg[0];y<arg.length;y++){
                HSSFCell cell = myExcelSheet.getRow(i).getCell(y);
                cell.getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
            }
        }
    }





    // создаём стили для ячеек
    public void createStyles() {
        //Дороже конкурента
        style1 = myExcelBook.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setFillForegroundColor(HSSFColor.RED.index);
        style1.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND);
        //Примерно также как у конкурента или равно
        style2 = myExcelBook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.YELLOW.index);
       style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND);
        //меньше чем у конкурента
        style3 = myExcelBook.createCellStyle();
        style3.setFillForegroundColor(HSSFColor.GREEN.index);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND);
        //нет на ззапе либо что-то не так
        style4 = myExcelBook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.BLACK.index);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style4.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND);
    }

    //читаем информацию с листа
    public void read(int rowStart, int rowEnd, int column, int column2, int column3) {
        System.out.println(myExcelSheet.getLastRowNum());
        for (int i = rowStart; i < rowEnd; i++) {
            //HSSFCell cell = hssfSheet.getRow(i).getCell(column);
            HSSFRow row = myExcelSheet.getRow(i);

                String name1 = row.getCell(column).getStringCellValue();
                System.out.print("      maker : "+name1);

                String name2 = row.getCell(column2).getStringCellValue();
                System.out.print("      oem2 : " + name2);

                double number = row.getCell(column3).getNumericCellValue();
                System.out.print("      price : " + number);

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
            System.out.print("  MAKER: "+name);
            return name;
    }

    public String readOEM(int rowIndex){
        HSSFRow row = myExcelSheet.getRow(rowIndex);
            String name = row.getCell(oemColumn).getStringCellValue();
            if(name.contains("/")){
                int count=name.indexOf("/");
                name=name.substring(0,count);

            }
            System.out.print("OEM: "+name);
            return name;
    }
    public double readPrice(int rowIndex){
        HSSFRow row = myExcelSheet.getRow(rowIndex);
            double price=row.getCell(priceColumn).getNumericCellValue();
            System.out.print("  Price: "+price);
            return price;
    }

    public void writeToExcel(int rowIndex, double ourPrice,double[] minPrices,String firstCompany,boolean isonzzap){
        System.out.println("Наичнаю запись...");
        System.out.println("isonzzap "+isonzzap);

        HSSFRow row = myExcelSheet.getRow(rowIndex);
        row.getCell(ourPriceColumn).setCellValue(ourPrice);
        row.getCell(firstPriceColumn).setCellValue(minPrices[0]);
        System.out.println("ourprice: "+ ourPrice);
        System.out.println("minrprice: "+ minPrices[0]);

        double abs;

        if(ourPrice>minPrices[0]){
            abs=ourPrice-minPrices[0];
            System.out.println("abs: " +abs);
            row.getCell(absPriceColumn).setCellValue(abs);
            row.getCell(absPriceColumn).setCellStyle(chooseStyle(abs,isonzzap));
            System.out.println("условие 1");
        }
        else if(ourPrice==minPrices[0]){
            abs=minPrices[0]-minPrices[1]; //no!
            System.out.println("abs: " +abs);
            row.getCell(absPriceColumn).setCellValue(abs);
            row.getCell(absPriceColumn).setCellStyle(chooseStyle(abs,isonzzap));
            System.out.println("условие 2");
        }
        else {
            //abs=minPrices[0]-ourPrice;
            //row.getCell(absPriceColumn).setCellValue(abs);
            row.getCell(absPriceColumn).setCellStyle(style4);
            System.out.println("условие 3");
        }

        row.getCell(firstCompanyColumn).setCellValue(firstCompany);
        if(ourPrice==minPrices[0]){
            row.getCell(ourPositionColumn).setCellValue(1);
        }
        else{
            row.getCell(ourPositionColumn).setCellValue(0);
        }



    }

    public HSSFCellStyle chooseStyle(double abs,boolean isonzzap) {
        if(isonzzap) {

            if (abs > 1000) { //red
                System.out.println("стиль1");
                return style1;
            } else if (abs > 0) {
                System.out.println("стиль2");
                return style2;
            } else if (abs < 0) {
                System.out.println("стиль3");
                return style3;
            }
        }
        else {
            System.out.println("стиль4");
            return style4;
        }
        System.out.println("no styles");
        return null;
    }



    //геттеры и сеттеры


    public void setFirstCompanyColumn(int firstCompanyColumn) {
        this.firstCompanyColumn = firstCompanyColumn;
    }

    public void setOurPriceColumn(int ourPriceColumn) {
        this.ourPriceColumn = ourPriceColumn;
    }

    public void setFirstPriceColumn(int firstPriceColumn) {
        this.firstPriceColumn = firstPriceColumn;
    }

    public void setOurPositionColumn(int ourPositionColumn) {
        this.ourPositionColumn = ourPositionColumn;
    }

    public void setAbsPriceColumn(int absPriceColumn) {
        this.absPriceColumn = absPriceColumn;
    }

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
