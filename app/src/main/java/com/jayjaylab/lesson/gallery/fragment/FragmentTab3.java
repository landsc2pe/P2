package com.jayjaylab.lesson.gallery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.model.User;
import com.jayjaylab.lesson.gallery.model.request.GetGitUserInfo;
import com.jayjaylab.lesson.gallery.util.LogTag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by HOMIN on 2016-05-25.
 */
public class FragmentTab3 extends Fragment {
    public static final String TAG = FragmentTab1.class.getSimpleName();
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_tab3, container, false);
//        imageView = (ImageView) rootView.findViewById(R.id.image1);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getUserInfo();
    }

    void getUserInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        GetGitUserInfo service = retrofit.create(GetGitUserInfo.class);
        final Call<User> callUser = service.getUser("landsc2pe");
        if(LogTag.DEBUG) Log.d(TAG, "url : " + callUser.request().url());
        Response<User> response = null;
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response != null && response.body() != null) {
                    User user = response.body();
                    if (LogTag.DEBUG) Log.d(TAG, "user : " + user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(LogTag.DEBUG) t.printStackTrace();
            }
        });
    }

}
