package zhuoxin.andriody.com.mygithub.network;



import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zhuoxin.andriody.com.mygithub.github.hotuser.HotUserResult;
import zhuoxin.andriody.com.mygithub.github.repoList.model.RepoResult;
import zhuoxin.andriody.com.mygithub.github.repoinfo.RepoContentResult;
import zhuoxin.andriody.com.mygithub.login.TokenInterceptor;
import zhuoxin.andriody.com.mygithub.login.model.AccessToken;
import zhuoxin.andriody.com.mygithub.login.model.User;

/**
 * 作者：yuanchao on 2016/8/26 0026 09:33
 * 邮箱：yuanchao@feicuiedu.com
 */
public class GithubClient implements GithubApi{

    private static GithubClient githubClient;
    private final GithubApi githubApi;

    public static GithubClient getInstance(){
        if (githubClient==null){
            githubClient = new GithubClient();
        }
        return githubClient;
    }

    private GithubClient(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //添加Token拦截器
                .addInterceptor(interceptor)
                .addInterceptor(new TokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)

                //Retrofit 强大的功能：Gson转换器----将我们的数据请求的结果进行json转换，转换为我们需要的类型,例如类或者集合
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        githubApi = retrofit.create(GithubApi.class);
    }

    @Override
    public Call<RepoResult> searchRepos(@Query("q") String query, @Query("page") int pageId) {
        return githubApi.searchRepos(query, pageId);
    }
    @Override
    public Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo) {
        return githubApi.getReadme(owner, repo);
    }
    @Override
    public Call<ResponseBody> markDown(@Body RequestBody body) {
        return githubApi.markDown(body);
    }

    @Override
    public Call<AccessToken> getOAuthToken(@Field("client_id") String clientId,
                                           @Field("client_secret") String ClientSecret,
                                           @Field("code") String code) {
        return githubApi.getOAuthToken(clientId,ClientSecret,code);
    }

    @Override
    public Call<User> getUser() {
        return githubApi.getUser();
    }

    @Override
    public Call<HotUserResult> searchUsers(@Query("q") String query, @Query("page") int pageId) {
        return githubApi.searchUsers(query,pageId);
    }
}
