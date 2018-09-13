import com.google.gson.Gson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    //private static double[] price = new double[20];
    //private static double priceExcel;

    public static void main(String[] args) {
        startProgramm();

    }

    public static void getJson(String part_number, String class_man, final int i, final Excel excel, final double priceEx) {
        //ConnectToZZap.getZzapApi().getInfo("164000H120", "TOYOTA").enqueue(new Callback<ResponseBody>() {
        ConnectToZZap.getZzapApi().getInfo(part_number, class_man).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                System.out.println("Start printing");
                try {
                    String s = response.body().string();
                    s = cutResponse(s);
                    System.out.println(s);
                    Gson gson = new Gson();

                    GetJson getJson2 = gson.fromJson(s, GetJson.class);
                    double[] prices = getPrices(getJson2); //получили две цены
                    excel.writeToExcel(i, priceEx, prices, getCompany(getJson2),isOnZzap(getJson2));
                    System.out.println("finished with row № "+(i+1));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                System.out.println("Errors!");
                throwable.printStackTrace();
            }
        });
    }

    private static void startProgramm() {

        String part_number;
        String class_man;

        Excel excel = new Excel();
        excel.init();
        excel.createStyles();
        excel.setMakerColumn(1);
        excel.setOemColumn(2);
        excel.setPriceColumn(10);
        excel.setPlusColumn(6);
        excel.setOurPriceColumn(12);
        excel.setFirstPriceColumn(13);
        excel.setFirstCompanyColumn(14);
        excel.setAbsPriceColumn(15);
        excel.setOurPositionColumn(16);
        int rowStart = 30;
        int rowEnd = 55;
        excel.createCells(rowStart, 12);
        excel.createCells(rowStart, 13);
        excel.createCells(rowStart, 14);
        excel.createCells(rowStart, 15);
        excel.createCells(rowStart, 16);

        String[] arrayExcel = new String[2];


        for (int i = rowStart; i < rowEnd; i++) {
            if (!excel.readPlus(i)) {
                continue;
            }

            arrayExcel[0] = excel.readOEM(i);
            arrayExcel[1] = excel.readMaker(i);
            double priceExcel = excel.readPrice(i);

            getJson(arrayExcel[0], arrayExcel[1], i, excel, priceExcel);
            wait3();

//            for(int t=0;t<4000;t++){
//                System.out.println("@");
//            }


        }

//        excel.createStyles();
//        excel.createCells(7,11);
//        excel.read( 7, 2216, excel.getMakerColumn(), excel.getOemColumn(), excel.getPriceColumn());
        //excel.setStyles(rowStart, rowEnd, 12, 13, 14);
        excel.saveAndClose();
    }

    public static double[] getPrices(GetJson getJson2) {
        int y = 0;
        double[] price = new double[10];
        double minPrice = 100000;
        double minPrice2 = 100000;
        double[] prices = new double[2];
        for (Table itrTable : getJson2.getTable()) {
            //if(!itrTable.getClassUser().equals("Авто Радиатор ООО")) {

            price[y] = Double.valueOf(cutPrice(itrTable.getPrice()));
            if (price[y] < minPrice) {
                minPrice2 = minPrice;
                minPrice = price[y];
            }
            System.out.println(price[y]);

            // }
            y++;
        }

        prices[0] = minPrice;
        prices[1] = minPrice2;
        return prices;
    }

    public static String getCompany(GetJson getJson2) {
        int y = 0;
        double[] price = new double[10];
        double minPrice = 100000;
        //String company="Авто Радиатор ООО";
        String company = null;


        for (Table itrTable : getJson2.getTable()) {

            price[y] = Double.valueOf(cutPrice(itrTable.getPrice()));
            if (price[y] < minPrice) {
                minPrice = price[y];
                System.out.println(itrTable.getClassMan());
                System.out.println(itrTable.getInstock());
                System.out.println(itrTable.getClassUser());
                company = itrTable.getClassUser();
            }


            y++;
        }


        return company;
    }

    public static boolean isOnZzap(GetJson getJson2) {
        String company = "Авто Радиатор ООО";

        for (Table itrTable : getJson2.getTable()) {
            if (company.equals(itrTable.getClassUser())) {
                System.out.println(itrTable.getClassUser());
                return true;
            }
        }


        return false;
    }

    private static void wait3() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //while()

    }

    private static String cutPrice(String s) {
        s = s.substring(0, s.length() - 2);
        s = s.replaceAll(" ", "");
//        for(int i=0;i<s.length();i++){
//            if(s.charAt(i)==' '){
//
//            }
//        }
        return s;
    }


    //обрезка строки
    private static String cutResponse(String s) {
        while (s.charAt(0) != '{') {
            s = s.substring(1);
        }
        s = s.substring(0, s.length() - 9);
        return s;
    }
}