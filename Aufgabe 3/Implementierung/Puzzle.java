import java.util.ArrayList;
public class Puzzle
{
    private ArrayList<Piece> oldFields0,oldFields1,oldFields2,oldFields3;
    private ArrayList<String> lastOrders;
    private ArrayList<int[][]> lastOrdersField;
    private int  open, position,pieces;

    /**
     * Erstellt ein neues Puzzle
     * @param field Zweidimensionales Feld aus ganzen Zahlen, welches das Puzzle darstellt
     * @param open Seite der Öffnung (unten=0, rechts=1, oben=2, links=3)
     * @param position Position der Öffnung von links, wenn die Seite unten wäre
     * @param pieces Anzahl an Stäbchen
     */
    public Puzzle(int[][] field, int open, int position, int pieces){
        this.open = open;
        this.position = position;
        this.pieces = pieces;
        lastOrders = new ArrayList<>();
        lastOrdersField = new ArrayList<>();
        oldFields0 = new ArrayList<>();
        oldFields1 = new ArrayList<>();
        oldFields2 = new ArrayList<>();
        oldFields3 = new ArrayList<>();
        getArrayList(open).add(makeField(field));
        lastOrders.add("");
        lastOrdersField.add(field);
    }

    /**
     * Löst das Puzzle
     * @return Die beste Drehfolge
     */
    public String bestSolution(){
        boolean done;
        do{
            done = true;
            int i=lastOrders.size();
            ArrayList<String> newLastOrders = new ArrayList<>();
            ArrayList<int[][]> newLastOrdersField = new ArrayList<>();
            for(int j=0; j<i;j++){
                int[][] newField = change(true,lastOrdersField.get(j));
                int state = check(newField,lastOrders.get(j)+"L");
                if(state==0){
                    return lastOrders.get(j)+"L";
                }else if(state==1){
                    newLastOrders.add(lastOrders.get(j)+"L");
                    newLastOrdersField.add(newField);
                }
                newField = change(false,lastOrdersField.get(j));
                state = check(newField,lastOrders.get(j)+"R");
                if(state==0){
                    return lastOrders.get(j)+"R";
                }else if(state==1){
                    newLastOrders.add(lastOrders.get(j)+"R");
                    newLastOrdersField.add(newField);
                    done = false;
                }
            }
            lastOrders=newLastOrders;
            lastOrdersField=newLastOrdersField;
        }while(!done);
        return "No solution found!";
    }

    /**
     * Gibt die ArrayList für Felder, bei welchen die Öffnung auf der angegebenen Seite ist, zurück
     * @param open Seite der Öffnung
     * @return Entsprechende ArrayList
     */
    private ArrayList<Piece> getArrayList(int open)
    {
        switch(open){
            case 0: return oldFields0;
            case 1: return oldFields1;
            case 2: return oldFields2;
            case 3: return oldFields3;
        }
        return null;
    }

    /**
     * Erstellt Knoten, die das Feld in die rekursiven Datenstruktur der bereits vorgekommenen Felder speichert
     * @param field Das zu speichernde Feld
     * @return Der erste Knoten
     */
    private Piece makeField(int[][] field)
    {
        int[] x = new int[pieces];
        int[] y = new int[pieces];
        boolean[] horizontal = new boolean[pieces];
        for(int i=0; i<pieces; i++)
        {
            x[i] = -1;
        }
        for(int i=0; i<field.length; i++)
        {
            for(int j=0; j<field[0].length; j++)
            {
                if(field[i][j]!=0 && x[field[i][j]-1]<0)
                {
                    int number = field[i][j]-1;
                    x[number] = j;
                    y[number] = i;
                    horizontal[number] = (j+1<field[0].length && field[i][j+1]-1==number);
                }
            }
        }
        return new Piece(x,y,horizontal);
    }

    /**
     * Dreht das Puzzle in die jeweilige Richtung
     * @param left Wenn ja, dreht sich das Feld nach links
     * @field Das zu drehende Feld
     * @return Das gedrehte Feld
     */
    private int[][] change(boolean left, int[][] field){
        int[][] newField = new int[field.length][field[0].length];
        for(int i=0; i<field.length; i++){
            for(int j=0; j<field[0].length;j++){
                if(left){
                    newField[i][j] = field[j][field.length-1-i];
                }else{
                    newField[i][j] = field[field.length-1-j][i];
                }
            }
        }
        return gravity(newField);
    }

    /**
     * Simulation der Schwerkraft: Freie Stäbchen fallen
     * @param field Das Feld, auf das die Schwerkraft wirkt
     * @return Das bearbeitete Feld
     */
    private int[][] gravity(int[][] field){
        for(int i=field.length-2;i>=0;i--)
        {
            field = barGravity(i,0,field);
        }
        return field;
    }

    /**
     * Freie Stäbchen in Reihe fallen
     * @param i Die zu überprüfende Reihe
     * @param j Die in der Reihe zu überprüfende Position
     * @param field Das zu überprüfende Feld
     * @return Das bearbeitete Feld
     */
    private int[][] barGravity(int i,int j,int[][] field){
        while(j+1<field.length && field[i][j]==0)
        {
            j++;
        }
        int k=1;
        if(field[i][j]!=0){
            while(j+k<field[0].length && field[i][j]==field[i][j+k]){
                k++;
            }
            boolean possible=true;
            int m=1;
            while(possible){
                for(int l=j;l<j+k;l++){
                    if(field[i+m][l]!=0)
                    {
                        possible = false;
                    }
                }
                if(possible){
                    for(int l=j;l<j+k;l++){
                        field[i+m][l]=field[i+m-1][l];
                        field[i+m-1][l]=0;
                    }
                    if(i+m+1<field.length)
                    {
                        m++;
                    }
                    else
                    {
                        possible = false;
                    }
                }
            }
            if(k+j<field[0].length)
            {
                field = barGravity(i,k+j,field);
            }
        }
        return field;
    }

    /**
     * Überprüfung, ob Stäbchen herausfällt, ein neues Feld entstanden ist oder das Feld bereits existiert hat
     * @param field Das zu überprüfende Feld
     * @param order Alle bisher durchgeführten Drehungen
     * @return Das Ergebnis (Puzzle gelöst=0, neues Feld=1, altes Feld=2)
     */
    private int check(int[][] field,String order){
        int checkOpen = 0;
        for(int i=0; i<order.length(); i++)
        {
            if(order.charAt(i)=='L'){
                checkOpen--;
            }else{
                checkOpen++;
            }
        }
        checkOpen = ((open+checkOpen)%4+4)%4;
        if(checkOpen==0){
            if(field[field.length-1][position]!=0&&field[field.length-1][position]==field[field.length-2][position])
                return 0;
        }
        int[] x = new int[pieces];
        int[] y = new int[pieces];
        boolean[] horizontal = new boolean[pieces];
        for(int i=0; i<pieces; i++)
        {
            x[i] = -1;
        }
        for(int i=0; i<field.length; i++)
        {
            for(int j=0; j<field[0].length; j++)
            {
                if(field[i][j]!=0 && x[field[i][j]-1]<0)
                {
                    int number = field[i][j]-1;
                    x[number] = j;
                    y[number] = i;
                    horizontal[number] = (j+1<field[0].length && field[i][j+1]-1==number);
                }
            }
        }
        for(int i=0; i<getArrayList(checkOpen).size(); i++)
        {
            if(getArrayList(checkOpen).get(i).same(x[0],y[0],horizontal[0]))
            {
                if(getArrayList(checkOpen).get(i).isNewField(x,y,horizontal))
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            }
        }
        getArrayList(checkOpen).add(makeField(field));
        return 1;
    }
}
