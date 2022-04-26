package monte.test.utils;

public final class Constants {

    public final static class Files {
        private static final String BASE_PATH = "src/test/resources/json";

        public static final String TEMPLATE_API_AUDIT_1 = BASE_PATH + "/template-api-audit-1.json";
        public static final String TEMPLATE_API_AUDIT_2 = BASE_PATH + "/template-api-audit-2.json";
    }

    public final static class TestData {
        public static final String ID_TO_REPLACE = "REPLACE_ME_WITH_REAL_ID";

        public static final String AUDIT_READ_COMMENT = "read user";
        public static final String AUDIT_CREATE_COMMENT = "create new user";
        public static final String AUDIT_UPDATE_COMMENT = "update user";
    }

}
