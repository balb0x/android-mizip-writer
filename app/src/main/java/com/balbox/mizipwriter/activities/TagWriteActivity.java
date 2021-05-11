package com.balbox.mizipwriter.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import com.balbox.mizipwriter.R;
import com.balbox.mizipwriter.models.Block;
import com.balbox.mizipwriter.models.MiZipReaderWriter;
import com.balbox.mizipwriter.models.Sector;
import com.balbox.mizipwriter.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TagWriteActivity extends AppCompatActivity {
    private static final String TAG = "TagWriteActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_write);
        Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        MiZipReaderWriter miZipReaderWriter = MiZipReaderWriter.get(tag);
        /*List<Sector> sectorList = miZipReaderWriter.readSectors();
        for (Sector sector : sectorList) {
            Log.d(TAG, "Sector: " + sector.getIndex() + " Data: \n" + sector.toString());
        }*/




        List<Block> blockList = new ArrayList<Block>();
        Block block = new Block.Builder()
                .setIndex(9)
                .setData(Utils.hexStringToByteArray("01730072000000000000000000000034"))
                .build();
        blockList.add(block);

        Block block2 = new Block.Builder()
                .setIndex(8)
                .setData(Utils.hexStringToByteArray("01FF00FE000000000000000000000035"))
                .build();
        blockList.add(block2);



        miZipReaderWriter.writeBlocks(blockList);


        /*try {
            miZipReaderWriter.connectToTag();
            Log.d(TAG, "Data " + Utils.bytesToHex(miZipReaderWriter.readBlock(9)));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            miZipReaderWriter.test(8);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*String[] data = {
                "01050004000000000000000000000036",
                "01180017000000000000000000000037",
                "55370000000000000000000000000000",
                "5085070069DF78778810BDD0620EFC76"
        };
        int sIndex = 2;
        int bIndex = sIndex * 4;

        Sector.Builder sectorBuilder = new Sector.Builder()
                .setIndex(2);
        for (int i = 0; i < data.length; i++) {
            Block block = new Block.Builder()
                    .setIndex(bIndex)
                    .setData(data[i])
                    .build();
            sectorBuilder.addBlock(block);
            bIndex++;
        }

        miZipReaderWriter.writeSector(sectorBuilder.build());

        Log.d(TAG, "Done");*/
    }
}
