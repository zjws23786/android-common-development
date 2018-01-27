package com.hua.librarytools.utils.safetyrelated;

/**
 * Created by huajz on 2018/1/27 0027.
 */

public class Xxtea {
    private static final int delta = -1640531527;

    private Xxtea() {
    }

    private static final int MX(int sum, int y, int z, int p, int e, int[] k) {
        return (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
    }

    public static final byte[] eryt(byte[] data, byte[] key) {
        return data.length == 0?data:toByteArray(encrypt(toIntArray(data, true), toIntArray(key, false)), false);
    }

    public static final byte[] dryt(byte[] data, byte[] key) {
        return data.length == 0?data:toByteArray(decrypt(toIntArray(data, false), toIntArray(key, false)), true);
    }

    private static final int[] encrypt(int[] v, int[] k) {
        int n = v.length - 1;
        if(n < 1) {
            return v;
        } else {
            if(k.length < 4) {
                int[] z = new int[4];
                System.arraycopy(k, 0, z, 0, k.length);
                k = z;
            }

            int var9 = v[n];
            int y = v[0];
            int sum = 0;

            int e;
            int p;
            for(int q = 6 + 52 / (n + 1); q-- > 0; var9 = v[n] += MX(sum, y, var9, p, e, k)) {
                sum += -1640531527;
                e = sum >>> 2 & 3;

                for(p = 0; p < n; ++p) {
                    y = v[p + 1];
                    var9 = v[p] += MX(sum, y, var9, p, e, k);
                }

                y = v[0];
            }

            return v;
        }
    }

    private static final int[] decrypt(int[] v, int[] k) {
        int n = v.length - 1;
        if(n < 1) {
            return v;
        } else {
            if(k.length < 4) {
                int[] z = new int[4];
                System.arraycopy(k, 0, z, 0, k.length);
                k = z;
            }

            int var10000 = v[n];
            int y = v[0];
            int q = 6 + 52 / (n + 1);

            for(int sum = q * -1640531527; sum != 0; sum -= -1640531527) {
                int e = sum >>> 2 & 3;

                int p;
                int var9;
                for(p = n; p > 0; --p) {
                    var9 = v[p - 1];
                    y = v[p] -= MX(sum, y, var9, p, e, k);
                }

                var9 = v[n];
                y = v[0] -= MX(sum, y, var9, p, e, k);
            }

            return v;
        }
    }

    private static final int[] toIntArray(byte[] data, boolean includeLength) {
        int n = (data.length & 3) == 0?data.length >>> 2:(data.length >>> 2) + 1;
        int[] result;
        if(includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }

        n = data.length;

        for(int i = 0; i < n; ++i) {
            result[i >>> 2] |= (255 & data[i]) << ((i & 3) << 3);
        }

        return result;
    }

    private static final byte[] toByteArray(int[] data, boolean includeLength) {
        int n = data.length << 2;
        if(includeLength) {
            int result = data[data.length - 1];
            if(result > n) {
                return null;
            }

            n = result;
        }

        byte[] var5 = new byte[n];

        for(int i = 0; i < n; ++i) {
            var5[i] = (byte)(data[i >>> 2] >>> ((i & 3) << 3) & 255);
        }

        return var5;
    }
}
