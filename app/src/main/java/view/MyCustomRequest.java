package view;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by samsung on 2016/10/25.
 * Request后面的泛型用于声明加载结果的类型
 */
public class MyCustomRequest<T> extends Request<T>{

    private Response.Listener <T> listener;
    private Class<T> clazz;

    public MyCustomRequest(String url, Class<T> clazz,Response.Listener <T> listener,Response.ErrorListener errorListener) {
        this(Method.GET,url,clazz,listener,errorListener);
    }

    public MyCustomRequest(int method, String url,Class<T> clazz, Response.Listener <T> listener,Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.clazz = clazz;

    }

    private Map<String,String> map;
    public MyCustomRequest(int method, String url, Map<String,String> map,Class<T> clazz, Response.Listener <T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.clazz = clazz;
        this.map = map;

    }

    //此方法相当于异步任务中的doInBackground方法，该方法中的所有代码运行在子线程中，返回值代表本次连网的处理结果
    //并且此结果会传递到deliverResponse方法中
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        // 得到json字符串
        String json;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }
        //解析json字符串
        T bean = new Gson().fromJson(json,clazz);
        //将解析后的结果返回
        return  Response.success(bean, HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * 此方法相当于异步任务中的onPostExecute方法，会在parseNetworkResponse方法执行结束后自动调用
     *
     * @param response  本次连网的处理结果
     *       通常在此方法中会将此结果通过结果回调的方式传递出去
     */
    @Override
    protected void deliverResponse(T response) {
        if (listener !=null) {
            listener.onResponse(response);
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
