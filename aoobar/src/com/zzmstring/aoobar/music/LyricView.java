package com.zzmstring.aoobar.music;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * �滭��ʣ��Զ�����Ч��
 * 
 * @author CWD
 * @version 2013.01.24 v1.0
 */

/**
 * By CWD 2013 Open Source Project
 * 
 * <br>
 * <b>�滭��ʣ��Զ�����Ч����OKЧ��</b></br>
 * 
 * ��Щ��ʺܳ������пռ䲻����ȫ��ʾ��Ҫ�������춯��һ���Զ�������̫���ˣ�<br>
 * ����ʣ�µĹ������ɸ�λ��һ���Ż������ˡ�����
 * 
 * @author CWD
 * @version 2013.06.23 v1.0 ʵ������ģʽ�ĸ��<br>
 *          2013.08.24 v1.1 �����޸ĸ�ʸ���ɫ</br>
 */
public class LyricView extends TextView {

	private int index = 0;// ����ڼ�����
	private int lyricSize = 0;// ����ܾ���

	private int currentTime = 0;// ��ǰ����Ĳ���λ��
	private int dunringTime = 0;// ��ǰ���ʵĳ���ʱ��
	private int startTime = 0;// ��ǰ���ʿ�ʼ��ʱ��

	private float width = 0;// ��õĻ�����
	private float height = 0;// ��õĻ�����
	private float tempW = 0;// ���㻭�����м�λ��(��)
	private float tempH = 0;// ���㻭�����м�λ��(��)
	private float tempYHigh = 0;// ���㿨��OKģʽ��һ���Y��λ��
	private float tempYLow = 0;// ���㿨��OKģʽ�ڶ����Y��λ��

	private float textHeight = 35;// �����ָ߶�
	private float textSize = 20;// �����С

	private Paint currentPaint = null;// ��ǰ�仭��
	private Paint defaultPaint = null;// �ǵ�ǰ�仭��
	private List<LyricItem> mSentenceEntities = new ArrayList<LyricItem>();
	private int[] paintColorsCurrent = { Color.argb(250, 251, 248, 29),
			Color.argb(250, 255, 255, 255) };// ����OKģʽ�滭�л�����ɫ����
	private int[] paintColorsDefault = { Color.argb(250, 255, 255, 255),
			Color.argb(250, 255, 255, 255) };// ����OKģʽĬ�ϻ�����ɫ����

	private boolean isKLOK = false;

	public LyricView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public LyricView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public LyricView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		setFocusable(true);

		// ��������
		currentPaint = new Paint();
		currentPaint.setAntiAlias(true);
		currentPaint.setTextAlign(Paint.Align.CENTER);
		currentPaint.setColor(Color.argb(250, 251, 248, 29));
		currentPaint.setTextSize(textSize);
		currentPaint.setTypeface(Typeface.SERIF);

		// �Ǹ�������
		defaultPaint = new Paint();
		defaultPaint.setAntiAlias(true);
		defaultPaint.setTextAlign(Paint.Align.CENTER);
		defaultPaint.setColor(Color.argb(250, 255, 255, 255));
		defaultPaint.setTextSize(textSize);
		defaultPaint.setTypeface(isKLOK ? Typeface.SERIF : Typeface.DEFAULT);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (canvas == null || lyricSize <= 0
				|| index >= mSentenceEntities.size()) {
			return;
		}

		if (isKLOK) {
			int nextIndex = index + 1;// ��һ��
			String text = mSentenceEntities.get(index).getLyric();// ��ø��
			float len = this.getTextWidth(currentPaint, text);// �þ��ʾ�ȷ����
			float position = dunringTime == 0 ? 0
					: ((float) currentTime - (float) startTime)
							/ (float) dunringTime;// ���㵱ǰλ��

			if (index % 2 == 0) {
				float start1 = len / 2;// ��һ������λ��
				LinearGradient gradient = new LinearGradient(0, 0, len, 0,
						paintColorsCurrent, new float[] { position, position },
						TileMode.CLAMP);// �ػ潥��
				currentPaint.setShader(gradient);
				canvas.drawText(text, start1, tempYHigh, currentPaint);

				if (nextIndex < lyricSize) {
					text = mSentenceEntities.get(nextIndex).getLyric();// ��һ����
					len = this.getTextWidth(currentPaint, text);// �þ��ʾ�ȷ����
					float start2 = width - len / 2;// �ڶ�������λ��
					gradient = new LinearGradient(start2, 0, width, 0,
							paintColorsDefault, null, TileMode.CLAMP);// �ػ潥��
					defaultPaint.setShader(gradient);
					canvas.drawText(text, start2, tempYLow, defaultPaint);
				}
			} else {
				float start2 = width - len / 2;// �ڶ�������λ��
				float w = width > len ? width - len : 0;// �ڶ���Ľ������λ��
				LinearGradient gradient = new LinearGradient(w, 0, width, 0,
						paintColorsCurrent, new float[] { position, position },
						TileMode.CLAMP);// �ػ潥��
				defaultPaint.setShader(gradient);
				canvas.drawText(text, start2, tempYLow, defaultPaint);

				if (nextIndex < lyricSize) {
					text = mSentenceEntities.get(nextIndex).getLyric();// ��һ����
					len = this.getTextWidth(currentPaint, text);// �þ��ʾ�ȷ����
					float start1 = len / 2;// ��һ������λ��
					gradient = new LinearGradient(0, 0, len, 0,
							paintColorsDefault, null, TileMode.CLAMP);// �ػ潥��
					currentPaint.setShader(gradient);
					canvas.drawText(text, start1, tempYHigh, currentPaint);
				}
			}
		} else {
			float plus = dunringTime == 0 ? 0
					: (((float) currentTime - (float) startTime) / (float) dunringTime)
							* (float) 30;
			// ���Ϲ��� ����Ǹ�ݸ�ʵ�ʱ�䳤������������������
			canvas.translate(0, -plus);

			try {
				canvas.drawText(mSentenceEntities.get(index).getLyric(), tempW,
						tempH, currentPaint);

				float tempY = tempH;
				// ��������֮ǰ�ľ���
				for (int i = index - 1; i >= 0; i--) {
					// ��������
					tempY = tempY - textHeight;

					canvas.drawText(mSentenceEntities.get(i).getLyric(), tempW,
							tempY, defaultPaint);
				}
				tempY = tempH;
				// ��������֮��ľ���
				for (int i = index + 1; i < lyricSize; i++) {
					// ��������
					tempY = tempY + textHeight;
					if (tempY > height) {
						break;
					}
					canvas.drawText(mSentenceEntities.get(i).getLyric(), tempW,
							tempY, defaultPaint);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		this.width = w;
		this.height = h;
		this.tempW = w / 2;
		this.tempH = h / 2;
		this.tempYHigh = tempH;
		this.tempYLow = tempH + textHeight;
	}

	/**
	 * ��ȷ�������ֿ��
	 * 
	 * @param paint
	 * @param str
	 * @return
	 */
	private int getTextWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}

	/**
	 * �Ƿ����ڿ���OKģʽ
	 * 
	 * @param isKLOK
	 *            true:��
	 */
	public void setKLOK(boolean isKLOK) {
		this.isKLOK = isKLOK;
	}

	/**
	 * ��ĸ�ʸ���ɫ
	 * 
	 * @param color
	 *            ��ɫֵ
	 */
	public void setLyricHighlightColor(int color) {
		paintColorsCurrent = new int[] { color, Color.argb(250, 255, 255, 255) };
		currentPaint.setColor(color);
	}

	/**
	 * �����
	 * 
	 * @param mSentenceEntities
	 *            ��ʼ���
	 */
	public void setSentenceEntities(List<LyricItem> mSentenceEntities) {
		this.mSentenceEntities = mSentenceEntities;
		this.lyricSize = mSentenceEntities.size();
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param indexInfo
	 *            <b>{�������,��ǰ����Ĳ���λ��,��ǰ���ʵĳ���ʱ��,��ǰ���ʿ�ʼ��ʱ��}</b>
	 */
	public void setIndex(int[] indexInfo) {
		this.index = indexInfo[0];
		this.currentTime = indexInfo[1];
		this.startTime = indexInfo[2];
		this.dunringTime = indexInfo[3];
	}

	/**
	 * ��ղ���
	 * 
	 * (��������ò�������⣬���service���List<LyricItem>Ҳ��գ��ѵ������ڴ��ˣ���ֵ��ָ����ͬһ���ڴ��ַ??)
	 */
	public void clear() {
		this.mSentenceEntities.clear();
		this.index = 0;
		this.lyricSize = 0;
		this.invalidate();
	}
}
