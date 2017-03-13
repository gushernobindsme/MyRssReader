package com.example.android.sample.myrssreader.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.sample.myrssreader.database.RssRepository;

/**
 * リンクの一覧を取得するAsyncTaskLoader。
 */
public class LinkListLoader extends AsyncTaskLoader {

    /**
     * コンストラクタ。
     *
     * @param context
     */
    public LinkListLoader(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {
        // 登録されているリンクを全て取得する
        return RssRepository.getAllLinks(getContext());
    }
}
