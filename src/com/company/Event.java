package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Event{
	private double end;
	private double time;
	private int transact;
	private String event;
	private int type;                       //Типы событий 1-вошёл в модель, 2-вышел из модели, 3-занял устройство, 4-освободил устройство, 5-занял очередь, 6-освободил очередь
	private int device;
	Event(double _end, double _time, int _transact, String _event, int _type) {
		time = _time;
		transact = _transact;
		event = _event;
		type = _type;
		end = _end;
	}
	Event(double _end,double _time, int _transact, String _event, int _type, int _device) {
		time = _time;
		transact = _transact;
		event = _event;
		type = _type;
		device = _device;
		end = _end;
	}
	public void execute(File file){
		try(FileWriter writer = new FileWriter(file, true))
		{
			if (time>end) writer.write("FEC: ");
			writer.write("В момент времени " + time + " транзакт с идентификатором " + transact + " " + event+"\n");
			writer.flush();
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
		}
	}
	public double getTime(){
		return time;
	}

	public int getTransact(){
		return transact;
	}

	public int getDevice(){
		return device;
	}

	public int getType(){
		return type;
	}
}
