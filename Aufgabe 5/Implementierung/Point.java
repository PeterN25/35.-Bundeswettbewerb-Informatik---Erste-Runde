public class Point
{
    private double height, maxTime;
    private boolean big;
    
    /**
     * Erzeugt einen neuen Punkt
     * @param height y-Koordinate
     * @param big Wahr, wenn Max durch die Lücke passt
     */
    public Point(double height,boolean big){
        this.height = height;
        this.big = big;
    }
    
    public double getHeight(){
        return height;
    }
    
    public boolean isBig(){
        return big;
    }
    
    public void setMaxTime(double time){
        maxTime = time;
    }
    
    public double getMaxTime(){
        return maxTime;
    }
}
