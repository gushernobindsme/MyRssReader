package com.example.android.sample.myrssreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.android.sample.myrssreader.R;
import com.example.android.sample.myrssreader.data.Link;
import com.example.android.sample.myrssreader.data.Site;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * フィード内の各リンク情報を表示するためのアダプタ。
 */
public class LinkAdapter extends RecyclerView.Adapter {

    /**
     * ViewType。
     */
    private static final int VIEW_TYPE_LINK = 0;

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
    private List<Link> mLinks;

    /**
     * リストアイテムがタップされた時のリスナ。
     */
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        /**
         * リストのアイテムがタップされた時。
         * @param link
         */
        void onItemClick(Link link);
    }

    /**
     * コンストラクタ。
     *
     * @param context
     */
    public LinkAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLinks = new ArrayList<Link>();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_LINK){
            View view = mInflater.inflate(R.layout.item_link,parent,false);
            return new LinkViewHolder(view,this);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LinkViewHolder){
            LinkViewHolder articleHolder = (LinkViewHolder) holder;

            Link link = mLinks.get(position);

            articleHolder.title.setText(link.getTitle());
            articleHolder.description.setText(link.getDescription());
            articleHolder.timeAgo.setText(mContext.getString(R.string.link_publish_date,link.getPubDate()));
        }
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    /**
     * positionに対応するViewTypeを返す。
     *
     * @param position
     * @return
     */
    public int getItemViewType(int position){
        return VIEW_TYPE_LINK;
    }

    /**
     * リストにリンクを全て追加する。
     *
     * @param links
     */
    public void addItems(List<Link> links){
        mLinks.addAll(links);
        notifyDataSetChanged();
    }

    /**
     * リストからアイテムを取り除く。
     *
     * @param feedId
     */
    public void removeItem(long feedId){
        Iterator<Link> iterator = mLinks.iterator();

        while (iterator.hasNext()){
            Link link = iterator.next();
            if(feedId == link.getId()){
                iterator.remove();
            }
        }
        notifyDataSetChanged();
    }

    /**
     * リストからアイテムを全て取り除く。
     */
    public void clearItems(){
        mLinks.clear();
        notifyDataSetChanged();
    }

    /**
     * 記事一覧のアイテム用ViewHolder。
     */
    private static class LinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private LinkAdapter adapter;
        private TextView title;
        private TextView description;
        private TextView timeAgo;

        /**
         * コンストラクタ。
         *
         * @param itemView
         * @param adapter
         */
        public LinkViewHolder(View itemView, LinkAdapter adapter) {
            super(itemView);

            this.adapter = adapter;
            title = (TextView) itemView.findViewById(R.id.Title);
            description = (TextView) itemView.findViewById(R.id.Description);
            timeAgo = (TextView) itemView.findViewById(R.id.TimeAgo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(adapter.mListener != null){
                int position = getLayoutPosition();
                Link data = adapter.mLinks.get(position);
                adapter.mListener.onItemClick(data);
            }
        }
    }

}
