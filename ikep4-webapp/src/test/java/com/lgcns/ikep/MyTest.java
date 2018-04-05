package com.lgcns.ikep;

import java.util.Arrays;

public class MyTest {

	public static void main(String[] args) {
		String value = "Zbcdefg";
		String reValue ="";
		char[] array = value.toCharArray();

		Arrays.sort(array);
		System.out.println(new String(array));

		for(int i = array.length ; i == 0 ; i--) {
			reValue += array[i];
		}
		System.out.println("reValue>>>"+reValue);
	}

	public static int getMean(int[] array) {
      	int sum = 0;
      	for(int number : array) {
      		sum += number;
        }
      	System.out.println(sum);

        return sum/array.length;
    }

}
