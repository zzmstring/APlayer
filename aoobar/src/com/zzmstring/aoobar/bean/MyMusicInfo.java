package com.zzmstring.aoobar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zzmstring on 2015/3/12.
 */
public class MyMusicInfo implements Parcelable{
    public String file;
    public String path;
    public int time;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        dest.writeString(name);
//        dest.writeString(id);
//        dest.writeInt(age);
//        dest.writeString(sex);
        parcel.writeString(file);
        parcel.writeString(path);
        parcel.writeInt(time);
    }
    public static final Parcelable.Creator<MyMusicInfo> CREATOR = new Creator<MyMusicInfo>(){


        @Override
        public MyMusicInfo createFromParcel(Parcel parcel) {
//            CustomeParcelable cus = new CustomeParcelable();
//            cus.name = source.readString();
//            cus.id = source.readString();
//            cus.age = source.readInt();
//            cus.sex = source.readString();
            MyMusicInfo info=new MyMusicInfo();
            info.file=parcel.readString();
            info.path=parcel.readString();
            info.time=parcel.readInt();
            return info;
        }

        @Override
        public MyMusicInfo[] newArray(int i) {
            return new MyMusicInfo[i];
        }
    };


}
