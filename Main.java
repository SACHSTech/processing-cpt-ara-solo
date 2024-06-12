import processing.core.PApplet;

class Main {
    public static void main(String[] args){

        String[] processingArgs = {"MySketch"};
        Money Money = new Money();
        PApplet.runSketch(processingArgs, Money);
    }
}