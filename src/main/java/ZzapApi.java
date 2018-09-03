
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

///webservice/datasharing.asmx/GetSearchResult?login=&password=&partnumber=19010RZAA51
// &class_man=HONDA&location=1&row_count=5&api_key=EAAAACsHYUQNdLtum/Kb2AxYrgfys/pWPm7fNZG18WCNt4BF


    public interface ZzapApi {
        @GET("/webservice/datasharing.asmx/GetSearchResult?login=&password=&part_number={part_number}&class_man={class_man}&location=1&row_count=5&api_key=EAAAACsHYUQNdLtum/Kb2AxYrgfys/pWPm7fNZG18WCNt4BF")
        Call<ResponseBody> getInfo(@Path("part_number") String part_number, @Path("class_man") String class_man);


//      @GET("/webservice/datasharing.asmx/GetSearchResult?login=&password=&partnumber=19010RZAA51&class_man=HONDA&location=1&row_count=5&api_key=EAAAACsHYUQNdLtum/Kb2AxYrgfys/pWPm7fNZG18WCNt4BF")
            }
