package com.example.cp.dataStructures.BIT.examples.example3;
import java.io.*;
import java.util.*;

/*
*
* link : https://codeforces.com/contest/296/problem/C
* */

public class Main {
    final static long MOD = 1000000007;
    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int n = fs.nextInt();
        int m = fs.nextInt();
        int k = fs.nextInt();

        long a[] = fs.nextLongArray(n);

        int mq[][] = new int[m][3];
        int kq[][] = new int[k][2];

        BIT mbit1 = new BIT(m);
        BIT mbit2 = new BIT(m);
        long temp1[] = new long[m];

        BIT nbit1 = new BIT(n);
        BIT nbit2 = new BIT(n);
        long temp2[] = new long[n];


        for(int i = 0; i < m; i++) {
            mq[i] = fs.nextIntArray(3);
        }

        for(int i = 0; i < k; i++) {
            kq[i] = fs.nextIntArray(2);
        }

        mbit1.build(temp1);
        mbit2.build(temp1);

        nbit1.build(temp2);
        nbit2.build(temp2);

        BITUtil bitUtil = new BITUtil();

        for(int i = 0; i < k; i++) {
            bitUtil.updateRange(mbit1, mbit2, 1, kq[i][0], kq[i][1]);
        }

        for(int j = 0; j < m; j++) {
            long mply = bitUtil.rangeSum(mbit1, mbit2, j+1, j+1);
            int l = mq[j][0];
            int r = mq[j][1];
            int d = mq[j][2];
            bitUtil.updateRange(nbit1, nbit2, d*mply, l, r);
        }

        for(int i = 0; i < n; i++) {
            out.print((a[i] + bitUtil.rangeSum(nbit1, nbit2, i+1, i+1))+" ");
        }
        out.println();
        out.flush();
        out.close();
    }

    public static long fact(long n){
        long rest = 1;
        while(n > 1){
            rest *= n;
            n--;
        }
        return rest;
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            try {
                br = new BufferedReader(new InputStreamReader(System.in));
                //                br = new BufferedReader(new FileReader("testdata.out"));
                st = new StringTokenizer("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String next() {
            if (st.hasMoreTokens())
                return st.nextToken();
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            String line = "";
            try {
                line = br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return line;
        }

        public Integer[] nextIntegerArray(int n) {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }

        public char[] nextCharArray() {
            return nextLine().toCharArray();
        }

        public char nextChar() {
            return next().charAt(0);
        }
    }
}
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
        tree = new long[n + 1];
    }

    void build(long a[]) {
        for (int i = 0; i < a.length; i++) {
            update(i + 1, a[i]);
        }
    }

    long read(int idx) {
        long sum = 0;
        while (idx > 0) {
            sum += tree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    void update(int idx, long val) {
        while (idx < tree.length) {
            tree[idx] += val;
            idx += (idx & -idx);
        }
    }
}

