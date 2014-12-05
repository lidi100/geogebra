package geogebra.html5.gui.util;

import geogebra.common.euclidian.event.PointerEventType;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.user.client.ui.Widget;

public abstract class ClickStartHandler {

	public static void init(Widget w, final ClickStartHandler handler) {
		w.addDomHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				if (!CancelEventTimer.cancelMouseEvent()) {
					handler.onClickStart(event.getX(), event.getY(),
					        PointerEventType.MOUSE);
				}
			}
		}, MouseDownEvent.getType());

		w.addDomHandler(new TouchStartHandler() {
			public void onTouchStart(TouchStartEvent event) {
				handler.onClickStart(event.getTouches().get(0).getClientX(),
				        event.getTouches().get(0).getClientY(),
				        PointerEventType.TOUCH);
				CancelEventTimer.touchEventOccured();
			}
		}, TouchStartEvent.getType());
	}

	public ClickStartHandler() {
	}

	public abstract void onClickStart(int x, int y, PointerEventType type);
}
