package Tasksss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/*
*  封装一个万能的下载并解析json数据的异步任务
*  该类封装完成后，可以解决此工程中所有的网络连接及解析操作
*  当然，也可将此类拷贝其他工程中继续使用
*
*  使用自定义泛型去指代任意类型
* */
public class WNTextTask<T> extends AsyncTask<String, Void, T> {

	public interface TextCallback<T> {
		void getText(T result);
	}

	private TextCallback back;
	private ProgressDialog pd;

	private Class<T> clazz;

	public WNTextTask(TextCallback back, Context context,Class<T> clazz) {

		super();
		this.back = back;
		pd = new ProgressDialog(context);
		pd.setMessage("loading... ...");
		pd.setCancelable(false);
		this.clazz = clazz;
	}
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd.show();
	}

	@Override
	protected  T doInBackground(
			String... params) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new  URL(params[0]).openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			if (conn.getResponseCode() == 200 ) {
				InputStream is = conn.getInputStream();
				byte[] b = new byte[1024];
				int num = -1;
				StringBuffer sb = new StringBuffer();
				while((num = is.read(b)) != -1) {
					sb.append(new String(b,0,num));
				}
				String json = sb.toString();

				T bean = new Gson().fromJson(json, clazz);

				return bean;
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(T result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.i("tmd", "====  "+back+"  "+result);
		pd.dismiss();
		if (back != null && result != null) {
			back.getText(result);
		}
	}
	

}
