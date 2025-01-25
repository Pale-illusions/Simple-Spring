package test.ioc.converter;

import com.iflove.simplespring.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String source) {
		return Integer.valueOf(source);
	}

}
