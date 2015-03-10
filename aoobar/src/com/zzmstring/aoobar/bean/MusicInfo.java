package com.zzmstring.aoobar.bean;

/**
 * Created by zzmstring on 2015/3/11.
 */
public class MusicInfo {
    private String file;// 文件名(通过文件名来作为唯一判断并且获得歌词)
    private String time;// 时长
    private String size;// 大小
    private String name;// 歌名
    private String artist;// 艺术家
    private String path;// 路径
    private String format;// 格式(编码类型)
    private String album;// 专辑
    private String years;// 年代
    private String channels;// 声道
    private String genre;// 风格
    private String kbps;// 比特率
    private String hz;// 采样率
    private int audioSessionId;// 音频会话ID
    private int mp3Duration;// 精确的音乐时长(用于SeekBar总长度)
    private boolean favorite;// 是否最爱
    /**
     * 获得文件名(通过文件名来作为唯一判断并且获得歌词)
     *
     * @return 文件名
     */
    public String getFile() {
        return file;
    }

    /**
     * 设置文件名(通过文件名来作为唯一判断并且获得歌词)
     *
     * @param file
     *            文件名
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * 获得时长
     *
     * @return 时长
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置时长
     *
     * @param time
     *            时长
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获得大小
     *
     * @return 大小
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置大小
     *
     * @param size
     *            大小
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * 获得歌名
     *
     * @return 歌名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置歌名
     *
     * @param name
     *            歌名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得艺术家
     *
     * @return 艺术家
     */
    public String getArtist() {
        return artist;
    }

    /**
     * 设置艺术家
     *
     * @param artist
     *            艺术家
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * 获得路径
     *
     * @return 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path
     *            路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获得格式(编码类型)
     *
     * @return 格式(编码类型)
     */
    public String getFormat() {
        return format;
    }

    /**
     * 设置格式(编码类型)
     *
     * @param format
     *            格式(编码类型)
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 获得专辑
     *
     * @return 专辑
     */
    public String getAlbum() {
        return album;
    }

    /**
     * 设置专辑
     *
     * @param album
     *            专辑
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * 获得年代
     *
     * @return 年代
     */
    public String getYears() {
        return years;
    }

    /**
     * 设置年代
     *
     * @param years
     *            年代
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * 获得声道
     *
     * @return 声道(一般情况是立体声)
     */
    public String getChannels() {
        return channels;
    }

    /**
     * 设置声道
     *
     * @param channels
     *            声道(一般情况是立体声)
     */
    public void setChannels(String channels) {
        this.channels = channels;
    }

    /**
     * 获得风格
     *
     * @return 风格
     */
    public String getGenre() {
        return genre;
    }

    /**
     * 设置风格
     *
     * @param genre
     *            风格
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * 获得比特率
     *
     * @return 比特率
     */
    public String getKbps() {
        return kbps;
    }

    /**
     * 设置比特率
     *
     * @param kbps
     *            比特率
     */
    public void setKbps(String kbps) {
        this.kbps = kbps;
    }

    /**
     * 获得采样率
     *
     * @return 采样率
     */
    public String getHz() {
        return hz;
    }

    /**
     * 设置采样率
     *
     * @param hz
     *            采样率
     */
    public void setHz(String hz) {
        this.hz = hz;
    }

    /**
     * 获得audioSessionId
     *
     * @return MediaPlayer.getAudioSessionId()
     */
    public int getAudioSessionId() {
        return audioSessionId;
    }

    /**
     * 设置audioSessionId
     *
     * @param audioSessionId
     *            MediaPlayer.getAudioSessionId()
     */
    public void setAudioSessionId(int audioSessionId) {
        this.audioSessionId = audioSessionId;
    }

    /**
     * 获得精确的音乐时长
     *
     * @return 音乐时长
     */
    public int getMp3Duration() {
        return mp3Duration;
    }

    /**
     * 设置精确的音乐时长
     *
     * @param mp3Duration
     *            音乐时长
     */
    public void setMp3Duration(int mp3Duration) {
        this.mp3Duration = mp3Duration;
    }

    /**
     * 是否最爱
     *
     * @return true||false
     */
    public boolean isFavorite() {
        return favorite;
    }

    /**
     * 设置最爱
     *
     * @param favorite
     *            true||false
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }



}
