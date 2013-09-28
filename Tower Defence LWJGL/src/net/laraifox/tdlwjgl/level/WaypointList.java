package net.laraifox.tdlwjgl.level;

import java.util.ArrayList;
import java.util.List;

public class WaypointList {
	private List<Waypoint> waypoints;

	public WaypointList() {
		this.waypoints = new ArrayList<Waypoint>();
	}

	public void addWaypoint(Waypoint waypoint) {
		waypoints.add(waypoint);
	}

	public void removeWaypoint(int i) {
		waypoints.remove(i);
	}

	public void removeWaypoint(Waypoint waypoint) {
		waypoints.remove(waypoint);
	}

	public Waypoint getWaypoint(int i) {
		return waypoints.get(i);
	}

	public Waypoint[] getWaypoints() {
		return waypoints.toArray(new Waypoint[waypoints.size()]);
	}
	
	public int getLength() {
		return waypoints.size();
	}
}
