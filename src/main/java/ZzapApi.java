
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

///webservice/datasharing.asmx/GetSearchResult?login=&password=&partnumber=19010RZAA51
// &class_man=HONDA&location=1&row_count=5&api_key=EAAAACsHYUQNdLtum/Kb2AxYrgfys/pWPm7fNZG18WCNt4BF


    public interface ZzapApi {
//        @GET("/webservice/datasharing.asmx/GetSearchResult?login=&password=&partnumber={part_number}&class_man={maker}" +
//                "&location=1&row_count=5&api_key=EAAAACsHYUQNdLtum/Kb2AxYrgfys/pWPm7fNZG18WCNt4BF")

        @GET("/webservice/datasharing.asmx/GetSearchResult?login=&password=&partnumber=19010RZAA51&class_man=HONDA&location=1&row_count=5&api_key=EAAAACsHYUQNdLtum/Kb2AxYrgfys/pWPm7fNZG18WCNt4BF")

        //Call<ArrayList<GetJson>> getInfo(@Path("part_number") String partnumber, @Path("maker") String maker);

            Call<GetJson> getInfo();
        //Observable<List<GetJson>> getInfo();

    }
