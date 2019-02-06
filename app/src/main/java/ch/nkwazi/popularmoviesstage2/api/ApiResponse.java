package ch.nkwazi.popularmoviesstage2.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nkwazi on 06.02.19.
 */

public class ApiResponse<R> {

    @SerializedName("results")
    public List<R> results;

}
