package domain;

import exceptions.PerformanceException;

public class Singer implements Performer {
    private String name;
    private String song;

    public Singer(String name) {
	this(name, null);
    }

    public Singer(String name, String song) {
	this.name = name;
	this.song = song;
    }

    @Override
    public void perform() throws PerformanceException {
	if (song == null) {
	    throw new PerformanceException(name + " doesn't have a song to sing.");
	}
	System.out.println(name + " is singing " + song);
    }

    public void setSong(String song) {
	this.song = song;
    }
}
