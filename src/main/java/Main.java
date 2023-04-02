import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Order> orders = objectMapper.readValue(new FileReader("src/main/resources/orders.json"), new TypeReference<>(){});
        Store store = objectMapper.readValue(new FileReader("src/main/resources/store.json"), Store.class);

        for (String completedOrder : completeOrders(orders, store)) {
            System.out.println(completedOrder);
        }
    }

    static List<String> completeOrders(List<Order> orders, Store store) {

        List<String> results = new ArrayList<>();
        List<String> pickers = store.getPickers();
        LocalTime pickingStartTime = store.getPickingStartTime();

        Map<String, LocalTime> pickerAvailableTimeMap = new HashMap<>();
        for (String picker : pickers) {
            pickerAvailableTimeMap.put(picker, pickingStartTime);
        }

        List<Order> sortedOrders = Orders.sortOrders(orders);

        for (Order order : sortedOrders) {
            String earliestPicker = null;
            LocalTime earliestTime = null;
            LocalTime latestToStart = order.getLatestToStart();

            for (Map.Entry<String, LocalTime> entry : pickerAvailableTimeMap.entrySet()) {
                if ((earliestPicker == null || entry.getValue().isBefore(earliestTime)) && (latestToStart.isAfter(entry.getValue()) || latestToStart.equals(entry.getValue()))) {
                    earliestPicker = entry.getKey();
                    earliestTime = entry.getValue();
                }
            }

            if (earliestPicker != null || earliestTime != null) {
                results.add(earliestPicker + " " + order.getOrderId() + " " + earliestTime);
                LocalTime newTime = earliestTime.plusMinutes(order.getPickingTime().toMinutes());
                pickerAvailableTimeMap.put(earliestPicker, newTime);
            }
        }

        return results;
    }
}
