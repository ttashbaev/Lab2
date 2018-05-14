package com.example.timur.labvacancies.data;

import com.example.timur.labvacancies.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Timur on 26.04.2018.
 */

public interface AuService {

    @FormUrlEncoded
    @POST (Constants.URL_Endpoint)
    Call<List<UserModel>> getVacancies (@Field("login") String login,
                                             @Field("f") String vacancies,
                                             @Field("limit") int limit,
                                             @Field("page") int page);


}
