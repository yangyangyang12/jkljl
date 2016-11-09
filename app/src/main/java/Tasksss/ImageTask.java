package Tasksss;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageTask extends AsyncTask<String, Void, Bitmap> {

	private String url;

	public interface ImageCallback {

		void getImage(Bitmap bitmap, String url);
	}

	private ImageCallback callback;

	public ImageTask(ImageCallback callback) {
		this.callback = callback;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			url = params[0];
			HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			if (conn.getResponseCode() == 200) {
				return BitmapFactory.decodeStream(conn.getInputStream());
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
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (callback != null && result != null) {
			callback.getImage(result,url);
		}

	}
}
