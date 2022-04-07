package monte.test.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class TestDbUser {
    private String id;
    private String firstName;
    private String secondName;
    private LocalDate dateOfBirth;
    private String email;
    private TestDbDL driverLicense;
    private List<TestDbAddress> addresses;
}
