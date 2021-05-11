package com.balbox.mizipwriter.models;

import com.balbox.mizipwriter.utils.Utils;

import androidx.annotation.NonNull;

public class Block {
    private int index;
    private byte[] data;

    public Block(Builder builder) {
        this.index = builder.index;
        this.data = builder.data;
    }

    public byte[] getData() {
        return data;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        return index + ": " + Utils.bytesToHex(data);
    }

    public static final class Builder {
        private int index;
        private byte[] data;

        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        public Builder setData(byte[] data) {
            this.data = data;
            return this;
        }

        public Builder setData(String data) {
            this.data = Utils.hexStringToByteArray(data);
            return this;
        }

        public Block build() {
            return new Block(this);
        }
    }
}
