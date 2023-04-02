import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testCompleteOrders() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        List<TestCase> testCases = objectMapper.readValue(new FileReader("src/main/resources/test-cases.json"), new TypeReference<>() {});

        for (TestCase testCase : testCases) {
            String ordersFile = testCase.getInput().get("ordersFile");
            String storeFile = testCase.getInput().get("storeFile");

            List<Order> orders = objectMapper.readValue(new FileReader(ordersFile), new TypeReference<>(){});
            Store store = objectMapper.readValue(new FileReader(storeFile), Store.class);

            List<String> actualOutput = Main.completeOrders(orders, store);
            List<String> expectedOutput = testCase.getExpectedOutput();
            assertEquals(expectedOutput, actualOutput);
        }
    }
}
