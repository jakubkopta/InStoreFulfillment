import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

@Data
public class Order {
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("orderValue")
    private BigDecimal orderValue;
    @JsonProperty("pickingTime")
    private Duration pickingTime;
    @JsonProperty("completeBy")
    private LocalTime completeBy;

    public LocalTime getLatestToStart() {
        return completeBy.minus(pickingTime);
    }
}
