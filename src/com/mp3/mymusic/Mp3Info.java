package com.mp3.mymusic;


public class Mp3Info {

	private int id;
	private String title;
	private String artist;
	private String duration;
	private String  size;
	private String url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Mp3Info [id=" + id + ", title=" + title + ", artist=" + artist
				+ ", duration=" + duration + ", size=" + size + ", url=" + url
				+ "]";
	}
	public Mp3Info(int id, String title, String artist, String duration,
			String size, String url) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.duration = duration;
		this.size = size;
		this.url = url;
	}
	public Mp3Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
