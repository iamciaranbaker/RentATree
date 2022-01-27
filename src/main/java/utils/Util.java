package utils;

import java.text.DecimalFormat;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class Util {
	
	public String getFormattedPrice(double price) {
		return "£" + new DecimalFormat("0.00").format(price);
	}

}
