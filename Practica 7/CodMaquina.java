import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodMaquina {
    String valor, contloc, et, codop, oper, addr, cmc, cmf, ff;
    String[] result;
    int op = 0, base = 0, valor1;
    boolean negpos = true;
    String rr = "", aa = "", n, bin, concat, acumulador, z, s;
    Operando valOpe = new Operando();
    Conversor conv = new Conversor();
    Idx idx = new Idx();

    /**
     * Método para leer el archivo
     * 
     * @param nom nombre del archivo
     * @throws IOException
     */
    public void cMaquina() throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader("TMP.txt"));
        boolean ban = sc.hasNextLine();
        try {// abre el fichero
             // Lee el archivo origial y reemplaza todos los espacios en blanco
             // por comas en el archivo temporal
            while (ban) {
                valor = sc.next();
                contloc = sc.next();
                et = sc.next();
                codop = sc.next();
                oper = sc.next();
                addr = sc.next();
                cmc = sc.next();

                switch (addr) {
                    case "Inherente":
                        System.out.println("CODOP = " + codop + "\tCMC = " + cmc);
                        break;

                    case "Directo":
                    case "Extendido":
                        System.out.print("CODOP = " + codop + "\tCMC = " + cmc);
                        op = valOpe.basesnum(oper);
                        switch (op) {
                            case 1:// Hex
                                base = conv.hextodec(oper);
                                cmf = conv.dectohex(base);
                                System.out.println(" CMF: " + cmf);
                                break;
                            case 2:// Oct
                                base = conv.octtodec(oper);
                                cmf = conv.dectohex(base);
                                System.out.println(" CMF: " + cmf);
                                break;
                            case 3:// Bin
                                base = conv.bintodec(oper);
                                cmf = conv.dectohex(base);
                                System.out.println(" CMF: " + cmf);
                                break;
                            case 4:// dec
                                cmf = conv.dectohex(Integer.parseInt(oper));
                                System.out.println(" CMF: " + cmf);
                                break;

                            default:
                                break;
                        }
                        break;
                    case "Inmediato":
                    case "Inmediato1":
                        System.out.print("CODOP = " + codop + "\tCMC = " + cmc);
                        op = valOpe.basesnum(oper);
                        switch (op) {
                            case 1:// Hex
                                base = conv.hextodecimm(oper);
                                cmf = conv.dectohex(base);
                                System.out.println(" CMF: " + cmf);
                                break;
                            case 2:// Oct
                                base = conv.octtodecimm(oper);
                                cmf = conv.dectohex(base);
                                System.out.println(" CMF: " + cmf);
                                break;
                            case 3:// Bin
                                base = conv.bintodecimm(oper);
                                cmf = conv.dectohex(base);
                                System.out.println(" CMF: " + cmf);
                                break;
                            case 4:// dec
                                base = conv.decimm(oper);
                                cmf = conv.dectohex(base);
                                System.out.println(" CMF: " + cmf);
                                break;

                            default:
                                break;
                        }
                        break;
                    case "IDX":
                        System.out.print("CODOP = " + codop + "\tCMC = " + cmc);
                        Pattern ind5bits; // Indizado 5 bits
                        Matcher buscador1;

                        Pattern prepost; // Pre-post Incremento-Decremento
                        Matcher buscador2;

                        Pattern acum;// Acumulador
                        Matcher buscador3;

                        ind5bits = Pattern.compile(
                                "^[-]*[0-9]+,[X|Y]$|^[-]*[0-9]+,[S]P$|^[-]*[0-9]+,[P]C$|^,[X|Y]$|^,[S]P$|,[P]C$");
                        buscador1 = ind5bits.matcher(oper);
                        boolean encontrado = buscador1.find();

                        prepost = Pattern.compile(
                                "^[-]*[1-8]+,[+|-][X|Y]$|^[-]*[1-8]+,[X|Y][+|-]$|^[-]*[1-8]+,[+|-][S]P$|^[-]*[1-8]+,[S]P[+|-]$|^[-]*[1-8]+,[+|-][P]C$|^[-]*[1-8]+,[P]C[+|-]$");
                        buscador2 = prepost.matcher(oper);
                        boolean encontrado2 = buscador2.find();

                        acum = Pattern.compile("^[A|B|D]+,[X|Y]$|^[A|B|D]+,[S]P$|^[A|B|D]+,[P]C$");
                        buscador3 = acum.matcher(oper);
                        boolean encontrado3 = buscador3.find();

                        if (encontrado) { // Indizado 5 Bits rr0nnnnn
                            result = oper.split(",");
                            if (result[0] == "") {
                                valor1 = 0;
                            } else {
                                valor1 = Integer.parseInt(result[0]);
                            }
                            switch (result[1]) {
                                case "X":
                                    rr = "00";
                                    break;
                                case "Y":
                                    rr = "01";
                                    break;
                                case "SP":
                                    rr = "10";
                                    break;
                                case "PC":
                                    rr = "11";
                                    break;
                                default:
                                    break;
                            }
                            if (valor1 < 0 && valor1 > -15) {
                                n = "1";
                            } else {
                                n = "0";
                                negpos = false;
                            }
                            valor1 = Math.abs(valor1);
                            bin = Integer.toBinaryString(valor1);
                            bin = conv.ceros(bin);
                            if (negpos) {
                                bin = idx.C2(bin);
                            }
                            concat = rr + "0" + n + bin;
                            valor1 = conv.bintodec(concat);
                            cmf = conv.dectohex(valor1);
                            if (cmf.length() == 1) {
                                cmf = "0" + cmf;
                            }
                            System.out.println(" CMF: " + cmf.toUpperCase());

                        } else if (encontrado2) { // Indizado pre-post rr1pnnnn
                            String[] result = oper.split(",");
                            valor1 = Integer.parseInt(result[0]);

                            switch (result[1]) {
                                /*
                                 * Casos para el pre incremento
                                 * o pre decremento
                                 */
                                case "-X":
                                case "+X":
                                    rr = "00";
                                    n = "0";
                                    break;

                                case "-Y":
                                case "+Y":
                                    rr = "01";
                                    n = "0";
                                    break;

                                case "-SP":
                                case "+SP":
                                    rr = "10";
                                    n = "0";
                                    break;

                                case "-PC":
                                case "+PC":
                                    rr = "11";
                                    n = "0";
                                    break;
                                /*
                                 * Casos para el post incremento
                                 * y decremento
                                 */
                                case "X+":
                                case "X-":
                                    rr = "00";
                                    n = "1";
                                    break;

                                case "Y-":
                                case "Y+":
                                    rr = "01";
                                    n = "1";
                                    break;

                                case "SP-":
                                case "SP+":
                                    rr = "10";
                                    n = "1";
                                    break;

                                case "PC-":
                                case "PC+":
                                    rr = "11";
                                    n = "1";
                                    break;

                                default:
                                    break;
                            }
                            String prepos = result[1];
                            if (prepos.startsWith("-") || prepos.endsWith("-")) {
                                valor1 = Math.abs(valor1);
                            } else {
                                valor1 = (Math.abs(valor1) - 1);
                                negpos = false;
                            }
                            bin = Integer.toBinaryString(valor1);
                            bin = conv.ceros(bin);
                            if (negpos) {
                                bin = idx.C2(bin);
                            }
                            concat = rr + "1" + n + bin;
                            valor1 = conv.bintodec(concat);
                            cmf = conv.dectohex(valor1);
                            if (cmf.length() == 1) {
                                cmf = "0" + cmf;
                            }
                            System.out.println(" CMF: " + cmf.toUpperCase());

                        } else if (encontrado3) {// Idizado acumulador 111rr1aa
                            String[] result = oper.split(",");
                            acumulador = (result[0]);
                            switch (result[1]) {
                                case "X":
                                    rr = "00";
                                    break;
                                case "Y":
                                    rr = "01";
                                    break;
                                case "SP":
                                    rr = "10";
                                    break;

                                default:
                                    break;
                            }
                            switch (acumulador) {
                                case "A":
                                    aa = "00";
                                    break;
                                case "B":
                                    aa = "01";
                                    break;
                                case "D":
                                    aa = "10";
                                    break;
                                default:
                                    break;
                            }

                            concat = "111" + rr + "1" + aa;
                            valor1 = conv.bintodec(concat);
                            cmf = conv.dectohex(valor1);
                            if (cmf.length() == 1) {
                                cmf = "0" + cmf;
                            }
                            System.out.println(" CMF: " + cmf.toUpperCase());
                        }

                        break;

                    case "IDX1":// Indizado 9 bits 111rr0zs
                        System.out.print("CODOP = " + codop + "\tCMC = " + cmc);
                        z = "0"; // Representa que es de 9 bits
                        result = oper.split(",");

                        valor1 = Integer.parseInt(result[0]);

                        switch (result[1]) {
                            case "X":
                                rr = "00";
                                break;
                            case "Y":
                                rr = "01";
                                break;
                            case "SP":
                                rr = "10";
                                break;
                            case "PC":
                                rr = "11";
                                break;
                            default:
                                break;
                        }
                        if (valor1 <= -17) {
                            s = "1";
                        } else {
                            s = "0";
                            negpos = false;
                        }
                        valor1 = Math.abs(valor1);
                        bin = Integer.toBinaryString(valor1);
                        bin = conv.ceros9bits(bin);
                        if (negpos) {
                            bin = idx.C2(bin);
                        }
                        concat = "111" + rr + "0" + z + s;
                        valor1 = conv.bintodec(concat);
                        cmf = conv.dectohex(valor1);
                        if (cmf.length() == 1) {
                            cmf = "0" + cmf;
                        }                       
                        valor1 = conv.bintodec(bin);
                        ff = conv.dectohex(valor1);
                        System.out.println(" CMF: " + cmf.toUpperCase() + " "+ ff.toUpperCase());

                        break;

                    case "IDX2":// Indizado 9 bits 111rr0zs
                        System.out.print("CODOP = " + codop + "\tCMC = " + cmc);
                        z = "1"; // Representa que es de 9 bits
                        s = "0";
                        result = oper.split(",");

                        valor1 = Integer.parseInt(result[0]);

                        switch (result[1]) {
                            case "X":
                                rr = "00";
                                break;
                            case "Y":
                                rr = "01";
                                break;
                            case "SP":
                                rr = "10";
                                break;
                            case "PC":
                                rr = "11";
                                break;
                            default:
                                break;
                        }
                        valor1 = Math.abs(valor1);
                        bin = Integer.toBinaryString(valor1);
                        concat = "111" + rr + "0" + z + s;
                        valor1 = conv.bintodec(concat);
                        cmf = conv.dectohex(valor1);                      
                        valor1 = conv.bintodec(bin);
                        ff = conv.dectohex(valor1);
                        ff = conv.ceros(ff);
                        System.out.println(" CMF: " + cmf.toUpperCase() + " "+ ff.toUpperCase());

                        break;


                    default:
                        if (codop.equals("END")) {
                            ban = false;
                        }
                        break;
                }

            }
        } finally {
            if (sc != null) {
                sc.close();
            } // Fin del if
        } // Fin de try

    }// Fin de método
}
