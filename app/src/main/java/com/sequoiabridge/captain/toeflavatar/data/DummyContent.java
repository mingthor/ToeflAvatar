package com.sequoiabridge.captain.toeflavatar.data;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DataContract.QuestionItem> ITEMS = new ArrayList<>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DataContract.QuestionItem> ITEM_MAP = new HashMap<>();
    public Context context;

    public DummyContent(Context context) {
        this.context = context;
    }

    public static void populateQuestionsList(XmlResourceParser parser, int type) {
        try {
            int eventType = -1;
            ITEMS.clear();
            ITEM_MAP.clear();
            DataContract.QuestionItem question = null;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                String name = parser.getName();
                switch (eventType) {
                    case XmlResourceParser.START_TAG:
                        Log.d("START_TAG", "START_TAG ");
                        if (name.equals(DataContract.QuestionEntry.QUESTION_ITEM_NODE_NAME)) {
                            question = new DataContract.QuestionItem();
                        } else if (question != null) {
                            switch (name) {
                                case DataContract.QuestionEntry.QUESTION_ITEM_ATTR_ID:
                                    question.id = parser.nextText();
                                    break;
                                case DataContract.QuestionEntry.QUESTION_ITEM_ATTR_TYPE:
                                    question.type = parser.nextText();
                                    break;
                                case DataContract.QuestionEntry.QUESTION_ITEM_ATTR_TITLE:
                                    question.titleEnglish = parser.nextText();
                                    break;
                                case DataContract.QuestionEntry.QUESTION_ITEM_ATTR_TITLE_CH:
                                    question.titleChinese = parser.nextText();
                                    break;
                                case DataContract.QuestionEntry.QUESTION_ITEM_ATTR_CONTENT:
                                    question.content = parser.nextText();
                                    break;
                            }
                        }
                        break;
                    case XmlResourceParser.END_TAG:
                        assert (question != null);
                        if (name.equals(DataContract.QuestionEntry.QUESTION_ITEM_NODE_NAME) &&
                                Integer.parseInt(question.type) == type &&
                                !ITEM_MAP.containsKey(question.id)) {
                            addItem(question);
                            question = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            Log.d("XML file parser", "Done");
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(DataContract.QuestionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);

        Log.d("QuestionItem", item.toString());
    }


}
