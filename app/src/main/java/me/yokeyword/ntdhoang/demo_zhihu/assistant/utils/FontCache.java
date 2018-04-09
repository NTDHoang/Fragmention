package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.LruCache;

public class FontCache {
    private static LruCache<String, Typeface> mFontCache = new LruCache<>(12);

    public static Typeface getFont(String fontName, Context context) throws RuntimeException {
        Typeface typeface = mFontCache.get(fontName);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(),
                    "fonts/" + fontName);
            mFontCache.put(fontName, typeface);
        }
        return typeface;
    }
}
