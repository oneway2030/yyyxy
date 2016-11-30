package com.yyyxt.www.commom;


import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonViewHolder {

	public final View convertView;

	public CommonViewHolder(View convertView) {
		this.convertView = convertView;
		convertView.setTag(this);
	}

	// andriod特有的集合： 离散的数组：当作key是int 的map集合就可
	SparseArray<View> Cacheview = new SparseArray<View>();

	@SuppressWarnings("unchecked")
	public <T> T getView(int viewId) {
		if (Cacheview.get(viewId) == null) {
			View v = convertView.findViewById(viewId);
			Cacheview.put(viewId, v);
		}
		return (T) Cacheview.get(viewId);
	}

	@SuppressWarnings("unchecked")
	public <T> T getView(int viewId, Class<T> view) {

		return (T) getView(viewId);
	}

	public TextView getTv(int viewId) {
		return getView(viewId, TextView.class);
	}

	public ImageView getIv(int viewId) {
		return getView(viewId, ImageView.class);
	}

	public static CommonViewHolder getCVH(View convertView, Context context, int resource) {
		if (convertView == null) {
			convertView = View.inflate(context, resource, null);
			return new CommonViewHolder(convertView);
		}

		return (CommonViewHolder) convertView.getTag();
	}

}
