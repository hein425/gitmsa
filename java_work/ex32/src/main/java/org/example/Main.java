package org.example;
// 시간차 이용해서 성능 테스트 코드
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Instant is1 = Instant.now();

        System.out.println("is1 = "+is1);

        List<String> list = Arrays.asList("11","22","33","44","55");
        list.stream()
                .forEach(System.out::println);

//        for (String temp : list){
//            System.out.println(temp);
//        }

        Instant is2 = Instant.now();
        System.out.println(is2);

        Duration dt = Duration.between(is1,is2);
        System.out.println(dt);
        System.out.println(dt.toMillis());

    }
}