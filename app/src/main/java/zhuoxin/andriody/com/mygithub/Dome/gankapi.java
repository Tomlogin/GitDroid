package zhuoxin.andriody.com.mygithub.Dome;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zhuoxin.andriody.com.mygithub.gank.model.GankResult;

/**
 * Created by Administrator on 10/9 0009.
 */
public interface gankapi {
    /**
     * Api 获取每日干货
     *
     每日数据： http://gank.io/api/day/年/月/日

     例：
     http://gank.io/api/day/2015/08/06
     http://gank.io/api/day/{年}/{月}/{日}

     http://baidu.com/{name}/image

     htpp://baidu.com/zzz/image
     */
    String BASEURL="http://gank.io/api/day";
    @GET("/{year}/{month}/{day}")
    Call<GankResult> getgankdata(@Path("year")int year, @Path("month")int month, @Path("day")int day);
}
