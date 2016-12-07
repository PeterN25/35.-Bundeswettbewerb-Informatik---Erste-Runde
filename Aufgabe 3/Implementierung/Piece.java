import java.util.ArrayList;
import java.util.Arrays;
public class Piece
{
    private ArrayList<Piece> nextPieces;
    private int x;
    private int y;
    private boolean horizontal;

    /**
     * Erstellt einen neuen Knoten und alle dazugehörigen Nachfolger
     * @param x Die x-Positionen der zu speichernden Stäbchen
     * @param y Die y-Positionen der zu speichernden Stäbchen
     * @param horizontal Ist wahr, wenn das Stäbchen horizontal liegt
     */
    public Piece(int[] x, int[] y, boolean[] horizontal)
    {
        this.x = x[0];
        this.y = y[0];
        this.horizontal = horizontal[0];
        if(x.length>1)
        {
            nextPieces = new ArrayList<>();
            nextPieces.add(new Piece(Arrays.copyOfRange(x,1,x.length),
                    Arrays.copyOfRange(y,1,x.length),Arrays.copyOfRange(horizontal,1,x.length)));
        }
    }

    /**
     * Vergleicht ein Stäbchen mit diesem
     * @param x Die x-Position des Stäbchens
     * @param y Die y-Position des Stäbchens
     * @param horizontal Ist wahr, wenn das Stäbchen horizontal liegt
     * @return Wahr, wenn die Stäbchen gleich sind
     */
    public boolean same(int x, int y, boolean horizontal)
    {
        if(x!=this.x || y!=this.y || horizontal != this.horizontal)
        {
            return false;
        }
        return true;
    }

    /**
     * Überprüft, ob ein neues Feld entstanden ist
     * @param x Die x-Positionen der Stäbchen
     * @param y Die y-Positionen der Stäbchen
     * @param horizontal Ist wahr, wenn das Stäbchen horizontal liegt
     * @return Wahr, wenn ein neues Feld entstanden ist
     */
    public boolean isNewField(int[] x,int[] y,boolean[] horizontal)
    {
        if(nextPieces==null)
        {
            return false;
        }
        for(int i=0; i<nextPieces.size(); i++)
        {
            if(nextPieces.get(i).same(x[1],y[1],horizontal[1]))
            {
                return nextPieces.get(i).isNewField(Arrays.copyOfRange(x,1,x.length),
                    Arrays.copyOfRange(y,1,x.length),Arrays.copyOfRange(horizontal,1,x.length));
            }
        }
        nextPieces.add(new Piece(Arrays.copyOfRange(x,1,x.length),
                Arrays.copyOfRange(y,1,x.length),Arrays.copyOfRange(horizontal,1,x.length)));
        return true;
    }
}
