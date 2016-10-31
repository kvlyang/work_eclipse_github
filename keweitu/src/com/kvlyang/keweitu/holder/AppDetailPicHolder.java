package com.kvlyang.keweitu.holder;

import java.util.List;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.bean.AppDetailBean;
import com.kvlyang.keweitu.conf.UrlUpdate.URLS;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.utils.BitmapHelper;
import com.kvlyang.keweitu.utils.UIUtils;
import com.kvlyang.keweitu.views.RatioLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午11:05:30
 * @des	TODO
 *
 * @version $Rev: 43 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 15:47:06 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public class AppDetailPicHolder extends BaseHolder<AppDetailBean> {
	@ViewInject(R.id.app_detail_pic_iv_container)
	LinearLayout	mContainerPic;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_pic, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(AppDetailBean data) {
		// TODO
		List<String> picUrls = data.screen;
		for (int i = 0; i < picUrls.size(); i++) {
			String url = picUrls.get(i);
			ImageView ivPic = new ImageView(UIUtils.getContext());
			ivPic.setImageResource(R.drawable.ic_default);// 默认图片
			BitmapHelper.display(ivPic, URLS.ImageBASEURL + url);
			
			// 控件宽度等于屏幕的1/3
			int widthPixels = UIUtils.getResource().getDisplayMetrics().widthPixels;
			widthPixels = widthPixels - mContainerPic.getPaddingLeft() - mContainerPic.getPaddingRight();

			int childWidth = widthPixels / 3;
			// 已知控件的宽度,和图片的宽高比,去动态的计算控件的高度
			RatioLayout rl = new RatioLayout(UIUtils.getContext());
			rl.setPicRatio(150 / 250);// 图片的宽高比
			rl.setRelative(RatioLayout.RELATIVE_WIDTH);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(childWidth, LayoutParams.WRAP_CONTENT);

			rl.addView(ivPic);

			if (i != 0) {// 不处理第一张图片
				params.leftMargin = UIUtils.dip2Px(5);
			}

			mContainerPic.addView(rl, params);
			
		}
	}
}
