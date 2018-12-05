package com.example.cp.dataStructures.segmentTree.Examples.example1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.StringTokenizer;

/*
*
* link : https://www.hackerearth.com/practice/data-structures/advanced-data-structures/segment-trees/practice-problems/algorithm/distinct-integers-in-range-66eca44b/
*
* */

class SegmentTree {

    private Node[] heap;
    private long[] array;
    private int size;

    public SegmentTree(long[] array) {
        this.array = Arrays.copyOf(array, array.length);
        size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));

        heap = new Node[size];
        build(1, 0, array.length);
    }

    private void build(int v, int from, int size) {
        heap[v] = new Node();
        heap[v].from = from;
        heap[v].to = from + size - 1;

        if(size == 1) {
            heap[v].bs.set((int)array[from]);
        } else {
            build(2 * v, from, size / 2);
            build(2 * v + 1, from + size / 2, size - size / 2);

            heap[v].bs.or(heap[2 * v].bs);
            heap[v].bs.or(heap[2 * v + 1].bs);
        }
    }

    private boolean contains(int from1, int to1, int from2, int to2) {
        return from2 >= from1 && to2 <= to1;
    }

    //check inclusive intersection, test if range1[from1, to1] intersects range2[from2, to2]
    private boolean intersects(int from1, int to1, int from2, int to2) {
        return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
    }

    public BitSet rsq(int from, int to) {
        return rsq(1, from, to);
    }

    private BitSet rsq(int v, int from, int to) {
        Node n = heap[v];

        if (contains(from, to, n.from, n.to)) {
            return heap[v].bs;
        }

        if (intersects(from, to, n.from, n.to)) {
            BitSet bs = new BitSet();
            bs.or(rsq(2 * v, from, to));
            bs.or(rsq(2 * v + 1, from, to));

            return bs;
        }

        return new BitSet();
    }


    static class Node {

        Node() {
            bs = new BitSet();
        }
        BitSet bs;

        int from;
        int to;

        Integer pendingVal = null;

        int size() {
            return to - from + 1;
        }

    }
}
public class Main {
    public static void main(String args[]) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int n = fs.nextInt();

        long a[] = fs.nextLongArray(n);
        long b[] = fs.nextLongArray(n);

        int q = fs.nextInt();

        SegmentTree st1 = new SegmentTree(a);
        SegmentTree st2 = new SegmentTree(b);

        //System.out.println("here");

        for(int i = 0; i < q; i++) {
            int l1 = fs.nextInt();
            int r1 = fs.nextInt();

            int l2 = fs.nextInt();
            int r2 = fs.nextInt();

            BitSet answer = new BitSet();

            answer.or(st1.rsq(l1-1, r1-1));
            answer.or(st2.rsq(l2-1, r2-1));
            out.println(answer.cardinality());
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
