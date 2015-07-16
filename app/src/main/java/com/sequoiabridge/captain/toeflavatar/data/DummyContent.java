package com.sequoiabridge.captain.toeflavatar.data;

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
    public static List<QuestionItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, QuestionItem> ITEM_MAP = new HashMap<>();

    public static void populateQuestionsList(XmlResourceParser parser) {
        try {
            int eventType = -1;
            QuestionItem question = null;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                String name = parser.getName();
                Log.d("XML file parser", "name = " + name);
                switch (eventType) {
                    case XmlResourceParser.START_TAG:
                        Log.d("START_TAG", "START_TAG ");
                        if (name.equals(DataContract.QuestionEntry.QUESTION_ITEM_NODE_NAME)) {
                            question = new QuestionItem();
                        } else if (question != null) {
                            switch (name) {
                                case DataContract.QuestionEntry.QUESTION_ITEM_ATTR_ID:
                                    question.id = parser.nextText();
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
                        if (name.equals(DataContract.QuestionEntry.QUESTION_ITEM_NODE_NAME)) {
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

    private static void addItem(QuestionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        Log.d("QuestionItem", item.toString());
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class QuestionItem {
        public String id;
        public String titleEnglish;
        public String titleChinese;
        public String content;

        public QuestionItem() {
            this.id = null;
            titleEnglish = null;
            titleChinese = null;
            content = null;
        }
        public QuestionItem(String id, String en, String ch, String content) {
            this.id = id;
            this.titleEnglish = en;
            this.titleChinese = ch;
            this.content = content;
        }

        @Override
        public String toString() {
            return this.titleEnglish;
        }
    }
}
