package com.sequoiabridge.captain.toeflavatar.data;

import android.provider.BaseColumns;

/**
 * Data contract Created by mingthor on 5/31/2015.
 */
public final class DataContract {
    public static abstract class RecordingEntry implements BaseColumns {
        public static final String TABLE_NAME ="Recording";
        public static final String COLUMN_NAME_ENTRY_QUESTION_ID = "question_id";
        public static final String COLUMN_NAME_ENTRY_QUESTION_CONTENT = "question_content";
        public static final String COLUMN_NAME_ENTRY_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_ENTRY_FILENAME = "filename";
    }
}
