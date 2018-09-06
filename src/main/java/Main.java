import com.google.gson.Gson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ConnectToZZap.getZzapApi().getInfo("164000H120", "TOYOTA").enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                System.out.println("Start printing");
                try {
                    String s=response.body().string();
                    s=cutResponse(s);
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
                System.out.println("Errors!");
                throwable.printStackTrace();
            }
        });

    }
    //обрезка строки
    public static String cutResponse(String s){
        while (s.charAt(0)!='{') {
            s=s.substring(1);
        }
        s=s.substring(0,s.length()-9);
        return s;
    }
}