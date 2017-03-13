package com.example.android.sample.myrssreader.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.sample.myrssreader.database.RssRepository;

/**
 * サイト情報を削除するAsyncTaskLoader。
 */
public class DeleteSiteLoader extends AsyncTaskLoader {

    /**
     * 削除対象のID。
     */
    private long id;

    /**
     * コンストラクタ。
     *
     * @param context
     */
    public DeleteSiteLoader(Context context,long id) {
        super(context);
        this.id = id;
    }

    public long getTargetId() {
        return id;
    }

    @Override
    public Object loadInBackground() {
        // サイトをデータベースから削除する
        return RssRepository.deleteSite(getContext(),id);
    }
}
