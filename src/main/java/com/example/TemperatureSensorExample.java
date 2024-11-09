package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TemperatureSensorExample {
    
    public static void main(String[] args) {
        //Crear un pool de 3 hilos, uno para cada "sensor"
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //Lista de tareas que simulan las lecturas de los sensores
        List<Callable<Double>> sensors = Arrays.asList(
                () -> getTemperature("Sensor 1"),
                () -> getTemperature("Sensor 2"),
                () -> getTemperature("Sensor 3")
        );

        try {
            //Ejecutar todas las tareas (lecturas de sensores)
            List<Future<Double>> results = executor.invokeAll(sensors);

            //Procesar los resultados
            double sum = 0;
            for (Future<Double> result : results) {
                sum += result.get(); //Obtener la temperatura del sensor
            }
            
            double averageTemperature = sum / results.size();
            System.out.println("Temperatura Promedio: " + averageTemperature);
            
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); //Finalizar el pool de hilos
        }
    }
    
    //Método para simular la obtención de temperatura de un sensor
    private static Double getTemperature(String sensorName) throws InterruptedException {
        double temperature = Math.random() * 100; //Simulando temperatura entre 0 y 100
        System.out.println(sensorName + " temperatura: " + temperature);
        Thread.sleep(1000); //Pausa para simular el tiempo de lectura
        return temperature;
    }
}
