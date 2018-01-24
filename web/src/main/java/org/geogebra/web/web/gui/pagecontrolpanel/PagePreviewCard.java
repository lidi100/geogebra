package org.geogebra.web.web.gui.pagecontrolpanel;

import org.geogebra.common.euclidian.event.KeyEvent;
import org.geogebra.common.euclidian.event.KeyHandler;
import org.geogebra.common.euclidian.event.PointerEventType;
import org.geogebra.common.gui.SetLabels;
import org.geogebra.common.main.Feature;
import org.geogebra.common.main.Localization;
import org.geogebra.common.util.StringUtil;
import org.geogebra.web.html5.Browser;
import org.geogebra.web.html5.euclidian.EuclidianViewWInterface;
import org.geogebra.web.html5.event.FocusListenerW;
import org.geogebra.web.html5.gui.inputfield.AutoCompleteTextFieldW;
import org.geogebra.web.html5.gui.util.ClickStartHandler;
import org.geogebra.web.html5.gui.util.MyToggleButton;
import org.geogebra.web.html5.gui.util.NoDragImage;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.html5.main.GgbFile;
import org.geogebra.web.resources.SVGResource;
import org.geogebra.web.web.css.MaterialDesignResources;
import org.geogebra.web.web.gui.view.algebra.InputPanelW;

import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * Page Preview Card showing preview of EuclidianView
 * 
 * @author Alicia Hofstaetter
 *
 */
public class PagePreviewCard extends FlowPanel implements DragStartHandler,
DragOverHandler, DragLeaveHandler, DropHandler, SetLabels {

	public interface ReorderListener {
		void reorder(int srcIdx, int destIdx);
	}
	
	private static final int LABELFONT_SIZE = 16;
	private AppW app;
	private Localization loc;
	private int pageIndex;
	private FlowPanel imagePanel;
	private String image;
	private FlowPanel titlePanel;
	private AutoCompleteTextFieldW textField;
	private boolean isTitleSet = false;
	private MyToggleButton moreBtn;
	private ContextMenuPagePreview contextMenu = null;
	private static PagePreviewCard dragCard;
	/**
	 * ggb file
	 */
	protected GgbFile file;
	
	private HandlerRegistration hrDragStart=null;
	private HandlerRegistration hrDragEnter=null;
	private HandlerRegistration hrDragOver=null;
	private HandlerRegistration hrDragLeave=null;
	private HandlerRegistration hrDrop=null;
	private ReorderListener reorderListener =null;
	
	/**
	 * @param app
	 *            parent application
	 * @param pageIndex
	 *            current page index
	 * @param file
	 *            see {@link GgbFile}
	 */
	public PagePreviewCard(AppW app, int pageIndex, GgbFile file) {
		this.app = app;
		this.pageIndex = pageIndex;
		this.file = file;
		this.loc = app.getLocalization();
		this.image = file.get("geogebra_thumbnail.png");
		initGUI();
		if (app.has(Feature.MOW_DRAG_AND_DROP_PAGES)) {
			getElement().setAttribute("draggable", "true");
			addDragStartHandler(this);
			addDragOverHandler(this);
			addDragLeaveHandler(this);
			addDropHandler(this);
		}
	}


	/**
	 * Duplicates card with pageIndex incremented by 1.
	 * @param source to duplicate.
	 * @return The duplicated card.
	 */
	public static PagePreviewCard duplicate(PagePreviewCard source) {
		return new PagePreviewCard(source.app, source.getPageIndex() + 1, source.getFile().duplicate());
	}
	
	private void initGUI() {
		addStyleName("mowPagePreviewCard");

		imagePanel = new FlowPanel();
		imagePanel.addStyleName("mowImagePanel");

		titlePanel = new FlowPanel();
		titlePanel.addStyleName("mowTitlePanel");

		add(imagePanel);
		add(titlePanel);
		if (StringUtil.empty(image)) {
			updatePreviewImage();
		} else {
			setPreviewImage(image);
		}
		addTextField();
		updateLabel();
		addMoreButton();
	}

	private void addTextField() {
		textField = InputPanelW.newTextComponent(app);
		textField.setAutoComplete(false);
		textField.addFocusListener(new FocusListenerW(this) {
			@Override
			protected void wrapFocusLost() {
				rename();
			}
		});
		textField.addKeyHandler(new KeyHandler() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isEnterKey()) {
					rename();
				}
			}
		});
		titlePanel.add(textField);
	}

	/**
	 * remember if title was renamed
	 */
	protected void rename() {
		if (textField.getText().equals(getDefaultLabel())) {
			isTitleSet = false;
		} else {
			isTitleSet = true;
		}
		setTextFieldWidth();
		textField.setFocus(false);
	}

	/**
	 * using an approximate calculation for text field width.
	 * 
	 */
	private void setTextFieldWidth() {
		int length = LABELFONT_SIZE * (textField.getText().length() + 2);
		textField.setWidth(Math.max(Math.min(length, 178), 10));
	}


	/**
	 * @return ggb file associated to this card
	 */
	public GgbFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            see {@link GgbFile}
	 */
	public void setFile(GgbFile file) {
		this.file = file;
	}

	private void setPreviewImage(String img) {
		image = img;
		if (image != null && image.length() > 0) {
			imagePanel.getElement().getStyle().setBackgroundImage(
					"url(" + Browser.normalizeURL(image) + ")");
		}
	}

	/**
	 * Updates the preview image
	 */
	public void updatePreviewImage() {
		imagePanel.clear();
		setPreviewImage(((EuclidianViewWInterface) app.getActiveEuclidianView())
				.getExportImageDataUrl(0.2, false));
	}

	private String getDefaultLabel() {
		return loc.getMenu("page") + " " + (pageIndex + 1);
	}

	private void updateLabel() {
		if (!isTitleSet) {
			textField.setText(getDefaultLabel());
			setTextFieldWidth();
		}
	}

	/**
	 * get the index of the page
	 * 
	 * @return page index
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * set index of page
	 * 
	 * note: this will also update the title of the page
	 * 
	 * @param index
	 *            new index
	 */
	public void setPageIndex(int index) {
		pageIndex = index;
		updateLabel();
	}

	private void addMoreButton(){
		if (moreBtn == null) {
			moreBtn = new MyToggleButton(
					getImage(
							MaterialDesignResources.INSTANCE.more_vert_black()),
					app);
		}
		moreBtn.getUpHoveringFace()
				.setImage(getImage(
						MaterialDesignResources.INSTANCE.more_vert_mebis()));
		moreBtn.addStyleName("mowMoreButton");
		ClickStartHandler.init(moreBtn, new ClickStartHandler(true, true) {
			@Override
			public void onClickStart(int x, int y, PointerEventType type) {
				toggleContexMenu();
			}
		});
		titlePanel.add(moreBtn);
	}
	
	private static Image getImage(SVGResource res) {
		return new NoDragImage(res, 24, 24);
	}

	/**
	 * show context menu of preview card
	 */
	protected void toggleContexMenu() {
		if (contextMenu == null) {
			contextMenu = new ContextMenuPagePreview(app, this);
		}
		if (contextMenu.isShowing()) {
			contextMenu.hide();
			toggleMoreButton(false);
		} else {
			contextMenu.show(moreBtn.getAbsoluteLeft() - 134,
				moreBtn.getAbsoluteTop() + 33);
			toggleMoreButton(true);
		}
	}
	
	private void toggleMoreButton(boolean toggle) {
		if (toggle) {
			moreBtn.getUpFace().setImage(getImage(
					MaterialDesignResources.INSTANCE.more_vert_mebis()));
			moreBtn.addStyleName("active");
		} else {
			moreBtn.getUpFace().setImage(getImage(
					MaterialDesignResources.INSTANCE.more_vert_black()));
			moreBtn.removeStyleName("active");
		}
	}

	@Override
	public void setLabels() {
		if (moreBtn != null) {
			moreBtn.setAltText(loc.getMenu("Options"));
		}
		if (contextMenu != null) {
			contextMenu.setLabels();
		}
		updateLabel();
	}

	public void addDragStartHandler(DragStartHandler handler) {
		hrDragStart = addDomHandler(handler, DragStartEvent.getType());
	}

	public void addDropHandler(DropHandler handler) {
		hrDrop = addDomHandler(handler, DropEvent.getType());
	}

	public void addDragOverHandler(DragOverHandler handler) {
		hrDragOver = addDomHandler(handler, DragOverEvent.getType());
	}

	public void addDragLeaveHandler(DragLeaveHandler handler) {
		hrDragLeave = addDomHandler(handler, DragLeaveEvent.getType());
	}

	public void addDragEnterHandler(DragEnterHandler handler) {
		hrDragEnter = addDomHandler(handler, DragEnterEvent.getType());
	}

	public void removeDragNDrop() {
		if (hrDragStart != null) {
			hrDragStart.removeHandler();
		}

		if (hrDrop != null) {
			hrDrop.removeHandler();
		}

		if (hrDragOver != null) {
			hrDragOver.removeHandler();
		}

		if (hrDragLeave != null) {
			hrDragLeave.removeHandler();
		}

		if (hrDragEnter != null) {
			hrDragEnter.removeHandler();
		}
	}


	@Override
	public void onDragStart(DragStartEvent event) {
		dragCard = this;
		event.setData("text", "dragging preview card");
		event.getDataTransfer().setDragImage(getElement(), 10, 10);
		event.stopPropagation();
//		addStyleName("dragged");
	}

	private void removeDragStyles() {
		removeStyleName("dragOver");
		removeStyleName("dragLeave");
	}

	@Override
	public void onDragOver(DragOverEvent event) {
		event.preventDefault();
		removeStyleName("dragLeave");
		addStyleName("dragOver");
	}

	@Override
	public void onDragLeave(DragLeaveEvent event) {
		removeStyleName("dragOver");
		addStyleName("dragLeave");
	}

	@Override
	public void onDrop(DropEvent event) {
		removeDragStyles();
//		removeStyleName("dragged");
		event.preventDefault();
		if (reorderListener != null && dragCard != null) {
			reorderListener.reorder(dragCard.getPageIndex(), getPageIndex());
		}
	}


	public ReorderListener getReorderListener() {
		return reorderListener;
	}


	public void setReorderListener(ReorderListener reorderListener) {
		this.reorderListener = reorderListener;
	}
}

