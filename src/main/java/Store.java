import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class Store {
    @JsonProperty("pickers")
    private List<String> pickers;
    @JsonProperty("pickingStartTime")
    private LocalTime pickingStartTime;
    @JsonProperty("pickingEndTime")
    private LocalTime pickingEndTime;
}
