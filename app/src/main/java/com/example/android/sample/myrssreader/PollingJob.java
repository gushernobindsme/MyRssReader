package com.example.android.sample.myrssreader;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.os.AsyncTask;

import com.example.android.sample.myrssreader.data.Site;
import com.example.android.sample.myrssreader.database.RssRepository;
import com.example.android.sample.myrssreader.net.HttpGet;
import com.example.android.sample.myrssreader.parser.RSSParser;

import java.util.List;

/**
 * RSSフィードを定期的にチェックするためのJobService。
 */
@SuppressLint("NewApi")
public class PollingJob extends JobService {

    /**
     * ジョブパラメータ。
     */
    private JobParameters params;

    @Override
    public boolean onStartJob(JobParameters params) {
        this.params = params;
        new DownloadTask().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private class DownloadTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            List<Site> sites = RssRepository.getAllSites(PollingJob.this);

            int newArticles = 0;

            // 全ての登録済みRSS配信サイトからダウンロードする
            for (Site site : sites){
                HttpGet httpGet = new HttpGet(site.getUrl());

                // ダウンロードする
                if(!httpGet.get()){
                    continue;
                }

                RSSParser parser = new RSSParser();

                // ダウンロードしたフィードを解析する
                if(!parser.parse(httpGet.getResponse())){
                    continue;
                }

                newArticles += RssRepository.insertLinks(PollingJob.this,site.getId(),parser.getLinkList());
            }

            if (newArticles > 0){
                // 新しいリンクがある場合には通知する
                NotificationUtil.notifyUpdate(PollingJob.this,newArticles);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jobFinished(params,false);
        }
    }

}
