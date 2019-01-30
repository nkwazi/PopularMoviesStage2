package ch.nkwazi.popularmoviesstage2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nkwazi on 29.01.19.
 */

public class Trailer implements Parcelable {

    private String name;
    private String key;

    public Trailer(String name, String key){
        this.name = name;
        this.key = key;
    }

    public Trailer(Parcel in){
        name = in.readString();
        key = in.readString();
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(key);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {

        @Override
        public Trailer createFromParcel(Parcel source) { return new Trailer(source); }


        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[0];
        }
    };
}
