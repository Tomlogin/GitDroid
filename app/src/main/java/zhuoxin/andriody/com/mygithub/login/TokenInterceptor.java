package zhuoxin.andriody.com.mygithub.login;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import zhuoxin.andriody.com.mygithub.login.model.UserRepo;

/**
 * Created by Administrator on 10/8 0008.
 * 拦截器，拦截token并添加到请求头
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request.Builder builder=request.newBuilder();
        if(UserRepo.hasAccessToken()){
            builder.header("Authorization","token "+UserRepo.getAccessToken());
        }
        Response response=chain.proceed(builder.build());
        if(response.isSuccessful()){
            return  response;
        }
        if(response.code()==401||response.code()==403){
            throw new IOException("未经授权！");
        }else {
            throw new IOException("响应码："+response.code());
        }

    }
}
