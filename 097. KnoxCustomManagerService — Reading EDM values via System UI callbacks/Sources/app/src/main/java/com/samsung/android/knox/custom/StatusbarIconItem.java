package com.samsung.android.knox.custom;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class StatusbarIconItem implements Parcelable {
    public static final Parcelable.Creator<StatusbarIconItem> CREATOR = new Parcelable.Creator<StatusbarIconItem>() {
        @Override
        public StatusbarIconItem createFromParcel(Parcel parcel) {
            return new StatusbarIconItem(parcel);
        }

        @Override
        public StatusbarIconItem[] newArray(int i) {
            return new StatusbarIconItem[i];
        }
    };

    private final String TAG;
    private AttributeColour[] mAttributeColour;
    private final String mAttributeColour_KEY;
    private int mIcon;
    private final String mIcon_KEY;

    private StatusbarIconItem(Parcel parcel) {
        this.TAG = "StatusbarIconItem";
        this.mIcon_KEY = "ICON";
        this.mAttributeColour_KEY = "ATTRIBUTE_COLOUR";
        this.mIcon = parcel.readInt();
        int readInt = parcel.readInt();
        this.mAttributeColour = null;
        if (readInt > 0) {
            this.mAttributeColour = new AttributeColour[readInt];
            for (int i = 0; i < readInt; i++) {
                this.mAttributeColour[i] = new AttributeColour(parcel.readInt(), parcel.readInt());
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public class AttributeColour {
        private int mAttribute;
        private int mColour;

        public AttributeColour() {
            this.mAttribute = 0;
            this.mColour = 0;
        }

        public AttributeColour(int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }

        public int getAttribute() {
            return this.mAttribute;
        }

        public int getColour() {
            return this.mColour;
        }

        public void setAttributeColour(int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }
    }

    public StatusbarIconItem(int i, AttributeColour[] attributeColourArr) {
        this.TAG = "StatusbarIconItem";
        this.mIcon_KEY = "ICON";
        this.mAttributeColour_KEY = "ATTRIBUTE_COLOUR";
        this.mIcon = i;
        this.mAttributeColour = attributeColourArr;
    }

    public String toString() {
        return "descr:" + describeContents() + " icon:" + this.mIcon + " attributeColour:" + Arrays.toString(this.mAttributeColour);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIcon);
        int length = this.mAttributeColour != null ? this.mAttributeColour.length : 0;
        parcel.writeInt(length);
        if (length > 0) {
            for (AttributeColour attributeColour : this.mAttributeColour) {
                parcel.writeInt(attributeColour.getAttribute());
                parcel.writeInt(attributeColour.getColour());
            }
        }
    }
}