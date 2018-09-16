import com.google.gson.Gson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                    double[] prices = getPrices3(getJson2); //получили две цены
                    excel.writeToExcel(i, priceEx, prices, getCompany(getJson2), isOnZzap(getJson2));
                    System.out.println("finished with row № " + (i + 1));

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
        excel.setPriceColumn(6);
        //excel.setPlusColumn(6);
        excel.setOurPriceColumn(12);
        excel.setFirstPriceColumn(13);
        excel.setFirstCompanyColumn(14);
        excel.setAbsPriceColumn(15);
        excel.setOurPositionColumn(16);
        int rowStart =4;
        int rowEnd = 300;
        //excel.clearCells(rowStart,rowEnd);
        excel.createCells(rowStart, 12);
        excel.createCells(rowStart, 13);
        excel.createCells(rowStart, 14);
        excel.createCells(rowStart, 15);
        excel.createCells(rowStart, 16);

        String[] arrayExcel = new String[2];


        for (int i = rowStart; i < rowEnd; i++) {
//            if (!excel.readPlus(i)) {
//                continue;
//            }
            arrayExcel[0] = excel.readOEM(i);
            arrayExcel[1] = excel.readMaker(i);
            double priceExcel = excel.readPrice(i);

            getJson(arrayExcel[0], arrayExcel[1], i, excel, priceExcel);
            wait3();
        }
        excel.saveAndClose();
    }

//    public static double[] getPrices(GetJson getJson2) {
//        int y = 0;
//        double[] price = new double[25];
//        double minPrice = 100000;
//        double minPrice2 = 100000;
//        double[] prices = new double[2];
//        for (Table itrTable : getJson2.getTable()) {
//
//            if(itrTable.getInstock()!=0 && itrTable.getDescrAddress().equals("Москва, м.Молодёжная") && itrTable.getLocal()==1) {
//                price[y] = Double.valueOf(cutPrice(itrTable.getPrice()));
//                if (price[y] < minPrice) {
//                    minPrice2 = minPrice;
//                    minPrice = price[y];
//                }
//                System.out.println(price[y]);
//            }
//            // }
//            y++;
//        }
//
//        prices[0] = minPrice;
//        prices[1] = minPrice2;
//        return prices;
//    }

    public static double[] getPrices3(GetJson getJson2) {
        int y = 0;
        System.out.println("getPrices3 is working");
        ArrayList<Double> arrayList = new ArrayList<Double>();
        List<Table> table = getJson2.getTable();
        double[] prices = new double[3];

        for (Table itrTable : getJson2.getTable()) {
//            System.out.println(itrTable.getInstock());
//            System.out.println(itrTable.getDescrAddress());
//            System.out.println(itrTable.getPrice());
//
//            System.out.println("\n");

            if (itrTable.getInstock() != 0 && itrTable.getDescrAddress().equals("Москва, м.Молодёжная") && itrTable.getLocal() == 1) {
                arrayList.add(Double.valueOf(cutPrice(itrTable.getPrice())));
                //System.out.println(arrayList.get(y));
            }
            // }
            y++;
        }
        Collections.sort(arrayList);
        System.out.println(arrayList.size());
        System.out.println("prices: ");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
        if(arrayList.size()==0) {
        prices[0]=0;
        }
        else if (arrayList.size() ==1) {
            prices[0] = arrayList.get(0);
        } else if (arrayList.size()==2) {
            prices[0] = arrayList.get(0);
            prices[1] = arrayList.get(1);
        } else if(arrayList.size() == 3){
            prices[0] = arrayList.get(0);
            prices[1] = arrayList.get(1);
            prices[2] = arrayList.get(2);
        }
        else{
            prices[0] = arrayList.get(0);
            prices[1] = arrayList.get(1);
            prices[2] = arrayList.get(2);

        }
//        else {
//            return new double[0];
//        }
        System.out.println("finished with prices");
        return prices;
    }

    public static double[] getPrices2(GetJson getJson2) {
        int y = 0;
        double[] price = new double[25];
        List<Table> table = getJson2.getTable();
        double minPrice = Double.valueOf(table.get(0).getPrice());
        double minPrice2 = Double.valueOf(table.get(1).getPrice());
        double[] prices = new double[2];
        for (Table itrTable : getJson2.getTable()) {

            if (itrTable.getInstock() != 0 && itrTable.getDescrAddress().equals("Москва, м.Молодёжная") && itrTable.getLocal() == 1) {
                price[y] = Double.valueOf(cutPrice(itrTable.getPrice()));
                if (price[y] < minPrice) {
                    minPrice2 = minPrice;
                    minPrice = price[y];
                }
                System.out.println(price[y]);

            }
            // }
            y++;
        }
        prices[0] = minPrice;
        prices[1] = minPrice2;

        return prices;
    }

    public static String getCompany(GetJson getJson2) {
        int y = 0;
        double[] price = new double[500];
        double minPrice = 100000;
        //String company="Авто Радиатор ООО";
        String company = null;

        for (Table itrTable : getJson2.getTable()) {
            if (itrTable.getInstock() != 0 && itrTable.getDescrAddress().equals("Москва, м.Молодёжная") && itrTable.getLocal() == 1) {
                price[y] = Double.valueOf(cutPrice(itrTable.getPrice()));

                if (price[y] < minPrice) {
                    minPrice = price[y];
                    System.out.println(itrTable.getClassMan());
                    System.out.println(itrTable.getInstock());
                    System.out.println(itrTable.getClassUser());
                    company = itrTable.getClassUser();
                }

            }
            y++;
        }


        return company;
    }

    public static boolean isOnZzap(GetJson getJson2) {
        String company = "Авто Радиатор ООО";
        System.out.println("isOnZZap is checking");

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