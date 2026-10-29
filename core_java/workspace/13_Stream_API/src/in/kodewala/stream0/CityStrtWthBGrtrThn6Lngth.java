package in.kodewala.stream0;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Write a program to find a list which starts 'B' and having length greater than 6 from a given list
 */

public class CityStrtWthBGrtrThn6Lngth {

	public static void main(String[] args) {

		List<String> cities = Arrays.asList("Banglore", "Delhi", "Uttar Pradesh", "Bhopal", "Bhubaneswar", "Dehradun");
		
		List<String> output = cities.stream()
		      .filter(city -> city.startsWith("B") && city.length() > 6  )
		      .collect(Collectors.toList());
		
		System.out.println(output);  
	}
}
