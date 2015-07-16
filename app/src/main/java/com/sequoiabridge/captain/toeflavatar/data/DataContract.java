package com.sequoiabridge.captain.toeflavatar.data;

import android.provider.BaseColumns;

/**
 * Data contract that specifies the questions data format
 */
public final class DataContract {
    public static abstract class RecordingEntry implements BaseColumns {
        public static final String TABLE_NAME ="Recording";
        public static final String COLUMN_NAME_ENTRY_QUESTION_ID = "question_id";
        public static final String COLUMN_NAME_ENTRY_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_ENTRY_PATH = "path";
        public static final String COLUMN_NAME_ENTRY_FILENAME = "filename";
        public static final String COLUMN_NAME_ENTRY_DURATION = "duration";
    }

    public static abstract class QuestionEntry {
        public static final String QUESTION_ITEM_NODE_NAME = "item";
        public static final String QUESTION_ITEM_ATTR_ID = "id";
        public static final String QUESTION_ITEM_ATTR_TITLE = "title";
        public static final String QUESTION_ITEM_ATTR_TITLE_CH = "title_chinese";
        public static final String QUESTION_ITEM_ATTR_CONTENT = "question";
    }
}
