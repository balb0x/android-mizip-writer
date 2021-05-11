package com.balbox.mizipwriter.models;

import com.balbox.mizipwriter.utils.Utils;

public class SectorKey {
    private String keyA;
    private String keyB;

    public SectorKey(String keyA, String keyB) {
        this.keyA = keyA;
        this.keyB = keyB;
    }

    public byte[] getKeyA() {
        return Utils.hexStringToByteArray(keyA);
    }

    public byte[] getKeyB() {
        return Utils.hexStringToByteArray(keyB);
    }
}
