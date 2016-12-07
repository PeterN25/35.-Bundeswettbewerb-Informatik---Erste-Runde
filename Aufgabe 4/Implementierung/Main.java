import java.util.Scanner;
import java.io.FileReader;

public class Main
{
    private int min,max;
    private String file;
    
    /**
     * 
     */
    public Main(String file)
    {
        this.file = file;
        if(checkIfPossible())
        {
            showOrder();
        }
    }
    
    public static void main(String[]args)
    {
        for(int i=0; i<args.length; i++)
        {
            new Main(args[0]);
        }
    }
    
    boolean checkIfPossible()
    {
        try
        {
            Scanner in = new Scanner(new FileReader(file));
            int smallestMin=0;
            in.useDelimiter("");
            while(in.hasNext())
            {
                switch(in.next())
                {
                    case "/": max--;min--;break;
                    case "\\": max++;min++;break;
                    case "_": max++;min--;break;
                }
                if(max<0){
                    System.out.println("Impossible");
                    return false;
                }
                if(min<smallestMin)
                {
                    smallestMin=min;
                }
            }
            if(min>0 || max%2==1 || min>smallestMin){
                System.out.println("Impossible");
                return false;
            }
            else{
                System.out.println("Possible");
                return true;
            }
        }catch(Exception e)
        {
            System.out.println("File not found!");
            return false;
        }
    }
    
    void showOrder()
    {
        Scanner in = null;
        try
        {
            in = new Scanner(new FileReader(file));
            in.useDelimiter("");
            int ups = 0-min/2;
            while(in.hasNext())
            {
                if(in.next().equals("_"))
                {
                    if(ups>0){
                        System.out.print("+");
                        ups--;
                    }else{
                        System.out.print("-");
                    }
                }
            }
            System.out.println();
        }catch(Exception e)
        {
            System.out.println("File not found!");
        }
    }
}
