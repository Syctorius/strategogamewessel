package messagefactory;

import messageenum.MessageType;
import messages.*;

public class DefaultFactory {
    public synchronized Message CreateMessage(MessageType type, int teamcolor)
    {
        switch (type)
        {
            case RESETUI:
                return new ResetUiMessage(teamcolor);

            case REMOVEALL:
                return new RemoveAllUnitsMessage(teamcolor);
            case PLACEALL:
                return new PlaceAllUnitsMessage(teamcolor);
            default:
                return null;

        }
    }
}
