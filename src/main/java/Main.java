import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.Reader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //Call<ArrayList<GetJson>> call=ConnectToZZap.getZzapApi().getInfo("19010RZAA51", "HONDA");
        ConnectToZZap.getZzapApi().getInfo().enqueue(new Callback<GetJson>() {
            public void onResponse(Call<GetJson> call, Response<GetJson> response) {
                GetJson getJson=response.body();
                System.out.println("Start printing");

//                for (Table g:getJson.getTable()) {
//                    System.out.println(g);
//
//                }
            }

            public void onFailure(Call<GetJson> call, Throwable throwable) {
                System.out.println("Bad");
                throwable.printStackTrace();
            }
        });

//        Call<GetJson> call=ConnectToZZap.getZzapApi().getInfo();
//        call.enqueue(new Callback<GetJson>() {
//            public void onResponse(Call<GetJson> call, Response<GetJson> response) {
//                GetJson getJson=response.body();
//                System.out.println("Start printing");
//                for (Table g:getJson.getTable()) {
//                    System.out.println(g);
//
//                }
//            }
//
//            public void onFailure(Call<GetJson> call, Throwable throwable) {
//
//            }
//        });

        //System.out.println();

    }
}
