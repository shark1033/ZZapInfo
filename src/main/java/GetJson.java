import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

    public class GetJson {
        @SerializedName("error")
        @Expose
        private String error;
        @SerializedName("row_count")
        @Expose
        private Integer rowCount;
        @SerializedName("terms")
        @Expose
        private String terms;
        @SerializedName("table")
        @Expose
        private List<Table> table = null;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public Integer getRowCount() {
            return rowCount;
        }

        public void setRowCount(Integer rowCount) {
            this.rowCount = rowCount;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public List<Table> getTable() {
            return table;
        }

        public void setTable(List<Table> table) {
            this.table = table;
        }


    }
