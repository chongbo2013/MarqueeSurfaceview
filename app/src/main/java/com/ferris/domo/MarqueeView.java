package com.ferris.domo;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2015/12/4.
 */
public class MarqueeView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private RefreshThread myThread;

    protected TextPaint mTextPaint;

    protected int ShadowColor=Color.BLACK;
    protected float textSize = 100; //时间数字的字体大小
    protected int textColor = Color.RED; //时间数字的颜色
    private String margueeString;
    private int textWidth=0,textHeight=0;
    protected float padTextSize=0;

    public MarqueeView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(null, 0);
    }
    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public void init(AttributeSet attrs, int defStyle){
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.marquee, defStyle, 0);
        textColor = a.getColor(
                R.styleable.marquee_textcolor,
                Color.RED);
        textSize = a.getDimension(
                R.styleable.marquee_textSize,
                48);

        holder = this.getHolder();
        holder.addCallback(this);
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        myThread = new RefreshThread(holder);//创建一个绘图线程
    }

    public void setText(String msg){
        if(!TextUtils.isEmpty(msg)){
            measurementsText(msg);
        }
    }
    protected void measurementsText(String msg) {
        margueeString=msg;
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setFakeBoldText(true);
        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
        mTextPaint.setShadowLayer(5, 3, 3, ShadowColor);
        textWidth = (int)mTextPaint.measureText(margueeString);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        textHeight = (int) fontMetrics.bottom;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        myThread.isRun = true;
        myThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        myThread.isRun = false;
    }



    //线程内部类
    class RefreshThread extends Thread
    {
        private SurfaceHolder holder;
        public boolean isRun ;

        public int currentX=0;
        public int sepX=5;
        public  RefreshThread(SurfaceHolder holder)
        {
            this.holder =holder;
            isRun = true;
        }

        public void onDraw() {
            try
            {
                synchronized (holder)
                {

                    if(TextUtils.isEmpty(margueeString)){
                        Thread.sleep(1000);//睡眠时间为1秒
                        return;
                    }

                    Canvas canvas = holder.lockCanvas();
                    // TODO: consider storing these as member variables to reduce



                    int paddingLeft = getPaddingLeft();
                    int paddingTop = getPaddingTop();
                    int paddingRight = getPaddingRight();
                    int paddingBottom = getPaddingBottom();

                    int contentWidth = getWidth() - paddingLeft - paddingRight;
                    int contentHeight = getHeight() - paddingTop - paddingBottom;

                    int centeYLine = paddingTop + contentHeight / 2;//中心线


                  if(currentX>=contentWidth){
                      currentX=-textWidth;
                  }else{
                      currentX+=sepX;
                  }

                    canvas.drawColor(Color.BLACK);
                    canvas.drawText(margueeString,currentX,
                            centeYLine-textHeight/2,
                            mTextPaint);
                    holder.unlockCanvasAndPost(canvas);//结束锁定画图，并提交改变。
                    Thread.sleep(1000);//睡眠时间为1秒
                }
            }
            catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

        @Override
        public void run()
        {

            while(isRun)
            {

                onDraw();

            }
        }
    }

}
