package com.mialab.palmsuda.main;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mialab.palmsuda.modules.ContentItem;
import com.mialab.palmsuda.modules.ModuleItem;
import com.mialab.palmsuda.views.MyScrollView;

/**
 * @author mialab
 * @date 创建时间：2015-8-22 下午7:06:05
 * 
 */
public class MainViewHelper {
	private PalmSudaHome mActivity;
	private LinearLayout barDots, bottomBar;
	private MyScrollView scrollView;
	private ContentItem mAItem;
	private LayoutInflater mInflater = null;

	private List<ModuleItem> gridItems, barItems;
	private int PAGENUM = 16;
	private int vspace = 0, hspace = 0;
	float oldX = 0;

	MainViewHelper(PalmSudaHome maty) {
		mActivity = maty;
		// 找到相应的View
		scrollView = (MyScrollView) mActivity.findViewById(R.id.vflipper);
		bottomBar = (LinearLayout) mActivity.findViewById(R.id.bar_item_holder);
		barDots = (LinearLayout) mActivity.findViewById(R.id.bar_item_dot);

		mInflater = LayoutInflater.from(mActivity);

		gridItems = new ArrayList<ModuleItem>();
		barItems = new ArrayList<ModuleItem>();

		scrollView.setOnViewChangedListener(new MyScrollView.OnViewChanged() {
			@Override
			public void onViewChange(int position, int count) {
				setFlipperDots(position, count);
			}
		});
	}

	public void setContentItem(ContentItem aItem) {
		mAItem = aItem;
		notifyDataChange();
	}

	public void onDestroy() {
		scrollView.removeAllViews();
		barDots.removeAllViews();
		if (gridItems != null)
			gridItems.clear();
		if (barItems != null)
			barItems.clear();
	}

	public void notifyDataChange() {
		bottomBar.removeAllViews();
		scrollView.removeAllViews();
		barDots.removeAllViews();
		setSomeData();
		if (gridItems != null)
			gridItems.clear();
		if (barItems != null)
			barItems.clear();
		if (mAItem != null) {
			List<ModuleItem> ms = mAItem.getmItems();
			for (int i = 0; i < ms.size(); i++) {
				ModuleItem item = ms.get(i);
				if (item != null) {
					if (item.getLevel() == 4) {
						barItems.add(item);
					} else {
						gridItems.add(item);
					}
				}
			}
			setFlipperView(gridItems);
			setBarView(barItems);
		}
	}

	private void setSomeData() {
		float sh = PalmSudaApp.SCREEN_HEIGTH;
		float wh = PalmSudaApp.SCREEN_WEIDTH;
		int ih = (int) (wh / 4 + 10);
		int iw = (int) (wh / 4 - 15);
		int col = (int) ((sh - ih * 2) / ih);

		if (col < 4) {
			PAGENUM = 12;
			vspace = (int) ((sh - (col + 2) * ih) / 4);
		} else if (col == 4) {
			PAGENUM = 16;
			vspace = (int) ((sh - 6 * ih) / 4);
			// vspace = 0;
		} else if (col == 5) {
			PAGENUM = 16;
			vspace = (int) ((sh - 6 * ih) / 4);
			// vspace = 0;
		} else if (col == 6) {
			PAGENUM = 20;
			vspace = (int) ((sh - 7 * ih) / 5);
		} else if (col > 6) {
			PAGENUM = 24;
			vspace = (int) ((sh - 8 * ih) / 6);
		}
		hspace = (int) ((wh - 4 * iw) / 4);
	}

	private void setFlipperView(List<ModuleItem> gridItems) {
		int size = gridItems.size();
		int count = size / PAGENUM + (size % PAGENUM == 0 ? 0 : 1);
		for (int i = 0; i < count; i++) {
			List<ModuleItem> tmp = null;
			GridView gView = null;
			if (i == (count - 1)) {
				tmp = gridItems.subList((count - 1) * PAGENUM, size);
				gView = createGridView(tmp);
			} else {
				tmp = gridItems.subList(i * PAGENUM, i * PAGENUM + PAGENUM);
				gView = createGridView(tmp);
			}

			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);
			scrollView.addView(gView, params);
		}
		scrollView.setToScreen(0);
		setFlipperDots(0, scrollView.getChildCount());
	}

	private void setFlipperDots(int index, int count) {
		int num = barDots.getChildCount();
		if (num == 0) {
			for (int i = 0; i < count; i++) {
				ImageView dot = new ImageView(mActivity);
				dot.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				dot.setPadding(5, 1, 5, 1);
				dot.setImageResource(R.drawable.life_home_blue_circle);
				barDots.addView(dot);
			}
		}
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				ImageView dot = (ImageView) barDots.getChildAt(i);
				if (i == index) {
					dot.setImageResource(R.drawable.life_home_blue_circle);
				} else {
					dot.setImageResource(R.drawable.life_home_white_circle);
				}
			}
		}
	}

	private void setBarView(List<ModuleItem> barItems) {
		if (barItems == null || barItems.size() == 0) {
			mActivity.findViewById(R.id.bar_scroll_view).setVisibility(
					View.GONE);
		} else {
			mActivity.findViewById(R.id.bar_scroll_view).setVisibility(
					View.VISIBLE);
		}
		for (final ModuleItem item : barItems) {
			View baritem = createBarItem(item);
			baritem.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mActivity.gridViewClick(mAItem, item);
				}
			});
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					PalmSudaApp.SCREEN_WEIDTH / 4,
					PalmSudaApp.SCREEN_WEIDTH / 4 + 10);
			bottomBar.addView(baritem, params);
		}
	}

	private View createBarItem(final ModuleItem item) {
		View convertView = this.mInflater
				.inflate(R.layout.grid_icon_item, null);
		TextView tvItem = (TextView) convertView
				.findViewById(R.id.wap_item_name);
		ImageView iView = (ImageView) convertView
				.findViewById(R.id.wap_item_image);
		ImageView icView = (ImageView) convertView
				.findViewById(R.id.wap_ic_image);
		icView.setVisibility(View.GONE);
		tvItem.setText("" + item.getModuleName());
		RelativeLayout.LayoutParams paLayoutParams = (android.widget.RelativeLayout.LayoutParams) iView
				.getLayoutParams();
		paLayoutParams.width = PalmSudaApp.SCREEN_WEIDTH * 3 / 16;
		paLayoutParams.height = paLayoutParams.width * 100 / 96;
		iView.setLayoutParams(paLayoutParams);
		mActivity.getImageWorker().loadBitmap(item.getIconUrl(), iView,
				PalmSudaApp.SCREEN_WEIDTH / 4, PalmSudaApp.SCREEN_WEIDTH / 4);
		return convertView;
	}

	private GridView createGridView(List<ModuleItem> items) {
		GridView gView = (GridView) mInflater.inflate(R.layout.main_gridview,
				null);
		gView.setVerticalSpacing(vspace);
		gView.setPadding(0, vspace / 2, 0, 0);
		GdvAdapter adapter = new GdvAdapter(mActivity,
				mActivity.getImageWorker(), gView);
		adapter.setData(items);
		gView.setAdapter(adapter);
		gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ModuleItem item = (ModuleItem) arg0.getAdapter().getItem(arg2);
				mActivity.gridViewClick(mAItem, item);
			}
		});
		return gView;
	}
}
