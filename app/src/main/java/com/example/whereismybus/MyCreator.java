package com.example.whereismybus;

import android.os.Parcel;
import android.os.Parcelable;

public class MyCreator implements Parcelable.Creator<LigneBus> {
    @Override
    public LigneBus createFromParcel(Parcel source) {
        return new LigneBus(source);
    }

    @Override
    public LigneBus[] newArray(int size) {
        return new LigneBus[0];
    }
}
