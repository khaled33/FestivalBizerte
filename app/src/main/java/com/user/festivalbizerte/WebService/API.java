package com.user.festivalbizerte.WebService;

import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.User;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Model.UserQuiz;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API {
    @Headers({
            "Accept: application/json"
    })
    //Login
    @POST("Login.php")
    Call<RSResponse> loginUser(@Body User user);

    //Inscription
    @Multipart
    @POST("Inscription.php")
    Call<RSResponse> inscrireUser(
            @Part MultipartBody.Part part,
            @Part("nom") RequestBody nom,
            @Part("prenom") RequestBody prenom,
            @Part("tel") RequestBody tel,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );

    //Artiste
    @GET("Artiste.php")
    Call<RSResponse> loadArtiste();

    //Programme
    @GET("Programme.php")
    Call<RSResponse> loadProgramme();

    @GET("ProgrammeArtiste.php")
    Call<RSResponse> loadArtisteProgramee();

    //Sponsor
    @GET("Sponsor.php")
    Call<RSResponse> loadSponsor();

    //User
    @Multipart
    @POST("UpdateUser.php")
    Call<RSResponse> updateUser(
            @Part MultipartBody.Part part,
            @Part("nom") RequestBody nom,
            @Part("prenom") RequestBody prenom,
            @Part("tel") RequestBody tel,
            @Part("password") RequestBody password,
            @Part("id_user") RequestBody id_user
    );

    @FormUrlEncoded
    @POST("UpdateScore.php")
    Call<RSResponse> updateScore(@Field("id_user") int id_user, @Field("score") int score);

    @POST("UserQuiz.php")
    Call<RSResponse> addUserQuiz(@Body UserQuiz userQuiz);

    @Multipart
    @POST("UserSelfi.php")
    Call<RSResponse> userSelfi(
            @Part MultipartBody.Part part,
            @Part("id_user") RequestBody id_user,
            @Part("date_jouer") RequestBody date_jouer
    );

    @GET("GetScore.php")
    Call<RSResponse> getScore(@Query("id_user") int id_user);

    //Quiz
    @FormUrlEncoded
    @POST("GetDaysQuiz.php")
    Call<RSResponse> getQuiz(@Field("datequiz") String date, @Field("timequiz") String time);

    @GET("GetQuestionQuiz.php")
    Call<RSResponse> loadQuestion(@Query("id_quiz") int id_quiz);
}
