package com.mialab.palmsuda.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mialab.palmsuda.main.R;

public class VScrollTextView extends ViewFlipper {
	private TextView tView;
	private List<TitleAdItem> vInfos;
	private int currentIndex = 0;

	int scrollTime = 9000;
	private int type = 0; // 0 横向滚动，1 竖直滚动

	private TextClickListener ilistener;

	public VScrollTextView(Context context) {
		super(context);
		init();
	}

	public VScrollTextView(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	private void init() {
		setFlipInterval(scrollTime);
		setInAnimation(getContext(), R.anim.push_up_in);
		setOutAnimation(getContext(), R.anim.push_up_out);
		tView = new TextView(getContext());
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		tView.setLayoutParams(params);
		tView.setGravity(Gravity.CENTER);
		tView.setTextColor(Color.WHITE);
		tView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (ilistener != null) {
					if (vInfos != null&&vInfos.size()>0)
						ilistener.onTextClick(vInfos.get(currentIndex), currentIndex);
				}
			}
		});
		this.addView(tView);
	}

	public void setOnTextClickListener(TextClickListener i) {
		ilistener = i;
	}

	public void setTxtInfos(List<TitleAdItem> infos) {
		if (infos != null && infos.size() > 0) {
			vInfos = infos;
			if(vInfos.size()==1){
				type = 0;
			}else {
				type = 1;
			}
			notifyDataSetChange();
		}
	}

	public void setTxtInfo(String msg) {
		if (msg != null) {
			TitleAdItem titleAdItem=new TitleAdItem();
			titleAdItem.setMsg(""+msg);
			titleAdItem.setParam("");
			titleAdItem.setUrl("");
			vInfos =new ArrayList<TitleAdItem>();
			vInfos.add(titleAdItem);
			type = 0;
			notifyDataSetChange();
		}
	}
	
	public void notifyDataSetChange() {
		currentIndex = 0;
		if (type == 0) {
			setAutoStart(false);
			stopFlipping();
			clearAnimation();
			TitleAdItem item=vInfos.get(currentIndex);
			tView.setText(item.getMsg());
		} else if (type == 1) {
			tView.setText(vInfos.get(currentIndex).getMsg());
			Animation animation = getInAnimation();
			if (animation != null) {
				animation.setAnimationListener(new Animation.AnimationListener() {
							public void onAnimationStart(Animation animation) {
								if (currentIndex < (vInfos.size() - 1)) {
									currentIndex++;
								} else {
									currentIndex = 0;
								}
								tView.setText(vInfos.get(currentIndex).getMsg());
							}

							public void onAnimationRepeat(Animation animation) {
							}

							public void onAnimationEnd(Animation animation) {
							}
						});
			}
			this.startFlipping();
			setAutoStart(true);
		}
		tView.setSingleLine(true);
		tView.setFocusableInTouchMode(true);
		tView.setHorizontallyScrolling(true);
		tView.setEllipsize(TruncateAt.MARQUEE);
		tView.setMarqueeRepeatLimit(-1);
		tView.setFocusable(true);
		tView.requestFocus();
	}

	public void setTextSize(Context ct, float size) {
		tView.setTextSize(size);//DisplayUtil.getInstance((Activity) ct).sp2px(size));
	}

	public void setTextColor(int color) {
		tView.setTextColor(color);
	}

	public TextView getTextView() {
		return tView;
	}

	public void setScrollDTime(int mSec) {
		scrollTime = mSec;
	}

	public interface TextClickListener {
		public void onTextClick(TitleAdItem titem, int index);
	}

}
