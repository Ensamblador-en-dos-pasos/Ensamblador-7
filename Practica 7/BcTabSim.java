import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class BcTabSim {

    String valor, contloc, et, valenc;
    boolean ban = true;

    public String BuscarEt(String oper) throws FileNotFoundException{
        Scanner sc = new Scanner(new FileReader("TABSIM.txt"));
        
        try {
            
            while (sc.hasNext()){
                contloc = sc.next();
                et = sc.next();
                valor = sc.next();
            

                if(oper.equals(et)){
                    valenc = valor;
                }

            }

        }finally {
            if (sc != null) {
                sc.close();
            } // Fin del if
        }

        return valenc;
    }
}
