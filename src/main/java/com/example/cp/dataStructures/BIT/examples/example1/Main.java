package com.example.cp.dataStructures.BIT.examples.example1;

import java.io.*;
import java.util.*;
/*
*
* link : https://www.hackerearth.com/practice/data-structures/advanced-data-structures/fenwick-binary-indexed-trees/practice-problems/algorithm/7f30c9c/
* */
public class Main {
    final static long MOD = 1000000007;

    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        int n = fs.nextInt();
        long a[] = fs.nextLongArray(n);

        long b[] = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            b[i] = a[i - 1];
        }

        int q = fs.nextInt();

        long temp[] = new long[n];

        BIT bit1 = new BIT(a.length);
        bit1.build(temp);

        BIT bit2 = new BIT(a.length);
        bit2.build(temp);

        BITUtil bitUtil = new BITUtil();
        for (int i = 0; i < q; i++) {
            int type = fs.nextInt();
            if (type == 1) {
                int r = fs.nextInt();
                bitUtil.updateRange(bit1, bit2, -1, 1, r);
            } else {
                int key = fs.nextInt();
                out.println(binarySearch(bitUtil, bit1, bit2, n, key, b) ? "yes" : "no");
            }
        }
        out.flush();
        out.close();
    }

    public static boolean binarySearch(BITUtil bitUtil, BIT bit1, BIT bit2, int n, int key, long b[]) {
        int low = 1;
        int high = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            long value = b[mid] + bitUtil.rangeSum(bit1, bit2, mid, mid);

            if (value == key) {
                return true;
            } else if (value > key) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;
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

        public char nextChar() {
            return next().charAt(0);
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
    }
}

class BITUtil {
    void updateRange(BIT bit1, BIT bit2, long val, int l, int r) {

        bit1.update(l, val);
        bit1.update(r + 1, -val);

        bit2.update(l, val * (l - 1));
        bit2.update(r + 1, -val * r);
    }

    long rangeSum(BIT bit1, BIT bit2, int l, int r) {
        return sum(r, bit1, bit2) - sum(l - 1, bit1, bit2);
    }

    private long sum(int x, BIT bit1, BIT bit2) {
        return bit1.read(x) * x - bit2.read(x);
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