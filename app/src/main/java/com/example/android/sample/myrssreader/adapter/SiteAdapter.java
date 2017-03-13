package com.example.android.sample.myrssreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.sample.myrssreader.R;
import com.example.android.sample.myrssreader.data.Site;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * RSSフィールドを配信しているサイトを表示するためのアダプタ。
 */
public class SiteAdapter extends BaseAdapter {

    /**
     * コンテキスト。
     */
    private Context mContext;

    /**
     * レイアウトインフレータ。
     */
    private LayoutInflater mInflater;

    /**
     * サイト情報のリスト。
     */
    private List<Site> mSites;

    /**
     * コンストラクタ。
     *
     * @param context
     */
    public SiteAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mSites = new ArrayList<>();
    }

    /**
     * リストにサイトを追加する。
     *
     * @param site
     */
    public void addItem(Site site){
        mSites.add(site);
        notifyDataSetChanged();
    }

    /**
     * リストにサイトを全て追加する。
     *
     * @param sites
     */
    public void addAll(List<Site> sites){
        mSites.addAll(sites);
        notifyDataSetChanged();
    }

    /**
     * リストからアイテムを取り除く。
     *
     * @param siteId
     */
    public void removeItem(long siteId){
        Iterator<Site> iterator = mSites.iterator();

        while (iterator.hasNext()){
            Site site = iterator.next();
            if(siteId == site.getId()){
                iterator.remove();
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mSites.size();
    }

    @Override
    public Site getItem(int position) {
        return mSites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mSites.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SiteViewHolder holder;
        View itemView;

        if(convertView == null){
            itemView = mInflater.inflate(R.layout.item_site,parent,false);
            holder = new SiteViewHolder(itemView);
            itemView.setTag(holder);
        } else {
            itemView = convertView;
            holder = (SiteViewHolder) convertView.getTag();
        }

        Site site = (Site) getItem(position);

        holder.title.setText(site.getTitle());
        holder.linksCount.setText(mContext.getString(R.string.site_link_count,site.getLinkCount()));

        return itemView;
    }

    /**
     * 記事一覧のアイテム用ViewHolder。
     */
    private static class SiteViewHolder{

        private TextView title;
        private TextView linksCount;

        public SiteViewHolder(View itemView) {
            title = (TextView) itemView.findViewById(R.id.Title);
            linksCount = (TextView) itemView.findViewById(R.id.AticlesCount);
        }
    }

}
