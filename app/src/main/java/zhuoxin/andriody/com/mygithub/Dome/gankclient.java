package zhuoxin.andriody.com.mygithub.Dome;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import zhuoxin.andriody.com.mygithub.gank.model.GankResult;

/**
 * Created by Administrator on 10/9 0009.
 */
public class gankclient implements gankapi {
    private gankapi gankapi;
    private static gankclient gankclient;
    public static gankclient getInstance(){
        if(gankclient==null){
            gankclient=new gankclient();
        }
        return  gankclient;
    }
    private gankclient(){
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(gankapi.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gankapi=retrofit.create(gankapi.getClass());
    }
    @Override
    public Callback<GankResult> getgankdata(@Path("year") int year, @Path("month") int month, @Path("day") int day) {
        return gankapi.getgankdata(year,month,day);
    }
}
