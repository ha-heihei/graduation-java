package com.lh;

/**
 * @author LiHao
 * @create 2022-04-11 17:27
 */
public class Test {

    public static void main(String[] args) {
        AbstractTest abstractTest = new B();
    }
}

class B extends AbstractTest{

}
abstract class AbstractTest implements A{
    @Override
    public void print1(){};
}

interface A{
    default void print(){
        System.out.println("sjdjajd");
    }
    void print1();
}
