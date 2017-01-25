package com.store.catalog.tech.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class SFFilter extends Filter<ILoggingEvent> {

	public static final String SYSTEM_FAILURE_ERROR = "SYSTEM_FAILURE_ERROR";
	
	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getFormattedMessage().contains(SYSTEM_FAILURE_ERROR)) {
		      return FilterReply.ACCEPT;
		    } else {
		      return FilterReply.DENY;
		    }

	}


}
