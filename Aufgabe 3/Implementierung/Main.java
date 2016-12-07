import java.util.Scanner;
import java.io.FileReader;
public class Main
{
    public static void main(String[]args)
    {
        for(int i=0; i<args.length; i++)
        {
            new Main(args[i]);
        }
    }

    /**
     * Erstellt ein zweidimensionales Feld aus der Eingabedatei und erstellt daraus ein Objekt Puzzle
     * @param s Name der Textdatei
     */
    public Main(String file){
        Scanner in = null;
        try{
            in = new Scanner(new FileReader(file));
            int length = Integer.parseInt(in.nextLine()); 
            int[][] field = new int[length-2][length-2];
            int position=0;
            int open=0;
            int numberOfSticks = 0;
            for(int i=0; i<length; i++){
                String s = in.nextLine();
                for(int j=0; j<length;j++){
                    char c = s.charAt(j);
                    if(c==' '){
                        if(i==0){
                            position=length-j-2;
                            open=2;
                        }else if(j==0){
                            position=i-1;
                            open=3;
                        }else if(i==length-1){
                            position=j-1;
                            open=0;
                        }else if(j==length-1){
                            position=length-i-2;
                            open = 1;
                        }else
                            field[i-1][j-1]=0;
                    }else if(c!='#'){
                        if(Integer.parseInt(""+c)+1>numberOfSticks)
                        {
                            numberOfSticks = Integer.parseInt(""+c)+1;
                        }
                        field[i-1][j-1]=Integer.parseInt(""+c)+1;
                    }
                }
            }
            Puzzle p = new Puzzle(field,open,position,numberOfSticks);
            System.out.println(p.bestSolution());
        }
        catch(Exception e)
        {
            System.out.println("File not found!");
        }
    }
}
