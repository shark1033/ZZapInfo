import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Type;

public class Main {

    public static void main(String[] args) {
        //Call<ArrayList<GetJson>> call=ConnectToZZap.getZzapApi().getInfo();
        ConnectToZZap.getZzapApi().getInfo("165959455L", "AUDI").enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                System.out.println("Start printing");
                try {
                    String s=response.body().string();

                    while (s.charAt(0)!='{') {
                        s=s.substring(1);
                    }
                    s=s.substring(0,s.length()-9);
                    System.out.println(s);
                    Gson gson = new Gson();

                    //Type collectionType = new TypeToken<GetJson>(){}.getType();
                    GetJson getJson2 = gson.fromJson(s, GetJson.class);

                    for (Table itrTable : getJson2.getTable()) {
                        System.out.println(itrTable.getClassMan());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
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