package io.pivotal.ce.gemfire.fastfootshoes.repositories;

import io.pivotal.ce.gemfire.fastfootshoes.model.Alert;

import org.springframework.data.gemfire.repository.GemfireRepository;


public interface AlertRepository extends GemfireRepository<Alert, String> {


}
