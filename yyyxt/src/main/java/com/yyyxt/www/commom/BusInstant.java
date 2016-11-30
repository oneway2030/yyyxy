package com.yyyxt.www.commom;


import com.squareup.otto.Bus;

public class BusInstant {
	static Bus bus = new Bus();
	public static Bus getBus() {
		return bus;
	}
}
