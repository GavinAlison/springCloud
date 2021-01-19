package com.alison.server.test;

public class ShowMeBug1 {

    // 在 IP 网络中，我们常用类似 `IP/掩码位数` 这种方式表示一个IP子网
// 例如：192.168.1.0/24，其中 24 表示的是子网掩码的位数，数字越大
// 表示的网络范围就越大。需要完成一个函数，输入为两个 IP 的字符串，输出为
// 能够把这两个 IP 包含在内的范围最小的子网的字符串表示。
// 例如：
//   输入："192.168.128.1", "192.168.255.2"
//   输出："192.168.128.0/17"
//
//   输入："10.16.0.255", "10.16.0.2"
//   输出："10.16.0.0/24"

    private static String getMinNetwork(String ip1, String ip2) {
        String[] ip1Arr = ip1.split("\\.");
        String[] ip2Arr = ip2.split("\\.");
        StringBuilder resultArr = new StringBuilder();
        int mask = 0;
        for (int i = 0, size = ip1Arr.length; i < size; i++) {
            int m = Integer.valueOf(ip1Arr[i]);
            int n = Integer.valueOf(ip2Arr[i]);
            int tmp = m & n;
            if (m == n) {
                resultArr.append(tmp).append(".");
                mask = 8 * (i + 1);
            }
            if (m != n) {
                resultArr.append(tmp).append(".");
                String tmpBinary = Integer.toBinaryString(tmp);
                mask = mask + tmpBinary.indexOf("1") + 1;
                break;
            }
        }
        if (resultArr.toString().split("\\.").length != 4) {
            int d = 4 - resultArr.toString().split("\\.").length;
            for (int k = 0; k < d; k++) {
                resultArr.append("0.");
            }
        }
        resultArr.deleteCharAt(resultArr.toString().length() - 1);
        resultArr.append("/").append(mask);
        return resultArr.toString();
//        return "192.168.128.0/17";
    }

    public static void main(String[] args) {
        System.out.println(getMinNetwork("192.168.128.1", "192.168.255.2"));
        System.out.println(getMinNetwork("192.168.128.1", "192.168.128.2"));
    }
}
