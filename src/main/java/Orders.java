import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class Orders {
    private List<Order> orders;

    public static List<Order> sortOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getLatestToStart)
                        .thenComparing(Order::getOrderValue, Comparator.reverseOrder())
                        .thenComparing(Order::getCompleteBy)
                ).toList();
    }
}
