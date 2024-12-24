package search;

import java.util.Arrays;

public class BinarySearch {
    // :NOTE: args.length >= 2
    //Pre: args.length always > 0 && i belongs Z && 0 <= i < args.length
    // && args - array, sorted in ascending order && Integer.parseInt(args[i]) belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
    //Post: args.length > 0 && always Integer.parseInt(args[i]) belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
    public static void main(String[] args) {
        //Pre: args.length > 0 && always Integer.parseInt(args[0])
        // belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        
        int target = Integer.parseInt(args[0]);
        //Post target is int && target belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        System.err.println(Arrays.toString(args));
        //Pre: args.length > 1 &&
        // always Integer.parseInt(args[i]) belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        int[] arr = new int[args.length - 1];
        //Post: arr[i] is an array of int's, arr[i]
        // belongs [Integer.MIN_VALUE; Integer.MAX_VALUE] && i > 0 && arr.length is int

        //Pre: -
        int i = 1;
        //Post: i belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]

        //Pre: i belongs [Integer.MIN_VALUE; Integer.MAX_VALUE] && args.length is int
        while (i < args.length) {
            //Post: i < args.length

            //Pre Integer.parseInt(args[i]) belongs Z && Integer.parseInt(args[i])
            // belongs [Integer.MIN_VALUE; Integer.MAX_VALUE] && i - 1 > -1
            // && i - 1 belongs Z
            arr[i - 1] = Integer.parseInt(args[i]);
            //Post arr[i - 1] belongs Z && arr[i - 1]
            // belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]

            //Pre: i belongs Z && i > 0
            i++;
            //Post: i belongs Z && i <= args.length
        }
        //Pre: -
        int left = -1;
        //Post: left belongs Z && left == -1

        //Pre: -
        int right = arr.length;
        //Post: right belongs Z && right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        // && right == arr.length

        //Pre: arr -> int[] && left left belongs Z && left == -1 && right belongs Z && right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        // && right == arr.length && target is int
        // && target belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        System.out.println(iterBinarySearch(arr, left, right, target));
        //Post: output of an int value && belongs [0, arr.length]
    }

    // :NOTE: contract?
    static int iterBinarySearch (int[] arr, int left, int right, int target) {
        //Pre: -
        int middle = 0;
        // Post: middle belongs Z && middle == 0

        //Pre: right is int && left is int && left, right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        while (right - left > 1) {
            //Post: while right - left > 1

            //Pre: right is int && left is int && left, right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
            middle = (right + left) / 2;
            //Post: middle belongs int && (middle == (right + left) / 2) && middle > -1
            //  && middle < arr.length

            //Pre: middle belongs int && (middle == (right + left) / 2) && middle > -1
            // && middle < arr.length
            // && target is int && target belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
            if (arr[middle] <= target) {
                //Post: arr[middle] <= target
                // && right == middle && middle is int

                //Pre: arr[middle] <= target
                // && right == middle && middle is int
                right = middle;
                //Post: right == middle && right is int && right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
            } else {
                //Post: arr[middle] > target
                // && left == middle && middle is int

                //Pre: arr[middle] > target
                // && left == middle && middle is int
                left = middle;
                //Post: left == middle && left is int && left belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
            }
        }
        //Pre: right is int && right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        return right;
        //Post: return right && right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE] && right is int
    }
    static int recBinarySearch(int[] arr, int left, int right, int target) {
        //Pre: right is int && left is int && left, right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        int middle = (right + left) / 2;
        //Post: middle belongs int && (middle == (right + left) / 2) && middle > -1

        //Pre: right is int && left is int && left, right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        if (right - left > 1) {
            //Post: right - left > 1

            //Pre:  middle belongs int && (middle == (right + left) / 2) && middle > -1
            // && middle < arr.length
            // && target is int && target belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
            if (arr[middle] <= target) {
                //Pre: arr -> int[] && left belongs Z  && right belongs Z && right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
                // && right == middle && target is int
                // && target belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
                return recBinarySearch(arr, left, middle, target);
                //Post: right == middle
            } else {
                //Pre: arr -> int[] && left belongs Z && left == middle && right belongs Z && right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
                // && target is int
                // && target belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
                return recBinarySearch(arr, middle, right, target);
                //Post: left == middle
            }
        }
        //Post right - left <= 1

        //Pre right - left <= 1 && right is int && left is int
        // && left, right belongs [Integer.MIN_VALUE; Integer.MAX_VALUE]
        return right;
        //Post: return right && right is int
    }
}
