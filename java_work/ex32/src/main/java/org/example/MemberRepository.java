package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    public void select(){
        List<Member> list = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jhn",
                    "root",
                    "1234");
            PreparedStatement pstmt = conn.prepareStatement("select * from member order by idx desc");
            ResultSet rs = pstmt.executeQuery();
            //Class.forName("com.mysql.cj.jdbc.Driver");

            while ((rs.next())){
                Member member = Member.builder()
                        .name(rs.getString("name"))
                        .idx(rs.getInt("idx"))
                        .age(rs.getInt("age"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .regdate(rs.getObject("regdate", LocalDateTime.class))
                        .mydate(rs.getObject("mydate", LocalDateTime.class))
                        .build();
                list.add(member);

            }

            list.stream()
                    .forEach(System.out::println);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void insert(){

        LocalDateTime mydate = LocalDateTime.now();

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jhn",
                    "root",
                    "1234");
            PreparedStatement pstmt = conn.prepareStatement("""
                    INSERT INTO member
                        (NAME, age, email, PASSWORD,mydate)
                    values
                        (?,?,?,?,?);
                    """);
            pstmt.setString(1,"홍길동");
            pstmt.setInt(2,30);
            pstmt.setString(3,"ak@naver.com");
            pstmt.setString(4,"password");
            pstmt.setObject(5,mydate);

            pstmt.executeUpdate();
            //Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
