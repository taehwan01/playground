package poly.ex2;

public class Dog extends Animal{
    @Override
    public void sound(){

        super.sound();
        System.out.println("WOOF!!!");
    }
}