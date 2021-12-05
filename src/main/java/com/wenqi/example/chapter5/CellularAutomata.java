package com.wenqi.example.chapter5;

import javafx.concurrent.Worker;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 通过CyclicBarrier协调细胞自动衍生系统中的计算
 *
 * @author liangwenqi
 * @date 2021/9/17
 */
public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata (Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (Boolean.FALSE.equals(board.hasConverged())) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    return;
                } catch (BrokenBarrierException e) {
                    return;
                }
            }
        }

        public void start() {
            for (int i = 0; i < workers.length; i++) {
                new Thread(workers[i]).start();
                mainBoard.watForConvergence();
            }
        }
        public int computeValue(int x, int y) {
            return x + y;
        }
    }


    private class Board {
        private int x;
        private int y;

        public int getMaxX() {
            return x;
        }

        public int getMaxY() {
            return y;
        }

        public void watForConvergence() {

        }

        public void commitNewValues() {

        }

        public Board getSubBoard(int count , int i) {
            return new Board();
        }

        public Boolean hasConverged() {
            return Boolean.FALSE;
        }

        public void setNewValue(int x, int y, int z) {

        }
    }

}
