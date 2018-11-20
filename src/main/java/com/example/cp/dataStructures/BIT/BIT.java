package com.example.cp.dataStructures.BIT;
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
