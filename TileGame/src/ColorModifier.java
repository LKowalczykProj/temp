import java.awt.*;

public class ColorModifier {

    public Color darken(Color c, int level){
        double R = c.getRed()*(level*0.2 + 0.4);
        double G = c.getGreen()*(level*0.2 + 0.4);
        double B = c.getBlue()*(level*0.2 + 0.4);
        return new Color((int)R,(int)G,(int)B);
    }
}
