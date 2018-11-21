package com.example.cp.dataStructures.BIT;

class BITUtil{
    void updateRange(BIT bit1, BIT bit2, long val,int l, int r) {

        bit1.update(l, val);
        bit1.update(r+1, -val);

        bit2.update(l, val*(l-1));
        bit2.update(r+1, -val*r);
    }

    long rangeSum(BIT bit1, BIT bit2, int l, int r) {
        return sum(r, bit1, bit2) - sum(l-1, bit1, bit2);
    }

    private long sum(int x, BIT bit1, BIT bit2) {
        return bit1.read(x)*x - bit2.read(x);
    }
}
class BIT {
    
    private long tree[];

    BIT(int n) {
        tree = new long[n+1];
    }

    void build (long a[]) {
        for(int i = 0; i < a.length; i++) {
            update(i+1, a[i]);
        }
    }

    long read (int idx) {
        long sum = 0;
        while(idx > 0) {
            sum += tree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    void update (int idx, long val) {
        while(idx < tree.length) {
            tree[idx] += val;
            idx += (idx & -idx);
        }
    }
}
