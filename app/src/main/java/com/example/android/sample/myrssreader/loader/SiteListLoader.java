package com.example.android.sample.myrssreader.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.sample.myrssreader.database.RssRepository;

/**
 * サイトの一覧を取得するAsyncTaskLoader。
 */
public class SiteListLoader extends AsyncTaskLoader {

    /**
     * コンストラクタ。
     *
     * @param context
     */
    public SiteListLoader(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {
        // 登録されているRSSフィード配信サイトを全て取得する
        return RssRepository.getAllSites(getContext());
    }

}
