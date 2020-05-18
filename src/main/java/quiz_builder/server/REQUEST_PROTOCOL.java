/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package quiz_builder.server;

public enum REQUEST_PROTOCOL {
	GAMES_LIST,
	NEW_GAME,
	NEXT_QUESTION,
	QUESTION,
	END_GAME,
	END_LEVEL,
	MULTI_GAMES_LIST,
	CREATE_MULTI_GAME,
	START_MULTI_GAME,
	JOIN_MULTI_GAME,
	WAIT,
	ERROR
}
