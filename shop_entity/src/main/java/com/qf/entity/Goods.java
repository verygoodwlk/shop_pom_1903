package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/5 9:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String gname;
    private String ginfo;
    private String gimage;
    private BigDecimal gprice;
    private Integer tid;
    private Integer gsave;


//    public static void main(String[] args) {
//        System.out.println(5.0-4.9);//10/3
//
////        BigDecimal bigDecimal1 = new BigDecimal("5.0");
//        BigDecimal bigDecimal1 = BigDecimal.valueOf(5.0);
////        BigDecimal bigDecimal2 = new BigDecimal("4.9");
//        BigDecimal bigDecimal2 = BigDecimal.valueOf(4.9);
//
//        System.out.println(bigDecimal1.subtract(bigDecimal2));
//    }
}
