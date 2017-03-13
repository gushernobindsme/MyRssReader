package com.example.android.sample.myrssreader.parser;

import android.provider.DocumentsContract;

import com.example.android.sample.myrssreader.data.Link;
import com.example.android.sample.myrssreader.data.Site;

import org.w3c.dom.Document;

import java.util.List;

/**
 * RSSフィードをパーサするインタフェース。
 */
public interface FeedParser {

    /**
     * 解析する。
     *
     * @param document
     * @return
     */
    boolean parse(Document document);

    /**
     * 解析結果のSiteを取得する。
     *
     * @return
     */
    Site getSite();

    /**
     * 解析結果のリンクリストを受け取る。
     *
     * @return
     */
    List<Link> getLinkList();
}
