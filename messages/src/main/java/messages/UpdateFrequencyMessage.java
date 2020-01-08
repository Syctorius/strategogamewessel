package messages;

import java.util.ArrayList;
import java.util.List;

public class UpdateFrequencyMessage extends Message {
    ArrayList<String> ranks;
    private int color;
    public UpdateFrequencyMessage(ArrayList ranks, int color)

    {
        this.ranks = ranks;
        this.color = color;
    }

    public ArrayList<String> getRanks() {
        return ranks;
    }
    public int getColor() {
        return color;
    }
}
