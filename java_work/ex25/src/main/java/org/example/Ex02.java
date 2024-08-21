package org.example;

import java.util.Set;
import java.util.TreeSet;

public class Ex02 {
    public static void main(String[] args) {

        Set<Integer> set = new TreeSet<>();

        set.add(4);
        set.add(1);
        set.add(2);
        set.add(3);

        System.out.println(set);

        Set<Member> set1 = new TreeSet<>();
        //        set1.add(new Member());

        Member member1 = new Member();
        member1.setName("홍길동");
        member1.setAge(20);

        Member member2 = new Member();
        member2.setName("김길동");
        member2.setAge(30);

        Member member3 = new Member();
        member3.setName("이길동");
        member3.setAge(25);

        set1.add(member1);
        set1.add(member2);
        set1.add(member3);

        System.out.println(set1);
    }

}