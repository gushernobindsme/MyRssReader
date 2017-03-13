package com.example.android.sample.myrssreader;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.sample.myrssreader.adapter.LinkAdapter;
import com.example.android.sample.myrssreader.data.Link;
import com.example.android.sample.myrssreader.loader.LinkListLoader;

import java.util.List;

/**
 * リンク情報を取得してリスト表示するフラグメント。
 */
public class LinkListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Link>>,
        LinkAdapter.OnItemClickListener{

    /**
     * LoaderのID。
     */
    private static final int LOADER_LINKS = 1;

    /**
     * リストがタップされた時のリスナ。
     */
    public interface LinkListFragmentListener{
       void onLinkClicked(@NonNull Link link);
    }

    /**
     * アダプタ。
     */
    private LinkAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(!(context instanceof LinkListFragmentListener)){
            // ActivityがLinkListFragmentListenerを実装していない場合
            throw new RuntimeException(context.getClass().getSimpleName() + " does not implement LinkListFragmentListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Loaderを初期化する
        getLoaderManager().initLoader(LOADER_LINKS,null,this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Loaderを破棄
        getLoaderManager().destroyLoader(LOADER_LINKS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Viewを生成する
        View v = inflater.inflate(R.layout.fragment_links,container,false);

        Context context = inflater.getContext();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.LinkList);
        // 必ずLayoutManagerを設定する
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        mAdapter = new LinkAdapter(context);
        // リストのタップイベントを、一旦フラグメントで受け取る
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onItemClick(Link link) {
        LinkListFragmentListener listener = (LinkListFragmentListener) getActivity();
        if(listener != null){
            // アクティビティにタップされたリンクを伝える
            listener.onLinkClicked(link);
        }
    }

    /**
     * RSS配信サイトが削除された時に、それに紐付く記事も削除する。
     *
     * @param siteId
     */
    public void removeLinks(long siteId){
        mAdapter.removeItem(siteId);
    }

    /**
     * RSS配信サイトが追加された時に、同時にそのフィードのリンクもリストに反映する。
     */
    public void reload(){
        mAdapter.clearItems();

        // すでにLoaderが作られているならそれを使う
        Loader loader = getLoaderManager().getLoader(LOADER_LINKS);
        if(loader != null){
            loader.forceLoad();
        }else{
            getLoaderManager().restartLoader(LOADER_LINKS,null,this);
        }
    }

    @Override
    public Loader<List<Link>> onCreateLoader(int id, Bundle args) {
        if(id == LOADER_LINKS){
            LinkListLoader loader = new LinkListLoader(getActivity());
            loader.forceLoad();
            return loader;
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Link>> loader, List<Link> data) {
        int id = loader.getId();

        if (id == LOADER_LINKS && data != null && data.size() > 0){
            mAdapter.addItems(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Link>> loader) {
        // do nothing.
    }

}
