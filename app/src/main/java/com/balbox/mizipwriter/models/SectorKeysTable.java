package com.balbox.mizipwriter.models;

public class SectorKeysTable {
    private static final String DEFAULT_SECTOR_0_KEY_A = "A0A1A2A3A4A5";
    private static final String DEFAULT_SECTOR_0_KEY_B = "B4C132439EEF";

    private SectorKey sectorKey0;
    private SectorKey sectorKey1;
    private SectorKey sectorKey2;
    private SectorKey sectorKey3;
    private SectorKey sectorKey4;

    public SectorKeysTable(Builder builder) {
        this.sectorKey0 = builder.sectorKey0;
        this.sectorKey1 = builder.sectorKey1;
        this.sectorKey2 = builder.sectorKey2;
        this.sectorKey3 = builder.sectorKey3;
        this.sectorKey4 = builder.sectorKey4;
    }

    public SectorKey getSectorKey(int sector) {
        switch (sector){
            case 0:
                return sectorKey0;
            case 1:
                return sectorKey1;
            case 2:
                return sectorKey2;
            case 3:
                return sectorKey3;
            case 4:
                return sectorKey4;
            default:
                return sectorKey0;
        }
    }

    public static class Builder {
        private SectorKey sectorKey0;
        private SectorKey sectorKey1;
        private SectorKey sectorKey2;
        private SectorKey sectorKey3;
        private SectorKey sectorKey4;

        public Builder () {
            this.sectorKey0 = new SectorKey(DEFAULT_SECTOR_0_KEY_A, DEFAULT_SECTOR_0_KEY_B);
        }

        public Builder setSectorKey(int sector, SectorKey sectorKey) {

            switch (sector) {
                case 1:
                    return setSector1Key(sectorKey);
                case 2:
                    return setSector2Key(sectorKey);
                case 3:
                    return setSector3Key(sectorKey);
                case 4:
                    return setSector4Key(sectorKey);
            }
            return this;
        }

        private Builder setSector1Key(SectorKey sectorKey) {
            this.sectorKey1 = sectorKey;
            return this;
        }

        private Builder setSector2Key(SectorKey sectorKey) {
            this.sectorKey2 = sectorKey;
            return this;
        }

        private Builder setSector3Key(SectorKey sectorKey) {
            this.sectorKey3 = sectorKey;
            return this;
        }

        private Builder setSector4Key(SectorKey sectorKey) {
            this.sectorKey4 = sectorKey;
            return this;
        }

        public SectorKeysTable build() {
            return new SectorKeysTable(this);
        }
    }
}
