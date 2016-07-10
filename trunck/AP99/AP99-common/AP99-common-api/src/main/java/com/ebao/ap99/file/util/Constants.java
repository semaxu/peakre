package com.ebao.ap99.file.util;

public interface Constants {

    public interface FileParseType {
        public static final String XML   = "XML";
        public static final String EXCEL = "EXCEL";
        public static final String JSON  = "JSON";
        public static final String OTHER = "OTHER";
    }

    public interface ENV {
        public static final String FILE_PATH     = "FILE_PATH";
        public static final String SEPARATOR     = "SEPARATOR";
        public static final String BIRT_HOME_URL = "BIRT_HOME_URL";
    }

    public interface PATH {
        public static final String ATTACHMENTS = "attachments";
        public static final String PRINTING    = "printing";
        public static final String TEMP        = "temp";
        public static final String EMAIL       = "email";

    }
}
