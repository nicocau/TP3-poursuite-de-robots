package Controleur;

public class SavePartie {
    private static final SavePartie savePartie = new SavePartie();

    private SavePartie(){}

    public SavePartie getInstance(){
        return SavePartie.savePartie;
    }


}
