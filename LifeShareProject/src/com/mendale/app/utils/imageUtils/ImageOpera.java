package com.mendale.app.utils.imageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;

import com.mendale.app.constants.URLS;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

public class ImageOpera {

	/**
	 * 根据不同的加载图片显示不同而构造不同的选项，主要配置图片加载过程中显示配置，缓存，显示动画
	 */
	private DisplayImageOptions options;
	private static ImageOpera util;
	private ImageLoader loader;
	
	File cacheDir =new File(URLS.SDCARD_DIR + "ButlerImage"+"/imageloader/Cache");
	
	private ImageOpera(Context ctx) {
		ImageLoaderConfiguration cfg = new ImageLoaderConfiguration
				.Builder(ctx)
				.defaultDisplayImageOptions(getDefaultOption())
				.diskCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径  
				.build();
		loader = ImageLoader.getInstance();
		loader.init(cfg);
	}

	public static ImageOpera getInstance(Context ctx) {
		if (util == null) {
			util = new ImageOpera(ctx);
		}
		return util;
	}
	
	/**
	 * 得到默认的option
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public DisplayImageOptions getDefaultOption(){
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true)   
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		return options;
	}
	
	/**
	 * 重新设置option
	 * @param options
	 */
	public void setOption(DisplayImageOptions options,Context ctx){
		this.options = options;
		ImageLoaderConfiguration cfg = new ImageLoaderConfiguration
				.Builder(ctx)
				.defaultDisplayImageOptions(options)
				.build();
		loader.init(cfg);
	}

	
	public Bitmap loadImage(String uri, Context context) {
		if (isNetworkConnected(context)) {
			loader.denyNetworkDownloads(true);
		}
		else {
			loader.denyNetworkDownloads(false);
		}
		return loader.loadImageSync(uri);
	}

	public  void loadImage(String uri,ImageView imageView){
		loader.displayImage(uri, imageView);
	}
	
	public void loadImage(String uri,ImageView imageView,DisplayImageOptions option){
		if(options == null){
			loader.displayImage(uri, imageView, this.options);
		}else{
			loader.displayImage(uri, imageView, option);
		}
	}
	
	public void loadImage(String uri,ImageView imageView,ImageLoadingListener listener){
		if(listener == null){
			loader.displayImage(uri, imageView);
		}else{
			loader.displayImage(uri, imageView, getDefaultOption(),listener);
		}
	}
	
	public void loadImage(String uri,ImageView imageView,DisplayImageOptions options,ImageLoadingListener listener){
		if(listener == null){
			loader.displayImage(uri, imageView, options);
		}else{
			loader.displayImage(uri, imageView, options, listener);
		}
	}
	
	public void loadImage(String uri,ImageView imageView,
			DisplayImageOptions options,
			ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener){
		if(progressListener == null){
			loader.displayImage(uri, imageView, options, listener);
		}else{
			loader.displayImage(uri, imageView, options, listener, progressListener);
		}
	}

	public void clearImageCache() {
		clearImageCache(loader);
	}

	@SuppressWarnings("deprecation")
	private void clearImageCache(ImageLoader loader) {
		loader.clearDiscCache();
		loader.clearMemoryCache();
	}

	/**
	 * bitmap保存到本地不压缩
	 * @param toPath 保存到的文件路径
	 * @param bitmap png格式的
	 */
	public void compressBitmapToFile(String toPath, Bitmap bitmap) {
		File f = new File(toPath);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);//将图像读取到fOut中
		} catch (IOException e) {
			print("IO流异常");
			e.printStackTrace();
		}finally{
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				print("流关闭错误，IO流异常");
				e.printStackTrace();
			}
		}
	}
	/**
	 * bitmap保存到本地并压缩
	 * @param toPath 保存到的文件路径
	 * @param bitmap png格式的
	 * @param offset 压缩比 1-100   例如 30  就是压缩70%
	 */
	public void compressBitmapToFile(String toPath, Bitmap bitmap,int offset) {
		File f = new File(toPath);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, offset, fOut);//将图像读取到fOut中
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 进行图片缩放和质量压缩 并写入文件
	 * @param fromPath 源文件
	 * @param toPath 目标文件
	 * @return 缩放过后的bitmap
	 * @throws Exception
	 */
	public Bitmap compImageScale(String fromPath,String toPath) throws Exception{
		Bitmap bitmap = BitmapFactory.decodeFile(fromPath, new Options());
		FileOutputStream fops = new FileOutputStream(toPath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		Log.e("LogUtil", "baos当前图片压缩为："+baos.toByteArray().length / 1024+"kb");
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap map = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,baos.toByteArray().length, newOpts);
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		Log.e("LogUtil", "outWidth:"+w+"----------"+"outHeight:"+h);
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float ww = 1280f;// 这里设置宽度为600f
		float hh = 720f;// 这里设置高度为800f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可 
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		}
		else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		Log.e("LogUtil", "be:"+be);
		newOpts.inSampleSize = be;// 设置缩放比例
		map = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,baos.toByteArray().length, newOpts);
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		map.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
		Log.e("LogUtil", "baos1当前图片压缩为："+baos1.toByteArray().length / 1024+"kb");
		fops.write(baos1.toByteArray());
		fops.flush();
		fops.close();
		return map;
	}
	
	/**
	  * @param fromPath 源文件
	 * @param toPath 目标文件
	 * @return 缩放过后的bitmap
	 * @throws Exception
	 */
	public Bitmap compImageScale(Bitmap bitmap,String filePath) throws Exception{
		FileOutputStream fops = new FileOutputStream(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		Log.e("LogUtil", "baos当前图片压缩为："+baos.toByteArray().length / 1024+"kb");
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap map = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,baos.toByteArray().length, newOpts);
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		Log.e("LogUtil", "outWidth:"+w+"----------"+"outHeight:"+h);
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float ww = 1200f;// 这里设置宽度为800f
		float hh = 720f;// 这里设置高度为600f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可 
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		}
		else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		Log.e("LogUtil", "be:"+be);
		newOpts.inSampleSize = be;// 设置缩放比例
		map = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,baos.toByteArray().length, newOpts);
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		map.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
		Log.e("LogUtil", "baos1当前图片压缩为："+baos1.toByteArray().length / 1024+"kb");
		fops.write(baos1.toByteArray());
		fops.flush();
		fops.close();
		return map;
	}

	/**
	 * 质量压缩Bitmap质量并写入文件
	 * @param image bitmap对象
	 * @param filePath	文件路径
	 * @param size	文件大小（单位：kb）
	 * @return 
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	public Bitmap compressToFile(Bitmap image,String filePath,int size) throws Exception{
		FileOutputStream fops = new FileOutputStream(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		Log.e("LogUtil", "当前图片原大小为："+baos.toByteArray().length / 1024+"kb");
		while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于(size)kb,大于继续压缩
			if(options < 0)
				return null;
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 5;// 每次都减少5
		}
		Log.e("LogUtil", "当前图片压缩为："+baos.toByteArray().length / 1024+"kb");
		fops.write(baos.toByteArray());
		fops.flush();
		fops.close();
		return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
	}
	
	
	/**
	 * 质量压缩Bitmap并写入文件
	 * @param frompath 文件路径
	 * @param toPath	文件路径
	 * @param size	文件大小（单位：kb）
	 * @return 
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	public Bitmap compressToFile(String fromPath, String toPath, int size)
			throws Exception {
		Bitmap bitmap = BitmapFactory.decodeFile(fromPath, new Options());
		if (bitmap != null) {
			FileOutputStream fops = new FileOutputStream(toPath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 90;
			while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于600kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				if (options < 0)
					return null;
				bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 5;
			}
			System.out.println("options的值" + options);
			Log.e("LogUtil", "压缩后的图片大小为：" + baos.toByteArray().length / 1024 + "kb");
			fops.write(baos.toByteArray());
			fops.flush();
			fops.close();
			return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
		} else {
			System.out.println("获得bitmap的对象是空");
			return null;
		}
	}


	/**
	 * 获取网络图片
	 * @param zipfile
	 * @param uri
	 * @return
	 */
	public Bitmap downloadImageFile(String uri) {
		URL newUrl;
		InputStream is = null;
		Bitmap download_bitmap = null;
		try {
			newUrl = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
			conn.setConnectTimeout(15000);
			conn.setReadTimeout(10000);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				download_bitmap = BitmapFactory.decodeStream(is);
			}
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			Log.i("123", "超时");
			return download_bitmap;
		}catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i("123", "url错误");
			return download_bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("123", "网络错误");
			return download_bitmap;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return download_bitmap;
	}

	/**
	 * 加载本地图片 Bitmap http://bbs.3gstdy.com
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 文件拷贝
	 * @param s 源文件
	 * @param t 新文件
	 */
	public static void copyFile(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * 判断网络是否连接
	 * @param context
	 * @return true 连接中
	 */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	public void print(String msg){
		Log.e("ImageUtil",msg);
	}
}
