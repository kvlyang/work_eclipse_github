package com.kvlyang.keweitu.listview.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.bean.AppInfoBean;
import com.kvlyang.keweitu.conf.UrlUpdate;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.utils.BitmapHelper;
import com.kvlyang.keweitu.utils.StringUtils;
import com.kvlyang.keweitu.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;



public class AppHolder extends BaseHolder<AppInfoBean>{
	
	@ViewInject(R.id.item_appinfo_iv_icon)
	ImageView mIvIcon;
	@ViewInject(R.id.item_appinfo_rb_stars)
	RatingBar mRbStars;
	@ViewInject(R.id.item_appinfo_tv_des)
	TextView mTvDes;
	@ViewInject(R.id.item_appinfo_tv_size)
	TextView mTvSize;
	@ViewInject(R.id.item_appinfo_tv_title)
	TextView mTvTitle;

	@Override
	public View initHolderView() {
		View itemView = View.inflate(UIUtils.getContext(),R.layout.item_app_info, null);
		//注入
		ViewUtils.inject(this, itemView);
		
		return itemView;
	}

	@Override
	public void refreshHolderView(AppInfoBean itemData) {
		mTvDes.setText(itemData.des);
		mTvSize.setText(StringUtils.formatFileSize(itemData.size));
		mTvTitle.setText(itemData.name);
		mRbStars.setRating(itemData.stars);
		
		//异步更新图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		//BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
		BitmapHelper.display(mIvIcon, UrlUpdate.URLS.ImageBASEURL+  itemData.iconUrl);
		
	}
	



}
