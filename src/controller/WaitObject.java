//
//  Distributed Algorithms in Java
//  See file copyright.txt for credits and copyright.
//
//  Moti Ben-Ari
//  WaitObject for synchronization with event handlers.
package controller;
class WaitObject {
	public synchronized void waitOK()   { 
		try { wait(); } catch (InterruptedException e) {}; 
	}
	public synchronized void signalOK() { 
		notify(); 
	}
}

