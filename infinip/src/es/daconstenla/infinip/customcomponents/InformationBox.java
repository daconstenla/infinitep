package es.daconstenla.infinip.customcomponents;

import es.daconstenla.infinip.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class InformationBox extends View {

	private Paint mPaintBackgroundExternal;
	private Paint mPaintBackgroundInternal;
	
	public InformationBox(Context context) {
		super(context);
		init();
	}
	public InformationBox(Context context, AttributeSet attr){
		super(context,attr);
		init();
	}
	
	private void init() {
		Resources res = getResources();
		
		mPaintBackgroundExternal = new Paint();
		mPaintBackgroundExternal.setColor(res.getColor(R.color.customGreyLight2));
		mPaintBackgroundInternal = new Paint();
		mPaintBackgroundExternal.setColor(res.getColor(R.color.customGreyLight1));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(0, 0,getWidth(),getHeight(),mPaintBackgroundExternal);
		canvas.drawRect(5, 5,getWidth()-5,getHeight()-5,mPaintBackgroundInternal);
	}
}
