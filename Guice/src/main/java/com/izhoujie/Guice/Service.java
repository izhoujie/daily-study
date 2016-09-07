package com.izhoujie.Guice;

import com.google.inject.ImplementedBy;
import com.izhoujie.Guice.imp.ServiceImpl;

@ImplementedBy(ServiceImpl.class)
public interface Service {

	void execute();
}
