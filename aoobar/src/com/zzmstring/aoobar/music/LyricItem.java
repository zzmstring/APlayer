package com.zzmstring.aoobar.music;

/**
 * By CWD 2013 Open Source Project
 * 
 * <br>
 * <b>�����Ϣ</b></br>
 * 
 * @author CWD
 * @version 2013.06.23 v1.0
 */
public class LyricItem {

	private String lyric; // ������
	private int time;// ���ʱ��

	/**
	 * ��õ�����
	 * 
	 * @return ������
	 */
	public String getLyric() {
		return lyric;
	}

	/**
	 * ���õ�����
	 * 
	 * @param lyric
	 *            ������
	 */
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	/**
	 * ��õ�����ʱ��
	 * 
	 * @return ������ʱ��
	 */
	public int getTime() {
		return time;
	}

	/**
	 * ���õ�����ʱ��
	 * 
	 * @param time
	 *            ������ʱ��
	 */
	public void setTime(int time) {
		this.time = time;
	}
}
