package com.mialab.palmsuda.main;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mialab.palmsuda.modules.ModuleItem;
import com.mialab.palmsuda.tools.ivache.ImageWorker;

public class GdvAdapter extends BaseAdapter {

	private LayoutInflater mInflater = null;
	private Context mContext = null;
	private List<ModuleItem> mThumbIds;
	private ImageWorker imgeWorker;

	public GdvAdapter(Context c, ImageWorker loader, GridView gv) {
		mContext = c;
		mInflater = LayoutInflater.from(mContext);
		imgeWorker = loader;
	}

	public void setData(List<ModuleItem> items) {
		mThumbIds = items;
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return (mThumbIds != null) ? mThumbIds.size() : 0;
	}

	public ModuleItem getItem(int position) {
		return (mThumbIds != null) ? mThumbIds.get(position) : null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		convertView = this.mInflater.inflate(R.layout.grid_icon_item, null);
		holder.tvItem = (TextView) convertView.findViewById(R.id.wap_item_name);
		holder.iView = (ImageView) convertView
				.findViewById(R.id.wap_item_image);
		holder.icView = (ImageView) convertView.findViewById(R.id.wap_ic_image);

		ModuleItem item = this.getItem(position);
		holder.tvItem.setText("" + item.getModuleName());

		if (item.getOptions().isHotModule()) {
			holder.icView.setVisibility(View.VISIBLE);
			holder.icView.setImageResource(R.drawable.icon_hot);
		} else if (item.getOptions().isNewModule()) {
			holder.icView.setVisibility(View.VISIBLE);
			holder.icView.setImageResource(R.drawable.icon_new);
		} else {
			holder.icView.setVisibility(View.GONE);
			holder.icView.setImageResource(0);
		}

		RelativeLayout.LayoutParams paLayoutParams = (LayoutParams) holder.iView
				.getLayoutParams();
		paLayoutParams.width = PalmSudaApp.SCREEN_WEIDTH * 3 / 16;
		paLayoutParams.height = paLayoutParams.width * 100 / 96;
		holder.iView.setLayoutParams(paLayoutParams);
		holder.iView.setScaleType(ScaleType.FIT_XY);
		imgeWorker.loadBitmap(item.getIconUrl(), holder.iView,
				PalmSudaApp.SCREEN_WEIDTH / 4, PalmSudaApp.SCREEN_WEIDTH / 4);

		GridView.LayoutParams lparams = new GridView.LayoutParams(
				PalmSudaApp.SCREEN_WEIDTH * 3 / 16 + 5,
				PalmSudaApp.SCREEN_WEIDTH / 4 + 10);
		convertView.setLayoutParams(lparams);
		return convertView;
	}

	class ViewHolder {
		TextView tvItem;
		ImageView iView;
		ImageView icView;
	}
}
