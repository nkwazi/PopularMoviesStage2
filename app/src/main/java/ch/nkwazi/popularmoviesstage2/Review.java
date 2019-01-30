package ch.nkwazi.popularmoviesstage2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nkwazi on 29.01.19.
 */

public class Review implements Parcelable {

    private String id;
    private String content;

    public Review(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public Review(Parcel in) {
        id = in.readString();
        content = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {

        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[0];
        }
    };
}
