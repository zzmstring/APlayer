package com.zzmstring.aoobar.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZGL on 2015/3/10.
 */
public class Dao {
    private static Dao dao=null;
    private Context context;
    private  Dao(Context context) {
        this.context=context;
    }
    public static  Dao getInstance(Context context){
        if(dao==null){
            dao=new Dao(context);
        }
        return dao;
    }
    public  SQLiteDatabase getConnection() {
        SQLiteDatabase sqliteDatabase = null;
        try {
            sqliteDatabase= new DBHelper(context).getReadableDatabase();
        } catch (Exception e) {
        }
        return sqliteDatabase;
    }

    /**
     * 查看数据库中是否有数据
     */
    public boolean isHasInfors(String urlstr) {
        SQLiteDatabase database = getConnection();
        int count = -1;
        Cursor cursor = null;
        try {
            String sql = "select count(*)  from download_info where url=?";
            cursor = database.rawQuery(sql, new String[] { urlstr });
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
            if (null != cursor) {
                cursor.close();
            }
        }
        return count == 0;
    }

    /**
     * 保存 下载的具体信息
     */
//    public synchronized void saveInfo(DownloadInfo info) {
//        SQLiteDatabase database = getConnection();
//        try {
//            String sql = "insert into download_info(file_id,name, fileSize,compelete_size,url,listUrl) values (?,?,?,?,?,?)";
//            Object[] bindArgs = { info.getId(), info.getName(),
//                    info.getFileSize(), info.getCompeleteSize(),
//                    info.getUrl(),info.getListUrl() };
//            database.execSQL(sql, bindArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != database) {
//                database.close();
//            }
//        }
//    }
//    public synchronized void saveInfos(List<DownloadInfo> infos) {
//        SQLiteDatabase database = getConnection();
//        try {
//            for (DownloadInfo info : infos) {
//                String sql = "insert into download_info(file_id,name, fileSize,compelete_size,url,listUrl) values (?,?,?,?,?,?)";
//                Object[] bindArgs = { info.getId(), info.getName(),
//                        info.getFileSize(), info.getCompeleteSize(),
//                        info.getUrl(),info.getListUrl() };
//                database.execSQL(sql, bindArgs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != database) {
//                database.close();
//            }
//        }
//    }

    /**
     * 得到下载具体信息
     */
//    public synchronized DownloadInfo getInfo(String url) {
//        SQLiteDatabase database = getConnection();
//        DownloadInfo info = null;
//        Cursor cursor = null;
//        try {
//            String sql = "select file_id, name, fileSize,compelete_size,url,listUrl from download_info where url=?";
//            cursor = database.rawQuery(sql, new String[] { url });
//            while (cursor.moveToNext()) {
//                info = new DownloadInfo(cursor.getInt(0),
//                        cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
//                        cursor.getString(4),cursor.getString(5));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != database) {
//                database.close();
//            }
//            if (null != cursor) {
//                cursor.close();
//            }
//        }
//        return info;
//    }
//	public synchronized List<DownloadInfo> getInfos(String listUrl) {
//		List<DownloadInfo> list = new ArrayList<DownloadInfo>();
//		SQLiteDatabase database = getConnection();
//		Cursor cursor = null;
//		try {
//			String sql = "select file_id, name, fileSize,compelete_size,url,listUrl from download_info where listUrl=?";
//			cursor = database.rawQuery(sql, new String[] { listUrl });
//			while (cursor.moveToNext()) {
//				DownloadInfo info = new DownloadInfo(cursor.getInt(0),
//						cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
//						cursor.getString(4),cursor.getString(5));
//				list.add(info);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != database) {
//				database.close();
//			}
//			if (null != cursor) {
//				cursor.close();
//			}
//		}
//		return list;
//	}

    /**
     * 更新数据库中的下载信息
     */
    public synchronized void updataInfos(int compeleteSize, String urlstr) {
        SQLiteDatabase database = getConnection();
        try {
            String sql = "update download_info set compelete_size=? where url=?";
            Object[] bindArgs = { compeleteSize,urlstr };
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

    /**
     * 下载完成后删除数据库中的数据
     */
    public synchronized void delete(String url) {
        SQLiteDatabase database = getConnection();
        try {
            database.delete("download_info", "url=?", new String[] { url });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }
}
