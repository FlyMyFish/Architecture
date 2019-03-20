package com.shichen.architecture.data.source;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDataApi {
    /**
     * 菜谱分类标签查询
     *
     * @param key mobApi申请得到的key
     * @return
     */
    @GET("/v1/cook/category/query")
    Observable<ResponseBody> getCategory(@Query("key") String key);
}
