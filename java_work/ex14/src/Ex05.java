import aa.Circle;

public class Ex05 {

    public static void main(String[] args) {

        System.out.println(Circle.PI);
        System.out.println(Circle.a);

        Circle c = new Circle();

        c.doD();
        c.doN();


        int result = c.doA();
        System.out.println(result);


    }
}
