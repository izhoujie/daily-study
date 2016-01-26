package com.kongming.forguice;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service {

	void execute();
}
