class BigNumber {

    private String number;


    // Constructor 1
    public BigNumber(String s) {
        this.number = s;
    }

    // Constructor 2
    public BigNumber(BigNumber b) {
        this.number = b.toString();
    }


    // Suma
    BigNumber add(BigNumber other) {
        BigNumber result;
        String number2 = other.toString();






        return other;
    }



    // Resta
    BigNumber sub(BigNumber other) {
        return other;
    }



    // Multiplica
    BigNumber mult(BigNumber other) {
        return other;
    }



    // Divideix
    BigNumber div(BigNumber other) {
        return other;
    }



    // Compara dos BigNumber. Torna 0 si són iguals, -1 si el primer és menor
    // i torna 1 si el segon és menor
    public int compareTo(BigNumber other) {
        return 0;
    }



    // Torna un String representant el número
    @Override
    public String toString() {
        return this.number;
    }



    // Mira si dos objectes BigNumber són iguals
    @Override
    public boolean equals(Object other) {

        String primerNum = quitarCeros(this.number);
        String segundoNum = quitarCeros(other.toString());

        // creando arrays
        int[] number1 = new int[primerNum.length()];
        int[] number2 = new int[segundoNum.length()];

        for (int i = 0; i < number1.length; i++) {
            number1[i] = primerNum.charAt(i);
        }

        for (int i = 0; i < number2.length; i++) {
            number2[i] = segundoNum.charAt(i);
        }


        // comparando los dos números //

        if (number1.length != number2.length) {
            return false;
        } else {

            for (int i = 0; i < number1.length; i++) {
                if (number1[i] != number2[i]) {
                    return false;
                }
            }
            return true;

        }
    }

    public String quitarCeros(String s) {

        // Quita los ceros que pueda haber al principio de cada número

        StringBuilder number = new StringBuilder();
        int counter = 0;
        for (int i = 0; s.charAt(i) == 48; i++) {
            counter++;
        }

        for (int i = 0; i < s.length() - counter; i++) {
            number.append(s.charAt(i + counter));
        }

        return number.toString();
    }
    
}
