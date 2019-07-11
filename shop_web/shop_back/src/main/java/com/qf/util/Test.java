package com.qf.util;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/11 10:07
 */
public class Test {

    public static void main(String[] args) {

        int n = 3;

        String s = "* ";

        //flag == true时，表示要递减，为false时表示要递增
        boolean flag = true;

        /**
         * i表示当前这一行*_的数量
         * j表示当前这一行前面空格_的数量
         */
        for(int i = n, j = 0;;){

            //打印这一行前面的所有空格
            for(int y = 0; y < j; y++){
                System.out.print(" ");
            }

            //打印*_
            for(int x = i; x > 0; x--) {
                System.out.print(s);
            }

            System.out.println();

            if(i == 1){
                flag = false;
            }

            //进行迭代
            if(flag){
                i--;
                j++;
            } else {
                i++;
                j--;

                if(i > n){
                    break;
                }
            }
        }

    }
}
