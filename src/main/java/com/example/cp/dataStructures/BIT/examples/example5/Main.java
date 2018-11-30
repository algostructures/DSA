package com.example.cp.dataStructures.BIT.examples.example5;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/*
*
* link : https://www.spoj.com/problems/INVCNT/
* */

public class Main {
    final static long MOD = 1000000007;
    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int t = fs.nextInt();

        while(t > 0) {
            int MAX =   (int) 1e7 + 10;

            t-=1;
            int n = fs.nextInt();
            long a[] = fs.nextLongArray(n);

            long temp [] = new long[MAX];

            BIT bit1 = new BIT(MAX);

            bit1.build(temp);

            long sum = 0;
            for(int i = 0; i < n; i++) {
                bit1.update((int)a[i]+1, 1);
                sum += bit1.read((int) 1e7 + 6) - bit1.read((int) (a[i] + 1));
            }

            out.println(sum);
        }

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
                while(!st.hasMoreTokens()) {
                    st = new StringTokenizer(br.readLine());
                }
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