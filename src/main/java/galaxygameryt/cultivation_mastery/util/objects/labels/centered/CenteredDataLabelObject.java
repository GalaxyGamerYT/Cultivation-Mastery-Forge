package galaxygameryt.cultivation_mastery.util.objects.labels.centered;

import galaxygameryt.cultivation_mastery.util.objects.labels.LabelObject;

public class CenteredDataLabelObject extends CenteredLabelObject {
    public boolean visible = false;

    public CenteredDataLabelObject() {
        this(0,0);
    }

    public CenteredDataLabelObject(int x, int y) {
        super(x, y);
    }

    public void setLabel(CenteredLabelObject label) {
        this.x = label.x;
        this.y = label.y;
    }
}
