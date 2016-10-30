package com.kvlyang.keweitu.listview.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.bean.SubjectBean;
import com.kvlyang.keweitu.conf.UrlUpdate;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.utils.BitmapHelper;
import com.kvlyang.keweitu.utils.StringUtils;
import com.kvlyang.keweitu.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;



public class SubjectHolder extends BaseHolder<SubjectBean>{
	
	@ViewInject(R.id.item_subject_iv_icon)
	ImageView mIvIcon;
	@ViewInject(R.id.item_subject_tv_title)
	TextView mTvDes;
	

	@Override
	public View initHolderView() {
		View itemView = View.inflate(UIUtils.getContext(),R.layout.item_subject, null);
		//注入
		ViewUtils.inject(this, itemView);
		
		return itemView;
	}

	@Override
	public void refreshHolderView(SubjectBean itemData) {
		mTvDes.setText(itemData.des);	
		//异步更新图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		BitmapHelper.display(mIvIcon, UrlUpdate.URLS.ImageBASEURL+  itemData.url);
		
	}
	



}
