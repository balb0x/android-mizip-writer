package com.balbox.mizipwriter.models;

import com.balbox.mizipwriter.utils.Utils;

public class Xor {
    private int sector;
    private String keyA;
    private String keyB;

    public Xor(int sector, String keyA, String keyB) {
        this.sector = sector;
        this.keyA = keyA;
        this.keyB = keyB;
    }

    public byte[] getKeyA() {
        return Utils.hexStringToByteArray(this.keyA);
    }

    public byte[] getKeyB() {
        return Utils.hexStringToByteArray(this.keyB);
    }

    public int getSector() {
        return this.sector;
    }
}
