package com.sequoiabridge.captain.toeflavatar.dummy;

import android.content.res.Resources;

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

    static {
        addItem(new QuestionItem("1", "An important book to you", "一本重要的书"));
        addItem(new QuestionItem("2", "Television's influence on society", "电视对社会的影响"));
        addItem(new QuestionItem("3", "A place you go very often", "一个你常去的地方"));
        addItem(new QuestionItem("4", "General or special education", "通才教育或专才教育"));
    }

    private static void addItem(QuestionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class QuestionItem {
        public String id;
        public String contentEnglish;
        public String contentChinese;

        public QuestionItem(String id, String contentEn, String contentCh) {
            this.id = id;
            this.contentEnglish = contentEn;
            this.contentChinese = contentCh;
        }

        @Override
        public String toString() {
            return contentEnglish;
        }
    }
}
