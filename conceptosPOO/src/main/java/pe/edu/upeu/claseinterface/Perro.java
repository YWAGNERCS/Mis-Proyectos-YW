package pe.edu.upeu.claseinterface;

public class Perro implements Animal{
    @Override
    public void emitirSonido() {
        System.out.println("Hello Brother soy mayli");
    }

    @Override
    public void dormir() {
        System.out.println("Grrr... Grrr...");

    }
}
