package galaxygameryt.cultivation_mastery.util.objects.labels;

public class DataLabelObject extends LabelObject {
    public boolean visible = false;

    public DataLabelObject() {
        this(0,0);
    }

    public DataLabelObject(int left, int top) {
        super(left, top);
    }

    public void setLabel(LabelObject label) {
        this.left = label.left;
        this.top = label.top;
    }
}
