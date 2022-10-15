package com.example.dontwastefood.Activities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dontwastefood.Listeners.IngredientsIdResponseListener;
import com.example.dontwastefood.Listeners.IngredientsInfoListener;
import com.example.dontwastefood.Listeners.IngredientsResponseListener;
import com.example.dontwastefood.Listeners.IngredientsUPCListener;
import com.example.dontwastefood.Listeners.InstructionsListener;
import com.example.dontwastefood.Listeners.RandomRecipeResponseListener;
import com.example.dontwastefood.Listeners.RecipeByIngredientsListener;
import com.example.dontwastefood.Listeners.RecipeDetailsListener;
import com.example.dontwastefood.Listeners.SimilarRecipeListener;
import com.example.dontwastefood.Models.IngredientUPCResponse;
import com.example.dontwastefood.Models.IngredientsIdResponse;
import com.example.dontwastefood.Models.IngredientsInfoResponse;
import com.example.dontwastefood.Models.IngredientsResponse;
import com.example.dontwastefood.Models.InstructionsResponse;
import com.example.dontwastefood.Models.RandomRecipeApiResponse;
import com.example.dontwastefood.Models.RecipeByIngredientsResponse;
import com.example.dontwastefood.Models.RecipeDetailsResponse;
import com.example.dontwastefood.Models.SimilarRecipeResponse;
import com.example.dontwastefood.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {

    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, String tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key),"10",tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());

            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                    listener.didError(t.getMessage());
            }
        });
    }

    public void getSimilarRecipe(SimilarRecipeListener listener, int id){
        CallSimilarRecipe callSimilarRecipe = retrofit.create(CallSimilarRecipe.class);
        Call<List<SimilarRecipeResponse>> call = callSimilarRecipe.callSimilarRecipe(id,"4",context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getIngredientsInfo(IngredientsInfoListener listener, int id){
        CallIngredientsInfo callSimilarRecipe = retrofit.create(CallIngredientsInfo.class);
        Call<IngredientsInfoResponse> call = callSimilarRecipe.callIngredientsInfo(id,"4",context.getString(R.string.api_key));
        call.enqueue(new Callback<IngredientsInfoResponse>() {
            @Override
            public void onResponse(Call<IngredientsInfoResponse> call, Response<IngredientsInfoResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<IngredientsInfoResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getIngredientsUPC(IngredientsUPCListener listener, String upc){
        CallIngredientsUPC callIngredientsUPC = retrofit.create(CallIngredientsUPC.class);


        Call<IngredientUPCResponse> call = callIngredientsUPC.callIngredientsUPC(upc,context.getString(R.string.api_key));
        call.enqueue(new Callback<IngredientUPCResponse>() {
            @Override
            public void onResponse(Call<IngredientUPCResponse> call, Response<IngredientUPCResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }

                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<IngredientUPCResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getInstructions(InstructionsListener listener,int id){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable t) {
               listener.didError(t.getMessage());
            }
        });
    }

    public void getIngredients(IngredientsResponseListener listener, String query){
//        Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
        CallIngredients callIngredients = retrofit.create(CallIngredients.class);
        Call<List<IngredientsResponse>> call = callIngredients.callIngredients(query,"10",context.getString(R.string.api_key));
        call.enqueue(new Callback<List<IngredientsResponse>>() {
            @Override
            public void onResponse(Call<List<IngredientsResponse>> call, Response<List<IngredientsResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());


            }

            @Override
            public void onFailure(Call<List<IngredientsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getIngredientsId(IngredientsIdResponseListener listener, String query){
//        Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
        CallIngredientsId callIngredients = retrofit.create(CallIngredientsId.class);
        Call<IngredientsIdResponse> call = callIngredients.callIngredients(query,"10",context.getString(R.string.api_key));
        call.enqueue(new Callback<IngredientsIdResponse>() {
            @Override
            public void onResponse(Call<IngredientsIdResponse> call, Response<IngredientsIdResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());


            }

            @Override
            public void onFailure(Call<IngredientsIdResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getRecipeByIngredients(RecipeByIngredientsListener listener,String ingredients){
        CallRecipeByIngredients callRecipeByIngredients = retrofit.create(CallRecipeByIngredients.class);
        Call<List<RecipeByIngredientsResponse>> call = callRecipeByIngredients.callRecipeByIngredients(ingredients,"10","2",true,context.getString(R.string.api_key));
        call.enqueue(new Callback<List<RecipeByIngredientsResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeByIngredientsResponse>> call, Response<List<RecipeByIngredientsResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<RecipeByIngredientsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
/* pun api_Key ca titlu si string-ul din string.xml
   number este numele parametrului din getRandomRecipes de pe site
 */
    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") String tags

        );
    }
    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallSimilarRecipe{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipeResponse>> callSimilarRecipe(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey

        );
    }
    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallIngredients{
        @GET("food/ingredients/autocomplete")
        Call<List<IngredientsResponse>> callIngredients(
                @Query("query") String query,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallIngredientsId{
        @GET("food/ingredients/search")
        Call<IngredientsIdResponse> callIngredients(
                @Query("query") String query,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallIngredientsInfo{
        @GET("food/ingredients/{id}/information")
        Call<IngredientsInfoResponse> callIngredientsInfo(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallIngredientsUPC{
        @GET("food/products/upc/{upc}")
        Call<IngredientUPCResponse> callIngredientsUPC(
                @Path("upc") String upc,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallRecipeByIngredients{
        @GET("recipes/findByIngredients")
        Call<List<RecipeByIngredientsResponse>> callRecipeByIngredients(
                @Query("ingredients") String ingredients,
                @Query("number")  String number,
                @Query("ranking") String ranking,
                @Query("ignorePantry") boolean ignorePantry,
                @Query("apiKey") String apiKey
        );
    }
}
