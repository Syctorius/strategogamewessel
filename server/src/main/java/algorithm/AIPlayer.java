package algorithm;

import enums.Color;
import helpers.Board;

import java.awt.*;
import java.util.ArrayList;

public class AIPlayer {
    int points;
    Color color;
    String type;


    AIPlayer(Color color) {
        points = 0;
        this.color = color;
    }

    AIPlayer(AIPlayer origin) {
        points = origin.points;
        color = origin.color;
        type = origin.type;
    }

    @Override
    public String toString() {
        return color.toString();
    }
    void playAI(Board board){

    }

    Point playRandom(Board board) {
        int random_index = (int) (Math.random() * 10); // getwidth
    ArrayList possibleMoves = board.getEmptyFields(color);
        while (random_index > possibleMoves.size() - 1) {
            random_index = (int) (Math.random() * 10);
        }
        return (Point) possibleMoves.get(random_index);
    }

  /*  Field playAlpha(Board board, User enemy) {
        Field move = board.getEmptyFields().get(0);
        int best = Integer.MIN_VALUE;
        for (Field field : board.getEmptyFields()) {
            Board nextMove = new Board(board, field, this);
            int moveValue = abeta(nextMove, Finals.DEPTH, true, enemy, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (moveValue > best) {
                best = moveValue;
                move = field;
            }
            field.player = null;

        }
        return move;
    }

    int abeta(Board node, int depth, boolean maximizingPlayer, User enemy, int alpha, int beta) {
        int points = this.points;
        if (depth == 0 || node.getEmptyFields().size() == 1) {
            return getFinalMovePoints(node, maximizingPlayer, enemy);
        }

        if (maximizingPlayer) {
            Double bestValue = Double.NEGATIVE_INFINITY;
            for (Field field : node.getEmptyFields()) {
                Board nextMove = new Board(node, field, this);
                PointsCalculator pc_max = new PointsCalculator(nextMove, field);
                points += pc_max.calculatePoints();
                int v = abeta(nextMove, depth - 1, false, enemy, alpha, beta);
                bestValue = Math.max(bestValue, v);
                field.player = null;

                alpha = Math.max(alpha, bestValue.intValue());
                if (beta <= alpha) break;
            }
            return bestValue.intValue();
        }

        Double bestValue = Double.POSITIVE_INFINITY;
        for (Field field : node.getEmptyFields()) {
            Board nextMove = new Board(node, field, enemy);
            PointsCalculator pc_min = new PointsCalculator(nextMove, field);
            enemy.points += pc_min.calculatePoints();
            int v = abeta(nextMove, depth - 1, true, enemy, alpha, beta);
            bestValue = Math.min(bestValue, v);
            field.player = null;

            beta = Math.min(beta, bestValue.intValue());
            if (beta <= alpha) break;
        }
        return bestValue.intValue();
    }

    Field playMinmax(Board board, User enemy) {
        Field move = board.getEmptyFields().get(0);
        int best = Integer.MIN_VALUE;
        for (Field field : board.getEmptyFields()) {
            Board nextMove = new Board(board, field, this);
            int moveValue = minmax(nextMove, Finals.DEPTH, true, enemy);
            if (moveValue > best) {
                best = moveValue;
                move = field;
            }
            field.player = null;

        }
        return move;
    }

    int minmax(Board node, int depth, boolean maximizingPlayer, User enemy) {
        int points = this.points;
        if (depth == 0 || node.getEmptyFields().size() == 1) {
            return getFinalMovePoints(node, maximizingPlayer, enemy);
        }

        if (maximizingPlayer) {
            Double bestValue = Double.NEGATIVE_INFINITY;
            for (Field field : node.getEmptyFields()) {
                Board nextMove = new Board(node, field, this);
                PointsCalculator pc_max = new PointsCalculator(nextMove, field);
                points += pc_max.calculatePoints();
                int v = minmax(nextMove, depth - 1, false, enemy);
                bestValue = Math.max(bestValue, v);
                field.player = null;
            }
            return bestValue.intValue();
        }

        Double bestValue = Double.POSITIVE_INFINITY;
        for (Field field : node.getEmptyFields()) {
            Board nextMove = new Board(node, field, enemy);
            PointsCalculator pc_min = new PointsCalculator(nextMove, field);
            enemy.points += pc_min.calculatePoints();
            int v = minmax(nextMove, depth - 1, true, enemy);
            bestValue = Math.min(bestValue, v);
            field.player = null;

        }
        return bestValue.intValue();
    }

    int getFinalMovePoints(Board node, boolean maximizingPlayer, User enemy) {
        if (node.getEmptyFields().size() == 0)
            return maximizingPlayer ? points : enemy.points;
        PointsCalculator pc = new PointsCalculator(node, node.getEmptyFields().get(0));
        return (maximizingPlayer ? points : enemy.points) + pc.calculatePoints();
    }*/
}
