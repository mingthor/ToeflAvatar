package com.sequoiabridge.captain.toeflavatar.dummy;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.sequoiabridge.captain.toeflavatar.QuestionListActivity;

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
    public static List<QuestionItem> ITEMS = new ArrayList<QuestionItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, QuestionItem> ITEM_MAP = new HashMap<String, QuestionItem>();

    public DummyContent(XmlResourceParser parser) {
        //addItem(new QuestionItem("1", "An important book to you", "一本重要的书", ""));
        //addItem(new QuestionItem("2", "Television's influence on society", "电视对社会的影响"));
        //addItem(new QuestionItem("3", "A place you go very often", "一个你常去的地方"));
        //addItem(new QuestionItem("4", "General or special education", "通才教育或专才教育"));
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
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
