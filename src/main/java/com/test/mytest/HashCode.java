package com.test.mytest;

public class HashCode {
    
    private static String diu = "869011025931079";
    private static String diu1 = "D7EF8018-6D9B-4863-8ECE-7173B8B73A51";
    private static String diu2 = "2CD2C38A-59EB-401C-8E38-1FF0F3596834";

    public static void main(String[] args) {
        
        String str = "</src><serverip>112.226.147.119</serverip>";
        System.out.println(str.replaceAll("serverip>[\\w\\W]*</serverip>", ""));
        
        
        System.out.println("va70709".matches("^v\\d{6}$"));
        //int i = Math.abs(diu.hashCode());
        //int mod = i % 10000;
        //System.out.println(mod);
        
        hashStr(diu, 4);
        hashStr(diu, 5);
        hashStr(diu, 6);
        hashStr(diu, 7);
        hashStr(diu, 8);
       /*
        System.out.println(HashAlgorithms.additiveHash(diu, 10));
        System.out.println(HashAlgorithms.additiveHash(diu1, 10));
        System.out.println(HashAlgorithms.additiveHash(diu2, 10));
        
        System.out.println(HashAlgorithms.rotatingHash(diu, 10));
        System.out.println(HashAlgorithms.rotatingHash(diu1, 10));
        System.out.println(HashAlgorithms.rotatingHash(diu2, 10));
        
        */

    }

    private static void hashStr(String diu, int size) {
        StringBuffer sb = new StringBuffer();
        int len = (int) Math.ceil(diu.length() / (double)size);
        for( int i=0 ; i< len; i++){
            if((i+1)*size >= diu.length()-1){
                String s = diu.substring(i*size);
                sb.append(HashAlgorithms.additiveHash(s, 10));
            }else{
                String s = diu.substring(i*size, (i+1)*size);
                sb.append(HashAlgorithms.additiveHash(s, 10));
            }
        }        
        System.out.println(sb.toString());
        sb.setLength(0);    
    }

}
