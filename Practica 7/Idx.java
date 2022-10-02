public class Idx {
    /**
     * Resta de binarios para pos o pre incremento
     * 
     * @param bin
     * @return
     */
    public String C2(String bin) {
        String resultado = "";
        boolean ban = true;
        int x = 0;
        x = (bin.length()-1);
        String[] arr = bin.split("");

        while (ban) {
            if(arr[x].equals("0")){
                x--;
            }else{
                x--;
                ban=false;
            }

        }

        for (int i = x; i >= 0; i--) {
            if (arr[i].equals("0")) {
                arr[i] = "1";
            } else {
                arr[i] = "0";
            }
        }

        
        for (int i = 0; i < arr.length; i++) {
            resultado = resultado+ arr[i];

        }

        return resultado;
    }// fin metodo
}
