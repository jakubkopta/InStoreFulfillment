import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class TestCase {
    private Map<String, String> input;
    private List<String> expectedOutput;
}
