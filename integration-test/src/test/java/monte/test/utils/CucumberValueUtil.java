package monte.test.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CucumberValueUtil {

    public final static Map<String, Object> STRING_VALUES = new HashMap<>();

    static {
        STRING_VALUES.put("[empty]", "");
        STRING_VALUES.put("[blank]", "  ");
        STRING_VALUES.put("[null]", null);
        STRING_VALUES.put("[empty_list]", List.of());
    }

}
