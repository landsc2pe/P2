package com.jayjaylab.lesson.gallery.model.request;

import com.jayjaylab.lesson.gallery.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jjkim on 2016. 6. 28..
 */
public interface GetGitUserInfo {
    @GET("/search/users")
    Call<User> getUser(@Query("q") String id);
}
