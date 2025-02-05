package com.example.proyecto2trim;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Table implements Parcelable {
    private String row;
    private int position;
    private String typeBox;


    public Table(String row, int position, String typeBox) {
        this.row = row;
        this.position = position;
        this.typeBox = typeBox;
    }

    //Serialización
    protected Table(Parcel in) {
        row = in.readString();
        position = in.readInt();
        typeBox = in.readString();
    }

    public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    public String getRow() {
        return row;
    }

    public int getPosition() {
        return position;
    }

    public String getTypeBox()
    {
        return typeBox;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTypeBox(String typeBox)
    {
        this.typeBox = typeBox;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(row);
        parcel.writeInt(position);
        parcel.writeString(typeBox);
    }
}
