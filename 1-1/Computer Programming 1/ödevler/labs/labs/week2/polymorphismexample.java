

class ProgrammingLanguage {
    String description = "Programming languages";

    public ProgrammingLanguage(){
        System.out.println("Cerating the parent class\n");
    }
    public void whoIAm(){
        System.out.println("I am the parent\n");
    }
}

class ObjectOrientedProgramming extends ProgrammingLanguage{

    public ObjectOrientedProgramming(){
        System.out.println("Cerating the OOP class\n");
    }
    public void whoIAm(){
        System.out.println("I am OOP\n");
    }
}


class Java extends ObjectOrientedProgramming {
    String description = "Java provides object-oriented programming";

    public Java(){
        System.out.println("Cerating the Java class\n");
    }
    public void whoIAm(){
        System.out.println("Hi everybody ! Java is fun :=))\n");
    }
}

public class PolymorphismExample{
    public static void main(String[] args){
        ProgrammingLanguage[] language = new ProgrammingLanguage[3];
        

        System.out.println("\n******language[0] is writing :");
        language[0] = new ProgrammingLanguage();
        language[0].whoIAm();
        System.out.println(language[0].description);

        System.out.println("\n******language[1] is writing:");
        language[1] = new ObjectOrientedProgramming();
        language[1] .whoIAm();
        System.out.println(language[1].description);

        System.out.println("\n******language[2] is writing:");
        language[2] = new Java();
        language[2].whoIAm();
        System.out.println(language[2].description);
      
        System.out.println("\n******language[2] is writing:");
        System.out.println(((Java)language[2]).description);
        ((ObjectOrientedProgramming)language[2]).whoIAm();

        System.out.println("\n******Java2 is writing:");
        Java java2 = new Java();
        java2.whoIAm();
        System.out.println(java2.description);

        System.out.println("\n******oop writes:");
        ObjectOrientedProgramming oop = (ObjectOrientedProgramming)language[1];
        oop.whoIAm();
        System.out.println(oop.description);
    }
}
