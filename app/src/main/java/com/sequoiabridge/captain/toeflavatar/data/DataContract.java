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
        public static final String COLUMN_NAME_ENTRY_FILENAME = "filename";
    }
}
