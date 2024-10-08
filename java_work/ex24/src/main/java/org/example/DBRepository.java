package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBRepository {
//   1. Class.forname jar 파일 추가 확인
//   2. Connection DB 연결
//   3.PrepareStatement sql 구문 작성
//   4. sql 구문 실행
//   executeUpdate(); -> 인서트 업데이트 딜리트 크리에ㅔ이트 얼터 문 실행할때
//   executeQuery(); -> select 문 실행할때

    public void insert(){

        Connection conn = null;
        PreparedStatement pstmt = null;

        // 엑셉션 소스 상에서 해결을 해야하고
        // 런타임엑셉션 소스 상에서 해결 안해도 됌

        // 강제 예외 발생
        // throw new runtimeException();
        // 강제 예외 발생
        // throw new Exception();

        try{
            // sql 연결
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jhn",
                    "root",
                    "1234");

            // sql 생성
            // 스캐너 말고 JOptionPane 으로 입력받기
            pstmt = conn.prepareStatement("INSERT INTO member (name,age) VALUES (?,?);");
            String name = JOptionPane.showInputDialog("이름");
            pstmt.setString(1,name);
            int age = Integer.parseInt(JOptionPane.showInputDialog("나이"));
            pstmt.setInt(2,age);

            //sql 구문 실행
            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt != null){conn.close();}

                if(conn != null){conn.close();}
            }catch (Exception e){}
        }
    }

    public void update(){

        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            // sql 연결
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jhn",
                    "root",
                    "1234");

            // sql 생성
            pstmt = conn.prepareStatement("UPDATE member set name = ?, age = ? where idx= ? ;");

            String name = JOptionPane.showInputDialog("이름");
            pstmt.setString(1,name);
            int age = Integer.parseInt(JOptionPane.showInputDialog("나이"));
            pstmt.setInt(2,age);
            int idx = Integer.parseInt(JOptionPane.showInputDialog("idx"));
            pstmt.setInt(3,idx);

            //sql 구문 실행
            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt != null){conn.close();}

                if(conn != null){conn.close();}
            }catch (Exception e){}
        }
    }


    public void delete(){

        Connection conn = null;
        PreparedStatement pstmt = null;



        try{
            // sql 연결
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jhn",
                    "root",
                    "1234");

            // sql 생성
            pstmt = conn.prepareStatement("DELETE FROM member WHERE idx = ? ");

            int idx = Integer.parseInt(JOptionPane.showInputDialog("idx"));
            pstmt.setInt(1,idx);

            //sql 구문 실행
            pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt != null){conn.close();}

                if(conn != null){conn.close();}
            }catch (Exception e){}
        }
    }


    public void select(){

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null; // SELECT 해서 반환되는 테이블 받아주는 메서드

        try{
            // sql 연결
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jhn",
                    "root",
                    "1234");

            // sql 생성
            pstmt = conn.prepareStatement("SELECT * FROM member");

            //sql 구문 실행
            rs = pstmt.executeQuery();
                while(rs.next()){
//                    System.out.println("idx= "+rs.getInt("idx"));
//                    System.out.println("name= "+rs.getString("name"));
//                    System.out.println("age= "+rs.getInt("age"));
//                      위 세줄이나 아래거나 같음
                    System.out.println("""
                            idx = %d
                            name = %s
                            age = %d
                            """.formatted(  rs.getInt("idx"),
                                            rs.getString("name"),
                                            rs.getInt("age")));
                }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt != null){conn.close();}

                if(conn != null){conn.close();}
            }catch (Exception e){}
        }
    }


}
