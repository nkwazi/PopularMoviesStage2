package ch.nkwazi.popularmoviesstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nkwazi on 29.01.19.
 */

public class Review implements Parcelable {

    private String author, content;

    public Review(String author, String content){
        this.author = author;
        this.content = content;
    }

    private Review(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public String getAuthor() {
        return author;
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
        dest.writeString(author);
        dest.writeString(content);
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {

        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
