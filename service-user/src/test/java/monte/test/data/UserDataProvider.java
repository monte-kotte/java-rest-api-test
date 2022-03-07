package monte.test.data;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UserDataProvider {

    public static Stream<Arguments> testData() {
        return Stream.of(
                arguments("driverLicense", null),
                arguments("addresses[0].zip", "12345-6789"),
                arguments("addresses[0].apartmentNumber", null),
                arguments("addresses[0].apartmentNumber", ""),
                arguments("addresses[0].apartmentNumber", "   ")
        );
    }


}
