package zhuoxin.andriody.com.mygithub.network;



import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zhuoxin.andriody.com.mygithub.github.hotuser.HotUserResult;
import zhuoxin.andriody.com.mygithub.github.repoList.model.RepoResult;
import zhuoxin.andriody.com.mygithub.github.repoinfo.RepoContentResult;
import zhuoxin.andriody.com.mygithub.login.model.AccessToken;
import zhuoxin.andriody.com.mygithub.login.model.User;

public interface GithubApi {
    // Github 申请授权登录的信息
    /**
     *  Client ID
     e213abb29cbccca2e515
     Client Secret
     d41b27f1dd27acfdcf492be4984bd77a0f9fefe3
     */
    public String CLIENT_ID = "e213abb29cbccca2e515";
    public String CLIENT_SECRET = "d41b27f1dd27acfdcf492be4984bd77a0f9fefe3";

    // 申请填写的标志（重定向标志）
    public String CALL_BACK = "feicuiedu";

    String AUTH_SCOPE = "user,public_repo,repo";

    // 登录页面的网址（WebView来进行访问）
    String AUTH_URL = "https://github.com/login/oauth/authorize?client_id="+CLIENT_ID+"&scope="+AUTH_SCOPE;

    /**
     * 获取仓库列表的请求Api
     *
     * @param query  查询的参数--体现为语言
     * @param pageId 查询页数--从1开始
     * @return
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepos(
            @Query("q") String query,
            @Query("page") int pageId);

    /**
     * 获取readme
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(@Path("owner") String owner,
                                      @Path("repo") String repo);

    /**
     * 获取MarkDown文件内容，内容以HTML形式展示出来
     * @param body
     * @return
     */
    @Headers({"Content-Type:text/plain"})
    @POST("/markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);

    /**
     * 获取访问令牌，根据code来获取
     *
     * @param clientId
     * @param ClientSecret
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    @Headers("Accept: application/json")
    Call<AccessToken> getOAuthToken(
            @Field("client_id")String clientId,
            @Field("client_secret")String ClientSecret,
            @Field("code")String code);
    @GET("/user")
    Call<User> getUser();
    /**
     * 获取热门开发者
     * @param query  查询条件
     * @param pageId 查询页数
     * @return
     */
    @GET("/search/users")
    Call<HotUserResult> searchUsers(@Query("q")String query, @Query("page")int pageId);
}
