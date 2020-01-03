package helpers;

import enums.Tile;
import enums.TileType;


public class MultiThreadingField  implements Runnable {
    private TileType tile;
  public Tile[][] tilesInThread;
    BoardGenerationMultiThreading bgmt;

    public MultiThreadingField(TileType tileToCalculate, int width, int length, BoardGenerationMultiThreading m) {
        tile = tileToCalculate;
        bgmt = m;
        tilesInThread = new Tile[width][length];
    }

    @Override
    public void run() {
        if (tile == TileType.BLUELAND) {

            for (int y = 0; y< 4; y++){
                for(int x = 0; x < 10; x++){
                    TileType tile = TileType.BLUELAND;

                    tilesInThread[y][x] = new Tile(tile);

                }
            }
        }


        if (tile == TileType.NEUTRAL) {
            for (int y = 4; y< 6; y++){
                for(int x = 0; x < 10; x++){
                    TileType tile;
                    if(x % 3 == 0) {
                        tile = TileType.WATER;
                    }
                    tile = TileType.NEUTRAL;
                    tilesInThread[y][x] = new Tile(tile);
                }
            }

        }


        if (tile == TileType.REDLAND) {
            for (int y = 6; y< 10; y++){
                for(int x = 0; x < 10; x++){
                    TileType tile = TileType.REDLAND;
                    tilesInThread[y][x] = new Tile(tile);

                }
            }

        }
        bgmt.addTiles(tilesInThread);
    }


}
