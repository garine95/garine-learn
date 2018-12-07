package com.garine.source.analy.demo.transaction.entity;

/**
 * @author zhoujy
 * @date 2018年12月06日
 **/
public class Test {
    private Integer id;

    private Integer v;

    private Integer no;

    private Integer uno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getUno() {
        return uno;
    }

    public void setUno(Integer uno) {
        this.uno = uno;
    }
    public Test(){}

    public Test(int v,int no, int uno){
        this.v=v;
        this.no=no;
        this.uno=uno;
    }
}
