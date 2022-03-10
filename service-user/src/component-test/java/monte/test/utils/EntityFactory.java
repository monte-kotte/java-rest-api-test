package monte.test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import monte.test.model.api.TestApiUser;
import monte.test.model.api.audit.TestAudit;
import monte.test.model.db.TestDbUser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EntityFactory {

    public static TestApiUser createApiUser(String fileName) throws IOException {
        return fromFile(fileName, TestApiUser.class);
    }

   public static TestApiUser createApiUser(String fileName, String id) throws IOException {
        var apiUser = fromFile(fileName, TestApiUser.class);
        apiUser.setId(id);
        return apiUser;
    }

    public static TestDbUser createDbUser(String fileName) throws IOException {
        return fromFile(fileName, TestDbUser.class);
    }

    public static List<TestAudit> createAuditList(String fileName) throws IOException {
        return Arrays.asList(fromFile(fileName, TestAudit[].class));
    }

    public static String userAuditJson(String id, TestAudit.TestAction testAction, String comment) throws JsonProcessingException {
        var auditRecord = TestAudit.builder()
                .entityName("User")
                .entityId(id)
                .action(testAction)
                .comment(comment)
                .build();
        return new ObjectMapper().writeValueAsString(auditRecord);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private static  <T> T fromFile(String fileName, Class<T> clazz) throws IOException {
        return new ObjectMapper().findAndRegisterModules().readValue(new File(fileName), clazz);
    }

}
