package com.example.cp.dataStructures.BIT.examples.example2;

import java.io.*;
import java.util.*;
/*
*
* link : https://www.hackerearth.com/practice/data-structures/advanced-data-structures/fenwick-binary-indexed-trees/practice-problems/algorithm/help-ashu-1/
* */
public class Main {
    final static long MOD = 1000000007;

    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        int n = fs.nextInt();
        long a[] = fs.nextLongArray(n);

        long e[] = new long[n];
        long o[] = new long[n];

        for(int i = 0; i < n; i++) {
            if(a[i] % 2 == 0) {
                e[i] = 1;
            } else {
                o[i] = 1;
            }
        }

        BIT bite = new BIT(n);

        BIT bito = new BIT(n);

        bite.build(e);
        bito.build(o);

        int q = fs.nextInt();

        for(int i = 0; i < q; i++) {
            int type = fs.nextInt();
            if(type == 0) {
                int ind = fs.nextInt();
                int val = fs.nextInt();
                if(val % 2 == 0) {
                    if(a[ind-1] % 2 != 0) {
                        bite.update(ind, 1);
                        bito.update(ind, -1);
                    }
                } else {
                    if(a[ind-1] % 2 == 0) {
                        bite.update(ind, -1);
                        bito.update(ind, 1);
                    }
                }
                a[ind-1] = val;
            } else {
                int l = fs.nextInt();
                int r = fs.nextInt();
                out.println(type == 1 ? bite.read(r) - bite.read(l - 1) : bito.read(r) - bito.read(l - 1));
            }
        }
        out.flush();
        out.close();
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

class BIT {
    @Override
    public String toString() {
        return "BIT{" +
                "tree=" + Arrays.toString(tree) +
                '}';
    }

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