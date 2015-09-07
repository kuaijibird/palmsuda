package com.mialab.palmsuda.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

public class MyHScrollView extends HorizontalScrollView {
	
	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;
	private View inner;
	private Rect normal = new Rect();
	private int mTouchSlop;
	private int mTouchState = TOUCH_STATE_REST;
	private float mLastMotionX;
	private Scroller mScroller;

	public MyHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}

	@Override
	public void computeScroll() {
		mScroller.getCurrX();
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float x = event.getX();
		switch (action) {
			case MotionEvent.ACTION_DOWN :
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
				mLastMotionX = x;
				break;
			case MotionEvent.ACTION_MOVE :
				int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;
				scrollBy(deltaX, 0);
				if (isNeedMove()) {
					if (normal.isEmpty()) {
						normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
					}
					inner.layout(inner.getLeft() - deltaX / 2, inner.getTop(), inner.getRight() - deltaX / 2,
					        inner.getBottom());
				}
				break;
			case MotionEvent.ACTION_UP :
				if (isNeedAnimation()) {
					animation();
				}
				mTouchState = TOUCH_STATE_REST;
				break;
			case MotionEvent.ACTION_CANCEL :
				mTouchState = TOUCH_STATE_REST;
				break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		final float x = ev.getX();
		switch (action) {
			case MotionEvent.ACTION_MOVE :
				final int xDiff = (int) Math.abs(mLastMotionX - x);
				if (xDiff > mTouchSlop) {
					mTouchState = TOUCH_STATE_SCROLLING;
				}
				break;
			case MotionEvent.ACTION_DOWN :
				mLastMotionX = x;
				mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
				break;
			case MotionEvent.ACTION_CANCEL :
			case MotionEvent.ACTION_UP :
				mTouchState = TOUCH_STATE_REST;
				break;
		}
		return mTouchState != TOUCH_STATE_REST;

	}

	// 开启动画移动
	public void animation() {
		TranslateAnimation ta = new TranslateAnimation(inner.getLeft(), normal.left, 0, 0);
		ta.setDuration(200);
		inner.startAnimation(ta);
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();
	}
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}
	public boolean isNeedMove() {
		int offset = inner.getMeasuredWidth() - getWidth();
		int scrollX = getScrollX();
		if (scrollX == 0 || scrollX == offset) {
			return true;
		}
		return false;
	}

}
