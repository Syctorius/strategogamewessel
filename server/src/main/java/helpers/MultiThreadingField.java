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
        if (this.tile == TileType.BLUELAND) {

            generateLand(0, 4, TileType.BLUELAND);
        }


       generateLand(4,6,TileType.NEUTRAL);
        //TODO else {  :))))if(x % 3 == 0) {
        //                        tile = TileType.WATER;
        //                    }

        if (this.tile == TileType.REDLAND) {
            generateLand(6, 10, TileType.REDLAND);

        }
        bgmt.addTiles(tilesInThread);
    }

    private void generateLand(int i, int i2, TileType landType) {
        for (int y = i; y < i2; y++) {
            for (int x = 0; x < 10; x++) {
                TileType tiletype = landType;

                tilesInThread[y][x] = new Tile(tiletype);

            }
        }
    }


}
