package com.balbox.mizipwriter.models;

import android.nfc.Tag;
import android.util.Log;

import com.balbox.mizipwriter.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MiZipCalculator {
    private static final String TAG = "MiZipCalculator";
    private static final String TYPE_A = "A";
    private static final String TYPE_B = "B";
    private SectorKeysTable sectorKeysTable;
    private byte[] id;
    private XorTable xorTable = new XorTable.Builder()
            .addXor(new Xor(1, "09125a2589e5", "F12C8453D821"))
            .addXor(new Xor(2, "AB75C937922F", "73E799FE3241"))
            .addXor(new Xor(3, "E27241AF2C09", "AA4D137656AE"))
            .addXor(new Xor(4, "317AB72F4490", "B01327272DFD"))
            .build();

    private static final int[] AID = {0,1,2,3,0,1};
    private static final int[] BID = {2,3,0,1,2,3};

    public MiZipCalculator() {

    }

    private boolean isValidId() {
        return id.length == 4;
    }

    public String readableId() {
        return Utils.bytesToHex(id);
    }

    private String calculateKey(Xor xor, String type) {
        List<Integer> p = new ArrayList<Integer>();
        StringBuilder test = new StringBuilder();
        int[] ids = TYPE_A.equals(type) ? AID : BID;
        for (int i = 0; i < ids.length; i++) {
            int index = TYPE_A.equals(type) ? AID[i] : BID[i];
            p.add(this.id[index] ^ (TYPE_A.equals(type) ? xor.getKeyA()[i] :  xor.getKeyB()[i]));
        }
        for (int i = 0; i < p.size(); i++) {
            test.append(String.format("%02X", (0xFF & p.get(i))) );
        }
        return test.toString();
    }

    public SectorKeysTable calculateKeys(Tag tag) {
        this.id = tag.getId();
        if (isValidId()) {
            SectorKeysTable.Builder sectorKeysTableBuilder= new SectorKeysTable.Builder();
            for (Xor xor : xorTable.getXorList()) {
                sectorKeysTableBuilder.setSectorKey(xor.getSector(), new SectorKey(calculateKey(xor, TYPE_A), calculateKey(xor, TYPE_B)));
            }
            sectorKeysTable = sectorKeysTableBuilder.build();
        }
        return sectorKeysTable;
    }

    public SectorKeysTable getSectorKeysTable() {
        return sectorKeysTable;
    }
}
