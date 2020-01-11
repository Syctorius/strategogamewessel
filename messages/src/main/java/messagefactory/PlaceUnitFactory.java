package messagefactory;

import messageenum.MessageType;
import messages.*;

import java.awt.*;

public class PlaceUnitFactory {
    public synchronized Message CreateMessage(MessageType type, Point pointToPlace, int teamcolor ,String rank) {
        Message result;
        switch (type) {
            case PLACEUNIT:
                result = new PlaceUnitMessage(pointToPlace,teamcolor,rank);
                break;

            case PLACEUNITFOROPPONENT:
                result = new PlaceUnitForOpponentMessage(pointToPlace,teamcolor,rank);
                break;

            default:
                result = null;
                break;

        }
        return result;
    }
}
