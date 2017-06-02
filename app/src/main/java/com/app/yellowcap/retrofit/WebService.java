package com.app.yellowcap.retrofit;


import com.app.yellowcap.entities.RegistrationResultEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.StaticPageEnt;
import com.app.yellowcap.entities.UserEnt;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<UserEnt>> registerUser(@Field("full_name") String userName,
                                                @Field("phone_no") String UserPhone);

    @FormUrlEncoded
    @POST("notification/updatedevicetoken")
    Call<ResponseWrapper> updateToken(@Field("user_id") String userid,
                                      @Field("device_type") String deviceType,
                                      @Field("device_token") String token);

    @FormUrlEncoded
    @POST("user/verifycode")
    Call<ResponseWrapper<RegistrationResultEnt>> verifyCode(@Field("user_id") String UserID,
                                                            @Field("code") String Code);

    @GET("user/getprofile")
    Call<ResponseWrapper<RegistrationResultEnt>> getUserProfile(@Query("user_id") String userID);

    @FormUrlEncoded
    @POST("user/update")
    Call<ResponseWrapper<RegistrationResultEnt>> updateProfile(@Field("user_id") String userID,
                                                               @Field("full_name") String userFullname,
                                                               @Field("email") String useremail,
                                                               @Field("address") String useraddress,
                                                               @Field("full_address") String userfulladdress,
                                                               @Field("profile_picture") File userprofileImage
    );

    @GET("cms")
    Call<ResponseWrapper<StaticPageEnt>> getTermandAbout(@Query("id") String userID, @Query("type") String type);

}