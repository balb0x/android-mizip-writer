package com.balbox.mizipwriter.models;

import java.util.ArrayList;
import java.util.List;

public class Sector {
    private List<Block> blockList;
    private int index;
    private boolean authenticated;

    public Sector(Builder builder) {
        this.blockList = builder.blockList;
        this.index = builder.index;
        this.authenticated = builder.authenticated;
    }

    public List<Block> getBlockList() {
        return blockList;
    }

    public Block getBlock(int index) {
        if (index > 0 && index < blockList.size() - 1) {
            return blockList.get(index);
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        String str = "";
        for (Block block : blockList) {
            str += block.toString() + "\n";
        }
        return str;
    }

    public static final class Builder {
        private List<Block> blockList;
        private int index;
        private boolean authenticated;

        public Builder() {
            this.blockList = new ArrayList<Block>();
        }

        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        public Builder addBlock(Block block) {
            this.blockList.add(block);
            return this;
        }

        public Builder setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
            return this;
        }

        public Sector build() {
            return new Sector(this);
        }
    }
}
