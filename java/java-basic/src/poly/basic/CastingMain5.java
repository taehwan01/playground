package poly.basic;

//다운캐스팅을 자동으로 하지 않는 이유
public class CastingMain5 {
    public static void main(String[] args) {
        Parent parent1 = new Parent();
        call(parent1);
        Parent parent2 = new Child();
        call(parent2);
    }

    private static void call(Parent parent) {
        if (parent instanceof Child) {
            System.out.println("Child 인스턴스 맞음");
            ((Child) parent).childMethod();
        } else {
            System.out.println("Child 인스턴스 아님");
        }
    }
}
