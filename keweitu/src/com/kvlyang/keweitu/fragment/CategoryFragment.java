package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kvlyang.keweitu.bean.CategoryBean;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt.OnCreateHolderListener;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.listview.item.CategoryHolder;
import com.kvlyang.keweitu.protocal.HttpProtocal;
import com.kvlyang.keweitu.utils.CacheDataLoad;
import com.kvlyang.keweitu.utils.UIUtils;



public class CategoryFragment extends BaseFragment {

	@Override
	protected LoadedDataAndView initDataFromCaches() {
		//SystemClock.sleep(3000);
		LoadedDataAndView dataView = new LoadedDataAndView();
		String result = CacheDataLoad.loadDataFromFile("category", 0);
		Log.e("keweitubug",">>>cache "+ result);
		if( result == null){
			dataView.state = LoadedResult.UPDATE;
			dataView.data = null;
			return dataView;
		}else{
			Category Category = new Category();
			Category.mDatas = jsonCategory(result);
			Log.e("keweitubug",">>>cache size "+ Category.mDatas.size());
			dataView.state = LoadedResult.UPDATE;
			dataView.data = Category;
			return dataView;
		}
		
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		Category Category = new Category();
		String result;
		
	//	SystemClock.sleep(4000);
		//如果缓存数据未过期，不从网络更新，节省流量
		if (!CacheDataLoad.isCachaOutTime("category", 0)){
			dataView.state = LoadedResult.EMPTY;
			dataView.data = null;
			return dataView;
		}
			
			
		try {
			// 解析json网络数据
			result =  HttpProtocal.loadHttpData("category",0);
			Log.e("keweitubug",">>>http "+ result);
			List<CategoryBean> categoryBeans = jsonCategory(result);
			// 异常处理，显示网络异常界面或缓存
			if (categoryBeans == null) {
				dataView.state = LoadedResult.ERROR;
				dataView.data = null;
				return dataView;
			}

			Category.mDatas = categoryBeans;


		} catch (Exception e) {
			Log.e("keweituBug", "HttpUtils() error;");
			// e.printStackTrace();
			dataView.state = LoadedResult.ERROR;
			dataView.data = null;
			return dataView;// 获取网络数据失败
		}
		//保存到缓存
		CacheDataLoad.writeDataToFile("category", 0, result);
		
		// 获取网络数据成功，强制更新SuccessView
		dataView.state = LoadedResult.UPDATE_F;
		dataView.data = Category;
		return dataView;
	}

	@Override
	protected View initSuccessView(Object data) {
		if (data == null || !(data instanceof Category)) {
			return null;
		}

		/*
		 * 测试
		 * TextView tv = new TextView(getActivity());
		 * tv.setText("hahahah"+((Category)data).result); return tv;
		 */

		Category category = (Category) data;

		// 无数据，返回null，容器将加载默认view
		if (category.mDatas == null || category.mDatas.size() == 0) {
			return null;
		}

		ListView listView = new ListView(UIUtils.getContext());

		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		

		
		CategoryAdapter CategoryAdapter = new CategoryAdapter(category.mDatas);
		// adapter最好先setOnCreateHolderListener再赋值给listView
		CategoryAdapter
				.setOnCreateHolderListener(new OnCreateHolderListener<CategoryBean>() {
					@Override
					public BaseHolder<CategoryBean> creatHolder(int type) {
							return new CategoryHolder();
					}
				});
		listView.setAdapter(CategoryAdapter);
		listView.setOnItemClickListener(CategoryAdapter);
		return listView;
	}

	List<CategoryBean> jsonCategory(String json) {
		if(json.equals("")){
			return null;
		}
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		try {
			JSONArray rootJsonArray = new JSONArray(json);
			Log.e("keweitubug",">>>cache rootJsonArray.length() "+ rootJsonArray.length());
			for (int i = 0; i < rootJsonArray.length(); i++) {
				JSONObject itemJson = rootJsonArray.getJSONObject(i);
				String title;

				title = itemJson.getString("title");

				CategoryBean titleBean = new CategoryBean();
				titleBean.title = title;
				titleBean.isTitle = true;
				list.add(titleBean);

				JSONArray infoJsonArray = itemJson.getJSONArray("infos");
				for (int j = 0; j < infoJsonArray.length(); j++) {
					JSONObject infoJsonObject = infoJsonArray.getJSONObject(j);
					CategoryBean infoBean = new CategoryBean();
					infoBean.name1 = infoJsonObject.getString("name1");
					infoBean.name2 = infoJsonObject.getString("name2");
					infoBean.name3 = infoJsonObject.getString("name3");
					infoBean.url1 = infoJsonObject.getString("url1");
					infoBean.url2 = infoJsonObject.getString("url2");
					infoBean.url3 = infoJsonObject.getString("url3");

					list.add(infoBean);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return list;
	}
	
	// adapter作为内部类的好处，方便调用父类数据
	class CategoryAdapter extends BaseAdapterKwt<CategoryBean> {

		public CategoryAdapter(List<CategoryBean> mDatas) {
			super(mDatas);
		}

		public void onNormalItemClick(AdapterView<?> parent, View view, int position,
				long id){
			
		}
		


		@Override
		public List<CategoryBean> OnLoadMore() throws Exception {
		//	SystemClock.sleep(3000);
			String result = HttpProtocal.loadHttpData("category",mListData.size());
			return jsonCategory(result);
			
		}

	}
	
	public class Category {
		public List<CategoryBean> mDatas;
		public String result; // 网络数据
		
		public Category() {
			mDatas = new ArrayList<CategoryBean>();

		}
	}

	
}
