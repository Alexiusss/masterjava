package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class MatrixUtil {

    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor)
            throws InterruptedException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < matrixSize; i++) {
            final int finalI = i;
            tasks.add(() -> {
                int[] secondMatrixColumn = new int[matrixSize];
                for (int j = 0; j < matrixSize; j++) {
                    secondMatrixColumn[j] = matrixB[j][finalI];
                }
                calculateSum(matrixA, matrixSize, matrixC, secondMatrixColumn, finalI);
                return null;
            });
        }

        executor.invokeAll(tasks);
        return matrixC;
    }

    // https://habrahabr.ru/post/114797/
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        int[] secondMatrixColumn = new int[matrixSize];

        for (int i = 0; i < matrixSize; i++) {

            for (int j = 0; j < matrixSize; j++) {
                secondMatrixColumn[j] = matrixB[j][i];
            }

            calculateSum(matrixA, matrixSize, matrixC, secondMatrixColumn, i);
        }
        return matrixC;
    }

    private static void calculateSum(int[][] matrixA, int matrixSize, int[][] matrixC, int[] secondMatrixColumn, int finalI) {
        for (int j = 0; j < matrixSize; j++) {

            int[] thisRow = matrixA[j];
            int sum = 0;
            for (int k = 0; k < matrixSize; k++) {
                sum += thisRow[k] * secondMatrixColumn[k];
            }
            matrixC[finalI][j] = sum;
        }
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
