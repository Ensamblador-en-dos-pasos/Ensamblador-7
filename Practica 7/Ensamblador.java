import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Isaac Ulises
 * @author Saul
 * @version 1.45
 */
public class Ensamblador {
        public static void main(String[] args) throws FileNotFoundException, IOException { 
                Leer read = new Leer();
                LcTabop tab = new LcTabop();
                CodMaquina cod= new CodMaquina();
                read.leerArc("P2ASM.txt");
                read.Lcc();
                tab.buscar();
                cod.cMaquina();
        }
}
