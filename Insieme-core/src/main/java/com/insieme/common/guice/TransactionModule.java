package com.insieme.common.guice;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.insieme.common.database.transactions.Transaction;
import com.insieme.common.database.transactions.TransactionInterceptor;

/**
 * Guice bindings for AOP transactions.
 */
public class TransactionModule extends AbstractModule {

	@Override
	protected void configure() {
		TransactionInterceptor intercepter = new TransactionInterceptor();
		requestInjection(intercepter);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Transaction.class), intercepter);
	}

}
