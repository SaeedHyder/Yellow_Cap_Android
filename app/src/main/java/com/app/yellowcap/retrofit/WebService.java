package com.app.yellowcap.retrofit;


import com.app.yellowcap.entities.NotificationEnt;
import com.app.yellowcap.entities.RegistrationResultEnt;
import com.app.yellowcap.entities.RequestEnt;
import com.app.yellowcap.entities.ResponseWrapper;
import com.app.yellowcap.entities.ServiceEnt;
import com.app.yellowcap.entities.StaticPageEnt;
import com.app.yellowcap.entities.UserEnt;

import com.app.yellowcap.entities.UserInProgressEnt;

import com.app.yellowcap.entities.countEnt;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<RegistrationResultEnt>> registerUser(@Field("full_name") String userName,
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

    @Multipart
    @POST("user/update")
    Call<ResponseWrapper<RegistrationResultEnt>> updateProfile(@Part("user_id") RequestBody userID,
                                                               @Part("full_name") RequestBody userFullname,
                                                               @Part("email") RequestBody useremail,
                                                               @Part("address") RequestBody useraddress,
                                                               @Part("full_address") RequestBody userfulladdress,
                                                               @Part MultipartBody.Part userprofileImage
    );

    @GET("cms")
    Call<ResponseWrapper<StaticPageEnt>> getTermandAbout(@Query("id") String userID, @Query("type") String type);

    @FormUrlEncoded
    @POST("technician/login")
    Call<ResponseWrapper<RegistrationResultEnt>> loginTechnician(@Field("email") String email,
                                                                 @Field("password") String password);

    @GET("notification/getnotifications")
    Call<ResponseWrapper<ArrayList<NotificationEnt>>> getNotification(@Query("user_id") String userID);

    @GET("allservice")
    Call<ResponseWrapper<ArrayList<ServiceEnt>>> getHomeServices();

    @GET("servicechild")
    Call<ResponseWrapper<ArrayList<ServiceEnt>>> getchildServices(@Query("parent_id") String parent_id);

    @Multipart
    @POST("request/create")
    Call<ResponseWrapper<RequestEnt>> createRequest(@Part("user_id") RequestBody userID,
                                                    @Part("service_id") RequestBody service_id,
                                                    @Part("services_ids") RequestBody services_ids,
                                                    @Part("discription") RequestBody discription,
                                                    @Part("address") RequestBody address,
                                                    @Part("full_address") RequestBody full_address,
                                                    @Part("date") RequestBody date,
                                                    @Part("time") RequestBody time,
                                                    @Part("payment_type") RequestBody payment_type,
                                                    @Part("status") RequestBody status,
                                                    @Part ArrayList<MultipartBody.Part> images

    );
    @GET("request/userinprogress")
    Call<ResponseWrapper<ArrayList<UserInProgressEnt>>>getUserInprogress(@Query("user_id")String userID);
    @Multipart
    @POST("request/editbyuser")
    Call<ResponseWrapper<RequestEnt>> editUserRequest(@Part("user_id") RequestBody userID,
                                                      @Part("request_id") RequestBody request_id,
                                                    @Part("service_id") RequestBody service_id,
                                                    @Part("services_ids") RequestBody services_ids,
                                                    @Part("discription") RequestBody discription,
                                                    @Part("address") RequestBody address,
                                                    @Part("full_address") RequestBody full_address,
                                                    @Part("date") RequestBody date,
                                                    @Part("time") RequestBody time,
                                                    @Part("payment_type") RequestBody payment_type,
                                                    @Part("status") RequestBody status,
                                                    @Part ArrayList<MultipartBody.Part> images

    );
    @FormUrlEncoded
    @POST("request/status")
    Call<ResponseWrapper<RequestEnt>> changeStatus(@Field("user_id")String userID,
                                                   @Field("request_id")Integer RequestID,
                                                   @Field("message")String message,
                                                   @Field("status")Integer Status);


    @GET("notification/count/{user_id}")
    Call<ResponseWrapper<countEnt>> getNotificationCount(
            @Path("user_id") String user_id);

}