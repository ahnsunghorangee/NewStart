package test;

import java.util.PriorityQueue;

public class Sample {
	public static void main(String[] args) {
		PriorityQueue<Vehicle> pq = new PriorityQueue<>();
		pq.add(new Vehicle("대중교통", 70));
		pq.add(new Vehicle("자가용", 45));
		pq.add(new Vehicle("오토바이", 45));
		pq.add(new Vehicle("도보", 400));
		pq.add(new Vehicle("자전거", 125));

		while (!pq.isEmpty()) {
			System.out.println(pq.poll());
		}
	}

	static class Vehicle implements Comparable<Vehicle> {
		private String name;
		private int time;

		Vehicle(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		@Override
		public String toString() {
			return "Vehicle{" + "name='" + name + '\'' + ", time=" + time + '}';
		}

		@Override
		public int compareTo(Vehicle that) {
			if (this.time == that.time)
				return this.name.compareTo(that.name);
			return this.time - that.time;
		}
	}
}
