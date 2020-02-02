public class BigNumber {

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

        StringBuilder nombre1 = new StringBuilder(llevarZeros(this.number));
        StringBuilder nombre2 = new StringBuilder(llevarZeros(other.toString()));
        StringBuilder resultat = new StringBuilder();
        int desbordament = 0;

        // ens asseguram que tots els nombre són vàlids
        if (!nombreValid(nombre1.toString()) || !nombreValid(nombre2.toString())) {
            return new BigNumber("0");
        }

        // si els nombres tenen diferent quantitat de dígits
        // aleshores emplenarem amb zeros a l'esquerra el menor
        int diferencia = Math.abs(nombre1.length() - nombre2.length());
        if (nombre1.length() > nombre2.length()) {
            nombre2.reverse();
            for (int i = 0; i < diferencia; i++) {
                nombre2.append("0");
            }
            nombre2.reverse();

        } else if (nombre1.length() < nombre2.length()) {
            nombre1.reverse();
            for (int i = 0; i < diferencia; i++) {
                nombre1.append("0");
            }
            nombre1.reverse();
        }

        // sumam
        for (int i = 0, c = nombre2.length() - 1; i < nombre2.length(); i++, c--) {
            if ((nombre1.charAt(c) - 48) + (nombre2.charAt(c) - 48) + desbordament >= 10) {
                resultat.append((nombre1.charAt(c) - 48 + desbordament) + (nombre2.charAt(c) - 48) - 10);
                desbordament = 1;
            } else {
                resultat.append((nombre1.charAt(c) - 48 + desbordament) + (nombre2.charAt(c) - 48));
                desbordament = 0;
            }
        }

        if (desbordament != 0) {
            resultat.append(desbordament);
        }
        resultat.reverse();
        return new BigNumber(llevarZeros(resultat.toString()));
    }

    // Resta
    BigNumber sub(BigNumber other) {

        StringBuilder nombre1 = new StringBuilder(llevarZeros(this.number));
        StringBuilder nombre2 = new StringBuilder(llevarZeros(other.toString()));
        StringBuilder resultat = new StringBuilder();
        int desbordament = 0;

        // suposarem que el nombre és més gran que el nombre 2

        if (!nombreValid(nombre1.toString()) || !nombreValid(nombre2.toString())) {
            return new BigNumber("0");
        }

        // si els nombres tenen diferent quantitat de dígits
        // aleshores emplenarem amb zeros a l'esquerra el menor
        int diferencia = Math.abs(nombre1.length() - nombre2.length());
        if (nombre1.length() > nombre2.length()) {
            nombre2.reverse();
            for (int i = 0; i < diferencia; i++) {
                nombre2.append("0");
            }
            nombre2.reverse();
        }

        // restam
        for (int i = 0, c = nombre1.length() - 1; i < nombre1.length(); i++, c--) {
            if ((nombre1.charAt(c) - 48) < (nombre2.charAt(c) - 48) + desbordament) {
                resultat.append((nombre1.charAt(c) - 48 + 10) - (nombre2.charAt(c) - 48 + desbordament));
                desbordament = 1;
            } else {
                resultat.append((nombre1.charAt(c) - 48) - (nombre2.charAt(c) - 48 + desbordament));
                desbordament = 0;
            }
        }
        resultat.reverse();
        return new BigNumber(llevarZeros(resultat.toString()));
    }

    // Multiplica
    BigNumber mult(BigNumber other) {

        StringBuilder nombre1 = new StringBuilder(llevarZeros(this.number));
        StringBuilder nombre2 = new StringBuilder(llevarZeros(other.toString()));
        StringBuilder multResultat = new StringBuilder();
        BigNumber resultat = new BigNumber("0");
        int desbordament = 0;
        int desbordamentAux;

        // feim les comprovacions pertinents
        if (!nombreValid(nombre1.toString()) || !nombreValid(nombre2.toString())) {
            return new BigNumber("0");
        }

        // bucle per multiplicar
        for (int i = nombre2.length() - 1, counter = 0; i >= 0; i--, counter++) {
            for (int j = nombre1.length() - 1; j >= 0; j--) {
                if ((nombre1.charAt(j) - 48) * (nombre2.charAt(i) - 48) + desbordament >= 10) {
                    desbordamentAux = ((nombre1.charAt(j) - 48) * (nombre2.charAt(i) - 48) + desbordament) / 10;
                    multResultat.append(((nombre1.charAt(j) - 48) * (nombre2.charAt(i) - 48) + desbordament) - (10 * desbordamentAux));
                    desbordament = desbordamentAux;
                } else {
                    multResultat.append((nombre1.charAt(j) - 48) * (nombre2.charAt(i) - 48) + desbordament);
                    desbordament = 0;
                }
            }

            if (desbordament != 0) multResultat.append(desbordament);
            desbordament = 0;

            multResultat.reverse();

            // afegir els zeros necessaris
            for (int j = 0; j < counter; j++) {
                multResultat.append(0);
            }

            BigNumber aux = new BigNumber(multResultat.toString());
            resultat = resultat.add(aux);

            // buidam el string abans de començar el bucle
            multResultat.delete(0, multResultat.length());
        }
        return resultat;
    }

    // Divideix
    BigNumber div(BigNumber other) {
        String[] resultat = divisioAmpliada(this, other);
        return new BigNumber(resultat[0]);
    }

    // Arrel quadrada
    BigNumber sqrt() {

        // comprovacions
        if (!nombreValid(this.toString())) return new BigNumber("0");

        StringBuilder resultat = new StringBuilder();
        BigNumber residu = new BigNumber("");
        StringBuilder grup = new StringBuilder();
        StringBuilder multiplicador = new StringBuilder();
        int punter = 0;
        boolean primeraVegada = true;

        // decidim quin serà el primer grup depenent de si el nombre es parell o imparell
        if (this.toString().length() % 2 == 0) {
            // es parell, el primer grup serà de dos dígits
            for (int i = 0; i < 2; i++) {
                grup.append(this.toString().charAt(i));
                punter++;
            }
        } else {
            // es imparell, el primer grup serà de un dígit
            grup.append(this.toString().charAt(0));
            punter++;
        }
        residu = new BigNumber(grup.toString());

        if (punter == this.toString().length()) {
            // per a nombres de 1 o 2 xifres
            int grupInt = Integer.parseInt(grup.toString());
            for (int i = 0; i < 9; i++) {
                if (i * i > grupInt) {
                    resultat.append(i - 1);
                    break;
                }
            }
        } else {
            // per a nombres de 3 o més xifres
            while (punter < this.toString().length()) {
                if (primeraVegada) {
                    // només entrarà el primer pic i és per evitar que s'afegeixi el grup següent que ja s'ha assignat abans
                    primeraVegada = false;
                } else {
                    grup = new StringBuilder(llevarZeros(residu.toString()));
                    // baixam el següent grup
                    for (int i = 0; i < 2; i++) {
                        grup.append(this.toString().charAt(punter));
                        punter++;
                    }
                }
                // calcula el resultat de l'arrel
                for (int i = 0; i <= 10; i++) {
                    if (new BigNumber(new BigNumber(resultat.toString()).mult(new BigNumber("2")).toString() + i).mult(new BigNumber(Integer.toString(i))).compareTo(new BigNumber(grup.toString())) == 1) {
                        residu = new BigNumber(grup.toString()).sub(new BigNumber(new BigNumber(resultat.toString()).mult(new BigNumber("2")).toString() + (i - 1)).mult(new BigNumber(Integer.toString(i - 1))));
                        resultat.append(i - 1);
                        break;
                    }
                }
            }
        }
        return new BigNumber(resultat.toString());
    }

    // Potència
    BigNumber power(int n) {

        // comprovacions pertinents
        if (!nombreValid(this.toString()) || n < 0) return new BigNumber("0");
        if (n == 0) return new BigNumber("1");

        BigNumber result = new BigNumber(this.toString());
        // bucle per calcular la potència
        for (int i = n - 1; i > 0; i--) {
            result = new BigNumber(result.mult(this));
        }
        return result;
    }

    // Factorial
    BigNumber factorial() {

        // comprovacions
        if (!nombreValid(this.toString())) return new BigNumber("0");

        BigNumber multiplicador = new BigNumber(this.toString());
        BigNumber resultat = new BigNumber(this.toString());

        while (multiplicador.compareTo(new BigNumber("1")) == 1) {
            multiplicador = new BigNumber(multiplicador.sub(new BigNumber("1")));
            resultat = new BigNumber(resultat.mult(multiplicador));
        }

        return resultat;
    }

    // MCD. Torna el Màxim comú divisor
    BigNumber mcd(BigNumber other) {

        // amb el mètode d'Euclides s'han d'ordenar els nombres de major a menor
        String[] resultatDivisio = new String[3];
        if (this.compareTo(other) >= 0) {
            resultatDivisio = divisioAmpliada(this, other);
        } else if (this.compareTo(other) < 0) {
            resultatDivisio = divisioAmpliada(other, this);
        }

        // mentre que el residu sigui diferent de 0 anirà dividint
        while (!llevarZeros(resultatDivisio[1]).equals("0")) {
            resultatDivisio = divisioAmpliada(new BigNumber(resultatDivisio[2]), new BigNumber(resultatDivisio[1]));
        }
        return new BigNumber(resultatDivisio[2]);
    }

    // aquesta funció executa la divisió de dos BigNumbers però també ens proporciona
    // més informació útil com el reste de la divisió o el cocient.
    String[] divisioAmpliada(BigNumber principal, BigNumber other) {

        String[] resultat = new String[3];
        BigNumber dividend = new BigNumber(llevarZeros(principal.toString()));
        BigNumber divisor = new BigNumber(llevarZeros(other.toString()));
        StringBuilder quocient = new StringBuilder();
        BigNumber quocientAux = new BigNumber("0");
        StringBuilder primerNombre = new StringBuilder();
        int punter = 0;
        boolean seguirDivisio = true;

        // comprovacions
        if (!nombreValid(dividend.toString()) || !nombreValid(divisor.toString()) || dividend.compareTo(divisor) == -1
                || dividend.compareTo(new BigNumber("0")) == 0) {
            resultat[0] = "0";
            seguirDivisio = false;
        }

        // elegim el nombre que dividirem el primer pic
        while (new BigNumber(primerNombre.toString()).compareTo(divisor) == -1 && seguirDivisio) {
            primerNombre.append(dividend.toString().charAt(punter));
            punter++;
        }
        StringBuilder residu = new StringBuilder(primerNombre);

        // començam a dividir
        while (seguirDivisio) {

            // elegim el quocient adequat
            while (new BigNumber(quocientAux.toString()).mult(divisor).compareTo(new BigNumber(residu.toString())) < 1) {
                quocientAux = new BigNumber(quocientAux.add(new BigNumber("1")));
            }
            quocientAux = new BigNumber(quocientAux.sub(new BigNumber("1")));
            quocient.append(quocientAux);

            // dividim
            residu = new StringBuilder(new BigNumber(residu.toString()).sub(divisor.mult(new BigNumber(quocientAux.toString()))).toString());
            quocientAux = new BigNumber("0");

            // baixam nombres del dividend al residu sempre que sigui necessari
            while (new BigNumber(residu.toString()).compareTo(divisor) < 0) {

                if (punter == dividend.toString().length()) {
                    seguirDivisio = false;
                    break;
                }
                residu.append(dividend.toString().charAt(punter));

                if (new BigNumber(residu.toString()).compareTo(divisor) < 0) {
                    quocient.append("0");
                }
                punter++;
            }
        }

        // ficam el quocient al primer lloc del string resultat
        if (resultat[0] == null) {
            resultat[0] = quocient.toString();
        }

        // ficam el residu al segon lloc
        resultat[1] = residu.toString();

        // ficam el divisor al darrer lloc
        resultat[2] = divisor.toString();

        return resultat;
    }

    // compara dos BigNumber. Torna 0 si són iguals, -1 si el primer és menor
    // i torna 1 si el segon és menor
    public int compareTo(BigNumber other) {

        String number1 = llevarZeros(this.number);
        String number2 = llevarZeros(other.toString());

        // comprovacions
        if (!nombreValid(number1) || !nombreValid(number2)) {
            return -2;
        }

        if (number1.length() != number2.length()) {
            // si la longitud dels nombres es diferent entre si
            if (number1.length() > number2.length()) return 1;
            return -1;

        } else {
            // si la longitud dels nombres es igual
            for (int i = 0; i < number1.length(); i++) {
                if (number1.charAt(i) > number2.charAt(i)) return 1;
                if (number1.charAt(i) < number2.charAt(i)) return -1;
            }
            return 0;
        }
    }

    // torna un String representant el nombre
    @Override
    public String toString() {
        return this.number;
    }

    // mira si dos objectes BigNumber són iguals
    @Override
    public boolean equals(Object other) {

        if (other instanceof BigNumber) {
            BigNumber b = (BigNumber) other;
            return llevarZeros(this.number).equals(llevarZeros(b.toString()));
        }
        return false;
    }

    // s'encarrega d'eliminar els zeros innecesaris d'un string
    public String llevarZeros(String s) {

        StringBuilder number = new StringBuilder();
        int counter = 0;

        // conta la quantitat de zeros al principi de cada nombre
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != 48) {
                break;
            } else {
                counter++;
            }
        }

        // significa que es un nombre de tipo: 0000
        if (counter == s.length()) {
            return "0";
        } else {
            for (int i = 0; i < s.length() - counter; i++) {
                number.append(s.charAt(i + counter));
            }
        }
        return number.toString();
    }

    // comprova que tots els dígits de n son vàlids
    public boolean nombreValid(String n) {
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) < 48 || n.charAt(i) > 57) {
                return false;
            }
        }
        return true;
    }
}
