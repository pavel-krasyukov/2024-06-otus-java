package ru.otus;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
public class HelloOtus {
    public static void main(String... args) {
	List<Integer> example = new ArrayList<>();
	final int min = 0;
	final int max = 33;
	for (int i = min; i < max; i++) {
	    example.add(i);
	}

	System.out.println(Lists.reverse(example));
    }
}
