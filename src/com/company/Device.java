package com.company;

public class Device{
	private int id;
	private double end;
	private boolean isInUse;
	public Queue queue;

	Device(int _id, double _end){
		id = _id;
		end = _end;
		queue = new Queue(id, end);
		isInUse = false;
	}

	public Event getSeizeEvent(double moment, int transact){
		String seizedDevice="занял устройство ";
		return new Event(end, moment, transact, seizedDevice+id, 3, id);
	}

	public Event getReleaseEvent(double moment, int transact){
		String releasedDevice="освободил устройство ";
		return new Event(end, moment, transact, releasedDevice+id, 4, id);
	}
	public void seize(){
		isInUse = true;
	}
	public void release(){
		isInUse = false;
	}

	public boolean isInUse(){
		return isInUse;
	}
}
