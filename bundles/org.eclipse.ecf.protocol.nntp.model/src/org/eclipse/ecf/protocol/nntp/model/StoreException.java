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
package org.eclipse.ecf.protocol.nntp.model;

public class StoreException extends NNTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6039782635307181320L;

	public StoreException(String arg0) {
		super(arg0);
	}

	public StoreException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
