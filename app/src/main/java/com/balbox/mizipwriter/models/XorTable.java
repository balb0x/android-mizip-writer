package com.balbox.mizipwriter.models;

import java.util.ArrayList;
import java.util.List;

public class XorTable {
    private List<Xor> xorList;

    public XorTable(Builder builder){
        this.xorList = builder.xorList;
    }

    public List<Xor> getXorList() {
        return xorList;
    }
    public static final class Builder {
        private List<Xor> xorList;

        public Builder() {
            this.xorList = new ArrayList<Xor>();
        }

        public Builder addXor(Xor xor) {
            this.xorList.add(xor);
            return this;
        }

        public XorTable build() {
            return new XorTable(this);
        }
    }
}
