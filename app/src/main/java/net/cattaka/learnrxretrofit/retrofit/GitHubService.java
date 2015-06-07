package net.cattaka.learnrxretrofit.retrofit;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by cattaka on 15/06/07.
 */
public interface GitHubService {
    @GET("/users/{user}/repos")
    List<Repo> listRepos(@Path("user") String user);
}