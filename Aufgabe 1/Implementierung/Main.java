public class Main
{
    //Minimaldifferenz zw. Weihnachten und Ostern
    private static final int differenceWOS = 87; 
    //Minimaldifferenz zw. Ostern und Weihnachten
    private static final int differenceOSW = 244; 
    public static void main(String[] args)
    {
        new Main();
    }

    public Main()
    {
        gregEasternJulChristmas();
        julEasternGregChristmas();
    }

    /**
     * Konsolenausgabe des ersten Datums, wenn gregorianisch Ostern und julianisch Weihnachten ist
     */
    public void gregEasternJulChristmas()
    {
        //Jahrhunderte, bis die Differenz erreicht ist, +2, da 200nChr die Differenz 0 war
        int centuriesUntilDifferenceReached = differenceWOS/3*4+2+differenceWOS%3;
        //Das Jahr muss gregorianisch schon mit 1 enden, damit julianisch das neue Jahrhundert schon gestartet hat und das Schaltjahr somit war
        int year = centuriesUntilDifferenceReached*100+1;
        //Solange Ostern nicht auf den Tag fällt, bei dem die Differenz passt, soll das nächste Jahr angeschaut werden, hierbei muss beachtet werden, dass ein Schalttag die Differenz erhöhen kann
        while((easternGregorian(year)!=(year/100-centuriesUntilDifferenceReached)-(year/100-centuriesUntilDifferenceReached)/4 || (year%4==0 && (year%100!=0 || year%400==0))) 
        && (easternGregorian(year)+1!=(year/100-centuriesUntilDifferenceReached)-(year/100-centuriesUntilDifferenceReached)/4 || (year%4!=0 || (year%100==0 && year%400!=0))))
        {
            year++;
        }
        String s = "Orthodox Christmas and Catholic and Protestant Eastern on the same day for the first time on: ";
        if(easternGregorian(year)+22<32)
            s += "3/"+(easternGregorian(year)+22)+"/"+year;
        else
            s += "4/"+(easternGregorian(year)-10)+"/"+year;
        System.out.println(s);
    }

    /**
     * Konsolenausgabe des ersten Datums, wenn gregorianisch Weihnachten und julianisch Ostern ist
     */
    public void julEasternGregChristmas()
    {
        //Siehe Methode gregEasternJulChristmas, hier muss kein Schalttaag beachtet werden
        int centuriesUntilDifferenceReached = differenceOSW/3*4+2+differenceOSW%3;
        int year = centuriesUntilDifferenceReached*100;
        while(easternJulian(year)!=35-(year/100-centuriesUntilDifferenceReached)-(year/100-centuriesUntilDifferenceReached)/4)
        {
            year++;
        }
        System.out.println("Orthodox Eastern and Catholic and Protestant Christmas on the same day for the first time on: 12/25/"+year);
    }

    /**
     * Berechnung des Ostersonntag gregorianisch im jeweiligen Jahr
     * @param year Eingabe des Jahres
     * @return Zahl repräsentiert Datum (0=22.März, 1=23.März,...,10=1.April,...)
     */
    public int easternGregorian(int year)
    {
        int a = year%19;
        int b= year %4;
        int c = year % 7;
        int k = year/100;
        int p = (8*k+13)/25;
        int q = k/4;
        int m = (15+k-p-q)%30;
        int d = (19*a+m)%30;
        int n = (4+k-q)%7;
        int e = (2*b+4*c+6*d+n)%7;
        if(d+e==35)
            return 28;
        if(d==28 && e==6 && (11*m+11)%30<19)
            return 27;
        return d+e;
    }

    /**
     * Berechnung des Ostersonntag julianisch im jeweiligen Jahr
     * @param year Eingabe des Jahres
     * @return Zahl repräsentiert Datum (0=22.März, 1=23.März,...,10=1.April,...)
     */
    public int easternJulian(int year)
    {
        int a = year%19;
        int b= year %4;
        int c = year % 7;
        int k = year/100;
        int m = 15;
        int d = (19*a+m)%30;
        int n = 6;
        int e = (2*b+4*c+6*d+n)%7;
        if(d+e==35)
            return 28;
        if(d==28 && e==6 && (11*m+11)%30<19)
            return 27;
        return d+e;
    }
}
