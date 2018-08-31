import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectToZZap implements ZzapApi {
    String BASE_URL = "https://api.github.com";
    private static ZzapApi zzapApi;
    private Retrofit retrofit;

    public void init() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    public Call<GetJson> getInfo(String username) {
        return null;
    }
}
