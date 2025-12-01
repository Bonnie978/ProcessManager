package algorithms;

/**
 * 冒泡排序算法实现
 * 提供基本的冒泡排序功能，支持整型数组排序
 */
public class BubbleSort {
    
    /**
     * 冒泡排序主方法
     * @param arr 待排序的整型数组
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        
        // 外层循环控制排序轮数
        for (int i = 0; i < n - 1; i++) {
            // 内层循环进行相邻元素比较和交换
            for (int j = 0; j < n - 1 - i; j++) {
                // 如果前一个元素大于后一个元素，则交换
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
    
    /**
     * 优化的冒泡排序方法
     * 当某一轮没有发生交换时，说明数组已经有序，可以提前结束排序
     * @param arr 待排序的整型数组
     */
    public static void optimizedBubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            
            // 如果这一轮没有发生交换，说明数组已经有序
            if (!swapped) {
                break;
            }
        }
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
     * 打印数组内容
     * @param arr 要打印的数组
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("数组为空");
            return;
        }
        
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        // 测试基本冒泡排序
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("原始数组:");
        printArray(arr1);
        
        bubbleSort(arr1);
        System.out.println("基本冒泡排序后:");
        printArray(arr1);
        
        // 测试优化冒泡排序
        int[] arr2 = {5, 1, 4, 2, 8};
        System.out.println("\n原始数组:");
        printArray(arr2);
        
        optimizedBubbleSort(arr2);
        System.out.println("优化冒泡排序后:");
        printArray(arr2);
        
        // 测试已排序数组
        int[] arr3 = {1, 2, 3, 4, 5};
        System.out.println("\n已排序数组:");
        printArray(arr3);
        
        optimizedBubbleSort(arr3);
        System.out.println("优化冒泡排序后:");
        printArray(arr3);
    }
}