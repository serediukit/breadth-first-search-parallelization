package main.java;

public class Result {
    int[] way;
    int weight;
    int length;

    public Result(int[] way, int weight) {
        this.way = way;
        this.weight = weight;
        this.length = way.length - 1;
    }

    public int[] getWay() {
        return way;
    }

    public int getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public void print() {
        System.out.print("Way: ");
        for (int i = 0; i < length; i++)
            System.out.print(way[i] + " -> ");
        System.out.println(way[length]);

        System.out.println("Way weight: " + weight);
        System.out.println("Way length: " + length);
    }
}
