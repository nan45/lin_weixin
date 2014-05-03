package com.test.builder;

public class Student {

	private final int id;
	private final String name;
	private final String idcard;
	private final int age;
	private final String address;
	private final float height;
	private final float weight;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIdcard() {
		return idcard;
	}

	public int getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public float getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}

	public static class Builder implements IBulider<Student>{
		
		private final String name;
		private final int id;
		
		private String idcard = "";
		private int age = 18;
		private String address = "ÖÐ¹ú";
		private float height = 0f;
		private float weight = 0f;
		
		public Builder(int id, String name){
			this.id = id;
			this.name = name;
		}
		public Student build() {
			return new Student(this);
		}
		public void setIdcard(String idcard) {
			this.idcard = idcard;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public void setHeight(float height) {
			this.height = height;
		}
		public void setWeight(float weight) {
			this.weight = weight;
		}
		
		
	}
	
	private Student(Builder builder){
		this.id = builder.id;
		this.name = builder.name;
		this.address = builder.address;
		this.age = builder.age;
		this.idcard = builder.idcard;
		this.height = builder.height;
		this.weight = builder.weight;
	}
}
