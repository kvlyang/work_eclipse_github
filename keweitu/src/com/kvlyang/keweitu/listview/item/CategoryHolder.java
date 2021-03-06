package com.kvlyang.keweitu.listview.item;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.bean.CategoryBean;
import com.kvlyang.keweitu.conf.UrlUpdate.URLS;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.utils.BitmapHelper;
import com.kvlyang.keweitu.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CategoryHolder extends BaseHolder<CategoryBean> {

	@ViewInject(R.id.item_category_icon_1)
	ImageView mIvIcon1;
	@ViewInject(R.id.item_category_icon_2)
	ImageView mIvIcon2;
	@ViewInject(R.id.item_category_icon_3)
	ImageView mIvIcon3;

	@ViewInject(R.id.item_category_name_1)
	TextView mTv1;
	@ViewInject(R.id.item_category_name_2)
	TextView mTv2;
	@ViewInject(R.id.item_category_name_3)
	TextView mTv3;
	@ViewInject(R.id.item_category_title)
	TextView mTvTitle;

	@ViewInject(R.id.item_category_title_ll)
	LinearLayout llTitle;
	@ViewInject(R.id.item_category_infos)
	LinearLayout llInfos;

	@Override
	public View initHolderView() {
		View itemView = View.inflate(UIUtils.getContext(),
				R.layout.item_category_info, null);
		// 注入
		ViewUtils.inject(this, itemView);

		return itemView;
	}

	@Override
	public void refreshHolderView(final CategoryBean itemData) {
		if (itemData.isTitle) {
			llTitle.setVisibility(View.VISIBLE);
			llInfos.setVisibility(View.GONE);
			mTvTitle.setText(itemData.title);
		} else {
			llTitle.setVisibility(View.GONE);
			llInfos.setVisibility(View.VISIBLE);
			mTv1.setText(itemData.name1);
			mTv2.setText(itemData.name2);
			mTv3.setText(itemData.name3);
			BitmapHelper.display(mIvIcon1, URLS.ImageBASEURL + itemData.url1);
			BitmapHelper.display(mIvIcon2, URLS.ImageBASEURL + itemData.url2);
			BitmapHelper.display(mIvIcon3, URLS.ImageBASEURL + itemData.url3);

			((ViewGroup) mTv1.getParent())
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Toast.makeText(UIUtils.getContext(),itemData.name1 , Toast.LENGTH_SHORT).show();
						}
					});
			((ViewGroup) mTv2.getParent())
			.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(),itemData.name2 , Toast.LENGTH_SHORT).show();
				}
			});
			((ViewGroup) mTv3.getParent())
			.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(),itemData.name3 , Toast.LENGTH_SHORT).show();
				}
			});
		}

	}

}
