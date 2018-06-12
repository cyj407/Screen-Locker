package controller;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class WindowsTransferEvent extends Event {
	private static final long serialVersionUID = 20180603L;
	public static final EventType<WindowsTransferEvent> TransferToLoading = new EventType<WindowsTransferEvent>(ANY,
			"TransferToLoading");
	public static final EventType<WindowsTransferEvent> TransferToMain = new EventType<WindowsTransferEvent>(ANY,
			"TransferToMain");
	public static final EventType<WindowsTransferEvent> TransferToSetting = new EventType<WindowsTransferEvent>(ANY,
			"TransferToSetting");
	public static final EventType<WindowsTransferEvent> TransferToQuestion = new EventType<WindowsTransferEvent>(ANY,
			"TransferToQuestion");
	public static final EventType<WindowsTransferEvent> RefreshTableView = new EventType<WindowsTransferEvent>(ANY,
			"RefreshTableView");

	public WindowsTransferEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
		super(source, target, eventType);

	}

	@Override
	public WindowsTransferEvent copyFor(Object newSource, EventTarget newTarget) {
		return (WindowsTransferEvent) super.copyFor(newSource, newTarget);
	}

	@Override
	public EventType<? extends WindowsTransferEvent> getEventType() {
		return (EventType<? extends WindowsTransferEvent>) super.getEventType();
	}
}
