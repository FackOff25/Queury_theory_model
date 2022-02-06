package com.company;

import java.util.ArrayList;

//Симулирует неограниченную очередь
public class Queue{
	ArrayList<Integer> transacts;
	int id;
	double end;
	Queue(int _id, double _end){
		id = _id;
		end = _end;
		transacts = new ArrayList<>();
	}

	//В очередь кто-то пришёл
	public Event enter(double moment, int transact){
		transacts.add(transact);

		String enteredQueue="встал в очередь ";
		return new Event(end, moment, transact, enteredQueue+id, 5, id);
	}
	//Из очереди
	public Event depart(double moment){
		int transact = transacts.get(0);
		transacts.remove(0);

		String departedQueue="вышел из очереди ";
		return new Event(end, moment, transact, departedQueue+id, 6, id);
	}
	//В очередь кто-то пришёл
	public int getLength(){
		return transacts.size();
	}
	boolean isEmpty(){
		return transacts.isEmpty();
	}
}
