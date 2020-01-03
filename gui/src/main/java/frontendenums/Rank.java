package frontendenums;

public enum Rank {

    BOMB(9999),  //calls constructor with value 9999
    MARSHAL(10),  //calls constructor with value 9
    GENERAL(9),   //calls constructor with value 8
    COLONEL(8),   //etc
    MAJOR(7),
    CAPTAIN(6),
    LIEUTENANT(5),
    SERGEANT(4),
    MINER(3),
    SCOUT(2),
    SPY(1),
    FLAG(0),
    UKNOWN(null), // you don't know
    ; // semicolon needed when fields / methods follow


    private Integer rankLevel = 0;

    Rank(Integer ranklevel) {
        this.rankLevel = ranklevel;
    }


    public int getLevelCode() {
        return this.rankLevel;
    }
}

