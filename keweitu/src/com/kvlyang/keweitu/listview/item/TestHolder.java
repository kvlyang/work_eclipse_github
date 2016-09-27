package com.kvlyang.keweitu.listview.item;

import android.view.View;
import android.widget.TextView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.utils.UIUtils;

public class TestHolder extends BaseHolder<String>{
	TextView tv1;
	TextView tv2;
	@Override
	public View initHolderView() {
		View itemView = View.inflate(UIUtils.getContext(),R.layout.item_tmp, null);
		tv1 = (TextView) itemView.findViewById(R.id.tmp_tv_1);
		tv2 = (TextView) itemView.findViewById(R.id.tmp_tv_2);
		return itemView;
	}

	@Override
	public void refreshHolderView(String itemData) {
		tv1.setText(itemData+"1");
		tv2.setText(itemData+"2");
		
	}

}
