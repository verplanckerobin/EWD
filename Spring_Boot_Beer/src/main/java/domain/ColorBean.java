package domain;

import java.util.List;

public class ColorBean {

    private final List<String> colorsList;

    public ColorBean() {
	colorsList = List.of("light", "brown", "dark");
    }

    public List<String> getColorsList() {
	return colorsList;
    }
}