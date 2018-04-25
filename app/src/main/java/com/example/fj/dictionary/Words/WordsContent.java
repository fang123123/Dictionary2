package com.example.fj.dictionary.Words;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.databaseHelper.DatabaseStatic;
import com.example.databaseHelper.MyHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class WordsContent {

    /**
     * An array of sample (Words) items.
     */
    public static final List<WordsItem> ITEMS = new ArrayList<WordsItem>();

    /**
     * A map of sample (Words) items, by ID.
     */
    public static final Map<String, WordsItem> ITEM_MAP = new HashMap<String, WordsItem>();

    private static final int COUNT = 25;
    private static SQLiteDatabase database = null;

    public static void searchDatabase() // 查询数据库中的数据
    {
        ITEM_MAP.clear();;
        ITEMS.clear();
        database = DatabaseStatic.myHelper.getWritableDatabase();
        Log.i("123","查询数据库");
/*   * 调用database的query方法，第一个参数是要查询的表名，
   * 后面的参数是一些查询的约束条件，对应于SQL语句的一些参
   * 数， 这里全为null代表查询表格中所有的数据
   * 查询的结果返回一个 Cursor对象
   * 对应的SQL语句：
   * Cursor cursor = database.rawQuery("select * from book", null);
*/
        Cursor cursor = database.query(DatabaseStatic.TABLE_NAME, null, null, null, null, null, null);
        int i=1;
        while(cursor.moveToNext())
        {
            addItem(new WordsItem(String.valueOf(i), cursor.getString(0), cursor.getString(1)));
            i++;
            Log.i("123","读取数据库");
        }
        cursor.close(); // 记得关闭游标对象
    }
/*    static {
        // Add some sample items.
            for (int i = 1; i <= COUNT; i++) {
            addItem(createWordsItem(i));
    }
        addItem(new WordsItem("1","Apple","Apple"));
        addItem(new WordsItem("2","Banana","Banana"));
        addItem(new WordsItem("3","Orange","Orange"));
        addItem(new WordsItem("4","Lemon","Lemon"));
    }*/
    private static void addItem(WordsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static WordsItem createWordsItem(int position) {
        return new WordsItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A Words item representing a piece of content.
     */
    public static class WordsItem {
        public final String id;
        public final String content;
        public final String details;

        public WordsItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
