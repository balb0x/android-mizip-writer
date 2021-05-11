package com.balbox.mizipwriter.models;

import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.util.Log;

import com.balbox.mizipwriter.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MiZipReaderWriter {
    private static final String TYPE_CLASSIC = "TYPE_CLASSIC";
    private static final String TYPE_PLUS = "TYPE_PLUS";
    private static final String TYPE_PRO = "TYPE_PRO";
    private static final String TYPE_UNKNOWN = "TYPE_UNKNOWN";

    private Tag tag;
    private MifareClassic mfc;
    private SectorKeysTable sectorKeysTable;

    public static MiZipReaderWriter get(Tag tag) {
        return new MiZipReaderWriter(tag);
    }

    private MiZipReaderWriter(Tag tag) {
        this.tag = tag;
        this.sectorKeysTable = new MiZipCalculator().calculateKeys(tag);
        this.connect();
    }

    public List<Sector> readSectors() {
        if (!mfc.isConnected()) connect();

        //Check that is a mini mifare

        int type = mfc.getType();
        int sectorCount = mfc.getSectorCount();
        String typeS = getType(type);
        String metaInfo = "Card type：" + typeS + " with " + sectorCount + " Sectors, "
                + mfc.getBlockCount() + " Blocks Storage Space: " + mfc.getSize() + "B\n";


        List<Sector> sectorList = new ArrayList<Sector>();
        for (int i = 0; i < sectorCount; i++) {
            sectorList.add(readSector(i));
        }
        try {
            mfc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sectorList;

    }

    private Sector readSector(int sector) {
        try {
            boolean authenticated = mfc.authenticateSectorWithKeyA(sector, sectorKeysTable.getSectorKey(sector).getKeyA());

            Sector.Builder sectorBuilder = new Sector.Builder()
                    .setIndex(sector)
                    .setAuthenticated(authenticated);

            if (authenticated) {
                int bCount = mfc.getBlockCountInSector(sector);
                int bIndex = mfc.sectorToBlock(sector);
                for (int i = 0; i < bCount; i++) {
                    Block block = new Block.Builder()
                            .setData(readBlock(bIndex))
                            .setIndex(bIndex)
                            .build();

                    sectorBuilder.addBlock(block);
                    bIndex++;
                }
            }

            return sectorBuilder.build();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] readBlock(int bIndex) throws IOException {
        return mfc.readBlock(bIndex);
    }

    public void writeSector(Sector sector) {
        if (!mfc.isConnected()) connect();
        writeBlocks(sector.getBlockList());
        try {
            mfc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private boolean authenticateB(int sector) {
        try {
            return mfc.authenticateSectorWithKeyB(sector, sectorKeysTable.getSectorKey(sector).getKeyB());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean authenticateA(int sector) {
        try {
            return mfc.authenticateSectorWithKeyA(sector, sectorKeysTable.getSectorKey(sector).getKeyA());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void writeBlocks(List<Block> blockList) {
        boolean auth = false;
        int lastSector = 0;
        if (blockList.size() > 0) {
            lastSector = mfc.blockToSector(blockList.get(0).getIndex());
            auth = authenticateB(lastSector);
        }
        for (Block block : blockList) {
            int currentSector = mfc.blockToSector(blockList.get(0).getIndex());
            if (lastSector != currentSector || !auth) {
                lastSector = currentSector;
                auth = authenticateB(lastSector);
            }
            if (auth) {
                try {
                    mfc.writeBlock(block.getIndex(), block.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void test(int blockIndex) throws IOException {
        MifareClassic mifare = MifareClassic.get(tag);
        mifare.connect();
        int value = 1;
        int sector = mifare.blockToSector(blockIndex);
        mifare.authenticateSectorWithKeyA(sector, Utils.hexStringToByteArray("5085070069df")); // you need to know key A

        byte[] zeroValue = {0, 0, 0, 0, 1, 3, 50, 50, 0, 0, 0, 0, 0, 50, 0, 50 };
        mifare.writeBlock(blockIndex, zeroValue);

        //mifare.increment(blockIndex, value);
        mifare.transfer(blockIndex);
    }

    private String getType(int type) {
        switch (type) {
            case MifareClassic.TYPE_CLASSIC:
                return TYPE_CLASSIC;
            case MifareClassic.TYPE_PLUS:
                return TYPE_PLUS;
            case MifareClassic.TYPE_PRO:
                return TYPE_PRO;
            default:
                return TYPE_UNKNOWN;
        }
    }

    public void connect() {
        try {
            mfc = MifareClassic.get(tag);
            mfc.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
