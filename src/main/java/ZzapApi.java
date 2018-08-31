
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


    public interface ZzapApi {
        @GET("/users/{username}")
        Call<GetJson> getInfo(@Path("username") String username);
    }
