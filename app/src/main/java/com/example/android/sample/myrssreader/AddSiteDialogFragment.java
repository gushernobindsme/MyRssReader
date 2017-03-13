package com.example.android.sample.myrssreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * 新規登録するダイアログのFragment。
 */
public class AddSiteDialogFragment extends DialogFragment {

    /**
     * サイトのURLを登録する際のキー。
     */
    public static final String RESULT_KEY_SITE_ID = "url";

    /**
     * RSSフィードのURLを入力するテキストボックス。
     */
    private EditText mEditText;

    /**
     * このフラグメントのインスタンスを返す。
     *
     * @param target
     * @param requestCode
     * @return
     */
    public static AddSiteDialogFragment newInstance(Fragment target,int requestCode) {

        AddSiteDialogFragment fragment = new AddSiteDialogFragment();

        // 結果を返す先のフラグメントを設定しておく
        fragment.setTargetFragment(target,requestCode);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        // 入力ダイアログ用のView
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.dialog_input_url,null);

        mEditText = (EditText) contentView.findViewById(R.id.URLEditText);

        // ダイアログ生成用のビルダ
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.add_site)
                .setView(contentView)
                .setPositiveButton(R.string.dialog_button_add,// 登録するボタン
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Fragment fragment = getTargetFragment();
                                if(fragment != null){
                                    // ユーザが入力したURLを詰めて返す
                                    Intent data = new Intent();
                                    data.putExtra(RESULT_KEY_SITE_ID,mEditText.getText().toString());

                                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,data);
                                }
                            }
                        })
                .setNegativeButton(R.string.dialog_button_cancel,// キャンセルボタン
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Fragment fragment = getTargetFragment();
                                if(fragment != null){
                                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED,null);
                                }
                            }
                        }
                );

        // ダイアログを生成して返す
        return builder.create();
    }
}
