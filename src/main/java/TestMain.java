import com.google.gson.Gson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class TestMain {

        private static double[] price=new double[20];
        private static GetJson getJson2;
        private static double priceExcel;
        private static String[] array;

        public static void main(String[] args) {
            startProgramm();

        }

        public static void getJson(String part_number, String class_man){
            //ConnectToZZap.getZzapApi().getInfo("164000H120", "TOYOTA").enqueue(new Callback<ResponseBody>() {
            ConnectToZZap.getZzapApi().getInfo(part_number, class_man).enqueue(new Callback<ResponseBody>() {
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    System.out.println("Start printing");
                    try {
                        String s=response.body().string();
                        s=cutResponse(s);
                        System.out.println(s);
                        Gson gson = new Gson();

                        //Type collectionType = new TypeToken<GetJson>(){}.getType();
                        getJson2 = gson.fromJson(s, GetJson.class);


                        int y = 0;
                        for (Table itrTable : getJson2.getTable()) {
                            price[y] = Double.valueOf(itrTable.getPrice());
                            System.out.println(price[y]);
                            y++;
                        }


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

        private static void startProgramm(){

            String part_number;
            String class_man;

            Excel excel = new Excel();
            excel.init();
            excel.setMakerColumn(1);
            excel.setOemColumn(2);
            excel.setPriceColumn(10);
            excel.setPlusColumn(6);
            int rowStart=7;
            int rowEnd=2216;

            String[] arrayExcel=new String[2];


            for (int i=rowStart;i<rowEnd;i++) {
                if(!excel.readPlus(i)) {
                    continue;
                }

                arrayExcel[0]=excel.readOEM(i);
                arrayExcel[1]=excel.readMaker(i);
                priceExcel=excel.readPrice(i);

                getJson(arrayExcel[0],arrayExcel[1]);



//            for(int t=0;t<4000;t++){
//                System.out.println("@");
//            }




            }

//        excel.createStyles();
//        excel.createCells(7,11);
//        excel.read( 7, 2216, excel.getMakerColumn(), excel.getOemColumn(), excel.getPriceColumn());
            excel.saveAndClose();
        }

        public static void comparePrices(){

        }

        //обрезка строки
        private static String cutResponse(String s){
            while (s.charAt(0)!='{') {
                s=s.substring(1);
            }
            s=s.substring(0,s.length()-9);
            return s;
        }

    }
