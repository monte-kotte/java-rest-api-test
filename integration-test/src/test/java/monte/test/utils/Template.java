package monte.test.utils;

public enum Template {

    TEMPLATE_DB_USER_1("/template-db-user-1.json"),
    TEMPLATE_API_USER_1("/template-api-user-1.json"),
    TEMPLATE_API_USER_2("/template-api-user-2.json"),
    TEMPLATE_API_AUDIT_SINGLE_RECORD("/template-api-audit-1.json"),
    TEMPLATE_API_AUDIT_MULTIPLE_RECORDS("/template-api-audit-2.json");

    public final String filePath;

    Template(String filePath) {
        this.filePath = "src/test/resources/json" + filePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
