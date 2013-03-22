/*******************************************************************************
 *  Copyright (c) 2010 Weltevree Beheer BV, Remain Software & Industrial-TSI
 *                                                                      
 * All rights reserved. This program and the accompanying materials     
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at             
 * http://www.eclipse.org/legal/epl-v10.html                            
 *                                                                      
 * Contributors:                                                        
 *    Wim Jongman - initial API and implementation
 *******************************************************************************/
package org.eclipse.ecf.salvo.ui.internal.views;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.james.mime4j.codec.DecoderUtil;
import org.apache.james.mime4j.parser.MimeStreamParser;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.ecf.protocol.nntp.core.ServerStoreFactory;
import org.eclipse.ecf.protocol.nntp.model.IArticle;
import org.eclipse.ecf.protocol.nntp.model.SALVO;
import org.eclipse.ecf.salvo.ui.internal.MimeArticleContentHandler;
import org.eclipse.ecf.salvo.ui.internal.editor.ArticlePanel;
import org.eclipse.ecf.salvo.ui.internal.editor.ArticleWidgetBuilder;
import org.eclipse.ecf.salvo.ui.internal.resources.ISalvoResource;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class MessageView implements ISelectionProvider {

	private IArticle article;

	private Composite parent;

	@Inject
	MPart part;

	public static String ID = "org.eclipse.ecf.salvo.ui.internal.views.messageView";

	public MessageView() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void createPartControl(Composite parent, ESelectionService service) {
		this.parent = parent;
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.marginWidth = 0;
		gridLayout_1.marginHeight = 0;
		parent.setLayout(gridLayout_1);
		
		description = new Label(parent, SWT.NONE);
		description.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false));
		description.setText("Please select an article");
		

		initializeToolBar();
		// service.addPostSelectionListener(this);
		// getSite().setSelectionProvider(this);

	}

	@Focus
	public void setFocus() {
		parent.getChildren()[0].setFocus();
	}

	private void initializeToolBar() {
		// IToolBarManager toolBarManager =
		// getViewSite().getActionBars().getToolBarManager();
	}

	List<ISelectionChangedListener> listeners = new ArrayList<ISelectionChangedListener>();

	ISelection theSelection = StructuredSelection.EMPTY;

	private Label description;

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	public ISelection getSelection() {
		return theSelection;
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	public void setSelection(ISelection selection) {
		theSelection = selection;
		final SelectionChangedEvent e = new SelectionChangedEvent(this,
				selection);
		Object[] listenersArray = listeners.toArray();

		for (int i = 0; i < listenersArray.length; i++) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listenersArray[i];
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(e);
				}
			});
		}
	}

	@Inject
	public void selectionChanged(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) ISalvoResource resource) {

		IArticle newArticle = null;

		if (resource == null || !(resource.getObject() instanceof IArticle)) {
			return;
		}

		newArticle = (IArticle) resource.getObject();
		// setSelection(selection);

		if (article != newArticle) {

			// FIXME same code is used in ReplyView

			try {
				article = newArticle;

				part.setDescription("From: " + article.getFrom() + "  To: "
						+ article.getXRef());
				part.setLabel(DecoderUtil.decodeEncodedWords(article
						.getSubject()));

				article.setRead(true);
				article.setThreadAttributes(ServerStoreFactory.instance()
						.getServerStoreFacade().getAllFollowUps(article));
				ServerStoreFactory.instance().getServerStoreFacade()
						.updateArticle(article);

				StringBuffer buffer = new StringBuffer();
				String[] body = (String[]) ServerStoreFactory.instance()
						.getServerStoreFacade().getArticleBody(article);
				for (String line : body) {
					buffer.append(line + SALVO.CRLF);
				}

				MimeArticleContentHandler handler = new MimeArticleContentHandler(
						article);
				MimeStreamParser parser = new MimeStreamParser();
				parser.setContentHandler(handler);

				parser.parse(new ByteArrayInputStream(buffer.toString()
						.getBytes()));

				if (!parent.isDisposed())
					for (Control child : parent.getChildren()) {
						child.dispose();
					}

				ArticleWidgetBuilder.build(parent, article, handler);
				// composite.setLayout(gridLayout);
				parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
						true));
				parent.layout(true);

				// FIXME tight coupling is a no go. Need to set the read
				// state
				// some other way
				if (part instanceof ArticlePanel) {
					((ArticlePanel) part).updateArticle(resource);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		part.setOnTop(true);

	}
}
