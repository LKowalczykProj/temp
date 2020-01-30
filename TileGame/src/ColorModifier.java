import java.awt.*;

public class ColorModifier {

    public Color darken(Color c, int level){
        double R = c.getRed()*(level*0.10 + 0.5);
        double G = c.getGreen()*(level*0.10 + 0.5);
        double B = c.getBlue()*(level*0.10 + 0.5);
        return new Color((int)R,(int)G,(int)B);
    }
}
