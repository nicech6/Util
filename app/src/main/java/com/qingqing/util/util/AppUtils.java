package com.qingqing.util.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.qingqing.util.dto.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    public static List<AppInfo> scanLocalInstallAppList(Context context, PackageManager packageManager) {
        List<AppInfo> list = new ArrayList<AppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //  过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                if (TextUtils.isEmpty(packageInfo.packageName)) {
                    continue;
                }
                if (!packageInfo.packageName.startsWith("com.qingqing")) {
                    continue;
                }
                if (packageInfo.packageName.endsWith("util")) {
                    continue;
                }
                AppInfo appInfo = new AppInfo();
                appInfo.setPackageName(packageInfo.packageName);
                appInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                appInfo.setAppIcon(drawableToBitmap(packageInfo.applicationInfo.loadIcon(packageManager)));
                list.add(appInfo);
            }
        } catch (Exception e) {
            Log.e("TAG", "===============获取应用包信息失败");
        }
        return list;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 检测其他应用是否处于debug模式。
     */
    public static boolean isApkDebugable(Context context, String packageName) {
        try {
            PackageInfo pkginfo = context.getPackageManager().getPackageInfo(
                    packageName, 1);
            if (pkginfo != null) {
                ApplicationInfo info = pkginfo.applicationInfo;
                return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 删除一个文件或者目录
     */
    public static boolean delete(File fileOrDir) {
        if (fileOrDir == null || !fileOrDir.exists())
            return false;

        if (fileOrDir.isFile()) {
            return fileOrDir.delete();
        } else if (fileOrDir.isDirectory()) {
            return clearDir(fileOrDir) && fileOrDir.delete();
        }

        return false;
    }


    /**
     * 清空一个指定的目录以及里面所有包含的文件，支持嵌套
     */
    public static boolean clearDir(File dir) {

        if (dir == null || !dir.exists() || dir.isFile())
            return false;

        boolean ret = true;
        if (dir.isDirectory()) {
            File[] fileList = dir.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (File f : fileList) {
                    if (f.isFile()) {
                        if (!f.delete())
                            ret = false;
                    } else if (f.isDirectory()) {
                        String[] subFiles = f.list();
                        if (subFiles != null && subFiles.length > 0) {
                            if (!clearDir(f))
                                ret = false;
                            if (!f.delete())
                                ret = false;
                        } else {
                            if (!f.delete())
                                ret = false;
                        }
                    }
                }
            }
        }
        return ret;
    }

}
