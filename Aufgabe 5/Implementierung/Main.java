import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

public class Main
{
    ArrayList<ArrayList<Point>> points;
    double maxStart;
    double minnieStart;
    
    public Main(String file)
    {
        points = new ArrayList<>();
        readPoints(file);
        calculateMaxTimes();
        String s = getPossibleMinnieWay();
        System.out.println(s);
    }
    
    public static void main(String[] args)
    {
        for(int i=0; i<args.length; i++)
        {
            new Main(args[0]);
        }
    }
    
    /**
     * Einlesen der Datei und Speicherung der Punkte in einer ArrayList
     * @param file Name der Datei
     */
    private void readPoints(String file)
    {
        try
        {
            Scanner in = new Scanner(new FileReader(file));
            while(in.hasNextLine())
            {
                String s = in.nextLine();
                String[] sf = s.split(" ");
                switch(sf[0]){
                    case "M": minnieStart = Double.parseDouble(sf[2]);break;
                    case "X": maxStart = Double.parseDouble(sf[2]);break;
                    default: 
                    while(Integer.parseInt(sf[1])/70>points.size()){
                        ArrayList<Point> newPoints = new ArrayList<>();
                        points.add(newPoints);
                    }
                    if(Integer.parseInt(sf[1])/70>0){
                        points.get(Integer.parseInt(sf[1])/70-1).add(new Point(Double.parseDouble(sf[2]),sf[0].equals("x")));
                    }
                }
            }
        }catch(Exception e)
        {
            System.out.println("File not found!");
        }
    }
    
    /**
     * Berechnet für jedes Loch die schnellste Zeit in der Max dieses erreichen kann.
     * Diese Werte werden in der ArrayList gespeichert
     */
    private void calculateMaxTimes()
    {
        for(int i=0; i<points.get(0).size(); i++){
            points.get(0).get(i).setMaxTime(calculateDistance(maxStart,points.get(0).get(i).getHeight())/(30/3.6));
        }
        for(int i=1; i<points.size(); i++){
            for(int j=0; j<points.get(i-1).size(); j++){
                if(points.get(i-1).get(j).isBig()){
                    for(int k=0; k<points.get(i).size(); k++){
                        double time = points.get(i-1).get(j).getMaxTime()+calculateDistance(points.get(i-1).get(j).getHeight(),points.get(i).get(k).getHeight())/(30/3.6);
                        if(points.get(i).get(k).getMaxTime()==0 || points.get(i).get(k).getMaxTime()>time)
                        points.get(i).get(k).setMaxTime(time);
                    }
                }
            }
        }
    }
    
    /**
     * Berechnet, ob es für Minnie eine Möglichkeit gibt, Max zu entkommen
     * @return Ein möglicher Fluchtplan in Form von Koordinaten bzw. die Meldung, dass Minnie nicht sicher entkommen kann
     */
    private String getPossibleMinnieWay()
    {
        return calculateMinnieTime(0,minnieStart,0);
    }
    
    /**
     * Berechnet für eine bestimmte Reihe für jeden Punkt, ob Minnie dort schneller sein kann als Max. Falls ja, wird mit dieser Zeit und Position in der nächsten Reihe gerechnet.
     * @param row Die zu betrachtende Reihe
     * @param positionHeight Die y-Koordinate des Lochs
     * @param timeSoFar Die bisher benötigte Zeit
     * @return Ein möglicher Fluchtplan in Form von Koordinaten bzw. die Meldung, dass Minnie nicht sicher entkommen kann
     */
    private String calculateMinnieTime(int row, double positionHeight, double timeSoFar)
    {
        for(int i=0; i<points.get(row).size(); i++)
        {
            double minnieTime = timeSoFar;
            minnieTime += calculateDistance(positionHeight, points.get(row).get(i).getHeight())/(20/3.6);
            if(minnieTime<points.get(row).get(i).getMaxTime()){
                if(points.size()>row+1)
                {
                    String s = calculateMinnieTime(row+1,points.get(row).get(i).getHeight(),minnieTime);
                    if(!s.equals("No way found!")){
                        return "("+(row+1)*70+"/"+points.get(row).get(i).getHeight()+")"+s;
                    }
                }
                else
                {
                    return "("+(row+1)*70+"/"+points.get(row).get(i).getHeight()+")";
                }
            }
        }
        return "No way found!";
    }
    
    /**
     * Berechnung der Distanz von einem Punkt zu einem anderen in der nächsten Reihe
     * @param height1 y-Koordinate des ersten Punkts
     * @param height2 y-Koordinate des zweiten Punkts
     */
    private double calculateDistance(double height1, double height2){
        return Math.sqrt(Math.pow(70,2)+Math.pow(height2-height1,2));
    }
}
