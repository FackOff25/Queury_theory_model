package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException{
        final int R1 = 7;
        final int G1 = 10;
        final int B1 = 6;
        final int N = R1+G1+B1;
        final int end = 3600;
        final int seed = 1;
        final String enteredToModel = "вошёл в модель";
        final String departedModel = "вышел из модели";

        File logFile = new File("modelLog.txt");
        Files.deleteIfExists(logFile.toPath());
        ArrayList<Event> events = new ArrayList<>(){
            Comparator<Event> eventComparator = Comparator.comparingDouble(Event::getTime);
            @Override
            public boolean add(Event event){
                boolean toReturn = super.add(event);
                super.sort(eventComparator);
                return toReturn;
            }
        };

        Random randomFlow = new Random(seed);
        double moment=0;
        Event event;

        Device device1 = new Device(1, end);
        Device device2 = new Device(2, end);

        //Все входы в модуль
        int transact=0;
        while (moment<end){
            moment += randomFlow.nextDouble()*N;
            transact++;
            event = new Event(end, moment, transact, enteredToModel, 1);
            events.add(event);
        }

        while (!events.isEmpty()){
            event = events.get(0);
            events.remove(0);

            //if (event.getTime()>3600) break;                                                                            //Строка, если нужно останавливать расчёт при окончании смены

            moment = event.getTime();
            switch (event.getType()) {
                case 1:
                    event.execute(logFile);
                    if (!device1.isInUse()) {
                        device1.seize();
                        device1.getSeizeEvent(moment, event.getTransact()).execute(logFile);
                        events.add(device1.getReleaseEvent(moment+R1+randomFlow.nextDouble()*N, event.getTransact()));
                    }else if (!device2.isInUse()) {
                        device2.seize();
                        device2.getSeizeEvent(moment, event.getTransact()).execute(logFile);
                        events.add(device2.getReleaseEvent(moment+G1+randomFlow.nextDouble()*N, event.getTransact()));
                    }else if (device1.queue.getLength()>device2.queue.getLength()) {
                        device2.queue.enter(moment, event.getTransact()).execute(logFile);
                    }else{
                        device1.queue.enter(moment, event.getTransact()).execute(logFile);
                    }
                    break;
                case 4:
                    event.execute(logFile);
                    new Event(end, moment, event.getTransact(), departedModel, 2).execute(logFile);
                    Device device;
                    if(event.getDevice()==1){
                        device = device1;
                    }else{
                        device = device2;
                    }

                    device.release();
                    if (!device.queue.isEmpty()) {
                        event = device.queue.depart(moment);
                        event.execute(logFile);
                        device.seize();
                        device.getSeizeEvent(moment, event.getTransact()).execute(logFile);
                        events.add(device1.getReleaseEvent(moment+R1+randomFlow.nextDouble()*N, event.getTransact()));
                    }
                    break;
                default:
                    event.execute(logFile);
            }

        }
    }
}
