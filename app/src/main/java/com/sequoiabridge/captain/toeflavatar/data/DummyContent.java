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

    public DummyContent(XmlResourceParser parser) {
        try {
            int eventType = -1;
            QuestionItem question = null;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                String name = parser.getName();
                Log.d("XML file parser", "name = " + name);
                switch (eventType) {
                    case XmlResourceParser.START_TAG:
                        Log.d("START_TAG", "START_TAG ");
                        name = parser.getName();
                        if (name.equals("item")) {
                            question = new QuestionItem();
                        } else if (question != null) {
                            //TODO:
                            // need to use switch, and use Contract
                            if (name.equals("id"))
                                question.id = parser.nextText();
                            else if (name.equals("title"))
                                question.titleEnglish = parser.nextText();
                            else if (name.equals("title_chinese"))
                                question.titleChinese = parser.nextText();
                            else if (name.equals("question"))
                                question.content = parser.nextText();
                        }
                        break;
                    case XmlResourceParser.END_TAG:
                        name = parser.getName();
                        if (name.equals("item")) {
                            addItem(question);
                            Log.d("END_TAG", "addItem " + question.toString());
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
            return titleEnglish;
        }
    }
}
