package com.demo.processmanager.test;

/**
 * 快速排序算法实现
 * 用于测试ProcessManager项目的排序功能
 */
public class QuickSort {
    
    /**
     * 快速排序主方法
     * @param arr 待排序的数组
     * @param low 起始索引
     * @param high 结束索引
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 获取分区点
            int pivotIndex = partition(arr, low, high);
            
            // 递归排序左半部分
            quickSort(arr, low, pivotIndex - 1);
            
            // 递归排序右半部分
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    
    /**
     * 分区方法
     * @param arr 待分区的数组
     * @param low 起始索引
     * @param high 结束索引
     * @return 分区点的索引
     */
    private static int partition(int[] arr, int low, int high) {
        // 选择最后一个元素作为基准
        int pivot = arr[high];
        
        // 小于基准的元素的索引
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            // 如果当前元素小于或等于基准
            if (arr[j] <= pivot) {
                i++;
                
                // 交换arr[i]和arr[j]
                swap(arr, i, j);
            }
        }
        
        // 将基准元素放到正确位置
        swap(arr, i + 1, high);
        
        return i + 1;
    }
    
    /**
     * 交换数组中两个元素的位置
     * @param arr 数组
     * @param i 第一个元素的索引
     * @param j 第二个元素的索引
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * 打印数组
     * @param arr 要打印的数组
     */
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        int[] testArray = {64, 34, 25, 12, 22, 11, 90};
        
        System.out.println("原始数组:");
        printArray(testArray);
        
        quickSort(testArray, 0, testArray.length - 1);
        
        System.out.println("排序后数组:");
        printArray(testArray);
        
        // 测试排序结果
        boolean isSorted = true;
        for (int i = 0; i < testArray.length - 1; i++) {
            if (testArray[i] > testArray[i + 1]) {
                isSorted = false;
                break;
            }
        }
        
        System.out.println("排序验证: " + (isSorted ? "成功" : "失败"));
    }
}