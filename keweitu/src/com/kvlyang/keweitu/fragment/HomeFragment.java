package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.utils.UIUtils;



public class HomeFragment extends BaseFragment {
	private List<String> mDatas =new ArrayList<String>();
	
	@Override
	protected LoadedResult initDataFromCaches() {
		SystemClock.sleep(4000);
		return LoadedResult.UPDATE;
	}
	
	@Override
	protected LoadedResult initDataFromHttp() {
		SystemClock.sleep(8000);
		return LoadedResult.UPDATE;
	}
 
	@Override
	protected View initSuccessView() {
		ListView listView = new ListView(UIUtils.getContext());
		
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);
		
		listView.setAdapter(new HomeAdapter());
		return listView;
	}

	class HomeAdapter extends BaseAdapter{

		

		@Override
		public int getCount() {
			if(mDatas!=null){
				return mDatas.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(mDatas != null){
				mDatas.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(UIUtils.getContext(), R.layout.item_tmp, null);
				holder.tv1 = (TextView) convertView.findViewById(R.id.tmp_tv_1);
				holder.tv2 = (TextView) convertView.findViewById(R.id.tmp_tv_2);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			String data = mDatas.get(position);
			holder.tv1.setText(data+"1");
			holder.tv2.setText(data+"2");
			
			return convertView;
		}
		
		class ViewHolder {
			TextView tv1;
			TextView tv2;
		}
		
	}
	

	

	

	
}
